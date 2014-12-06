import static org.junit.Assert.*;
import org.junit.*;
import java.awt.Rectangle;

public class TestBossModel {
	private BossModel boss;	
	
	@Test
	public void update_NotLowlifeAnd20Health_CorrectFieldUpdates() {
		boss = new BossModel(50,50);
		boss.health = 20;
		
		double preUpdateDir = boss.dir;
		double preUpdateTheta = boss.theta;		
		int preUpdateArmPieces = boss.armPieces.size();
		
		
		
		// Pre-conditions
		assertTrue("speed should initialize to 1", boss.speed == 1);
		assertTrue("lowlife should initialize to false", boss.lowlife == false);		
		
		boss.update(1);		
		assertTrue("Lowlife should become true", boss.lowlife == true);
		assertTrue("Speed should increase to 3", boss.speed == 3);
		assertTrue("Theta should have decreased ", preUpdateTheta > boss.theta);		
		assertTrue("armPieces should have increaded", preUpdateArmPieces < boss.armPieces.size());
		
	}
	
	@Test 
	public void test_update_XPos_xdirSignFlip() {
		boss = new BossModel(128,30);
		
		assertTrue("xdir should be 1", boss.xdir == 1);
		boss.update(0);
		assertTrue("xdir should have flipped to -1", boss.xdir == -1);	
		boss.xPos= ViewController.SCREEN_WIDTH;
		boss.update(0);
		assertTrue("xdir should flip back to 1", boss.xdir == 1);
	}
	
	@Test 
	public void test_update_YPos_ydirSignFlip() {
		boss = new BossModel(30,ViewController.SCREEN_HEIGHT);
		
		assertTrue("ydir should initilize -1", boss.ydir == -1);
		boss.update(0);
		assertTrue("ydir should have flipped to 1", boss.ydir == 1);	
		boss.yPos= 33;
		boss.update(0);
		assertTrue("ydir should flip back to -1", boss.ydir == -1);
	}
	
	@Test
	public void test_update_curFrame() {
		boss = new BossModel(100, 100);
		
		assertTrue("curFrame should initilize to 3", boss.curFrame == 3);
		
		boss.update(0);
		assertTrue("curFrame should have shifted to 3", boss.curFrame == 2);
		
		boss.curFrame = 0;
		boss.update(0);
		assertTrue("curFrame should have shifted to 1", boss.curFrame == 1);
		
		boss.update(0);
		assertTrue("curFrame should have shifted to 0", boss.curFrame == 0);		
	}
	
	
	@Test 
	public void testCheckBullet() {
		boss = new BossModel(100,100);
		int beforeBullet = boss.health;
		Bullet metrics = new Bullet(0, 0, 0);
		
		int bulletWidth = metrics.bulletImage.getWidth();
		int bulletHeight = metrics.bulletImage.getHeight();
		
		// Misses
		Bullet bulletA = new Bullet(102 - bulletWidth, 115, 0);
		Bullet bulletB = new Bullet(103, 112 - bulletWidth, 0);
		Bullet bulletC = new Bullet(0,0, 0);
		
		// Hits
		Bullet bulletD = new Bullet(102, 115, 0);
		Bullet bulletE = new Bullet(103, 112, 0);
		Bullet bulletF = new Bullet(116, 180, 0);	
		
		
		
		assertTrue("Boss direction should initilize to false", boss.direction == false);
		
		boss.checkBullet(bulletA);
		assertTrue("bulletA should have missed", 
					!boss.checkBullet(bulletA) && boss.health == beforeBullet);		
		
		assertTrue("bulletB should have missed", 
					!boss.checkBullet(bulletB) && boss.health == beforeBullet);		
		
		assertTrue("bulletC should have missed", 
					!boss.checkBullet(bulletC) && boss.health == 100);
		
		
		assertTrue("bulletD should have hit for" + bulletD.power + " damage", 
					boss.checkBullet(bulletD) && boss.health == beforeBullet - bulletD.power);
		
		beforeBullet = boss.health;
		assertTrue("bulletE should have hit for" + bulletE.power + " damage", 
					boss.checkBullet(bulletE) && boss.health == beforeBullet - bulletE.power);
		
		beforeBullet = boss.health;
		assertTrue("bulletF should have hit for" + bulletF.power + "damage", 
					boss.checkBullet(bulletF) && boss.health == beforeBullet - bulletF.power);
		
	}
	
}
