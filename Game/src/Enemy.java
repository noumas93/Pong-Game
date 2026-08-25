import java.awt.Color;
import java.awt.Graphics;

public class Enemy {

	private int y;
	private final int x;
	private int enemySpeed;
	private final int weight;
	private final int height;

	public Enemy() {
		y = 0;
		x = GameConfig.ENEMY_X;
		enemySpeed = GameConfig.ENEMY_SPEED_NORMAL;
		height = GameConfig.PADDLE_HEIGHT;
		weight = GameConfig.PADDLE_WIDTH;
	}

	public void hardspeed(boolean hard) {

		if (hard) {
			enemySpeed = GameConfig.ENEMY_SPEED_HARD;
		}

	}

	public int getEnemySpeed() {
		return enemySpeed;
	}

	public void setEnemySpeed(int enemySpeed) {
		this.enemySpeed = enemySpeed;
	}

	public int gety() {
		return this.y;
	}

	public void update(int ballY) {
		int ballMid = ballY + 10;
		int enemyMid = this.y + 50;

		if (ballMid < enemyMid)
			this.y -= enemySpeed;
		else if (ballMid > enemyMid)
			this.y += enemySpeed;

		if (this.y < 0)
			this.y = 0;
		else if (this.y > GameConfig.FIELD_HEIGHT - height)
			this.y = GameConfig.FIELD_HEIGHT - height;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, weight, height);
	}

	public int getWeight() {
		return weight;
	}

	public int getHeight() {
		return height;
	}
}