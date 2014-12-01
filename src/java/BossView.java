import java.awt.*;
import java.awt.Point;
import java.util.HashMap;

public class BossView extends View {
	private BossModel bossModel;

	BossView(BossModel pm) {
		this.bossModel = pm;
	}

	public void render(Graphics2D g2, float rw, float rh) {
		g2.drawImage(this.bossModel.getBossImage(), this.bossModel.getXPos(), this.bossModel.getYPos(), (int) rw * this.bossModel.getWidth(), (int) rh * this.bossModel.getHeight(), null);

		for (Point p:bossModel.armPieces){
			g2.drawImage(this.bossModel.armImg, p.x, p.y, (int) rw * this.bossModel.armImg.getWidth(), (int) rh * this.bossModel.armImg.getHeight(), null);
		}
		g2.setColor(Color.BLACK);
		g2.drawString("Life: "+bossModel.health,this.bossModel.getXPos(), this.bossModel.getYPos());
		//g2.drawString("theta: "+bossModel.theta,this.bossModel.getXPos(), this.bossModel.getYPos()+this.bossModel.getWidth());

		//for (int xx = 0; xx < this.bossModel.getBulletList().size(); xx++) {
		//	this.bossModel.getBulletList().get(xx).render(g2, rw, rh);
		//}


	}

	public HashMap<String, View> getVisibleViews() {
		return new HashMap<String, View>();
	}
}
