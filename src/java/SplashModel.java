import java.awt.*;
import java.util.HashMap;
import java.awt.event.KeyEvent;

/**
 * This model is associated with the "SpashView" A.K.A the initial screen of the
 * game(displays the title and BSD license). T
 */

public class SplashModel extends Model implements InputResponder {
	private int millisecElasped;

	public SplashModel(ModelController m) {
		modelController = m;
		millisecElasped = 0;
		registerSplashInput();

		// Play intro theme
		SoundManager sm = SoundManager.get();
		sm.playSound("intro");

	}

	// Go to title screen if 5 seconds elapse
	public int update(float dt) {
		millisecElasped += dt;
		if (millisecElasped > 5000) {
			unregisterSplashInput();
			setTitleModel();
			setTitleView();
			SoundManager.get().stopSound("intro");
		}
		return 0;
	}

	public void keyDownResponse(KeyEvent e) {
		return;
	}

	// Go to title screen if enter key is pressed
	public void keyUpResponse(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			unregisterSplashInput();
			setTitleModel();
			setTitleView();
			SoundManager.get().stopSound("intro");
		}
	}

	private void registerSplashInput() {
		modelController.getViewController().getDrawPanel().getInputHandler().registerInputResponder(this);
	}

	private void unregisterSplashInput() {
		modelController.getViewController().getDrawPanel().getInputHandler().unregisterInputResponder(this);
	}

	private void setTitleModel() {
		modelController.setMainModel(new TitleModel(modelController));
	}

	private void setTitleView() {
		modelController.getViewController().setMainView(new TitleView(modelController.getViewController()));
	}

	public HashMap<String, Model> getVisibleModels() {
		return new HashMap<String, Model>();
	}

}
