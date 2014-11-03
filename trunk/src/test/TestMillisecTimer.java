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
}
