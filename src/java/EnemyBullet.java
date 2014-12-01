import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class EnemyBullet extends Bullet{
	private Vector2 vectorDirection;

	EnemyBullet(int x, int y, Vector2 vectorDirection){
		super(x,y,0);

		this.vectorDirection = vectorDirection.normalizeCopy();
		this.velocity = 0.1f;

    }

    public int update(float dt){
		this.xPos += (this.velocity * this.vectorDirection.getX())*dt;
		this.yPos += (this.velocity* this.vectorDirection.getY())*dt;
		return 0;
    }

    public int getXPos(){
		return this.xPos;
    }

    public int getYPos(){
		return this.yPos;
    }
}
