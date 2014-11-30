import java.awt.*;
import java.util.HashMap;
import java.awt.event.KeyEvent;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameOverModel extends Model implements InputResponder{

	private ModelController modelController;
	private int choice;
	private BufferedImage cursor;

	public GameOverModel(ModelController theModelController) {
		modelController = theModelController;
		registerGameoverInput();
		choice = 0;

		try {
			cursor = ImageIO.read(new File("assets/flyer.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}	
	
	public int update(float dt){
		return 0;
	}
	
	public void keyDownResponse(KeyEvent e){
		return;
	}

	public void keyUpResponse(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
			choice = 1 - choice;
			SoundManager.get().playSound("interface");
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// Restart Level
			if (choice == 0) {
				unregisterGameoverInput();
				setLevelModel("assets/test_level.json");
				setLevelView("assets/test_level.png");
			}
			// Goto Title Screen
			else {
				unregisterGameoverInput();
				setTitleModel();
				setTitleView();
			}
		}
	}
	
	private void registerGameoverInput() {
		modelController.getViewController().getDrawPanel().getInputHandler().registerInputResponder(this);
	}
	
	private void unregisterGameoverInput() {
		modelController.getViewController().getDrawPanel().getInputHandler().unregisterInputResponder(this);
	}
	
	private void setLevelModel(String jsonMapFile) {
		modelController.setMainModel(new LevelModel(modelController));
	}
	
	private void setLevelView(String pngMapFile) {
		modelController.getViewController().setMainView(new LevelView(modelController.getViewController()));
	}
	
	private void setTitleView() {
		modelController.getViewController().setMainView(new TitleView(modelController.getViewController()));
	}
	
	private void setTitleModel() {
		modelController.setMainModel(new TitleModel(modelController));
	}

	

	public BufferedImage getCursor() {
		return cursor;
	}

	public int getChoice() {
		return choice;
	}

	public HashMap<String, Model> getVisibleModels() {
		return new HashMap<String, Model>();
	}
}
