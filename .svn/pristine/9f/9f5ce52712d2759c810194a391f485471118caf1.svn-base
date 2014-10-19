import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PlayerModel extends Model implements InputResponder{

    private boolean upDown;
    private boolean downDown;
    private boolean leftDown;
    private boolean rightDown;
    private boolean zDown;
    private boolean xDown;
    
    private BufferedImage[] playerFrames;
	private BufferedImage defensePodImage;
	private BufferedImage cobaltImage;
    private int curFrame;

    private int xPos;
    private int yPos;
    
    private int width;
    private int height;
    
    private ArrayList<Bullet> bulletList;
    private MillisecTimer bulletTimer;
	private MillisecTimer cobaltTimer;
    
    private int score;
    private int lives;
    private int missleLevel;
    private int laserLevel;
    private int ringLevel;
    private int fragmentCount;
    private int defensePodLevel;
	private float defensePodOscillator;
	
	private float velocity;
	

    public enum BulletType{
	RING,MISSLE,LASER,BASIC
    }
    private BulletType bulletType;
    
    private LevelModel levelModel;
    
    PlayerModel(LevelModel levelModel){
		this.upDown = false;
		this.downDown = false;
		this.leftDown = false;
		this.rightDown = false;
		this.zDown = false;
		this.xDown = false;
		
		this.xPos = 64;
		this.yPos = 128;
		
		this.score =0;
		this.lives = 4;
		this.missleLevel = 0;
		this.ringLevel = 0;
		this.laserLevel = 0;
		this.fragmentCount = 0;
		this.defensePodLevel = 0;
		this.defensePodOscillator = 0;
		this.cobaltTimer = null;
		
		this.velocity = .1f;
		
		this.bulletList = new ArrayList<Bullet>();
		this.bulletTimer = new MillisecTimer();
		this.bulletType = BulletType.BASIC;
		//701
		//6*2
		//543
		this.playerFrames = new BufferedImage[8];
		try{
			this.playerFrames[0] = ImageIO.read(new File("assets/player_up.png"));
			this.playerFrames[1] = ImageIO.read(new File("assets/player_up_right.png"));
			this.playerFrames[2] = ImageIO.read(new File("assets/player_right.png"));
			this.playerFrames[3] = ImageIO.read(new File("assets/player_down_right.png"));
			this.playerFrames[4] = ImageIO.read(new File("assets/player_down.png"));
			this.playerFrames[5] = ImageIO.read(new File("assets/player_down_left.png"));
			this.playerFrames[6] = ImageIO.read(new File("assets/player_left.png"));
			this.playerFrames[7] = ImageIO.read(new File("assets/player_up_left.png"));
			
			this.width = this.playerFrames[0].getWidth();
			this.height = this.playerFrames[0].getHeight();
			
			this.defensePodImage = ImageIO.read(new File("assets/defensePodImage.png"));
			this.cobaltImage = ImageIO.read(new File("assets/cobaltBomb.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}

		this.curFrame = 2;
		this.levelModel = levelModel;
    }
    
    public ArrayList<Integer> onTiles(){
		ArrayList<Integer> onTiles = new ArrayList<Integer>();
		
		int tileWidth = this.levelModel.getTileWidth();
		int tileHeight= this.levelModel.getTileHeight();
		
		int coverWide = 1;
		int coverHigh = 1;
		if(this.xPos % tileWidth == 0){
			coverWide = 1;
		}
		else{
			coverWide = 2;
		}
		
		if(this.yPos % tileHeight == 0){
			coverHigh = 2;
		}
		else{
			coverHigh = 3;
		}
		
		int tileCoordX = (this.xPos + this.levelModel.getScrollDistance()) / tileWidth;
		int tileCoordY = this.yPos / tileHeight;
		
		for(int yy=0; yy< coverHigh; yy++){
			for(int xx=0; xx<coverWide; xx++){
				onTiles.add(this.levelModel.getTileMap()[((tileCoordY + yy) * this.levelModel.getTileMapWidth()) + (tileCoordX + xx)]);
			}
		}
		return onTiles;
    }

    public void keyDownResponse(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_UP)
			this.upDown = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			this.downDown = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			this.leftDown = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			this.rightDown = true;
		if(e.getKeyCode() == KeyEvent.VK_Z)
			this.zDown = true;
		if(e.getKeyCode() == KeyEvent.VK_X)
			this.xDown = true;
		}

		public void keyUpResponse(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_UP)
			this.upDown = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			this.downDown = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			this.leftDown = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			this.rightDown = false;
		if(e.getKeyCode() == KeyEvent.VK_Z)
			this.zDown = false;
		if(e.getKeyCode() == KeyEvent.VK_X)
			this.xDown = false;
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(this.levelModel.paused == true)
				this.levelModel.paused = false;
			else
				this.levelModel.paused = true;
		}
	}
	
	public void getPickup(String type){
		if(type.equals("fragment")){
			this.fragmentCount += 1;
			if (this.fragmentCount > 32){
				this.fragmentCount = 0;
				this.lives +=1;
			}
			SoundManager.get().playSound("fragment");
		}
		else if(type.equals("ring")){
			this.ringLevel+=1;
			this.bulletType = BulletType.RING;
			SoundManager.get().playSound("pickup");
		}
		else if(type.equals("missle")){
			this.missleLevel +=1;
			this.bulletType = BulletType.MISSLE;
			SoundManager.get().playSound("pickup");
		}
		else if(type.equals("laser")){
			this.laserLevel +=1;
			this.bulletType = BulletType.LASER;
			SoundManager.get().playSound("pickup");
		}
		else if(type.equals("defense_pod")){
			this.defensePodLevel +=1;
			SoundManager.get().playSound("pickup");
		}
		else if(type.equals("speed")){
			this.velocity +=.05;
			SoundManager.get().playSound("pickup");
		}
	}
	
	public int update(float dt){
		if(this.xDown == true){
			if(this.bulletTimer.getDt() > 250){
				this.bulletTimer.reset();
				
				if(this.bulletType == BulletType.BASIC)
					SoundManager.get().playSound("bullet");
				
				this.bulletList.add(new Bullet(this.xPos+(this.width/2),this.yPos+(this.height/2),this.curFrame));
				
				if(this.bulletType == BulletType.RING){
					SoundManager.get().playSound("ring");
					this.bulletList.add(new RingBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),this.curFrame));
					if(this.ringLevel >=5){
						this.bulletList.add(new RingBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),(this.curFrame +4) % 8));
					}
					if(this.ringLevel >=10){
						this.bulletList.add(new RingBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),(this.curFrame +1) % 8));
						this.bulletList.add(new RingBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),(this.curFrame +7) % 8));
					}					
				}
				else if(this.bulletType == BulletType.MISSLE){
					SoundManager.get().playSound("missle");
					this.bulletList.add(new MissleBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),2));
					if(this.missleLevel >=5){
						this.bulletList.add(new MissleBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),6));
					}
					if(this.missleLevel >=10){
						this.bulletList.add(new MissleBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),0));
						this.bulletList.add(new MissleBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),4));
					}	
				}
				else if(this.bulletType == BulletType.LASER){
					SoundManager.get().playSound("laser");
					if(this.laserLevel >=10){
						this.bulletList.add(new LaserBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),(this.curFrame +1) % 8));
						this.bulletList.add(new LaserBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),(this.curFrame +7) % 8));
						this.bulletList.add(new LaserBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),(this.curFrame +3) % 8));
						this.bulletList.add(new LaserBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),(this.curFrame +5) % 8));
						
					}
					else if(this.laserLevel >=5){
						this.bulletList.add(new LaserBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),(this.curFrame +4) % 8));
						this.bulletList.add(new LaserBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),this.curFrame));
					}
					else{
						this.bulletList.add(new LaserBullet(this.xPos+(this.width/2),this.yPos+(this.height/2),this.curFrame));
					}
						
				}
			}
		}
	
		if(this.zDown == true){
			this.cobaltBomb();
		}
		
		int oldX = this.xPos;
		int oldY = this.yPos;
		
		float deltaX = 0;
		float deltaY = 0;
		
		if(this.upDown == true && this.rightDown == true){
			deltaX = this.velocity *dt;
			deltaY = this.velocity *dt;
			if(this.xDown == false)
				this.curFrame = 1;
		}
		else if(this.downDown == true && this.rightDown == true){
			deltaX = this.velocity *dt;
			deltaY = this.velocity *dt;
			if(this.xDown == false)
				this.curFrame = 3;
		}
		else if(this.downDown == true && this.leftDown == true){
			deltaX = -this.velocity *dt;
			deltaY = this.velocity *dt;
			if(this.xDown == false)
				this.curFrame = 5;
		}
		else if(this.upDown == true && this.leftDown == true){
			deltaX = -this.velocity *dt;
			deltaY = -this.velocity *dt;
			if(this.xDown == false)
				this.curFrame = 7;
		}
		else if(this.upDown == true){
			deltaY = -this.velocity *dt;
			if(this.xDown == false)
				this.curFrame = 0;
		}
		else if(this.rightDown == true){
			deltaX =this.velocity *dt;
			if(this.xDown == false)
				this.curFrame = 2;
		}
		else if(this.downDown == true){
			deltaY = this.velocity *dt;
			if(this.xDown == false)
				this.curFrame = 4;
		}
		else if(this.leftDown == true){
			deltaX = -this.velocity *dt;
			if(this.xDown == false)
				this.curFrame = 6;
		}
		
		this.xPos += deltaX;
		ArrayList<Integer> tiles = this.onTiles();
		for(int xx=0;xx<tiles.size();xx++){
			if(tiles.get(xx) <17 || tiles.get(xx) >23){
				this.xPos = oldX - this.levelModel.getScrollDelta();
			}
		}
		this.yPos += deltaY;
		tiles = this.onTiles();
		for(int xx=0;xx<tiles.size();xx++){
			if(tiles.get(xx) <17 || tiles.get(xx) >23){
				this.xPos = oldY;
			}
		}
		
		if(this.xPos < 0)
			this.xPos = 0;
		if(this.xPos > this.levelModel.getModelController().getViewController().SCREEN_WIDTH - this.width)
			this.xPos = this.levelModel.getModelController().getViewController().SCREEN_WIDTH - this.width;
		if(this.yPos < 0)
			this.yPos = 0;
		if(this.yPos > this.levelModel.getModelController().getViewController().SCREEN_HEIGHT - this.height)
			this.yPos = this.levelModel.getModelController().getViewController().SCREEN_HEIGHT - this.height;

		for(int xx=0;xx<this.levelModel.getLevelPickups().size();xx++){
			Pickup pk = this.levelModel.getLevelPickups().get(xx);
			if(Utils.boxCollision(new Rectangle(this.xPos,this.yPos,this.width,this.height),new Rectangle(pk.getXPos(),pk.getYPos(),pk.getWidth(),pk.getHeight()))){
				this.getPickup(pk.type);
				this.score += ScoreTable.PICKUPSCORE;
				this.levelModel.getLevelPickups().remove(xx);
				xx--;
			}
		}
		
		if(this.defensePodLevel > 0){
			this.defensePodOscillator += ((float)this.defensePodLevel/2 * dt);
			Rectangle boundingBox = new Rectangle(this.getDefensePodXPos(),this.getDefensePodYPos(),this.defensePodImage.getWidth(),this.defensePodImage.getHeight());
			for(int xx=0;xx<this.levelModel.getEnemyModels().size();xx++){
				EnemyModel em = levelModel.getEnemyModels().get(xx);
				if(Utils.boxCollision(em.getBoundingBox(),boundingBox)){
					em.kill();
					this.score += ScoreTable.ENEMYSCORE;
					Pickup p = em.getDrop();
					if(p != null){
						this.levelModel.getLevelPickups().add(p);
					}
				}
				
			}
		}

		for(int xx=0;xx<this.bulletList.size();xx++){
			if(this.bulletList.get(xx).shouldDelete()){
				this.bulletList.remove(xx);
			}
			else{
				Bullet b = this.bulletList.get(xx);
				b.update(dt);
				
				Rectangle boundingBox = b.getBoundingBox();
				int tileCoordX;
				int tileCoordY;
				boolean deleted = false;
				for(double yy=boundingBox.getY();yy<boundingBox.getY() + boundingBox.getHeight() +1;yy+= boundingBox.getHeight()){
					for(double zz=boundingBox.getX();zz<boundingBox.getX() + boundingBox.getWidth() + 1; zz+= boundingBox.getWidth()){
						tileCoordX = ((int)zz + this.levelModel.getScrollDistance()) / this.levelModel.getTileWidth();
						tileCoordY = (int)yy / this.levelModel.getTileHeight();
						int tile = 20;
						tile = this.levelModel.getTileMap()[((tileCoordY) * this.levelModel.getTileMapWidth()) + (tileCoordX)];
						if(tile <17 || tile >23){
							if(!(b instanceof RingBullet))
								deleted = true;
						}
						
						
					}
				}
				if(deleted){
					this.bulletList.remove(xx);
					continue;
				}
				
				for(int yy=0;yy<this.levelModel.getEnemyModels().size();yy++){
					EnemyModel em = levelModel.getEnemyModels().get(xx);
					if (b.collidesWith(em.getBoundingBox())){
						boolean kill = em.hit(b.getPower());
						if(kill == true){
							SoundManager.get().playSound("kill");
							this.score += ScoreTable.ENEMYSCORE;
							Pickup p = em.getDrop();
							if(p != null){
								this.levelModel.getLevelPickups().add(p);
							}
						}
						else{
							SoundManager.get().playSound("hit");
						}
					}
				}
			}
		}	
		return 0;
    }
	
	public void death(){
		this.lives -=1;
		
		this.xPos = 64;
		this.yPos = 128;
		
		if(this.bulletType == BulletType.MISSLE)
			this.missleLevel = 0;
		else if(this.bulletType == BulletType.LASER)
			this.laserLevel = 0;
		else if(this.bulletType == BulletType.RING)
			this.ringLevel = 0;
		
		this.fragmentCount = 0;
		this.defensePodLevel = 0;
		this.defensePodOscillator = 0;
		this.cobaltTimer = null;
		
		this.velocity = .1f;
		
		this.bulletList.clear();
		this.bulletTimer = new MillisecTimer();
		this.bulletType = BulletType.BASIC;
		
		this.curFrame = 2;
		
			
	}
	
	private void cobaltBomb(){
		if(this.fragmentCount >=4){
			this.levelModel.cobaltBomb();
			this.fragmentCount-=4;
			this.cobaltTimer = new MillisecTimer();
		}
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
    public int getLaserLevel(){
		return this.laserLevel;
	}
	public int getMissleLevel(){
		return this.missleLevel;
	}
	public int getRingLevel(){
		return this.laserLevel;
	}
	public int getScore(){
		return this.score;
	}
	public int getLives(){
		return this.lives;
	}
	public int getFragmentCount(){
		return this.fragmentCount;
	}
	public int getDefensePodLevel(){
		return this.defensePodLevel;
	}
	public double getDefensePodOscillator(){
		return Math.toRadians(this.defensePodOscillator % 360);
	}
	public int getDefensePodXPos(){
		return (int)(this.getXPos() + (int)this.getWidth()/2 + (Math.cos(this.getDefensePodOscillator()) *64));
	}
	public int getDefensePodYPos(){
		return (int)(this.getYPos() + (int)this.getHeight()/2 + (Math.sin(this.getDefensePodOscillator()) *64));
	}
	public float getCobaltDt(){
		if(cobaltTimer != null){
			float dt = this.cobaltTimer.getDt();
			if(dt < 1500)
				return this.cobaltTimer.getDt();
			else{
				this.cobaltTimer = null;
				return 0.0f;
			}
		}
		else{
			return 0.0f;
		}
	}
	public BufferedImage getCobaltBombImage(){
		return this.cobaltImage;
	}
	public Rectangle getBoundingBox(){
		return new Rectangle(this.xPos,this.yPos,this.width,this.height);
	}
    public ArrayList<Bullet> getBulletList(){
		return this.bulletList;
    }
	public BufferedImage getPlayerImage(){
		return this.playerFrames[this.curFrame];
    }
	public BufferedImage getDefensePodImage(){
		return this.defensePodImage;
	}
    public HashMap<String,Model> getVisibleModels(){
		return new HashMap<String,Model>();
    }
}
