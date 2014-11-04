import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class TestInputResponder implements InputResponder
{
	private InputResponder inputResponder;
	private KeyEvent currentKeyEvent = null;
	private static final char testKey = 'x';

	public void keyDownResponse(KeyEvent e)
	{
		currentKeyEvent = e;
	}

	public void keyUpResponse(KeyEvent e)
	{
		if ((currentKeyEvent == null) || (e.getKeyCode() != currentKeyEvent.getKeyCode()))
			throw new RuntimeException("keyUp is triggered before keyDown!");
		else
			currentKeyEvent = null;
	}

	@Before
	public void instantiate()
	{
		inputResponder = new TestInputResponder();
	}

	@Test
	public void testKeyDownUpResponse()
	{
		Component source = new Container();
		KeyEvent key = new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, (int)testKey, testKey);
		inputResponder.keyDownResponse(key);

		key = new KeyEvent(source, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, (int)testKey, testKey);
		inputResponder.keyUpResponse(key);
	}
}
