
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
		titleModel.keyUpResponse(
				new KeyEvent(this, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 
									 0, KeyEvent.VK_UP, (char)KeyEvent.VK_UP));
		
		assertFalse(modelController.getMainModel() instanceof LevelModel);
		assertFalse(viewController.getMainView() instanceof LevelView);	
		
		// Reposition cursor to "START"
		titleModel.keyUpResponse(
				new KeyEvent(this, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 
									 0, KeyEvent.VK_UP, (char)KeyEvent.VK_UP));		
		// Send ENTER key
		titleModel.keyUpResponse(
				new KeyEvent(this, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 
									 0, KeyEvent.VK_ENTER, (char)KeyEvent.VK_ENTER));		
		
		assertTrue(modelController.getMainModel() instanceof LevelModel);
		assertTrue(viewController.getMainView() instanceof LevelView);		
	}

}
