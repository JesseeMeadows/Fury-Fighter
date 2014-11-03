import java.awt.*;
import java.util.HashMap;
import java.awt.event.KeyEvent;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TitleModel extends Model implements InputResponder{

	private ModelController modelController;
	private int choice;
	private BufferedImage titleImage;
	private BufferedImage cursor;

	public TitleModel(ModelController m){
		this.modelController = m;
		m.getViewController().getDrawPanel().getInputHandler().registerInputResponder(this);
		this.choice = 0;
		
		try{
			this.titleImage = ImageIO.read(new File("assets/title.png"));
			this.cursor = ImageIO.read(new File("assets/flyer.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public HashMap<String,Model> getVisibleModels(){
		return new HashMap<String,Model>();
    }
	
	public int update(float dt){
		return 0;
	}
	
	public void keyDownResponse(KeyEvent e){
		return;
	}

	public void keyUpResponse(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN){
			this.choice = 1 - this.choice;
			SoundManager.get().playSound("interface");
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(this.choice == 0){
				this.modelController.getViewController().getDrawPanel().getInputHandler().unregisterInputResponder(this);
				this.modelController.setMainModel(new LevelModel(this.modelController));
				this.modelController.getViewController().setMainView(new LevelView(this.modelController.getViewController()));
			}
		}
	}
		
	public BufferedImage getTitleImage(){
		return this.titleImage;
	}
	public BufferedImage getCursor(){
		return this.cursor;
	}
	public int getChoice(){
		return this.choice;
	}
}