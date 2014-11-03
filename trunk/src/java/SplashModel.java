import java.awt.*;
import java.util.HashMap;
import java.awt.event.KeyEvent;

/**
 * This model is associated with the "SpashView" A.K.A the initial screen of the game(displays the 
 * title and BSD license). T
 */

public class SplashModel extends Model implements InputResponder{

	private ModelController modelController;
	private MillisecTimer timer;

	public SplashModel(ModelController m){
		modelController = m;
		timer = new MillisecTimer();
		m.getViewController().getDrawPanel().getInputHandler().registerInputResponder(this);
		
		// Play intro theme
		SoundManager sm = SoundManager.get();
		sm.playSound("intro");
		
	}	
	
	
	// Go to title screen if 5 seconds elapse  
	public int update(float dt){
		if(timer.getDt() > 5000){
			modelController.getViewController().getDrawPanel().getInputHandler().unregisterInputResponder(this);
			modelController.setMainModel(new TitleModel(modelController));
			modelController.getViewController().setMainView(new TitleView(modelController.getViewController()));
			SoundManager.get().stopSound("intro");
		}
		return 0;
	}
	
	public void keyDownResponse(KeyEvent e){
		return;
	}
	
	// Go to title screen if enter key is pressed
	public void keyUpResponse(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			modelController.getViewController().getDrawPanel().getInputHandler().unregisterInputResponder(this);
			this.modelController.setMainModel(new TitleModel(this.modelController));
			this.modelController.getViewController().setMainView(new TitleView(this.modelController.getViewController()));
			SoundManager.get().stopSound("intro");
		}
	}
	
	public HashMap<String,Model> getVisibleModels(){
		return new HashMap<String,Model>();
    }
	

}
