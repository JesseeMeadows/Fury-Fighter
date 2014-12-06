import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class WeaponPickup extends Pickup{

	private int frame;
	private BufferedImage[] weaponFrames;
	private MillisecTimer switchTimer;               // Used for rotation of 3 weapon pickups: rotates every second
	private String[] types;
	private int width;
	private int height;

	WeaponPickup(int x, int y){
		super(x, y, "weapon");

		frame = Utils.randInt(0,2);              // ensures initial
		weaponFrames = new BufferedImage[3];     // each weapon's pickup image(M/R/S)
		switchTimer = new MillisecTimer();		 // Rotation timer
		

		try {
			weaponFrames[0] = ImageIO.read(new File("assets/ringPickupImage.png"));
			weaponFrames[1] = ImageIO.read(new File("assets/missilePickupImage.png"));
			weaponFrames[2] = ImageIO.read(new File("assets/laserPickupImage.png"));
		}
		catch (IOException e){
			e.printStackTrace();
		}
		width = weaponFrames[0].getWidth();
		height = weaponFrames[0].getHeight();

		// 3 weapon types - indicator for pickup is String
		types = new String[3];
		types[0] = "ring";
		types[1] = "missile";
		types[2] = "laser";

		this.pickupImage = this.weaponFrames[this.frame % 3];
		this.type = this.types[this.frame % 3];
	}

	// update position of pickup and rotates to next weapon in rotation every second
	public int update(float dt, int dx){
		super.update(dt,dx);
		if(switchTimer.getDt() > 1000){
			frame += 1;
			pickupImage = this.weaponFrames[this.frame % 3];
			type = this.types[this.frame % 3];
			switchTimer.reset();
		}
		return 0;

	}
	
	public Rectangle getBoundingBox() {
		return new Rectangle(this.xPos, this.yPos, width, height);
	}


}
