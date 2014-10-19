import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class WeaponPickup extends Pickup{

	private int frame;
	private BufferedImage[] weaponFrames;
	private MillisecTimer switchTimer;
	private String[] types;
	
	WeaponPickup(int x, int y){
		super(x, y, "weapon");
		
		this.frame = Utils.randInt(0,2);
		this.weaponFrames = new BufferedImage[3];
		this.switchTimer = new MillisecTimer();
		
		try{
	    this.weaponFrames[0] = ImageIO.read(new File("assets/ringPickupImage.png"));
		this.weaponFrames[1] = ImageIO.read(new File("assets/misslePickupImage.png"));
		this.weaponFrames[2] = ImageIO.read(new File("assets/laserPickupImage.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		this.types = new String[3];
		this.types[0] = "ring";
		this.types[1] = "missle";
		this.types[2] = "laser";
		
		this.pickupImage = this.weaponFrames[this.frame % 3];
		this.type = this.types[this.frame %3];
	}
	
	public int update(float dt, int dx){
		super.update(dt,dx);
		if(this.switchTimer.getDt() > 1000){
			this.frame += 1;
			this.pickupImage = this.weaponFrames[this.frame % 3];
			this.type = this.types[this.frame %3];
			this.switchTimer.reset();
		}
		return 0;
		
	}


}