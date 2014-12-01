import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestLevelModel {
	private LevelModel levelModel;
	private ModelController modelController;	
	private ViewController viewController;
	
	private float scrollVelocity;
	
	@Before
	public void initialize() {
		modelController = new ModelController(null);
		viewController = new ViewController(modelController);
		
		viewController.setModelController(modelController);
		modelController.setViewController(viewController);
		
		
		levelModel = new LevelModel(modelController);
		scrollVelocity = levelModel.getScrollVelocity();	
	}
	
	@Test
	public void testUpdateBackground() 
	{
		int previousDistanceScrolled;
		
		// Computes value required for endOfLevel to be true 
		int  endOfLevel =    levelModel.getTileMap().getTileMapWidth() 
			               * levelModel.getTileMap().getTileWidth()
			               - (ViewController.SCREEN_WIDTH + 33);
		
		// Computes dt value(updateBackground input) needed to find point at which the end of level is reached
		float beforeEnd =  (endOfLevel - 1) / scrollVelocity;
		
		assertTrue("Pre-assumption failed", levelModel.getDistanceScrolled() == 0 );
		

		levelModel.updateBackground(beforeEnd);
		assertTrue("Background Failed to update", levelModel.getDistanceScrolled() > 0);
		
		
		// Ensures level updates up to the point at which it should no longer update
		float additiveForEnd = endOfLevel - levelModel.getDistanceScrolled();	
		previousDistanceScrolled = levelModel.getDistanceScrolled();
		levelModel.updateBackground(additiveForEnd / scrollVelocity);
		
		assertTrue("Background Failed to update to end-of-level point", 
				previousDistanceScrolled != levelModel.getDistanceScrolled());
		
		
		// Ensures level no Longer updates after reaching end of map
		previousDistanceScrolled = levelModel.getDistanceScrolled();
		levelModel.updateBackground(1000);
		assertTrue("Backgound updated when it shouldn't have", previousDistanceScrolled == levelModel.getDistanceScrolled());
				
	}
	
	
	@Test
	public void testSpawnEnemies() 
	{		
		float beforeBothEnemies;
				
		// Used in check in a enemy is activated correctly
		ArrayList<EnemyModel> queuedEnemies = levelModel.getQueuedEnemies();
	    ArrayList<EnemyModel> activeEnemies = levelModel.getActiveEnemies();
	    
	    queuedEnemies.clear();
	    activeEnemies.clear();
	    
	    // Test Enemies
	    FlyerModel enemyA = new FlyerModel(1000, 0);  // x-coord = 1000
	    FlyerModel enemyB = new FlyerModel(1000, 0);  // x-coord = 1000
	    
	    beforeBothEnemies = (enemyA.getXPos() - ViewController.SCREEN_WIDTH) / scrollVelocity; 
	    	    
	    queuedEnemies.add(enemyA);
	    queuedEnemies.add(enemyB);
	    
	    assertTrue("Enemies shouldn't be active", activeEnemies.isEmpty());
	    
	    // Before edge
	    levelModel.updateBackground(beforeBothEnemies);
	    levelModel.spawnEnemies();
	    assertTrue("Enemy shouldn't spawn", activeEnemies.isEmpty());
	    
	    // On edge -> should spawn
	    levelModel.updateBackground(1 / scrollVelocity);
	    levelModel.spawnEnemies();
	    assertTrue("EnemyA should have spawn", activeEnemies.contains(enemyA));
	    assertTrue("EnemyA should no longer be queued", !queuedEnemies.contains(enemyA));
	    assertTrue("EnemyB should have spawn", activeEnemies.contains(enemyB));
	    assertTrue("EnemyB should no longer be queued", !queuedEnemies.contains(enemyB));		
	}
	
	@Test
	public void testUpdateEnemies() {
		ArrayList<EnemyModel> activeEnemies = levelModel.getActiveEnemies();
		activeEnemies.clear();
		
		FlyerModel enemyA = new FlyerModel(1000, 200);
		FlyerModel enemyB = new FlyerModel(1500, 200);
	
		activeEnemies.add(enemyA);
		activeEnemies.add(enemyB);
		
		levelModel.updateEnemies(1);
		assertTrue("enemyA should be active", activeEnemies.contains(enemyA));
		assertTrue("enemyB should be active", activeEnemies.contains(enemyB));
		
		enemyB.kill();
		levelModel.updateEnemies(1);
		assertTrue("enemyA should still be active", activeEnemies.contains(enemyA));
		assertTrue("enemyB should be inactive", !activeEnemies.contains(enemyB));
		
		float preUpdateXpos = enemyA.getXPos();
		levelModel.updateEnemies(1);
		assertTrue("enemyA Xposition should have update", preUpdateXpos != enemyA.getXPos());
		
		
	}
	
	@Test
	public void testManageBullets() {
		ArrayList<Bullet> activeBullets = levelModel.getActiveBullets();
		activeBullets.clear();
		
		Bullet northPreEdge = new Bullet(0, -10, 0);
		Bullet northOnEdge  = new Bullet(0, -11, 0);
		Bullet eastPreEdge  = new Bullet(ViewController.SCREEN_WIDTH + 10, 100, 0);
		Bullet eastOnEdge   = new Bullet(ViewController.SCREEN_WIDTH + 11, 100, 0);
		Bullet southPreEdge = new Bullet(0, ViewController.SCREEN_HEIGHT - 72, 0);   // 72 = tile + bullet's image height
		Bullet southOnEdge  = new Bullet(0, ViewController.SCREEN_HEIGHT -71, 0);
		Bullet westPreEdge  = new Bullet(-10, 100, 0);
		Bullet westOnEdge   = new Bullet(-11, 100, 0);
		
		activeBullets.add(northPreEdge);
		activeBullets.add(northOnEdge);
		activeBullets.add(eastPreEdge);
		activeBullets.add(eastOnEdge);
		activeBullets.add(southPreEdge);
		activeBullets.add(southOnEdge);
		activeBullets.add(westPreEdge);
		activeBullets.add(westOnEdge);
		
		levelModel.manageBullets(0);
		
		assertTrue("northPreEdge should be active", activeBullets.contains(northPreEdge));
		assertTrue("eastPreEdge should be active", activeBullets.contains(eastPreEdge));
		assertTrue("southPreEdge should be active", activeBullets.contains(southPreEdge));
		assertTrue("westPreEdge should be active", activeBullets.contains(westPreEdge));
		
		assertTrue("northOnEdge should not be active", !activeBullets.contains(northOnEdge));
		assertTrue("eastOnEdge should not be active", !activeBullets.contains(eastOnEdge));
		assertTrue("southOnEdge should not be active", !activeBullets.contains(southOnEdge));
		assertTrue("westOnEdge should not be active", !activeBullets.contains(westOnEdge));
		
		Bullet killBullet = new Bullet(64, 128, 0);  // Corresponds to playerModel starting position
		activeBullets.clear();
		activeBullets.add(killBullet);
		
		levelModel.updateBackground(100);
		int scrolledDistance = levelModel.getDistanceScrolled();
		levelModel.manageBullets(0);		
		assertTrue("Player should of died, resetting scrolledDistance", scrolledDistance != levelModel.getDistanceScrolled());
		
		
		activeBullets.clear();
		Bullet checkUpdate = new Bullet( 0, 0, 2);  // direction = 2, to update xposition
		activeBullets.add(checkUpdate);
		
		double xPosPreUpdate = checkUpdate.xPos;
		levelModel.manageBullets(10);
		assertTrue("checkUpdate Bullet should be active", activeBullets.contains(checkUpdate));
		assertTrue("Bullet Location failed to udpate", xPosPreUpdate != checkUpdate.xPos);		
	}
	
	@Test
	public void testUpdatePickups() {
		ArrayList<Pickup> pickups = levelModel.getLevelPickups();
		pickups.clear();
		
		Pickup pickupA = new Pickup(100, 100, "");
		Pickup pickupB = new Pickup(50, 50, "");
		pickups.add(pickupA);
		pickups.add(pickupB);
		
		int preUpdateA = pickupA.xPos;
		int preUpdateB = pickupB.xPos;
		
		levelModel.updateBackground(30);
		levelModel.updatePickups(0);
		
		assertTrue("pickupA failed to update", preUpdateA != pickupA.xPos);
		assertTrue("pickupB failed to update", preUpdateB != pickupB.xPos);
	}
	
	@Test
	public void testCobaltBomb() {
		boolean allEnemiesDead = true;
		ArrayList<Bullet> activeBullets = levelModel.getActiveBullets();
		ArrayList<EnemyModel> activeEnemies = levelModel.getActiveEnemies();
		
		Bullet bulletA = new Bullet(50, 50, 0);
		Bullet bulletB = new Bullet(100, 100, 0);
		FlyerModel flyerA = new FlyerModel(20, 20);
		FlyerModel flyerB = new FlyerModel(40, 40);
		
		activeBullets.clear();
		activeEnemies.clear();
		
		activeBullets.add(bulletA);
		activeBullets.add(bulletB);
		activeEnemies.add(flyerA);
		activeEnemies.add(flyerB);
		
		levelModel.cobaltBomb();
		for (int i = 0; i < activeEnemies.size(); i++) {
			if (activeEnemies.get(i).isDead() != true)
				allEnemiesDead = false;
		}
		
		assertTrue("All bullets should of be cleared", activeBullets.isEmpty());
		assertTrue("Failed to kill all Enemies", allEnemiesDead);	
	}
	
	@Test 
	public void testPlayerDeath() {
		ArrayList<Bullet> activeBullets = levelModel.getActiveBullets();
		ArrayList<EnemyModel> activeEnemies = levelModel.getActiveEnemies();
		
		Bullet bulletA = new Bullet(50, 50, 0);
		Bullet bulletB = new Bullet(100, 100, 0);
		FlyerModel flyerA = new FlyerModel(20, 20);
		FlyerModel flyerB = new FlyerModel(40, 40);
		
		activeBullets.clear();
		activeEnemies.clear();
		
		activeBullets.add(bulletA);
		activeBullets.add(bulletB);
		activeEnemies.add(flyerA);
		activeEnemies.add(flyerB);
		
		levelModel.playerDeath(); // 3 - lives left
		
		assertTrue("ScrollDelta failed to reset", levelModel.getScrollDelta() == 0);
		assertTrue("DistanceScrolled failed to reset", levelModel.getDistanceScrolled() == 0);
		assertTrue("Failed to clear all enemies", activeEnemies.isEmpty());
		assertTrue("Failed to clear all activeBullets", activeBullets.isEmpty());
		
		levelModel.playerDeath(); //2 - lives left
		levelModel.playerDeath(); //1 - lives left
		levelModel.playerDeath(); //0 - lives left
		levelModel.playerDeath(); // GAMEOVER
		
		assertTrue("mainModel should be GameOverModel", modelController.getMainModel() instanceof GameOverModel);
		assertTrue("mainView should be GameOverView", viewController.getMainView() instanceof GameOverView);
	}
	
	@Test
	public void testDeathTimerDt() {
		assertTrue("DeathTimer should be null since player hasn't died", levelModel.getDeathTimerDt() == 0.0f);		
	}

}












































