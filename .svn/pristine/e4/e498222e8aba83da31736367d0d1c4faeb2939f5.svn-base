import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.awt.*;

public class Pickup{
	
	protected String type;
	
	protected int xPos;
	protected int yPos;
	protected int width;
	protected int height;
	
	BufferedImage pickupImage;
	
	Pickup(int x,int y,String type){
		this.xPos = x;
		this.yPos = y;
		this.type = type;
		this.pickupImage = null;
		this.width = 0;
		this.height = 0;
	}
	
	Pickup(int x, int y, String type, String filename){
		this(x,y,type);
		
		try{
			this.pickupImage = ImageIO.read(new File(filename));
			this.width = this.pickupImage.getWidth();
			this.height = this.pickupImage.getHeight();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void render(Graphics2D g2, float rw, float rh){
		g2.drawImage(this.pickupImage,this.xPos,this.yPos,(int)rw*this.pickupImage.getWidth(),(int)rh*this.pickupImage.getHeight(),null);
	}
	
	public int update(float dt, int dx){
		this.xPos -= dx;
		return 0;
	}
	
	public int getXPos(){
		return this.xPos;
	}
	public int getYPos(){
		return this.yPos;
	}
	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}
	public String getType(){
		return this.type;
	}
}
