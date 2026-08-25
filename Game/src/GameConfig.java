/**
 * Delade matt for spelplanen, sa att Ball, Player, Enemy, GamePanel, Board och MenuPanel
 * alla utgar fran samma varden istallet for att upprepa samma siffror i flera filer.
 */
public final class GameConfig {

	private GameConfig() {}

	public static final int FIELD_WIDTH = 800;
	public static final int FIELD_HEIGHT = 600;

	public static final int PADDLE_WIDTH = 20;
	public static final int PADDLE_HEIGHT = 100;

	public static final int PLAYER_X = 5;
	public static final int ENEMY_X = 760;

	public static final int BALL_SIZE = 20;
	public static final int BALL_START_SPEED = 6;

	public static final int ENEMY_SPEED_NORMAL = 4;
	public static final int ENEMY_SPEED_HARD = 5;

	// hard-mode-hindren; delas mellan Ball (kollision) och GamePanel (ritning)
	public static final int OBSTACLE_SIZE = 50;
	public static final int OBSTACLE_X = 350;
	public static final int OBSTACLE_TOP_Y = 100;
	public static final int OBSTACLE_BOTTOM_Y = 400;
}
