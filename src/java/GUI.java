import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class GUI{

	private PlayerModel playerModel;
	private BufferedImage guiBackground;
	private BufferedImage lives;
	private BufferedImage meter;

	GUI(PlayerModel pm){
		this.playerModel = pm;

		try{
			this.guiBackground = ImageIO.read(new File("assets/guiback.png"));
			this.lives = ImageIO.read(new File("assets/lives.png"));
			this.meter = ImageIO.read(new File("assets/meter.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}

	}

	public void render(Graphics2D g2, float rw, float rh){
		g2.drawImage(this.guiBackground,0,448,(int)rw*this.guiBackground.getWidth(),(int)rh * this.guiBackground.getHeight(),null);
		g2.setColor(Color.LIGHT_GRAY);
		g2.setFont(new Font("Monospaced",Font.BOLD,12));
		g2.drawString("SCORE: " + Integer.toString(this.playerModel.getScore()),10,470);
		g2.drawString("HIGH SCORE: 100000",200,470);
		g2.drawImage(this.lives,400,458,(int)rw*this.lives.getWidth(),(int)rh * this.lives.getHeight(),null);
		g2.drawString(Integer.toString(this.playerModel.getLives()),430,470);

		g2.drawString("LASER "+Integer.toString(this.playerModel.getLaserLevel()),200,490);
		g2.drawString("RING "+Integer.toString(this.playerModel.getRingLevel()), 300,490);
		g2.drawString("MISSILE "+Integer.toString(this.playerModel.getMissleLevel()),400,490);

		g2.setColor(Color.MAGENTA);
		g2.drawString("EXTRA",135,492);
		g2.fillRect(12,480,this.playerModel.getFragmentCount()*5,16);
		g2.drawImage(this.meter,10,478,(int)rw*this.meter.getWidth(),(int)rh * this.meter.getHeight(),null);




	}
}
