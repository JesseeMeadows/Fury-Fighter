import java.util.ArrayList;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Rectangle;
import java.io.IOException;
import java.awt.Point; // Used to represent the arm piece


// BossModel created by Aaron Disibio for CSC 439 Software Testing and Maintenance
//  Fall 2014.  Instructor  was  Brian Sauer

/** BossModel. Handles the boss logic. However any logic that requires the players position is handled in level model */
public class BossModel extends EnemyModel{
	public static final int SHOOT_BULLET_MIN = 0;
	public static final int SHOOT_BULLET_MAX = 60;
	public static final int SHOOT_BULLET_CONDITION = 1;
	


	public BufferedImage armImg;
	public int speed =1; // Speed changes at low life.	
	public int xdir=1; // 1 or -1 depending on direction
	public int ydir=-1; // 1 or -1 depending on direction
	public int health =100; 
	public int curFrame=3; // The 3rd and 4th frames are the ones that face left.
	public double dir = Math.random()+.01;
	public boolean direction=false; // False is standard direction (player starts on the left of the boss). True means flipped to keep it facing the player. (ie this boolean tells if the direction is flipped)
	
	public double theta=Math.PI; // The current rotation for the arm!
	public boolean lowlife=false; // Boss gets harder when it is low on life
	
	
	// This arrayList holds the piece of the arm for the boss. 
	// Each piece consists simply of an x and y.  So I use the built in Java Point class instead of making a new class.
	// The arms actual logic is handled by the bossModel.
	// Each arm piece is the same picture which is stored under the variable armImg in bossModel
	public ArrayList<Point> armPieces = new ArrayList<Point>();
	

	private final int AMPLITUDE = 5;

	// Creates Boss model at coordinate (x,y)
	BossModel(int x, int y) {
		super(x, y);

		//add the arm pieces
		for (int i=0;i<5;i++){
			armPieces.add(new Point());
		}
		
		//yPosBaseline = y;
		//oscillator = 0;
		enemyFrames = new BufferedImage[4];

		try {
			armImg = ImageIO.read(new File("assets/arm.png"));
			enemyFrames[0] = ImageIO.read(new File("assets/boss.png"));
			enemyFrames[1] = ImageIO.read(new File("assets/boss2.png"));
			enemyFrames[2] = ImageIO.read(new File("assets/bossR.png"));
			enemyFrames[3] = ImageIO.read(new File("assets/boss2R.png"));
			width = enemyFrames[0].getWidth();
			height= enemyFrames[1].getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public boolean checkBullet(Bullet bullet){
	// The boss has three hit points but they move as the boss flips
		Rectangle b1;
		Rectangle b2;
		Rectangle b3;


		if (direction==false){
			b1=new Rectangle(this.xPos+6,this.yPos+13,this.xPos+16,this.yPos+24);
			b2=new Rectangle(this.xPos+3,this.yPos+41,this.xPos+14,this.yPos+51);
			b3=new Rectangle(this.xPos+6,this.yPos+68,this.xPos+16,this.yPos+80);
			
		}
		else {
			 b1=new Rectangle(this.xPos+80,this.yPos+13,this.xPos+88,this.yPos+24);
			 b2=new Rectangle(this.xPos+82,this.yPos+41,this.xPos+92,this.yPos+51);
			 b3=new Rectangle(this.xPos+80,this.yPos+68,this.xPos+88,this.yPos+80);
		}
		
		if (bullet.collidesWith(b1)||bullet.collidesWith(b2)||bullet.collidesWith(b3)){
			health -= bullet.power;
			return true;
		}
	return false;
	}

	public int update(float dt) {
	shootBullet();
		if (!lowlife&&health<=20){
			lowlife=true;
			speed=3;
			dir = Math.random()+.01;
			armPieces.add(new Point());
		}
		if (lowlife){
			theta-=.07;
			
			yPos -= .01*dir + .09*dir*speed*ydir *dt;              // moves boss y only
		}
		else{
			theta+=.035;
			xPos -= .01*dir + .09*dir*speed*xdir* dt;               // moves boss x
			yPos -= .01*dir + .09*dir*speed*ydir * dt;              // moves boss y
		}

		
		if (xPos<=128){
			xdir=-1;
		}else if (xPos > ViewController.SCREEN_WIDTH - width){
			xdir=1;
		}
		
		
		if (yPos<=33){
			ydir=-1;
		}
		else if (yPos > ViewController.SCREEN_HEIGHT - height - 100){
			ydir=1;
		}
		
		if (curFrame==0){
			curFrame=1;
		}else if (curFrame==1){
			curFrame=0;
		}else if (curFrame==2){
			curFrame=3;
		}else if (curFrame==3){
			curFrame=2;
		}
		
		// AWESOME math to calculate the arm!
		// Special thanks to polar coordinates for making this easy and modular!  - Aaron Disibio
		for (int i=0;i<armPieces.size();i++){
			// How this works is I pretend the center of the boss is the origin, then each armPiece has 1 block more R. Theta is set in the update.
			// The new polar coordinates (r,theta) are converted to x and y and then I add the center of the boss.
			// Remember that polar coordinates consist of an r, which is the distance of the spot we want from the origin and a theta
			// The theta is the rotation relative to the origin (in this case the center of the boss).
			// Theta is in radians to degrees. 
			// (You could mod theta by 2PI but sin and cos account for that anyway to manually doing it is unnecessary.
			// This give the final x and y for the particular piece of the arm. The previous x and y has no relevance and are lost. 
			armPieces.get(i).x= (int)( Math.cos(theta)*(i)*32)+xPos+32;
			armPieces.get(i).y= (int)( Math.sin(theta)*(i)*32)+yPos+32;
		}
		
		return 0;
	}


	public BufferedImage getBossImage()
	{
		return enemyFrames[curFrame];
	}
	


	// Shoots bullets
	public boolean shootBullet() {
		int max=SHOOT_BULLET_MAX;
		if (lowlife){
			max=SHOOT_BULLET_MAX/2;
		}
	
		if (Utils.randInt(SHOOT_BULLET_MIN, max)< SHOOT_BULLET_CONDITION)
			return true;
		else
			return false;
	}

}
