import java.util.HashMap;
import java.util.Properties;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import java.lang.Number;
/*
 * This class is used to load and represent each of the games levels. Each individual level is stored
 * inside a JSON file, which contains all aspects of a given level.
 * Aspects are: Enemies, pickups, tilesets, and tilemappings used to construct that particular level
 */

public class LevelModel extends Model{
    
    private PlayerModel playerModel;
    private ModelController modelController;

    private int tileMapWidth;      // Width of map  : tile units
    private int tileMapHeight;	   // Height of map : tile units
    private int tileWidth;		   // Width of tile : pixel units
    private int tileHeight;		   // Height of tile: pixel units
    private int mapWidthInPixels;  //

    private float distanceScrolled;  // Total distance scrolled from beginning: pixel units
    private float scrollVelocity;    // 
    private int scrollDelta;         // Distance scrolled the previous frame
    
    private BufferedImage[] tileImages;
    private int[] tileMap;
    
    private ArrayList<EnemyModel> enemyModels;     // Contains each enemy model that's currently active
    private ArrayList<EnemyModel> queuedEnemies;   // Contains enemies waiting to be active
    private ArrayList<Bullet> enemyBullets;        // Contains active enemy bullets on screen
    private ArrayList<Pickup> levelPickups;        // Contains active enemy drops on screen

    public boolean paused;
	
    private MillisecTimer deathTimer;
	
    LevelModel(ModelController model){
		modelController = model;
		playerModel = new PlayerModel(this);
		model.getViewController().getDrawPanel().getInputHandler().registerInputResponder(playerModel);
		
		distanceScrolled = 0.0f;
		scrollVelocity = 0.05f;
		scrollDelta = 0;
		
		load("assets/test_level.json");
		
		queuedEnemies = new ArrayList<EnemyModel>();
		enemyModels = new ArrayList<EnemyModel>();
		enemyBullets = new ArrayList<Bullet>();
		levelPickups = new ArrayList<Pickup>();
		
		paused = false;
		
		deathTimer = null;
		
		SoundManager.get().playSound("music");
		
    }

	public int update(float dt) {
		// if paused or respawning, do nothing
		if (paused || getDeathTimerDt() > 0) {
			return 0;
		}
		
		mapScroll(dt);
		
		spawnEnemies();
		
		playerModel.update(dt);
		
		updateEnemies(dt);
		
		manageBullets(dt);
		
		updatePickups(dt);

		return 0;
    }
	
	/**
	 * displays rest of the map if there's more to see, -Note: the 33 refers to a buffer space to load 
	 * the enemies off screen to ensure their appearance is natural -- flyer's width is 32 pixels */
	public void mapScroll(float dt)
	{
		if (distanceScrolled < mapWidthInPixels - (ViewController.SCREEN_WIDTH + 33)) {   
			scrollDelta = (int) (scrollVelocity * dt);
			distanceScrolled += scrollDelta;
		} else {
			scrollDelta = 0;  // end of map
		}
	}
	
	/**Adds new enemies to screen(offscreen technically) when their start coordinate(xPos) appears on screen*/
	public void spawnEnemies()
	{
		for (int xx = 0; xx < queuedEnemies.size(); xx++) {
			EnemyModel em = queuedEnemies.get(xx);
			if (distanceScrolled + ViewController.SCREEN_WIDTH > em.getXPos()) {
				addEnemyModel(em);
				queuedEnemies.remove(xx);
				xx--;
			}

		}
	}
	
	/** Removes dead enemies, updates living/active enemies, and creates enemy bullets*/
	public void updateEnemies(float dt)
	{
		// for statement: 
		for(int xx=0;xx<enemyModels.size();xx++){
			EnemyModel em = enemyModels.get(xx);
			
			// Remove enemy from active enemies if dead
			if(em.getDead() == true){
			    enemyModels.remove(xx);
			    xx--;
			}
			// Otherwise update the enemies
			else{
			    em.update(dt);
			    
			    // Creates a bullet to travel from enemies position to players position(both positions in respect to time of roll)
			    if (em.shootBullet()){                                                                 
				Vector2 playerPos = new Vector2(playerModel.getXPos(),playerModel.getYPos());          // Obtains player's location to shoot towards that direction
				Vector2 enemyPos = new Vector2(em.getXPos(),em.getYPos());							   // Obtains enemy position at time of shot
				Vector2 dir = enemyPos.sub(playerPos);                                                 // Creates a vector for bullets travel during its life span
				enemyBullets.add(new EnemyBullet(em.xPos+(em.width/2),em.yPos+(em.height/2),dir));
			    }
				
			    // Player dies if he collides with enemy
				if(Utils.boxCollision(em.getBoundingBox(),playerModel.getBoundingBox())){
					playerDeath();
				}
			}
		}
	}
	
	/** Checks if any active bullets contact user and deletes those that are off-screen*/
	public void manageBullets(float dt)
	{
		for(int xx=0; xx < enemyBullets.size(); xx++){
			if(enemyBullets.get(xx).shouldDelete()){
				enemyBullets.remove(xx);
				xx--; // 
			}
			else{
				Bullet b = enemyBullets.get(xx);
				b.update(dt);
				if(b.collidesWith(playerModel.getBoundingBox())){
					playerDeath();
				}
				
			}
		}
	}
	
	/** Updates all pickups locations on screen*/
	public void updatePickups(float dt)
	{
		for (int xx = 0; xx < levelPickups.size(); xx++) {
			levelPickups.get(xx).update(dt, scrollDelta);
		}
	}
	
	// Destroys all enemies and their bullets on screen
	public void cobaltBomb() {
		enemyBullets.clear();
		for (int activeEnemy = 0; activeEnemy < enemyModels.size(); activeEnemy++) {
			enemyModels.get(activeEnemy).kill();
		}
	}
	
	// Configurations for player upon death
	public void playerDeath() {
		SoundManager.get().playSound("death");
		deathTimer = new MillisecTimer();
		playerModel.death();
		enemyBullets.clear();
		enemyModels.clear();

		// Game Over if player runs out of lives
		if (playerModel.getLives() < 0) {
			modelController.setMainModel(new GameOverModel(modelController));
			modelController.getViewController().setMainView(new GameOverView(modelController.getViewController()));
		}
	}
	
	// returns time since death if 2 seconds haven't elapsed, 0 otherwise
	public float getDeathTimerDt() {
		if (deathTimer != null) {
			float dt = deathTimer.getDt();
			if (dt < 2000)
				return deathTimer.getDt();
			else {
				deathTimer = null;
				return 0.0f;
			}
		} 
		else {
			return 0.0f;
		}
	}
	
	/* This function takes a LevelMap which is stored in a JSON file and loads it
	 * --- A JSON file is basically a file that's used to store a ton of different 
	 * 	   types of information                                                   */
    private void load(String filename){
		
		BufferedReader filein = null;
		BufferedImage tileSet = null;
		
		try{
			filein = new BufferedReader(new FileReader(filename));
			String linein;
			String json = "";
			while((linein = filein.readLine())!= null){
				json = json + linein;
			}
			// Parses the Level json file into a JSON object to prepare for splitting the data between attributes
			JSONObject jsonObject = (JSONObject)JSONValue.parse(json);
			
			tileMapHeight = ((Number)jsonObject.get("height")).intValue();   
			tileMapWidth = ((Number)jsonObject.get("width")).intValue();     
			tileWidth = ((Number)jsonObject.get("tilewidth")).intValue();
			tileHeight = ((Number)jsonObject.get("tileheight")).intValue();
			
			
			JSONArray tilesets = (JSONArray)jsonObject.get("tilesets");
			JSONObject tilesetsInfo = (JSONObject)tilesets.get(0);       // "firstgid:1"
			String tileMapFile = (String)tilesetsInfo.get("image");      // gets location of tileset.png
			
			tileSet = ImageIO.read(new File("assets/"+tileMapFile));
		  
			int rows = tileSet.getHeight() / tileHeight;
			int cols = tileSet.getWidth() / tileWidth;
			
			// Loads each individual tile of the tileset file into the buffer to be quickly 
			// reference when setting up the level
			tileImages = new BufferedImage[rows * cols];
			for(int yy = 0; yy < rows; yy++){
				for(int xx = 0; xx < cols; xx++){
					tileImages[yy * rows + xx] = tileSet.getSubimage(xx * tileWidth, yy * tileHeight, tileWidth, tileHeight);
				}
			}

			
			JSONArray layersArray = (JSONArray)jsonObject.get("layers");
			JSONObject backgroundLayer = (JSONObject)layersArray.get(0);
			
			JSONArray mapData = (JSONArray)backgroundLayer.get("data");    // gets data array(line:4 in testlevel)
			tileMap = new int[mapData.size()];							   // gets data array's size
			for(int xx = 0; xx < tileMap.length; xx++){                    // Loads the data array into memory
				tileMap[xx] = ((Number)mapData.get(xx)).intValue();		   // ^^^
			}
			/* The data array is basically the level map that's created from using the tiles in
			 * the tileset.png file. Each entry in "data" references which tile from the tileset goes
			 * to that particular location on the level's map(tilemap).
			 * Quick Breakdown(test level):
			 * 		tileMapHeight = 14     -- since there's 14 vertical tiles of gameplay, with the other 2 used for the gui
			 * 		tileMapWidth = 160     -- you only see 16 at a time(scrolls through them all eventually)
			 * 		tilemap Height * tileMapWidth = 14 * 160 = 2240 total tiles to represent the level's map(tileMap)
			 * 
			 * 		Thus the data array in the json file, which is loaded into mapData, is of size 2240, with each entry
			 * 		referencing the tile that goes to that particular position of the level's map(as previously stated).			 * 		
			 */
			
			// Loads all enemies and items into queues (currently only speed-pod and enemy-flyer in test level JSON file)
			JSONObject objectLayer = (JSONObject)layersArray.get(1);
			JSONArray objectList = (JSONArray)objectLayer.get("objects");
			for (int xx = 0; xx < objectList.size(); xx++) {
				JSONObject object = (JSONObject) objectList.get(xx);
				
				if(((String)object.get("type")) =="enemy-flyer"){
					queuedEnemies.add((EnemyModel)new FlyerModel(((Number)object.get("x")).intValue(),((Number)object.get("y")).intValue()));
				}
				
				if(((String)object.get("type")) =="speed-pod"){
					levelPickups.add(new Pickup(((Number)object.get("x")).intValue(),((Number)object.get("y")).intValue(),"speed","assets/speedPickupImage.png"));
				}
				
				if(((String)object.get("type")) == "defense-pod"){
					levelPickups.add(new Pickup(((Number)object.get("x")).intValue(),((Number)object.get("y")).intValue(),"defense_pod","assets/defensePodImage.png"));
				}
				
				
			}
		} // - end try
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if (filein != null) {
			try {
				filein.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			}
		}
    }

    public int getScrollDistance(){
		return (int)distanceScrolled;
    }
    public int getScrollDelta(){
	return scrollDelta;
    }
	    
    public int[] getTileMap(){
		return tileMap;
    }
    public BufferedImage getTileImage(int idx){
		return tileImages[idx];
    }
    public int getTileMapWidth(){
		return tileMapWidth;
    }
    public int getTileMapHeight(){
		return tileMapHeight;
    }
    public int getTileWidth(){
		return tileWidth;
    }
    public int getTileHeight(){
		return tileHeight;
    }

    public ModelController getModelController(){
		return modelController;
    }

    public void addEnemyModel(EnemyModel em){
	enemyModels.add(em);
    }
	
	public ArrayList<EnemyModel> getEnemyModels(){
		return enemyModels;
	}
    
    public ArrayList<Bullet> getEnemyBullets(){
	return enemyBullets;
    }
    
    public ArrayList<Pickup> getLevelPickups(){
	return levelPickups;
    }
      
    public HashMap<String,Model> getVisibleModels(){
		HashMap<String,Model> rv = new HashMap<String,Model>();
		rv.put("playerModel",playerModel);
		return rv;
    }
}
