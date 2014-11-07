import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;


import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import org.junit.Test;
import org.junit.Before;

public class TestInputHandler extends Component {
	private static final char testKey = 'x';
	
	private InputHandler inputHandler;	
	private InputSample inputSample;
	private boolean isRegistered;
	
	
	private class InputSample implements InputResponder {
		
		public void keyDownResponse(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_X) {
				isRegistered = true;
			}
		}
		
		public void keyUpResponse(KeyEvent e) {
			
		}
	}
	
	@Before
	public void instantiate() {
		inputHandler = new InputHandler();		
		inputSample = new InputSample();		
		isRegistered = false;		
		
	}
	
	@Test
	public void testRegisterInputResponder() {
		assertFalse(isRegistered);
		
		inputHandler.registerInputResponder(inputSample);
		inputHandler.keyDownResponse(new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_X, testKey));
		assertTrue("InputResponder failed to register", isRegistered);		
	}
	
	@Test
	public void testUnregisterInputResponder() {
		// Register
		inputHandler.registerInputResponder(inputSample);
		inputHandler.keyDownResponse(new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_X, testKey));		
		assertTrue(isRegistered);
		
		inputHandler.unregisterInputResponder(inputSample);
		isRegistered = false;
		inputHandler.keyDownResponse(new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_X, testKey));		
		assertFalse("InputResponder failed to unregister", isRegistered);	
		
	}
	

}
