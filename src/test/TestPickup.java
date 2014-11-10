import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.junit.*;
import static org.junit.Assert.*;


public class TestPickup {

	@Test
	public void testPickup()
		{
		Pickup pickup = new Pickup(1, 1, "unknown");
		assertNotNull(pickup);
		assertEquals("unknown", pickup.getType());
		assertEquals(0, pickup.getWidth());
		assertEquals(0, pickup.getHeight());
		assertEquals(1, pickup.getYPos());
		assertEquals(1, pickup.getXPos());
	}
	
	@Test
	public void testGetType()
		{
		Pickup pickup = new Pickup(1, 1, "unknown");
		assertEquals("unknown", pickup.getType()); 
	}

	
	@Test
	public void testUpdate()
		{
		Pickup pickup = new Pickup(1, 1, "");
		int temp=pickup.getXPos();
		assertEquals(0, pickup.update(1.0f, 1));
		assertTrue(pickup.getXPos()==0); //Since it subbtracts dt. So 1 - 1 = 0
	}
}