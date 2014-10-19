import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.awt.*;

public class Bullet{
    private float VELOCITY;
    
    protected int xPos;
    protected int yPos;
    private int direction;
    private int millisecDelay;
	
	protected int power;

    protected float velocity;
    
    BufferedImage bulletImage;

    Bullet(int x, int y, int direction){
	this.xPos = x;
	this.yPos = y;
	this.millisecDelay = 10;
	this.direction = direction;
	this.velocity = 0.5f;
	this.power = 1;

	try{
	    this.bulletImage = ImageIO.read(new File("assets/bulletImage.png"));
	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }
    
    public boolean collidesWith(Rectangle boundingBox){
	Rectangle bulletBox = new Rectangle(this.xPos, this.yPos, this.bulletImage.getWidth(),this.bulletImage.getHeight());
	return Utils.boxCollision(bulletBox, boundingBox);
    }
	
    public Rectangle getBoundingBox(){
		return new Rectangle(this.xPos, this.yPos, this.bulletImage.getWidth(),this.bulletImage.getHeight());
	}
	
    public int update(float dt){
	if(this.direction == 0){
	    this.yPos -= this.velocity*dt;
	}
	else if(this.direction == 1){
	    this.xPos += this.velocity*dt;
	    this.yPos -= this.velocity*dt;
	}
	else if(this.direction == 2){
	    this.xPos += this.velocity*dt;
	}
	else if(this.direction == 3){
	    this.xPos += this.velocity*dt;
	    this.yPos += this.velocity*dt;
	}
	else if(this.direction == 4){
	    this.yPos += this.velocity*dt;
	}
	else if(this.direction == 5){
	    this.xPos -= this.velocity*dt;
	    this.yPos += this.velocity*dt;
	}
	else if(this.direction == 6){
	    this.xPos -= this.velocity*dt;
	}
	else if(this.direction == 7){
	    this.xPos -= this.velocity*dt;
	    this.yPos -= this.velocity*dt;
	}
	return 0;
    }
    
    public boolean shouldDelete(){
	if(this.xPos>ViewController.SCREEN_WIDTH + 10 || this.xPos< -10 || this.yPos > ViewController.SCREEN_HEIGHT - (64 + this.bulletImage.getHeight()) || this.yPos < -10){
	    return true;
	}
	else{
	    return false;
	}
 
    }
    public int getMillisecDelay(){
	return this.millisecDelay;
    }
	
	public int getPower(){
		return this.power;
	}
	
    public void render(Graphics2D g2, float rw, float rh){
	g2.drawImage(this.bulletImage,this.xPos,this.yPos,(int)rw*this.bulletImage.getWidth(),(int)rh*this.bulletImage.getHeight(),null);
    }

}
