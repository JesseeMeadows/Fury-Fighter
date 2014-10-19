import java.awt.*;
import java.util.HashMap;
import java.awt.event.KeyEvent;

public class SplashModel extends Model implements InputResponder{

	private ModelController modelController;
	private MillisecTimer timer;

	public SplashModel(ModelController m){
		this.modelController = m;
		this.timer = new MillisecTimer();
		m.getViewController().getDrawPanel().getInputHandler().registerInputResponder(this);
		
		SoundManager sm = SoundManager.get();
		sm.playSound("intro");
		
	}
	
	public HashMap<String,Model> getVisibleModels(){
		return new HashMap<String,Model>();
    }
	
	public int update(float dt){
		if(timer.getDt() > 5000){
			this.modelController.setMainModel(new TitleModel(this.modelController));
			this.modelController.getViewController().setMainView(new TitleView(this.modelController.getViewController()));
			SoundManager.get().stopSound("intro");
		}
		return 0;
	}
	
	public void keyDownResponse(KeyEvent e){
		return;
	}

	public void keyUpResponse(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			this.modelController.setMainModel(new TitleModel(this.modelController));
			this.modelController.getViewController().setMainView(new TitleView(this.modelController.getViewController()));
			SoundManager.get().stopSound("intro");
		}
	}
	

}
