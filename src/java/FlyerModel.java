import java.util.ArrayList;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;



public class FlyerModel extends EnemyModel{
	public static final int SHOOT_BULLET_MIN = 0;
	public static final int SHOOT_BULLET_MAX = 19;
	public static final int SHOOT_BULLET_CONDITION = SHOOT_BULLET_MIN;

	public static final int DROP_BOMB_FRAGMENT_MIN = 0;
	public static final int DROP_BOMB_FRAGMENT_MAX = 4;
	public static final int DROP_BOMB_CONDITION = DROP_BOMB_FRAGMENT_MIN + 1;

	public static final int DROP_WEAPON_POD_MIN = 0;
	public static final int DROP_WEAPON_POD_MAX = 4;
	public static final int DROP_WEAPON_POD_CONDITION = DROP_WEAPON_POD_MIN;

	private int yPosBaseline;
	private double oscillator;
	private final int AMPLITUDE = 5;

	// Creates Flyer model at coordinate (x,y)
	FlyerModel(int x, int y) {
		super(x, y);
		yPosBaseline = y;
		oscillator = 0;
		enemyFrames = new BufferedImage[1];
		try {
			enemyFrames[0] = ImageIO.read(new File("assets/flyer.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int update(float dt) {
		xPos -= .1 * dt;              // moves enemy left
		oscillator += 20;
		if (oscillator > 359)
			oscillator = 0;

		// Computes y coordinate -- Flyer moves in sinusoidal pattern(basically oscillating up/down while moving forward)
		this.yPos = (int) (this.yPosBaseline + (Math.sin(Math.toRadians(this.oscillator)) * this.AMPLITUDE));

		return 0;
	}

	// Shoots bullet 5% of time
	public boolean shootBullet() {
		if (Utils.randInt(SHOOT_BULLET_MIN, SHOOT_BULLET_MAX) == SHOOT_BULLET_CONDITION)
			return true;
		else
			return false;
	}

	// Drop weapon pod 20% and fragment 40% of time
	public Pickup[] getDrop(){
		Pickup[] drops = {null, null};

		if (Utils.randInt(DROP_BOMB_FRAGMENT_MIN, DROP_BOMB_FRAGMENT_MAX) <= DROP_BOMB_CONDITION)
			drops[0] = new Pickup(this.xPos,this.yPos,"fragment","assets/fragmentImage.png");


		if (Utils.randInt(DROP_WEAPON_POD_MIN, DROP_WEAPON_POD_MAX) <= DROP_WEAPON_POD_CONDITION)
			drops[1] = new WeaponPickup(this.xPos,this.yPos);

		return drops;
	}

}
