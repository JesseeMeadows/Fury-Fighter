import static org.junit.Assert.*;
import org.junit.*;
import java.awt.Rectangle;

public class TestUtils{
	
	protected Rectangle r1;
	protected Rectangle r2;
	
	@Before
	public void setUp()
	{
		r1 = new Rectangle(0,0,5,5);
		r2 = new Rectangle(0,0,5,5);
	}
	
	@Test
	public void TestBoxCollisionA()
	{
		//this tests to make sure boxCollision works when the rectangles haven't collided
		r2.setLocation(6,6);
		assertFalse(Utils.boxCollision(r1,r2));
		r2.setLocation(0,6);
		assertFalse(Utils.boxCollision(r1,r2));
		r2.setLocation(6,0);
		assertFalse(Utils.boxCollision(r1,r2));
	}
	
	@Test
	public void TestBoxCollisionB()
	{
		//this tests to make sure boxCollision works when the rectangles have collided
		r2.setLocation(5,5);
		assertTrue(Utils.boxCollision(r1,r2));
		r2.setLocation(0,5);
		assertTrue(Utils.boxCollision(r1,r2));
		r2.setLocation(5,0);
		assertTrue(Utils.boxCollision(r1,r2));
		r2.setLocation(0,0);
		assertTrue(Utils.boxCollision(r1,r2));
	}
	
}