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


public class LevelModel extends Model{
    
    private PlayerModel playerModel;
    private ModelController modelController;

    private int tileMapWidth;
    private int tileMapHeight;
    private int tileWidth;
    private int tileHeight;

    private float scrollDistance;
    private float scrollVelocity;
    private int scrollDelta;
    
    private BufferedImage[] tileImages;
    private int[] tileMap;
    
    private ArrayList<EnemyModel> enemyModels;
    private ArrayList<EnemyModel> queuedEnemies;
    private ArrayList<Bullet> enemyBullets;
    private ArrayList<Pickup> levelPickups;

    public boolean paused;
	
    private MillisecTimer deathTimer;
	
    LevelModel(ModelController m){
		this.modelController = m;
		this.playerModel = new PlayerModel(this);
		m.getViewController().getDrawPanel().getInputHandler().registerInputResponder(this.playerModel);
		
		this.scrollDistance = 0.0f;
		this.scrollVelocity = 0.05f;
		this.scrollDelta = 0;
		
		this.load("assets/test_level.json");
		
		this.enemyModels = new ArrayList<EnemyModel>();
		this.enemyBullets = new ArrayList<Bullet>();
		this.levelPickups = new ArrayList<Pickup>();
		
		paused = false;
		
		this.deathTimer = null;
		
		SoundManager.get().playSound("music");
		
    }
    public int update(float dt){
	
		if(paused || this.getDeathTimerDt() > 0){
			return 0;
		}
		if(this.scrollDistance < this.tileMapWidth*this.tileWidth - (ViewController.SCREEN_WIDTH +33)){
		    this.scrollDelta = (int)(this.scrollVelocity *dt);
		    this.scrollDistance += this.scrollDelta;
		}
		else{
		    this.scrollDelta = 0;
		}
		
		for(int xx=0;xx<this.queuedEnemies.size();xx++){
			EnemyModel em = this.queuedEnemies.get(xx);
			if(this.scrollDistance + ViewController.SCREEN_WIDTH > em.getXPos() ){
				this.addEnemyModel(em);
				this.queuedEnemies.remove(xx);
				xx--;
			}
			
		}
		
		this.playerModel.update(dt);
		
		for(int xx=0;xx<this.enemyModels.size();xx++){
			EnemyModel em = this.enemyModels.get(xx);
			if(em.getDead() == true){
			    this.enemyModels.remove(xx);
			    xx--;
			}
			else{
			    em.update(dt);
			    if(em.shootBullet()){
				Vector2 playerPos = new Vector2(this.playerModel.getXPos(),this.playerModel.getYPos());
				Vector2 enemyPos = new Vector2(em.getXPos(),em.getYPos());
				Vector2 dir = enemyPos.sub(playerPos);
				this.enemyBullets.add(new EnemyBullet(em.xPos+(em.width/2),em.yPos+(em.height/2),dir));
			    }
				
				if(Utils.boxCollision(em.getBoundingBox(),playerModel.getBoundingBox())){
					this.playerDeath();
				}
			}
		}
		
		for(int xx=0;xx<this.enemyBullets.size();xx++){
			if(this.enemyBullets.get(xx).shouldDelete()){
				this.enemyBullets.remove(xx);
				xx--;
			}
			else{
				Bullet b = this.enemyBullets.get(xx);
				b.update(dt);
				if(b.collidesWith(this.playerModel.getBoundingBox())){
					this.playerDeath();
				}
				
			}
		}
		
		for(int xx=0;xx<this.levelPickups.size();xx++){
		    this.levelPickups.get(xx).update(dt,this.scrollDelta);
		}
		
		return 0;
    }
	
	public void cobaltBomb(){
		this.enemyBullets.clear();
		for(int xx=0;xx<this.enemyModels.size();xx++){
			this.enemyModels.get(xx).kill();
		}
	}
	
    public void playerDeath(){
	SoundManager.get().playSound("death");
	this.deathTimer = new MillisecTimer();
	this.playerModel.death();
	this.enemyBullets.clear();
	this.enemyModels.clear();
	
	if(playerModel.getLives() < 0){
	    this.modelController.setMainModel(new GameOverModel(this.modelController));
	    this.modelController.getViewController().setMainView(new GameOverView(this.modelController.getViewController()));
	}
    }
	
    public float getDeathTimerDt(){
	if(this.deathTimer != null){
		float dt = this.deathTimer.getDt();
		if(dt < 2000)
			return this.deathTimer.getDt();
		else{
			this.deathTimer = null;
			return 0.0f;
		}
	}
	else{
		return 0.0f;
	}
    }
	
    public HashMap<String,Model> getVisibleModels(){
		HashMap<String,Model> rv = new HashMap<String,Model>();
		rv.put("playerModel",this.playerModel);
		return rv;
    }

    public int getScrollDistance(){
		return (int)this.scrollDistance;
    }
    public int getScrollDelta(){
	return this.scrollDelta;
    }
	    
    public int[] getTileMap(){
		return this.tileMap;
    }
    public BufferedImage getTileImage(int idx){
		return this.tileImages[idx];
    }
    public int getTileMapWidth(){
		return this.tileMapWidth;
    }
    public int getTileMapHeight(){
		return this.tileMapHeight;
    }
    public int getTileWidth(){
		return this.tileWidth;
    }
    public int getTileHeight(){
		return this.tileHeight;
    }

    public ModelController getModelController(){
		return this.modelController;
    }

    public void addEnemyModel(EnemyModel em){
	this.enemyModels.add(em);
    }
	
	public ArrayList<EnemyModel> getEnemyModels(){
		return this.enemyModels;
	}
    
    public ArrayList<Bullet> getEnemyBullets(){
	return this.enemyBullets;
    }
    
    public ArrayList<Pickup> getLevelPickups(){
	return this.levelPickups;
    }
    
    
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
			
			JSONObject jsonObject = (JSONObject)JSONValue.parse(json);
			
			this.tileMapHeight = ((Number)jsonObject.get("height")).intValue();
			this.tileMapWidth = ((Number)jsonObject.get("width")).intValue();
			this.tileWidth = ((Number)jsonObject.get("tilewidth")).intValue();
			this.tileHeight = ((Number)jsonObject.get("tileheight")).intValue();
			
			JSONArray tilesets = (JSONArray)jsonObject.get("tilesets");
			JSONObject tilesetsInfo = (JSONObject)tilesets.get(0);
			String tileMapFile = (String)tilesetsInfo.get("image");
			
			tileSet = ImageIO.read(new File("assets/"+tileMapFile));
		  
			int rows = tileSet.getHeight() / this.tileHeight;
			int cols = tileSet.getWidth() / this.tileWidth;
			
			this.tileImages = new BufferedImage[rows * cols];

			for(int yy = 0; yy < rows; yy++){
				for(int xx = 0; xx < cols; xx++){
					this.tileImages[yy * rows + xx] = tileSet.getSubimage(xx * this.tileWidth, yy * this.tileHeight, this.tileWidth, this.tileHeight);
				}
			}

			JSONArray layersArray = (JSONArray)jsonObject.get("layers");
			JSONObject backgroundLayer = (JSONObject)layersArray.get(0);
			
			JSONArray mapData = (JSONArray)backgroundLayer.get("data");
			this.tileMap = new int[mapData.size()];
			for(int xx = 0; xx < this.tileMap.length; xx++){
				this.tileMap[xx] = ((Number)mapData.get(xx)).intValue();
			}
			
			JSONObject objectLayer = (JSONObject)layersArray.get(1);
			JSONArray objectList = (JSONArray)objectLayer.get("objects");
			for(int xx=0;xx<objectList.size();xx++){
				JSONObject object = (JSONObject)objectList.get(xx);
				if(((String)object.get("type")) =="enemy-flyer"){
					this.queuedEnemies.add((EnemyModel)new FlyerModel(((Number)object.get("x")).intValue(),((Number)object.get("y")).intValue()));
				}
				if(((String)object.get("type")) =="speed-pod"){
					this.levelPickups.add(new Pickup(((Number)object.get("x")).intValue(),((Number)object.get("y")).intValue(),"speed","assets/speedPickupImage.png"));
				}
				if(((String)object.get("type")) == "defense-pod"){
					this.levelPickups.add(new Pickup(((Number)object.get("x")).intValue(),((Number)object.get("y")).intValue(),"defense_pod","assets/defensePodImage.png"));
				}
			}
		}
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
}
