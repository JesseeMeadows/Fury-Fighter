import java.util.ArrayList;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;



public class FlyerModel extends EnemyModel{
	
	private int yPosBaseline;
	private double oscillator;
	private final int AMPLITUDE = 5;
	
	FlyerModel(int x, int y){
		super(x,y);
		this.yPosBaseline = y;
		this.oscillator = 0;
		this.enemyFrames = new BufferedImage[1];
		try{
			this.enemyFrames[0] = ImageIO.read(new File("assets/flyer.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public int update(float dt){
		this.xPos -= .1 * dt;
		this.oscillator += 20;
		if(this.oscillator>359)
			this.oscillator = 0;
			
		this.yPos = (int)(this.yPosBaseline + (Math.sin(Math.toRadians(this.oscillator)) * this.AMPLITUDE));
		
		return 0;
	}
	
	public boolean shootBullet(){
		int randomNum = Utils.randInt(0,1000);
		if(randomNum<20)
			return true;
		else
			return false;
	}
	
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
