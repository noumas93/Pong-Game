import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class MenuPanel extends JPanel {

	private JButton btnEasy;
	private JButton btnHard;
	private JButton btnExit;
	private final Highscore h;
	private final Board board;
	private ArrayList<JButton> btnList;
	private final HandlerClass handler;
	private int curOption;
	private boolean ishard;

	public MenuPanel(Board board) {
		curOption = 0;
		this.board = board;
		h = new Highscore();
		handler = new HandlerClass();
	}

	public void Init() {
		this.setLayout(new BorderLayout());

		JLabel lhs = new JLabel("Current Highscore: " + h.getScore());
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.setOpaque(false);
		topPanel.add(lhs);
		this.add(topPanel, BorderLayout.NORTH);

		// Menu Buttons
		btnEasy = new MenuButton("Easy");
		btnEasy.addKeyListener(handler);
		btnHard = new MenuButton("Hard");
		btnHard.addKeyListener(handler);
		btnExit = new MenuButton("Exit");
		btnExit.addKeyListener(handler);

		// stacked, all the same size (GridLayout sizes every cell to the largest button)
		JPanel buttonStack = new JPanel(new GridLayout(3, 1, 0, 10));
		buttonStack.setOpaque(false);
		buttonStack.add(btnEasy);
		buttonStack.add(btnHard);
		buttonStack.add(btnExit);

		// FlowLayout centers the stack horizontally; SOUTH anchors it near the bottom
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.setOpaque(false);
		bottomPanel.add(buttonStack);
		this.add(bottomPanel, BorderLayout.SOUTH);

		btnList = new ArrayList<>();
		btnList.add(btnEasy);
		btnList.add(btnHard);
		btnList.add(btnExit);

		btnEasy.addActionListener(e -> selectOption(0));
		btnHard.addActionListener(e -> selectOption(1));
		btnExit.addActionListener(e -> selectOption(2));

		btnEasy.requestFocusInWindow();
	}

	private void selectOption(int index) {
		curOption = index;

		if (index == 2) {
			System.exit(1);
			return;
		}

		ishard = (index == 1);

		GamePanel gp = new GamePanel(board, ishard);
		gp.setPreferredSize(new Dimension(GameConfig.FIELD_WIDTH, GameConfig.FIELD_HEIGHT));
		gp.setBorder(new LineBorder(Color.green));
		gp.setBackground(Color.black);
		board.Switch(gp);
	}

	private class HandlerClass implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_DOWN && curOption < 2) {

				curOption++;
				System.out.println("DOWN");

			} else if (e.getKeyCode() == KeyEvent.VK_UP && curOption > 0) {
				curOption--;
				System.out.println("UP");
			}

			btnList.get(curOption).requestFocus();

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				selectOption(curOption);
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

	}

}
