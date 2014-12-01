import java.util.Random;
import java.awt.Rectangle;

public class Utils {
	private static final Random rand = new Random();

	// Returns uniformly random between the the number range(ends are inclusive)
	public static int randInt(int min, int max) {
		return rand.nextInt((max - min) + 1) + min;
	}

	// Determines if 2 hitbox's collide
	public static boolean boxCollision(Rectangle r1, Rectangle r2) {
		if (r1.x + r1.width < r2.x || r1.x > r2.x + r2.width)
			return false;
		if (r1.y + r1.height < r2.y || r1.y > r2.y + r2.height)
			return false;

		return true;
	}

}
