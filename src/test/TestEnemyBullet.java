import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class TestEnemyBullet
{
	private EnemyBullet enemyBullet;
	private Vector2 vector2;
	private static final int pos = 100;

	@Before
	public void instantiate()
	{
		vector2 = new Vector2(34.1, 23.2);
		enemyBullet = new EnemyBullet(pos, pos, vector2);
	}

	@Test
	public void testUpdate()
	{
		float dt = 2.7f;

		Vector2 vectorDirection = vector2.normalizeCopy();
		int expectedPos = pos + (int)((0.1f * vectorDirection.getX()) * dt);

		enemyBullet.update(dt);

		assertEquals(enemyBullet.getXPos(), expectedPos);
		assertEquals(enemyBullet.getYPos(), expectedPos);
	}
}
