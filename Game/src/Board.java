import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;

public final class Board extends JFrame {

	public Board() {}

	public void Init() {
		MenuPanel mp = new MenuPanel(this);
		mp.setPreferredSize(new Dimension(GameConfig.FIELD_WIDTH, GameConfig.FIELD_HEIGHT));
		mp.setBorder(new LineBorder(Color.black));
		mp.setBackground(Color.gray);
		mp.Init();

		this.getContentPane().add(mp);
		this.pack();
	}

	public void Switch(MenuPanel mpp) {
		this.getContentPane().removeAll();
		this.getContentPane().add(mpp);
		this.pack();
	}

	public void Switch(GamePanel gpp) {
		this.getContentPane().removeAll();
		this.getContentPane().add(gpp);
		gpp.setBackground(Color.BLACK);
		this.pack();
		gpp.Run();
	}

}
