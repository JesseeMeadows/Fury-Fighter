import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class LaserBullet extends Bullet{

    LaserBullet(int x, int y, int direction){
	super(x,y,direction);
	
	this.velocity = 0.5f;
	this.power = 3;
	
	try{
	    if(direction == 0 || direction == 4)
		this.bulletImage = ImageIO.read(new File("assets/laserBulletImage_vertical.png"));
	    else if(direction == 2 || direction == 6)
		 this.bulletImage = ImageIO.read(new File("assets/laserBulletImage_horizontal.png"));
	    else if(direction == 7 || direction == 3)
		this.bulletImage = ImageIO.read(new File("assets/laserBulletImage_major.png"));
	    else if(direction == 1 || direction == 5)
		this.bulletImage = ImageIO.read(new File("assets/laserBulletImage_minor.png"));    
	    else
		throw new IllegalArgumentException();
	}
	catch(IOException e){
	    e.printStackTrace();
	}

	
	
	
    }
}