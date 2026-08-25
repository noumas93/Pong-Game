import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel {

	private final Board board;
	private final boolean isHard;
	private final Player player;
	private final Ball ball;
	private final Enemy enemy;
	private int localHs;
	private final Highscore h;
	private volatile boolean paused;
	private volatile boolean running;

	private JPanel pauseOverlay;
	private JButton btnResume;
	private JButton btnMainMenu;
	private JButton btnExitGame;
	private ArrayList<JButton> pauseBtnList;
	private int pauseOption;

	public GamePanel(Board board, boolean isHard) {
		this.board = board;

		this.localHs = 0;
		this.setBackground(Color.BLACK);
		this.isHard = isHard;
		this.setLayout(new BorderLayout());

		player = new Player();
		ball = new Ball();
		enemy = new Enemy();

		h = new Highscore();

		this.setFocusable(true);
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "togglePause");
		this.getActionMap().put("togglePause", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (paused) {
					resumeGame();
				} else {
					pauseOption = 0;
					paused = true;
					showPauseOverlay();
				}
			}
		});

		buildPauseOverlay();
	}

	// skapa pausmenyns knappar (Resume / Main Menu / Exit), navigerbara med tangentbord och mus
	private void buildPauseOverlay() {
		JLabel title = new JLabel("PAUSED");
		title.setForeground(Color.white);
		title.setFont(new Font("Arial", Font.BOLD, 36));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		KeyListener pauseHandler = new PauseHandler();

		btnResume = new MenuButton("Resume");
		btnResume.addKeyListener(pauseHandler);
		btnResume.addActionListener(e -> selectPauseOption(0));
		btnResume.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnMainMenu = new MenuButton("Main Menu");
		btnMainMenu.addKeyListener(pauseHandler);
		btnMainMenu.addActionListener(e -> selectPauseOption(1));
		btnMainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnExitGame = new MenuButton("Exit");
		btnExitGame.addKeyListener(pauseHandler);
		btnExitGame.addActionListener(e -> selectPauseOption(2));
		btnExitGame.setAlignmentX(Component.CENTER_ALIGNMENT);

		// vertical stack of title + buttons, centered as a block
		JPanel content = new JPanel();
		content.setOpaque(false);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.add(title);
		content.add(Box.createVerticalStrut(30));
		content.add(btnResume);
		content.add(Box.createVerticalStrut(10));
		content.add(btnMainMenu);
		content.add(Box.createVerticalStrut(10));
		content.add(btnExitGame);

		// GridBagLayout with a single, unconstrained child centers it both ways
		pauseOverlay = new JPanel(new GridBagLayout());
		pauseOverlay.setBackground(new Color(20, 20, 20));
		pauseOverlay.add(content);

		pauseBtnList = new ArrayList<>();
		pauseBtnList.add(btnResume);
		pauseBtnList.add(btnMainMenu);
		pauseBtnList.add(btnExitGame);
	}

	private void showPauseOverlay() {
		this.add(pauseOverlay);
		this.revalidate();
		this.repaint();
		btnResume.requestFocusInWindow();
	}

	private void hidePauseOverlay() {
		this.remove(pauseOverlay);
		this.revalidate();
		this.repaint();
		this.requestFocusInWindow();
	}

	private void resumeGame() {
		paused = false;
		hidePauseOverlay();
	}

	// aktivera det valda alternativet i pausmenyn, oavsett om det kom fran tangentbord eller mus
	private void selectPauseOption(int index) {
		pauseOption = index;

		if (index == 0) {
			resumeGame();
		} else if (index == 1) {
			hidePauseOverlay();
			paused = false;
			running = false;
		} else {
			System.exit(1);
		}
	}

	private class PauseHandler implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_DOWN && pauseOption < 2) {
				pauseOption++;
			} else if (e.getKeyCode() == KeyEvent.VK_UP && pauseOption > 0) {
				pauseOption--;
			}

			pauseBtnList.get(pauseOption).requestFocus();

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				selectPauseOption(pauseOption);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	public void Run() {
		running = true;
		Thread gameThread = new Thread(() -> {
			do {
				// update (game state), unless paused
				if (!paused) {
					running = Update();
				}

				// request repaint (render on EDT)
				repaint();

				// wait for next frame
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					running = false;
				}
			} while (running);

			// back to menu when round ends, or player chose Main Menu from the pause overlay
			SwingUtilities.invokeLater(() -> {
				MenuPanel mp = new MenuPanel(board);
				mp.setPreferredSize(new Dimension(GameConfig.FIELD_WIDTH, GameConfig.FIELD_HEIGHT));
				mp.setBorder(new LineBorder(Color.black));
				mp.setBackground(Color.gray);
				board.Switch(mp);
				mp.Init();
			});
		});
		gameThread.setDaemon(true);
		gameThread.start();
	}


	public boolean Update() {
		player.setY((int) (MouseInfo.getPointerInfo().getLocation().getY()
				- this.getLocationOnScreen().getY() - GameConfig.PADDLE_HEIGHT / 2));
		int action = ball.update(player.gety(), enemy.gety(), isHard);
		enemy.update(ball.gety());

		if (action == 1)
			return false;
		if (action == 2) {
			localHs++;

			if (localHs > h.getScore()) {
				h.setHs(localHs);
			}

			if (localHs > 3) { // power up: speed rises with score, capped, direction preserved
				int speed = Math.min(localHs + 2, Ball.MAX_SPEED);
				ball.setVinx(Integer.signum(ball.getVinx()) * speed);
				ball.setViny(Integer.signum(ball.getViny()) * speed);
			}

			ball.restart();
			return true;
		} else
			return true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.black);
		g.fillRect(0, 0, GameConfig.FIELD_WIDTH, GameConfig.FIELD_HEIGHT);
		g.setColor(Color.yellow);
		g.fillRect(375, 0, 3, GameConfig.FIELD_HEIGHT);
		g.setColor(Color.GREEN);
		g.drawString("Local highscore: " + localHs, 50, 15);

		player.draw(g);
		ball.draw(g);
		enemy.draw(g);

		if (isHard) {
			// keep enemy speed in sync with mode
			enemy.hardspeed(isHard);
			g.setColor(Color.white);
			g.fillRect(GameConfig.OBSTACLE_X, GameConfig.OBSTACLE_TOP_Y, GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE);
			g.fillRect(GameConfig.OBSTACLE_X, GameConfig.OBSTACLE_BOTTOM_Y, GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE);
		}
	}


}
