import java.awt.Color;
import java.awt.Graphics;

public class Player {

	private int y;
	private final int x;
	private final int weight;
	private final int height;

	public Player() {
		y = 0;
		x = GameConfig.PLAYER_X;
		height = GameConfig.PADDLE_HEIGHT;
		weight = GameConfig.PADDLE_WIDTH;
	}

	public int gety() {
		return this.y;
	}

	public void setY(int y) {
		if (y < 0)
			y = 0;
		else if (y > GameConfig.FIELD_HEIGHT - height)
			y = GameConfig.FIELD_HEIGHT - height;
		this.y = y;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, weight, height);
	}
}
