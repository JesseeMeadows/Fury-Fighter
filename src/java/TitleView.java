import java.awt.*;
import java.util.HashMap;
import java.awt.image.BufferedImage;

/**
 * This class acts as the opening title screen which takes place after timing out or
 * entering a key on the initial splash screen(displays "Fury fighter" and "BSD License").
 * The view displays various user options to play the game( currently "start" and "password").
 */

public class TitleView extends View{

	private ViewController viewController;
	private TitleModel titleModel;

	public TitleView(ViewController v){
		this.viewController = v;
		this.titleModel = (TitleModel)this.viewController.getModelController().getMainModel();
	}

	public void renderWithImages(Graphics2D g2, float rw, float rh, BufferedImage img, BufferedImage img2)
	{
		if ((img == null) || (img2 == null))
			throw new NullPointerException();

		g2.setBackground(Color.BLACK);
		g2.clearRect(0,0,ViewController.SCREEN_WIDTH,ViewController.SCREEN_HEIGHT);
		g2.drawImage(img,ViewController.SCREEN_WIDTH/2 - img.getWidth()/2,100,(int)rw*img.getWidth(),(int)rh * img.getHeight(),null);

		g2.setColor(Color.LIGHT_GRAY);
		g2.setFont(new Font("Monospaced",Font.BOLD,24));
		g2.drawString("Start",ViewController.SCREEN_WIDTH/2 ,300);
		g2.drawString("Password",ViewController.SCREEN_WIDTH/2,330);
		g2.drawImage(img2,ViewController.SCREEN_WIDTH/2 - 50,280 + this.titleModel.getChoice() * 30,(int)rw*img2.getWidth(),(int)rh * img2.getHeight(),null);
	}

	public void render(Graphics2D g2, float rw, float rh){
		renderWithImages(g2, rw, rh, this.titleModel.getTitleImage(), this.titleModel.getCursor());
	}

	public HashMap<String,View> getVisibleViews(){
		return new HashMap<String,View>();
    }
}
