import static org.junit.Assert.*;
import org.junit.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class TestEnemyModel{
	
	@Test
	public void testEnemyModelA()
	{
		EnemyModel enemy = new EnemyModel();
		assertEquals(0, enemy.getXPos());
		enemy.setXPos(1);
		assertEquals(1, enemy.getXPos());
		assertEquals(0, enemy.getYPos());
		enemy.setYPos(1);
		assertEquals(1, enemy.getYPos());
		assertEquals(0, enemy.getWidth());
		assertEquals(0, enemy.getHeight());
		assertFalse(enemy.isDead());
		assertFalse(enemy.shootBullet);
		enemy.kill();
		assertTrue(enemy.isDead());
		assertTrue(enemy.hit(1));
		assertTrue(enemy.getBoundingBox() instanceof Rectangle);
		assertTrue(enemy.getEnemyImage() instanceof BufferedImage);
		assertTrue(enemy.getDrop() instanceof Pickup[]);
		assertTrue(enemy.getVisibleModels() instanceof HashMap<String,Model>);
		assertEquals(0, enemy.update(1.0));
	}
	
	@Test
	public void testEnemyModelB()
	{
		EnemyModel enemy = new EnemyModel(0,0);
		assertEquals(0, enemy.getXPos());
		enemy.setXPos(1);
		assertEquals(1, enemy.getXPos());
		assertEquals(0, enemy.getYPos());
		enemy.setYPos(1);
		assertEquals(1, enemy.getYPos());
		assertEquals(0, enemy.getWidth());
		assertEquals(0, enemy.getHeight());
		assertFalse(enemy.isDead());
		assertFalse(enemy.shootBullet);
		enemy.kill();
		assertTrue(enemy.isDead());
		assertTrue(enemy.hit(1));
		assertTrue(enemy.getBoundingBox() instanceof Rectangle);
		assertTrue(enemy.getEnemyImage() instanceof BufferedImage);
		assertTrue(enemy.getDrop() instanceof Pickup[]);
		assertTrue(enemy.getVisibleModels() instanceof HashMap<String,Model>);
		assertEquals(0, enemy.update(1.0));
	}
	
	@Test
	public void testEnemyModelC()
	{
		EnemyModel enemy = new EnemyModel(0,0,5);
		assertEquals(0, enemy.getXPos());
		enemy.setXPos(1);
		assertEquals(1, enemy.getXPos());
		assertEquals(0, enemy.getYPos());
		enemy.setYPos(1);
		assertEquals(1, enemy.getYPos());
		assertEquals(0, enemy.getWidth());
		assertEquals(0, enemy.getHeight());
		assertFalse(enemy.isDead());
		assertFalse(enemy.shootBullet);
		enemy.kill();
		assertTrue(enemy.isDead());
		assertTrue(enemy.hit(1));
		assertTrue(enemy.getBoundingBox() instanceof Rectangle);
		assertTrue(enemy.getEnemyImage() instanceof BufferedImage);
		assertTrue(enemy.getDrop() instanceof Pickup[]);
		assertTrue(enemy.getVisibleModels() instanceof HashMap<String,Model>);
		assertEquals(0, enemy.update(1.0));
	}
	
}