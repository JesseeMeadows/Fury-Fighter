import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class TestInputResponder implements InputResponder
{
	private static final Component testKeyEventSource = new Container();

	private InputResponder inputResponder;
	private KeyEvent currentKeyEvent = null;
	private static final char testKey = 'x';

	private static KeyEvent generateKeyEvent(int id, char key)
	{
		return new KeyEvent(testKeyEventSource, id, System.currentTimeMillis(), 0, (int)key, key);
	}

	public static KeyEvent generateKeyDownEvent(char key)
	{
		return generateKeyEvent(KeyEvent.KEY_PRESSED, key);
	}

	public static KeyEvent generateKeyUpEvent(char key)
	{
		return generateKeyEvent(KeyEvent.KEY_RELEASED, key);
	}

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
		inputResponder.keyDownResponse(generateKeyDownEvent(testKey));
		inputResponder.keyUpResponse(generateKeyUpEvent(testKey));
	}
}
