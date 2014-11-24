import java.awt.*;
import java.util.HashMap;
import java.awt.event.KeyEvent;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TitleModel extends Model implements InputResponder {

	private ModelController modelController;
	private BufferedImage titleImage;
	private BufferedImage cursor;
	private int choice;

	public TitleModel(ModelController theModelController) {
		modelController = theModelController;
		choice = 0;
		registerTitleInput();

		try {
			titleImage = ImageIO.read(new File("assets/title.png"));
			cursor = ImageIO.read(new File("assets/flyer.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}


	public int update(float dt) {
		return 0;
	}

	public void keyDownResponse(KeyEvent e) {
		return;
	}

	public void keyUpResponse(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
			choice = 1 - choice;
			SoundManager.get().playSound("interface");
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (choice == 0) {
				unregisterTitleInput();
				setLevelModel("assets/test_level.json");
				setLevelView("assets/test_level.png");
			}
		}
	}

	private void registerTitleInput() {
		modelController.getViewController().getDrawPanel().getInputHandler().registerInputResponder(this);
	}
	private void unregisterTitleInput() {
		modelController.getViewController().getDrawPanel().getInputHandler().unregisterInputResponder(this);
	}

	private void setLevelModel(String jsonMapFile) {
		modelController.setMainModel(new LevelModel(modelController, jsonMapFile));
	}

	private void setLevelView(String pngMapFile) {
		modelController.getViewController().setMainView(new LevelView(modelController.getViewController(), pngMapFile));
	}

	public BufferedImage getTitleImage() {
		return titleImage;
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
