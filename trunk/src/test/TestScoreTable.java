import java.util.Collection;
import java.util.Arrays;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)

public class TestScoreTable
{
	@Parameter(value = 0)
	public EnemyModel enemyModel;

	@Parameter(value = 1)
	public Pickup pickup;

	@Parameters
	public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
        		{new FlyerModel(100, 100), new Pickup(100, 100, "weapon pod")}
        });
    }

	@Test
	public void testScoreForKilled()
	{
		assertNotEquals(ScoreTable.scoreForKilled(enemyModel), 0);
	}

	@Test
	public void testScoreForPickup()
	{
		assertNotEquals(ScoreTable.scoreForPickup(pickup), 0);
	}

	/* TODO: Figure out parameters for scoreForLevelBonus */
	@Test
	public void testScoreForLevelBonus()
	{
		assertNotEquals(ScoreTable.scoreForLevelBonus(), 0);
	}
}
