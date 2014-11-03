import java.lang.InterruptedException;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class TestFlyerModel
{
	private FlyerModel flyerModel;

	@Before
	public void instantiate()
	{
		flyerModel = new FlyerModel(100, 100);
	}

	@Test
	public void testUpdate()
	{
		float dt = 89.7f;
		flyerModel.update(dt);

		assertEquals(flyerModel.getXPos(), (int)(100.0f - (.1 * dt)));
		assertEquals(flyerModel.getYPos(), (int)(100 + (Math.sin(Math.toRadians(20)) * 5)));
	}

	@Test
	public void testShootBullets()
	{
		boolean seenTrue = false;
		boolean seenFalse = false;

		while (!seenTrue || !seenFalse)
			if (flyerModel.shootBullet() == true)
				seenTrue = true;
			else
				seenFalse = true;
	}

	@Test
	public void testGetDrop()
	{
		boolean seenPickup = false;
		boolean seenWeaponPickup = false;
		boolean seenNull = false;

		Pickup pickup = null;

		while (!seenPickup || !seenWeaponPickup || !seenNull)
		{
			pickup = flyerModel.getDrop();

			if (pickup == null)
				seenNull = true;
			else if (pickup.getClass() == WeaponPickup.class)
				seenWeaponPickup = true;
			else if (pickup.getClass() == Pickup.class)
				seenPickup = true;
		}
	}
}
