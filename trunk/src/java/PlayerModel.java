import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PlayerModel extends Model implements InputResponder{
	// Tracks whether a particular button was pressed down
    private boolean upDown;
    private boolean downDown;
    private boolean leftDown;
    private boolean rightDown;
    private boolean zDown;
    private boolean xDown;

    // Contains necessary images to correctly display character sprite
    private BufferedImage[] playerFrames;
	private BufferedImage defensePodImage;
	private BufferedImage cobaltImage;
    private int curFrame;

    // Coordinates to keep positioning of character sprite on viewing screen -- refers to top-left corner of its bounding box
    private int xPos;
    private int yPos;

    // Used in collision detection with enemies, boundaries, bullets, and walls
    private int spriteWidth;
    private int spriteHeight;


    private ArrayList<Bullet> bulletList; // User bullets
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

	// characters speed
	private float velocity;


    public enum BulletType{
	RING,MISSLE,LASER,BASIC
    }
    private BulletType bulletType;

    private LevelModel levelModel;

    PlayerModel(LevelModel levelModel){

    	// No buttons pressed on player creation
		upDown = false;
		downDown = false;
		leftDown = false;
		rightDown = false;
		zDown = false;
		xDown = false;

		// Starting position of sprite -- sprite's upper left pixel (1,1)
		xPos = 64;
		yPos = 128;

		// Initial character attribute on creation
		score =0;
		lives = 4;
		missleLevel = 0;
		ringLevel = 0;
		laserLevel = 0;
		fragmentCount = 0;
		defensePodLevel = 0;
		defensePodOscillator = 0;
		cobaltTimer = null;

		// Characters movement speed
		// Note movement updates at milliseconds since last frame(~33) * velocity(.1)
		// so 33 * .1 = 3.3 pixels moved per frame, which meets the specification
		velocity = .1f;

		bulletList = new ArrayList<Bullet>();
		bulletTimer = new MillisecTimer();
		bulletType = BulletType.BASIC;
		//701
		//6*2
		//543

		// Loads Images used to show which direction the character sprite is moving/aiming
		playerFrames = new BufferedImage[8];
		try{
			playerFrames[0] = ImageIO.read(new File("assets/player_up.png"));
			playerFrames[1] = ImageIO.read(new File("assets/player_up_right.png"));
			playerFrames[2] = ImageIO.read(new File("assets/player_right.png"));
			playerFrames[3] = ImageIO.read(new File("assets/player_down_right.png"));
			playerFrames[4] = ImageIO.read(new File("assets/player_down.png"));
			playerFrames[5] = ImageIO.read(new File("assets/player_down_left.png"));
			playerFrames[6] = ImageIO.read(new File("assets/player_left.png"));
			playerFrames[7] = ImageIO.read(new File("assets/player_up_left.png"));

			spriteWidth = playerFrames[0].getWidth();
			spriteHeight = playerFrames[0].getHeight();

			defensePodImage = ImageIO.read(new File("assets/defensePodImage.png"));
			cobaltImage = ImageIO.read(new File("assets/cobaltBomb.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}

		curFrame = 2;
		this.levelModel = levelModel;
		// levelModel.getModelController().getViewController().getDrawPanel().getInputHandler().registerInputResponder(this);
    }
    /*
     *  returns List of tiles that sprite is currently touching
     *  Notes: Character sprite size = 32 x 64 pixels or 1 x 2 tiles exactly
     *  Thus: character can either be touching 2, 4, or 6 tiles at any given time
     */

    // This Method returns an array of references to different types of tiles
    //  -- used later to ensure character sprite can't more through solid object tiles
    public ArrayList<Integer> onTiles(){
		ArrayList<Integer> onTiles = new ArrayList<Integer>();

		// Tile size in pixels
		int tileWidth = levelModel.getTileWidth();
		int tileHeight= levelModel.getTileHeight();

		int coverWide = 1;
		int coverHigh = 1;

		// Character only touching 1 column of tiles(Perfectly aligned vertically)
		if (xPos % tileWidth == 0) {
			coverWide = 1;
		}
		// Character touching 2 columns of tiles
		else {
			coverWide = 2;
		}

		// Character touching 2 rows of tiles(Perfectly aligned)
		if (yPos % tileHeight == 0) {
			coverHigh = 2;
		}
		// Character touching 3 rows of tiles
		else {
			coverHigh = 3;
		}

		// Obtains upper-left tile that the character sprite is currently on
		int tileCoordX = (xPos + levelModel.getScrollDistance()) / tileWidth;
		int tileCoordY = yPos / tileHeight;

		// Obtains all tiles character is currently touching
		for (int yy = 0; yy < coverHigh; yy++) {
			for (int xx = 0; xx < coverWide; xx++) {
				// adds the different types of tiles that the character is touching to the return list(references technically)
				onTiles.add(levelModel.getTileMap()[((tileCoordY + yy) * levelModel.getTileMapWidth()) + (tileCoordX + xx)]);
			}
		}
		return onTiles;
    }
    // Flags that a key is pressed down
    public void keyDownResponse(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_UP)
			upDown = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			downDown = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			leftDown = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightDown = true;
		if(e.getKeyCode() == KeyEvent.VK_Z)
			zDown = true;
		if(e.getKeyCode() == KeyEvent.VK_X)
			xDown = true;
		}

    // Flags when a key is released -- think holding down fire key for constant fire
	public void keyUpResponse(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_UP)
			upDown = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			downDown = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			leftDown = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightDown = false;
		if(e.getKeyCode() == KeyEvent.VK_Z)
			zDown = false;
		if(e.getKeyCode() == KeyEvent.VK_X)
			xDown = false;
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(levelModel.paused == true)
				levelModel.paused = false;
			else
				levelModel.paused = true;
		}
	}

	// Increments attribute bonus granted by pickup and plays the corresponding sound to that pickup
	public void getPickup(String type){
		if(type.equals("fragment")){
			fragmentCount += 1;
			if (fragmentCount > 32){
				fragmentCount = 0;
				lives +=1;
			}
			SoundManager.get().playSound("fragment");
		}
		else if(type.equals("ring")){
			ringLevel+=1;
			bulletType = BulletType.RING;
			SoundManager.get().playSound("pickup");
		}
		else if(type.equals("missle")){
			missleLevel +=1;
			bulletType = BulletType.MISSLE;
			SoundManager.get().playSound("pickup");
		}
		else if(type.equals("laser")){
			laserLevel +=1;
			bulletType = BulletType.LASER;
			SoundManager.get().playSound("pickup");
		}
		else if(type.equals("defense_pod")){
			defensePodLevel +=1;
			SoundManager.get().playSound("pickup");
		}
		else if(type.equals("speed")){
			velocity +=.05;
			SoundManager.get().playSound("pickup");
		}
	}

	/*
	 * The update is called explicitly in LevelModel's update method.
	 */
	public int update(float dt){

		/* x => fire
		 * Determines weapon fires in the appropriate direction
		 */
		if(xDown == true){
			// Can shoot 4 bullets a second(1 second = 1000ms =>  1000/250 = 4)
			// Note: basic bullets always shoot in addition to the upgrades
			if(bulletTimer.getDt() > 250){
				bulletTimer.reset();

				if(bulletType == BulletType.BASIC)
					SoundManager.get().playSound("bullet");

				bulletList.add(new Bullet(xPos + (spriteWidth/2), yPos + (spriteHeight/2), curFrame));

				if(bulletType == BulletType.RING){
					SoundManager.get().playSound("ring");
					// 1 ring => levels 1-4  -- fires facing direction
					bulletList.add(new RingBullet(xPos + (spriteWidth/2), yPos+(spriteHeight/2), curFrame));

					// 2 rings => levels 5-9 -- fires forward and backward of shooting direction
					if(ringLevel >=5){
						bulletList.add(new RingBullet(xPos + (spriteWidth/2), yPos + (spriteHeight/2), (curFrame +4) % 8));
					}

					// 4 rings => levels 10+ -- fires 1 behind and 3 in facing direction (straight, 45 degrees up, 45 degrees down)
					if(ringLevel >=10){
						bulletList.add(new RingBullet(xPos + (spriteWidth/2), yPos + (spriteHeight/2), (curFrame +1) % 8));
						bulletList.add(new RingBullet(xPos + (spriteWidth/2), yPos + (spriteHeight/2), (curFrame +7) % 8));
					}
				}
				else if (bulletType == BulletType.MISSLE){
					SoundManager.get().playSound("missle");

					// 1 missile => levels: 1-4  -- Fires right
					bulletList.add(new MissleBullet(xPos + (spriteWidth/2), yPos + (spriteHeight/2), 2));

					// 2 missiles => levels: 5-9  -- Fires: left, right
					if (missleLevel >=5){
						bulletList.add(new MissleBullet(xPos + (spriteWidth/2), yPos + (spriteHeight/2), 6));
					}

					// 4 missiles => levels: 10+  -- Fires: up, down, left, right
					if (missleLevel >=10){
						bulletList.add(new MissleBullet(xPos + (spriteWidth/2), yPos + (spriteHeight/2), 0));
						bulletList.add(new MissleBullet(xPos + (spriteWidth/2), yPos + (spriteHeight/2), 4));
					}
				}
				else if(bulletType == BulletType.LASER){
					SoundManager.get().playSound("laser");
					// 4 lasers => levels 10+   -- fires forward & backward of player facing direction and 1 perpendicular to both those
					if(laserLevel >=10){
						bulletList.add(new LaserBullet(xPos+(spriteWidth/2),yPos+(spriteHeight/2),(curFrame +1) % 8));
						bulletList.add(new LaserBullet(xPos+(spriteWidth/2),yPos+(spriteHeight/2),(curFrame +7) % 8));
						bulletList.add(new LaserBullet(xPos+(spriteWidth/2),yPos+(spriteHeight/2),(curFrame +3) % 8));
						bulletList.add(new LaserBullet(xPos+(spriteWidth/2),yPos+(spriteHeight/2),(curFrame +5) % 8));

					}
					// 2 lasers => levels 5-9   -- fires forward & backward of player facing direction
					else if(laserLevel >=5){
						bulletList.add(new LaserBullet(xPos+(spriteWidth/2),yPos+(spriteHeight/2),(curFrame +4) % 8));
						bulletList.add(new LaserBullet(xPos+(spriteWidth/2),yPos+(spriteHeight/2),curFrame));
					}

					// 1 laser => levels 1-4   -- fires facing direction
					else{
						bulletList.add(new LaserBullet(xPos+(spriteWidth/2),yPos+(spriteHeight/2),curFrame));
					}

				}
			}
		}

		// use CobaltBomb
		if(zDown == true){
			cobaltBomb();
		}
		// retain previous frames coordinates
		int oldX = xPos;
		int oldY = yPos;

		// Hold change in x and y variables between current and previous frame
		float deltaX = 0;
		float deltaY = 0;

/*=======================================================================================
 *Player Movement Updates: Note Y-axis is inverted -- Upper left corner of grid is (0,0)
 *=======================================================================================*/

		// Move: North-east
		if (upDown == true && rightDown == true) {
			deltaX = velocity * dt;
			deltaY = -velocity * dt;
			if (xDown == false)
				curFrame = 1;
		}

		// Move: South-east
		else if (downDown == true && rightDown == true) {
			deltaX = velocity * dt;
			deltaY = velocity * dt;
			if (xDown == false)
				curFrame = 3;
		}

		// Move: South-west
		else if (downDown == true && leftDown == true) {
			deltaX = -velocity * dt;
			deltaY = velocity * dt;
			if (xDown == false)
				curFrame = 5;
		}

		// Move: North-west
		else if (upDown == true && leftDown == true) {
			deltaX = -velocity * dt;
			deltaY = -velocity * dt;
			if (xDown == false)
				curFrame = 7;
		}

		// Move: North
		else if (upDown == true) {
			deltaY = -velocity * dt;
			if (xDown == false)
				curFrame = 0;
		}

		// Move: East
		else if (rightDown == true) {
			deltaX = velocity * dt;
			if (xDown == false)
				curFrame = 2;
		}
		// Move: South
		else if (downDown == true) {
			deltaY = velocity * dt;
			if (xDown == false)
				curFrame = 4;
		}
		// Move: West
		else if (leftDown == true) {
			deltaX = -velocity * dt;
			if (xDown == false)
				curFrame = 6;
		}
//=================================================================================
//    Applying change in character Location from previous frame
//=================================================================================

		// Updates x coordinate
		xPos += deltaX;
		ArrayList<Integer> tiles = onTiles();

//////////////////Corrected////////////////////////////////////////////////////////////

		// Ensures player doesn't move horizontally through solid object tiles
		for (int xx = 0; xx < tiles.size(); xx++) {
			if (tiles.get(xx) < 17 || tiles.get(xx) > 23) {
				xPos = oldX - levelModel.getScrollDelta();
			}
		}

		// Ensures player doesn't move vertically through solid object tiles
		yPos += deltaY;
		tiles = onTiles();
		for (int xx = 0; xx < tiles.size(); xx++) {
			if (tiles.get(xx) < 17 || tiles.get(xx) > 23) {
				yPos = oldY;
			}
		}
///////////////////////////////////////////////////////////////////////////////////////

		// Restricts player from moving off left side of screen
		if (xPos < 0)
			xPos = 0;

		// Restricts player from moving off right side of screen
		if (xPos > levelModel.getModelController().getViewController().SCREEN_WIDTH - spriteWidth)
			xPos = levelModel.getModelController().getViewController().SCREEN_WIDTH - spriteWidth;

		// Restricts player from moving off north side of screen
		if (yPos < 0)
			yPos = 0;

		// Restricts player from moving off south side of screen
		if (yPos > levelModel.getModelController().getViewController().SCREEN_HEIGHT - spriteHeight)
			yPos = levelModel.getModelController().getViewController().SCREEN_HEIGHT - spriteHeight;

		// Checks if player collides with any pickups
		for (int xx = 0; xx < levelModel.getLevelPickups().size(); xx++) {
			Pickup pk = levelModel.getLevelPickups().get(xx);

			if (Utils.boxCollision(new Rectangle(xPos,yPos,spriteWidth,spriteHeight),new Rectangle(pk.getXPos(),pk.getYPos(),pk.getWidth(),pk.getHeight()))){
				getPickup(pk.type);
				score += ScoreTable.scoreForPickup(pk);
				levelModel.getLevelPickups().remove(xx);
				xx--;
			}
		}

		// updates the defense pod's position and determines if it killed an enemy(updates scoretable if so)
		if (defensePodLevel > 0){
			// updates pod position
			defensePodOscillator += ((float)defensePodLevel/2 * dt);
			Rectangle boundingBox = new Rectangle(getDefensePodXPos(),getDefensePodYPos(),defensePodImage.getWidth(),defensePodImage.getHeight());  // defense pod's hitbox

			// determines if pod contacted and killed an enemy
			for (int xx=0;xx<levelModel.getEnemyModels().size();xx++){
				EnemyModel em = levelModel.getEnemyModels().get(xx);
				if (Utils.boxCollision(em.getBoundingBox(),boundingBox)){
					em.kill();
					score += ScoreTable.scoreForKilled(em);
					Pickup[] p = em.getDrop();
					if (p[0] != null)
						levelModel.getLevelPickups().add(p[0]);
					if (p[1] != null)
						levelModel.getLevelPickups().add(p[1]);
				}

			}
		}
		// Updates bullet: position, enemy damaged/killed, off-screen(expires)
		for (int xx=0;xx<bulletList.size();xx++){
			// removes off-screen bullets
			if (bulletList.get(xx).shouldDelete()){
				bulletList.remove(xx);
			}

			else {
				Bullet b = bulletList.get(xx);
				b.update(dt);

				Rectangle boundingBox = b.getBoundingBox();
				int tileCoordX;
				int tileCoordY;
				boolean deleted = false;
				/*
				 *  nested four loop only is grabbing the 4 corners of the bullet's hit box and checking the type of tiles that they contact.
				 *  The current bullets range from 8x8 to 17x17, so they all can contact the same range of tiles at any given time: 1-4
				 *  A bullet expires on contact with a solid object(besides ring bullets)
				 */

				for (double yy = boundingBox.getY(); yy < boundingBox.getY() + boundingBox.getHeight() + 1; yy += boundingBox.getHeight()) {
					for (double zz = boundingBox.getX(); zz < boundingBox.getX() + boundingBox.getWidth() + 1; zz += boundingBox.getWidth()) {
						tileCoordX = ((int) zz + levelModel.getScrollDistance()) / levelModel.getTileWidth();
						tileCoordY = (int) yy / levelModel.getTileHeight();
						int tile = 20;

						// flags bullet to be deleted if it contacts solid tile(besides ring bullets)
						tile = levelModel.getTileMap()[((tileCoordY) * levelModel.getTileMapWidth()) + (tileCoordX)];
						if (tile < 17 || tile > 23) {
							if (!(b instanceof RingBullet))
								deleted = true;
						}

					}
				}
				if(deleted){
					bulletList.remove(xx);
					continue;
				}
				/* For loop: Determines if a bullet contacts an enemy
				 * On contact: Damages/kills enemy and is deleted unless it's a ring bullet
				 * On Kill: updates scoreboard, determines drop
				 */
				for (int yy = 0; yy < levelModel.getEnemyModels().size(); yy++) {
					EnemyModel em = levelModel.getEnemyModels().get(xx);
					if (b.collidesWith(em.getBoundingBox())) {
						boolean kill = em.hit(b.getPower());
						if (kill == true) {
							SoundManager.get().playSound("kill");
							score += ScoreTable.scoreForKilled(em);
							Pickup[] p = em.getDrop();
							if (p[0] != null)
								levelModel.getLevelPickups().add(p[0]);
							if (p[1] != null)
								levelModel.getLevelPickups().add(p[1]);
						} else {
							SoundManager.get().playSound("hit");
						}
					}
				}
			}
		}
		return 0;
    }

	public void death(){
		lives -=1;

		// Starting position of character respawn
		xPos = 64;
		yPos = 128;

		// Sets level of weapon character was using at death to zero
		if(bulletType == BulletType.MISSLE)
			missleLevel = 0;
		else if(bulletType == BulletType.LASER)
			laserLevel = 0;
		else if(bulletType == BulletType.RING)
			ringLevel = 0;

		// Eliminates collected fragments and defense pod
		fragmentCount = 0;
		defensePodLevel = 0;
		defensePodOscillator = 0;
		cobaltTimer = null;

		velocity = .1f;

		bulletList.clear();
		bulletTimer = new MillisecTimer();
		bulletType = BulletType.BASIC;

		curFrame = 2;


	}


	private void cobaltBomb() {
		if (fragmentCount >= 4) {
			levelModel.cobaltBomb();
			fragmentCount -= 4;
			cobaltTimer = new MillisecTimer();
		}
	}

    public int getXPos(){
		return xPos;
    }
    public int getYPos(){
		return yPos;
    }
    public int getWidth(){
		return spriteWidth;
    }
    public int getHeight(){
		return spriteHeight;
    }
    public int getLaserLevel(){
		return laserLevel;
	}
	public int getMissleLevel(){
		return missleLevel;
	}
	public int getRingLevel(){
		return laserLevel;
	}
	public int getScore(){
		return score;
	}
	public int getLives(){
		return lives;
	}
	public int getFragmentCount(){
		return fragmentCount;
	}
	public int getDefensePodLevel(){
		return defensePodLevel;
	}
	public double getDefensePodOscillator(){
		return Math.toRadians(defensePodOscillator % 360);
	}
	public int getDefensePodXPos(){
		return (int)(getXPos() + (int)getWidth()/2 + (Math.cos(getDefensePodOscillator()) *64));
	}
	public int getDefensePodYPos(){
		return (int)(getYPos() + (int)getHeight()/2 + (Math.sin(getDefensePodOscillator()) *64));
	}
	public float getCobaltDt(){
		if(cobaltTimer != null){
			float dt = cobaltTimer.getDt();
			if(dt < 1500)
				return cobaltTimer.getDt();
			else{
				cobaltTimer = null;
				return 0.0f;
			}
		}
		else{
			return 0.0f;
		}
	}
	public BufferedImage getCobaltBombImage(){
		return cobaltImage;
	}
	public Rectangle getBoundingBox(){
		return new Rectangle(xPos,yPos,spriteWidth,spriteHeight);
	}
    public ArrayList<Bullet> getBulletList(){
		return bulletList;
    }
	public BufferedImage getPlayerImage(){
		return playerFrames[curFrame];
    }
	public BufferedImage getDefensePodImage(){
		return defensePodImage;
	}
    public HashMap<String,Model> getVisibleModels(){
		return new HashMap<String,Model>();
    }
}
