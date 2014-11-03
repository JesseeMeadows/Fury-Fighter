import java.util.ArrayList;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;



public class FlyerModel extends EnemyModel{
	
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
	
	// Shoots bullet 5% of time - Note the off by one error in randomNum and 20/1000 = .02 != 5%
	public boolean shootBullet() {
		int randomNum = Utils.randInt(0, 1000);
		if (randomNum < 20)
			return true;
		else
			return false;
	}
	// Drop weapon pod 20% and fragment 40% of time
	public Pickup getDrop(){
		int r = Utils.randInt(0,5);
		if(r <= 1)
			return new Pickup(this.xPos,this.yPos,"fragment","assets/fragmentImage.png");
		else if(r ==3){
			return new WeaponPickup(this.xPos,this.yPos);
		}
		else{
			return null;
		}
	}
	
}
