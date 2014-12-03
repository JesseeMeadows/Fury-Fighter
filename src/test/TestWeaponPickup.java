import java.awt.image.BufferedImage;
import org.junit.*;
import static org.junit.Assert.*;


public class TestWeaponPickup {

	@Test
	public void testWeaponPickup(){
		int x = 1;
		int y = 1;

		WeaponPickup weaponPick = new WeaponPickup(x, y);

		assertNotNull(weaponPick);
		assertNotNull(weaponPick.pickupImage);
		assertTrue(weaponPick.getType().matches("ring|missile|laser")); // default type in the class in the constructor
		assertEquals(0, weaponPick.getWidth());
		assertEquals(0, weaponPick.getHeight());
	}


	@Test
	public void testUpdate(){
		WeaponPickup weaponPick = new WeaponPickup(1, 1);
		float dt = 1.0f;
		int dx = 1;

		assertEquals(0, weaponPick.update(dt, dx));
	}

}
