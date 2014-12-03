import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.awt.*;

public class Bullet {
	private float VELOCITY;
	
	protected boolean toBeDeleted;
	protected int xPos; // x-Coordinate of bullet
	protected int yPos; // y-Coordinate of bullet
	private int direction;
	private int millisecDelay;
	

	protected int power; // bullet's damage

	protected float velocity; // bullet's speed

	BufferedImage bulletImage;

	Bullet(int x, int y, int direction) {
		xPos = x;
		yPos = y;
		millisecDelay = 10;
		this.direction = direction;
		velocity = 0.5f;
		power = 1;
		toBeDeleted = false;

		// loads bullet's image -- should be cached instead
		try {
			this.bulletImage = ImageIO.read(new File("assets/bulletImage.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Checks if bullets contacts passed in object's hitbox
	public boolean collidesWith(Rectangle boundingBox) {
		Rectangle bulletBox = getBoundingBox();
		return Utils.boxCollision(bulletBox, boundingBox);
	}

	// Gets hit box of bullet
	public Rectangle getBoundingBox() {
		return new Rectangle(this.xPos, this.yPos, this.bulletImage.getWidth(), this.bulletImage.getHeight());
	}

	// Updates the bullets position based on the bullets velocity and direction
	public int update(float dt) {
		
		switch (direction) {
			case 0:		yPos -= velocity * dt;
						break;
						
			case 1:		xPos += velocity * dt;
						yPos -= velocity * dt;
						break;
			
			case 2: 	xPos += velocity * dt;
						break;
			
			case 3:		xPos += velocity * dt;
						yPos += velocity * dt;
						break;
			
			case 4: 	yPos += velocity * dt;
						break;
				
			case 5:		xPos -= this.velocity * dt;
						yPos += this.velocity * dt;
						break;
			
			case 6:		xPos -= velocity * dt;
						break;
			
			case 7:		xPos -= velocity * dt;
						yPos -= velocity * dt;
						break;			
		}
		return 0;
	}

	// returns true if bullet of off screen
	public boolean shouldDelete() {
		if (this.xPos > ViewController.SCREEN_WIDTH + 10 || this.xPos < -10 || this.yPos > ViewController.SCREEN_HEIGHT - (64 + this.bulletImage.getHeight() + 32 ) || this.yPos < 32) {
			return true;
		}
		else if (toBeDeleted == true) {
			return true;
		}
		else {
			return false;
		}

	}

	public int getMillisecDelay() {
		return this.millisecDelay;
	}
	
	public int getDirection() {
		return direction;
	}

	public int getPower() {
		return this.power;
	}

	public void render(Graphics2D g2, float rw, float rh) {
		g2.drawImage(this.bulletImage, this.xPos, this.yPos, (int) rw * this.bulletImage.getWidth(), (int) rh * this.bulletImage.getHeight(), null);
	}

}
