import java.util.HashMap;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public abstract class EnemyModel extends Model{

	// upper-left corner coordinate of enemies location box -- in relation the Map's level
	protected int xPos;
	protected int yPos;

	// enemies size(boxed) -- used in conjunction with x and y position to get enmemies bounding box
	protected int width;
	protected int height;

	protected boolean dead;

	protected int curFrame;
	protected BufferedImage[] enemyFrames;

	EnemyModel(){
		this(0,0,0);
	}

	EnemyModel(int x, int y){
		this(x,y,0);
	}

	EnemyModel(int x, int y, int frame){
		this.xPos = x;
		this.yPos = y;
		this.curFrame = frame;
		this.dead = false;
	}

	// returns hitbox of enemy
	public Rectangle getBoundingBox(){
		return new Rectangle(this.xPos,this.yPos,this.enemyFrames[0].getWidth(), this.enemyFrames[0].getHeight());
	}

	public int update(float dt){
		return 0;
	}

	public BufferedImage getEnemyImage(){
		return this.enemyFrames[this.curFrame];
    }
	public void setXPos(int x){
		this.xPos = x;
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
    public boolean getDead(){
		return this.dead;
	}


	public boolean shootBullet(){
		return false;
	}
	public boolean hit(int value){
		this.dead = true;
		return this.dead;
	}
	public void kill(){
		this.dead = true;
	}
	public Pickup[] getDrop(){
		Pickup[] drops = {null, null};
		return drops;
	}

	public HashMap<String,Model> getVisibleModels(){
		return new HashMap<String,Model>();
    }
}
