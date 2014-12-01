import static org.junit.Assert.*;
import org.junit.*;
import java.awt.Rectangle;

public class TestRingBullet{
	
	@Test
	public void testRingBullet()
	{
		RingBullet bullet = new RingBullet(1,1,1);
		assertNotNull(bullet);
		assertNotNull(bullet.bulletImage);
		assertEquals(1, bullet.getPower()); // Default bullet power
		assertEquals(10, bullet.getMillisecDelay()); // Default MillisecDelay 
	}

	
}