import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.awt.*;

public class Pickup{
	
	protected String type;
	
	protected int xPos;    // x-coordinate of pickup image
	protected int yPos;	   // y-coordinate of pickup image
	protected int width;   // width of pickup hit-box
	protected int height;  // height of pickup hit-box
	
	BufferedImage pickupImage;
	
	Pickup(int x,int y,String type){
		xPos = x;
		yPos = y;
		this.type = type;
		pickupImage = null;
		width = 0;
		height = 0;
	}
	
	Pickup(int x, int y, String type, String filename){
		this(x,y,type);
		
		try{
			pickupImage = ImageIO.read(new File(filename));
			width = pickupImage.getWidth();
			height = pickupImage.getHeight();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void render(Graphics2D g2, float rw, float rh){
		g2.drawImage(pickupImage, xPos, yPos,(int)rw * pickupImage.getWidth(),(int)rh * pickupImage.getHeight(),null);
	}
	
	// Items are stationary, so they move linearly with the distanced scrolled each frame(scrollDelta)	
	public int update(float dt, int dx){
		this.xPos -= dx;
		return 0;
	}
	
	public int getXPos(){
		return xPos;
	}
	public int getYPos(){
		return yPos;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public String getType(){
		return type;
	}
}
