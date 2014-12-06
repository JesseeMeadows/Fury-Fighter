import java.awt.*;
import java.util.HashMap;


public class SplashView extends View{
	
	private ViewController viewController;
	
	public SplashView(ViewController v){
		this.viewController = v;
	}
	
	public void render(Graphics2D g2, float rw, float rh){ 
		g2.setBackground(Color.BLACK);
		g2.clearRect(0,0,ViewController.SCREEN_WIDTH,ViewController.SCREEN_HEIGHT);
		g2.setColor(Color.LIGHT_GRAY);
		g2.setFont(new Font("Monospaced",Font.BOLD,16));
		FontMetrics fm = g2.getFontMetrics();
		if (fm!=null){
			g2.drawString("FURY FIGHTER",ViewController.SCREEN_WIDTH/2 - fm.stringWidth("FURY FIGHTER")/2,ViewController.SCREEN_HEIGHT/2 - fm.getHeight()/2);
			g2.drawString("Released under the BSD license",ViewController.SCREEN_WIDTH/2 - fm.stringWidth("Released under the BSD license")/2,(ViewController.SCREEN_HEIGHT/2 - fm.getHeight()/2)+30);
		}
	
	}
	
	public HashMap<String,View> getVisibleViews(){
		return new HashMap<String,View>();
    }
}
