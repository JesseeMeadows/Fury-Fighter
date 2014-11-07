
import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.event.KeyEvent;

import org.junit.Test;
import org.junit.Before;

public class TestGameOverModel extends Component {
	private ModelController modelController;
	private ViewController viewController;
	private GameOverModel gameOverModel;
	private InputHandler inputHandler;
	
	@Before
	public void initialize() {
		modelController = new ModelController();
		viewController = new ViewController();	
		
		// Setup Model-View
		modelController.setViewController(viewController);
		viewController.setModelController(modelController);	
		gameOverModel = new GameOverModel(modelController);
		
		inputHandler = viewController.getDrawPanel().getInputHandler();
	}
	
	// Test KeyUpResponse first option: Restart Level
	@Test
	public void testRestartLevel() {
		
		assertFalse(modelController.getMainModel() instanceof LevelModel);
		assertFalse(viewController.getMainView() instanceof LevelView);	
		
		// Enter to Restart Level
		sendKeyRelease(KeyEvent.VK_ENTER);		
		assertTrue(modelController.getMainModel() instanceof LevelModel);
		assertTrue(viewController.getMainView() instanceof LevelView);	
	}
	
	
	// Test KeyUpResponse second option: Goto Title Screen
	@Test
	public void testGotoTitleScreen() {
		assertFalse(modelController.getMainModel() instanceof TitleModel);
		assertFalse(viewController.getMainView() instanceof TitleView);	
		
		// Move cursor to Title Screen Option
		sendKeyRelease(KeyEvent.VK_DOWN);
		
		// Enter confirm option
		sendKeyRelease(KeyEvent.VK_ENTER);
		
		assertTrue(modelController.getMainModel() instanceof TitleModel);
		assertTrue(viewController.getMainView() instanceof TitleView);	
	}
	
	private void sendKeyRelease(int keyCode) {
		gameOverModel.keyUpResponse(new KeyEvent(this, KeyEvent.KEY_RELEASED, 
				    System.currentTimeMillis(), 0, keyCode, (char) keyCode));
	}
	

}
