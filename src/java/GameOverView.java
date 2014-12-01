import java.awt.*;
import java.util.HashMap;
import java.awt.image.BufferedImage;

public class GameOverView extends View{
	
	private ViewController viewController;
	private GameOverModel gameOverModel;
	
	public GameOverView(ViewController v){
		this.viewController = v;
		this.gameOverModel = (GameOverModel)this.viewController.getModelController().getMainModel();
	}
	
	public void render(Graphics2D g2, float rw, float rh){ 
		g2.setBackground(Color.BLACK);
		g2.clearRect(0,0,ViewController.SCREEN_WIDTH,ViewController.SCREEN_HEIGHT);
		
		g2.setColor(Color.LIGHT_GRAY);
		g2.setFont(new Font("Monospaced",Font.BOLD,24));
		FontMetrics fm = g2.getFontMetrics();
		g2.drawString("GAME OVER",ViewController.SCREEN_WIDTH/2 - fm.stringWidth("GAME OVER")/2,ViewController.SCREEN_HEIGHT/2 - fm.getHeight()/2);
		g2.drawString("Continue",ViewController.SCREEN_WIDTH/2 ,300);
		g2.drawString("Quit",ViewController.SCREEN_WIDTH/2,330);
		BufferedImage img2 = this.gameOverModel.getCursor();
		g2.drawImage(img2,ViewController.SCREEN_WIDTH/2 - 50,280 + this.gameOverModel.getChoice() * 30,(int)rw*img2.getWidth(),(int)rh * img2.getHeight(),null);
	}
	
	public HashMap<String,View> getVisibleViews(){
		return new HashMap<String,View>();
    }
}
