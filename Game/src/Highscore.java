import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author mohma171 Klassen Highscore sparar scoret i en extern fil och läser
 *         sedan in filen i GamePanel och skickar nuvarande highscore till
 *         MenuPanel.
 */
public class Highscore {
	private int hs;
	private final String path;
	private int Score;


	public Highscore() {
		// Use a portable path (Windows/Linux) and create the file if missing.
		String home = System.getProperty("user.home");
		String fileName = "highscore.txt";
		this.path = home + File.separator + fileName;
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
				// initialize with 0
				try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
					dos.writeInt(0);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public void setScore(int score) {
		Score = score;
	}

	private void saveScore(int highscore) {

		try {
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeInt(highscore);
			dos.close();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public int getScore() {
		File file = new File(path);
		try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
			Score = dis.readInt();
		} catch (IOException e) {
			// If reading fails, treat as 0.
			Score = 0;
		}
		return Score;
	}


	public void setHs(int localHs) {
		hs = localHs;
		saveScore(hs);

	}
}
