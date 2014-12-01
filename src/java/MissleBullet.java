import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MissleBullet extends Bullet{

    MissleBullet(int x, int y, int direction){
	super(x,y,direction);
	
	this.velocity = 0.3f;
	this.power = 2;
	
	try{
	    if(direction == 0)
		this.bulletImage = ImageIO.read(new File("assets/missileBulletImage_up.png"));
	    else if(direction == 2)
		 this.bulletImage = ImageIO.read(new File("assets/missileBulletImage_right.png"));
	    else if(direction == 4)
		this.bulletImage = ImageIO.read(new File("assets/missileBulletImage_down.png"));
	    else if(direction == 6)
		this.bulletImage = ImageIO.read(new File("assets/missileBulletImage_left.png"));    
	    else
		throw new IllegalArgumentException();
	}
	catch(IOException e){
	    e.printStackTrace();
	}

	
	
	
    }
}