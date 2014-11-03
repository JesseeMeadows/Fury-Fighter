import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class RingBullet extends Bullet{
    RingBullet(int x, int y, int direction){
	super(x,y,direction);

	this.velocity = 0.7f;
	this.power = 1;
	
	try{
	    this.bulletImage = ImageIO.read(new File("assets/ringBulletImage.png"));
	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }
}
