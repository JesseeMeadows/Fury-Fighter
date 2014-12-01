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

		assertEquals(FlyerModel.SHOOT_BULLET_MIN, 0);
		assertEquals(FlyerModel.SHOOT_BULLET_MAX, 19);
		assertEquals(FlyerModel.SHOOT_BULLET_CONDITION, 0);

		while (!seenTrue || !seenFalse)
			if (flyerModel.shootBullet() == true)
				seenTrue = true;
			else
				seenFalse = true;
	}

	@Test
	public void testGetDrop()
	{
		int i;

		boolean seenPickup = false;
		boolean seenWeaponPickup = false;
		boolean seenNull = false;

		Pickup[] pickups = null;
		Pickup pickup = null;

		assertEquals(FlyerModel.DROP_BOMB_FRAGMENT_MIN, 0);
		assertEquals(FlyerModel.DROP_BOMB_FRAGMENT_MAX, 4);
		assertEquals(FlyerModel.DROP_BOMB_CONDITION, 1);

		assertEquals(FlyerModel.DROP_WEAPON_POD_MIN, 0);
		assertEquals(FlyerModel.DROP_WEAPON_POD_MAX, 4);
		assertEquals(FlyerModel.DROP_WEAPON_POD_CONDITION, 0);

		while (!seenPickup || !seenWeaponPickup || !seenNull)
		{
			pickups = flyerModel.getDrop();

			for (i = 0; i < 2; i++) {
				pickup = pickups[i];

				if (pickup == null)
					seenNull = true;
				else if (pickup.getClass() == WeaponPickup.class)
					seenWeaponPickup = true;
				else if (pickup.getClass() == Pickup.class)
					seenPickup = true;
			}
		}
	}
}
