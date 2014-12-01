import java.lang.InterruptedException;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class TestMillisecTimer
{
	private MillisecTimer millisecTimer;

	@Before
	public void instantiate()
	{
		millisecTimer = new MillisecTimer();
	}

	@Test
	public void testGetDt()
	{
		assertTrue(millisecTimer.getDt() >= 0);
	}

	@Test
	public void testReset()
	{
		testGetDt();
		millisecTimer.reset();
		testGetDt();
	}

	@Test
	public void testGetTotalElapsed()
	{
		float initialTotalElapsed = millisecTimer.getTotalElapsed();

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			fail("InterruptedException thrown while sleeping");
		}

		assertTrue(millisecTimer.getTotalElapsed() > initialTotalElapsed);
	}
}
