import java.util.HashMap;
import java.util.Properties;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
    
    private PlayerModel     playerModel;
    private ModelController modelController;
    private JSONObject      levelFile;

    
    private TileMap tileMap;
    private int mapWidthInPixels; 


    private float distanceScrolled;  // Total distance scrolled from beginning: pixel units
    private float scrollVelocity;    // Speed screen scrolls
    private int   scrollDelta;         // Distance scrolled the previous frame
    
    
    
    private ArrayList<EnemyModel> activeEnemies;   // Contains each enemy model that's currently active
    private ArrayList<EnemyModel> queuedEnemies;   // Contains enemies waiting to be active
    private ArrayList<Bullet>     activeBullets;   // Contains active enemy bullets on screen
    private ArrayList<Pickup>     levelPickups;    // Contains active enemy drops on screen



    public boolean paused;
	
    private MillisecTimer deathTimer;
	
    LevelModel(ModelController theModelController, String fileLocation){
		modelController = theModelController;
		levelFile = loadToJson(fileLocation);
		tileMap = new TileMap(levelFile);
		playerModel = new PlayerModel(this, tileMap);
		theModelController.getViewController().getDrawPanel().getInputHandler().registerInputResponder(playerModel);
		
		
		
		distanceScrolled = 0.0f;
		scrollVelocity = 0.05f;
		scrollDelta = 0;		
				
		queuedEnemies = new ArrayList<EnemyModel>();
		activeEnemies = new ArrayList<EnemyModel>();
		activeBullets = new ArrayList<Bullet>();
		levelPickups  = new ArrayList<Pickup>();
		
		// Retrieves Enemies, pickups, and tileMap
		loadObjects(levelFile, queuedEnemies, levelPickups);
		
		paused = false;
		
		deathTimer = null;
		
		SoundManager.get().playSound("music");
		
		mapWidthInPixels = tileMap.getTileMapWidth() * tileMap.getTileWidth();
		
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
	private void mapScroll(float dt)
	{
		boolean endOfLevel = distanceScrolled >= mapWidthInPixels - (ViewController.SCREEN_WIDTH + 33);
				
		if (!endOfLevel) {
			// Scroll right
			scrollDelta = (int) (scrollVelocity * dt);
			distanceScrolled += scrollDelta;
		}
		else {
			scrollDelta = 0; // end of map
		}
	}
	
	/**Adds new enemies to screen(offscreen technically) when their start coordinate(xPos) appears on screen*/
	private void spawnEnemies()
	{
		for (int i = 0; i < queuedEnemies.size(); i++) {
			EnemyModel enemy = queuedEnemies.get(i);
			boolean enemyCoordReached = distanceScrolled + ViewController.SCREEN_WIDTH > enemy.getXPos();		
			
			if (enemyCoordReached) {
				activateEnemy(enemy);
				queuedEnemies.remove(i);
				i--;
			}

		}
	}
	
	/** Removes dead enemies, updates living/active enemies, and creates enemy bullets*/
	private void updateEnemies(float dt)
	{
		// for statement:
		for (int i = 0; i < activeEnemies.size(); i++) {
			EnemyModel enemy = activeEnemies.get(i);

			// Remove enemy from active enemies if dead
			if (enemy.isDead()) {
				activeEnemies.remove(i);
				i--;
			}
			// Otherwise update the enemies
			else{
			    enemy.update(dt);
			    
			    // Creates a bullet to travel from enemies position to players position(both positions in respect to time of roll)
			    if (enemy.shootBullet()){                                                                 
					Vector2 playerPos = new Vector2(playerModel.getXPos(), playerModel.getYPos());          // Obtains player's location to shoot towards that direction
					Vector2 enemyPos = new Vector2(enemy.getXPos(), enemy.getYPos());							   // Obtains enemy position at time of shot
					Vector2 dir = enemyPos.sub(playerPos);                                       // Creates a vector for bullets travel during its life span
					activeBullets.add(new EnemyBullet(enemy.xPos + (enemy.width / 2), enemy.yPos + (enemy.height / 2), dir));
			    }
				
			    // Player dies if he collides with enemy
				if(Utils.boxCollision(enemy.getBoundingBox(),playerModel.getBoundingBox())){
					playerDeath();
				}
			}
		}
	}
	
	/** Checks if any active bullets contact user and deletes those that are off-screen*/
	private void manageBullets(float dt)
	{
		for (int i = 0; i < activeBullets.size(); i++) {
			if (activeBullets.get(i).shouldDelete()) {
				activeBullets.remove(i);
				i--; //
			}
			else {
				Bullet bullet = activeBullets.get(i);
				bullet.update(dt);
				if (bullet.collidesWith(playerModel.getBoundingBox())) {
					playerDeath();
				}

			}
		}
	}
	
	/** Updates all pickups locations on screen*/
	private void updatePickups(float dt)
	{
		for (int i = 0; i < levelPickups.size(); i++) {
			levelPickups.get(i).update(dt, scrollDelta);
		}
	}

	// Destroys all enemies and their bullets on screen
	public void cobaltBomb()
	{
		activeBullets.clear();
		for (int i = 0; i < activeEnemies.size(); i++) {
			activeEnemies.get(i).kill();
		}
	}
	
	// Configurations for player upon death
	public void playerDeath()
	{
		SoundManager.get().playSound("death");
		deathTimer = new MillisecTimer();
		playerModel.death();
		activeBullets.clear();
		activeEnemies.clear();

		// Game Over if player runs out of lives
		if (playerModel.getLives() < 0) {
			modelController.setMainModel(new GameOverModel(modelController));
			modelController.getViewController().setMainView(new GameOverView(modelController.getViewController()));
		}
	}
	
	// returns time since death if 2 seconds haven't elapsed, 0 otherwise
	public float getDeathTimerDt()
	{
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
	
	private JSONObject loadToJson(String filename) {
		BufferedReader filein = null;
		String linein;
		String json = "";
		
		try {
			filein = new BufferedReader(new FileReader(filename));
			while ((linein = filein.readLine()) != null) {
				json = json + linein;
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (filein != null) {
				try {
					filein.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return (JSONObject) JSONValue.parse(json);
	}		

    public int getDistanceScrolled(){
		return (int)distanceScrolled;
    }
    
    public int getScrollDelta(){
	return scrollDelta;
    }
	    
    public TileMap getTileMap(){
		return tileMap;
    }
//    public BufferedImage getTileImage(int idx){
//		return tileImages[idx];
//    }   

    public ModelController getModelController(){
		return modelController;
    }

    public void activateEnemy(EnemyModel em){
	activeEnemies.add(em);
    }
	
	public ArrayList<EnemyModel> getActiveEnemies(){
		return activeEnemies;
	}
    
    public ArrayList<Bullet> getEnemyBullets(){
	return activeBullets;
    }
    
    public ArrayList<Pickup> getLevelPickups(){
	return levelPickups;
    }
      
    public HashMap<String,Model> getVisibleModels(){
		HashMap<String,Model> rv = new HashMap<String,Model>();
		rv.put("playerModel",playerModel);
		return rv;
    }
    
//    public BufferedImage compileTileMap(int[] tileReferences, TileSet tileset) {
//    	int tileWidth  = tileset.getTileWidth();
//    	int tileHeight = tileset.getTileHeight();
//    	int tileMapWidth  = tileset.getTileMapWidth();
//    	int tileMapHeight = tileset.getTileMapHeight(); 
//    	
//    	BufferedImage tileMap = new BufferedImage()
//    }

    private void loadObjects(JSONObject levelFile, ArrayList<EnemyModel> enemyQueue, ArrayList<Pickup> pickupQueue) {
    	String objectType;

    	JSONArray layersArray = (JSONArray) levelFile.get("layers");
		JSONObject objectLayer = (JSONObject) layersArray.get(1);
		JSONArray objectList = (JSONArray) objectLayer.get("objects");

			for (int i = 0; i < objectList.size(); i++) {
				// Retrieve Object
				JSONObject object = (JSONObject) objectList.get(i);
				objectType = (String) (object.get("type"));

				if (objectType == "enemy-flyer") {
					enemyQueue.add((EnemyModel) new FlyerModel( ((Number) object.get("x")).intValue(), 
																   ((Number) object.get("y")).intValue()));
				}
				else if (objectType == "speed-pod") {
					pickupQueue.add(new Pickup( ((Number) object.get("x")).intValue(),
											     ((Number) object.get("y")).intValue(), 
											     "speed", "assets/speedPickupImage.png"));
				}
				else if (objectType == "defense-pod") {
					pickupQueue.add(new Pickup( ((Number) object.get("x")).intValue(), 
								   				 ((Number) object.get("y")).intValue(), 
								   				  "defense_pod", "assets/defensePodImage.png"));
				}
				
				
			}
    }
    
}
