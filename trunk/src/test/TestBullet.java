import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import org.junit.*;
import static org.junit.Assert.*;


public class TestBullet
{
	@Test
	public void testBullet()
	{
		Bullet bullet = new Bullet(1,1,1);
		assertNotNull(bullet);
		assertNotNull(bullet.bulletImage)
		assertEquals(1, bullet.getPower()); // Default bullet power
		assertEquals(10, bullet.getMillisecDelay()); // Default MillisecDelay 
	}


	
	@Test
	public void testCollidesWith()
		{
		Bullet bullet = new Bullet(1, 1, 1);
		Rectangle boundingBox = new Rectangle(1,1,1,1);
		// It should collide with this box
		assertTrue(bullet.collidesWith(boundingBox);
	}

	@Test
	public void testGetBoundingBox()
		{
		Bullet bullet = new Bullet(1, 1, 1);

		Rectangle result = bullet.getBoundingBox();

		assertNotNull(result);
		assertEquals(1.0, result.getY(), 1.0);
		assertEquals(1.0, result.getX(), 1.0);

	}

	@Test
	public void testUpdate()
		{
		Bullet bullet = new Bullet(1, 1, 1);
		assertEquals(0, bullet.update(1.0f));
	}
}