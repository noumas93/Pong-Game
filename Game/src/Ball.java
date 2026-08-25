import java.awt.Color;
import java.awt.Graphics;

/**
 * @author mohma171
 * Den här klassen representerar bollen i spelet, hanterar hastighet samt kollisionen.
 */
public class Ball {

	public static final int MAX_SPEED = 14;

	private int x;
	private int y;
	private int vinx;
	private int viny;
	private final int size;

	public int gety() {
		return this.y;
	}

	public Ball() {

		x = 50;
		y = 50;
		vinx = GameConfig.BALL_START_SPEED;
		viny = GameConfig.BALL_START_SPEED;
		size = GameConfig.BALL_SIZE;
	}

	public int getVinx() {
		return vinx;
	}

	public void setVinx(int vinx) {
		this.vinx = vinx;
	}

	public int getViny() {
		return viny;
	}

	public void setViny(int viny) {
		this.viny = viny;
	}

	// kollision
	public int update(int playerY, int enemyY, boolean isHard) {
		this.y += viny;
		this.x += vinx;

		// bollen går neråt
		if (this.y + size > GameConfig.FIELD_HEIGHT) {
			this.y = GameConfig.FIELD_HEIGHT - size;
			this.viny = -viny;
		}
		// bollen går upåt
		else if (this.y < 0) {
			this.y = 0;
			this.viny = -viny;
		}

		// detektera spelarn
		if (this.x < 35) {
			if (this.y < playerY + GameConfig.PADDLE_HEIGHT && this.y + size > playerY) {
				this.x = 35;
				this.vinx = -vinx;
			} else {
				return 1;
			}
		}

		// detektera fienden
		if (this.x + size > 770) {
			if (this.y < enemyY + GameConfig.PADDLE_HEIGHT && this.y + size > enemyY) {
				this.x = 750;
				this.vinx = -vinx;
			} else {
				return 2;
			}
		}
		if (isHard) {
			int ymid = this.y + 10;
			int xmid = this.x + 10;
			int yboxmid1 = GameConfig.OBSTACLE_TOP_Y + GameConfig.OBSTACLE_SIZE / 2;
			int xboxmid = GameConfig.OBSTACLE_X + GameConfig.OBSTACLE_SIZE / 2;
			int yboxmid2 = GameConfig.OBSTACLE_BOTTOM_Y + GameConfig.OBSTACLE_SIZE / 2;
			if (intersect(this.x, this.y, size, size, GameConfig.OBSTACLE_X, GameConfig.OBSTACLE_TOP_Y,
					GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE)) {
				if ((Math.abs(xmid - xboxmid) < Math.abs(ymid - yboxmid1)) && (
						(ymid < yboxmid1 && viny > 0) || (ymid > yboxmid1 && viny < 0)
						))
					viny = -viny;
				else if ((Math.abs(xmid - xboxmid) > Math.abs(ymid - yboxmid1)) && (
						(xmid < xboxmid && vinx > 0) || (xmid > xboxmid && vinx < 0)
						))
					vinx = -vinx;
				else {
					int normaldiffx = Math.abs(xmid - xboxmid);
					int normaldiffy = Math.abs(ymid - yboxmid1);
					
					int nextdiffx = Math.abs((xmid + vinx) - xboxmid);
					int nextdiffy = Math.abs((ymid + viny) - yboxmid1);
					
					if(nextdiffx < normaldiffx && nextdiffy < normaldiffy)
					{
						vinx = -vinx;
						viny = -viny;
					}
				}
			}
			if (intersect(this.x, this.y, size, size, GameConfig.OBSTACLE_X, GameConfig.OBSTACLE_BOTTOM_Y,
					GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE)) {
				if ((Math.abs(xmid - xboxmid) < Math.abs(ymid - yboxmid2)) && (
						(ymid < yboxmid2 && viny > 0) || (ymid > yboxmid2 && viny < 0)
						))
					viny = -viny;
				else if ((Math.abs(xmid - xboxmid) > Math.abs(ymid - yboxmid2)) && (
						(xmid < xboxmid && vinx > 0) || (xmid > xboxmid && vinx < 0)
						))
					vinx = -vinx;
				else {
					int normaldiffx = Math.abs(xmid - xboxmid);
					int normaldiffy = Math.abs(ymid - yboxmid2);
					
					int nextdiffx = Math.abs((xmid + vinx) - xboxmid);
					int nextdiffy = Math.abs((ymid + viny) - yboxmid2);
					
					if(nextdiffx < normaldiffx && nextdiffy < normaldiffy)
					{
						vinx = -vinx;
						viny = -viny;
					}
				}
			}
		}
		return 0;
	}

	public void restart() {
		this.x = 50;
		this.y = 50;
	}

	boolean intersect(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
		if (x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2)
			return true;
		return false;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, size, size);
	}
}