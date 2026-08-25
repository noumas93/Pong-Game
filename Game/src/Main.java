import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setTitle("Pong Game");
		board.setResizable(false);
		board.Init();
		board.setVisible(true);
	}

}
