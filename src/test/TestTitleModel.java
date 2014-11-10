
import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.event.KeyEvent;

import org.junit.Test;
import org.junit.Before;

public class TestTitleModel extends Component {
	private ModelController modelController;
	private ViewController viewController;
	private TitleModel titleModel;
	private InputHandler inputHandler;
	
	@Before
	public void initilize() {
		modelController = new ModelController();
		viewController = new ViewController();		
		
		modelController.setViewController(viewController);
		viewController.setModelController(modelController);	
		
		inputHandler = viewController.getDrawPanel().getInputHandler();
		titleModel = new TitleModel(modelController);
	}
	
	@Test
	public void testKeyUpResponse() {		
		
		assertFalse(modelController.getMainModel() instanceof LevelModel);
		assertFalse(viewController.getMainView() instanceof LevelView);	
		
		// Send up arrow Key
		sendKeyRelease(KeyEvent.VK_UP);		
		assertFalse(modelController.getMainModel() instanceof LevelModel);
		assertFalse(viewController.getMainView() instanceof LevelView);	
		
		// Reposition cursor to "START"
		sendKeyRelease(KeyEvent.VK_UP);
		
		// Enter to start Game
		sendKeyRelease(KeyEvent.VK_ENTER);		
		assertTrue(modelController.getMainModel() instanceof LevelModel);
		assertTrue(viewController.getMainView() instanceof LevelView);
	}
	
	private void sendKeyRelease(int KeyCode) {
		titleModel.keyUpResponse( new KeyEvent(this, KeyEvent.KEY_RELEASED, 
				   System.currentTimeMillis(), 0, KeyCode, (char)KeyCode));
	}

}
