import java.awt.*;
import java.util.HashMap;

public class PlayerView extends View {
	private PlayerModel playerModel;
	private GUI gui;

	PlayerView(PlayerModel pm) {
		this.playerModel = pm;
		this.gui = new GUI(pm);
	}

	public void render(Graphics2D g2, float rw, float rh) {
		g2.drawImage(this.playerModel.getPlayerImage(), this.playerModel.getXPos(), this.playerModel.getYPos(), (int) rw * this.playerModel.getWidth(), (int) rh * this.playerModel.getHeight(), null);

		if (this.playerModel.getDefensePodLevel() > 0) {
			int drawx = this.playerModel.getDefensePodXPos();
			int drawy = this.playerModel.getDefensePodYPos();
			g2.drawImage(this.playerModel.getDefensePodImage(), drawx, drawy, (int) rw * this.playerModel.getDefensePodImage().getWidth(),
					(int) rh * this.playerModel.getDefensePodImage().getHeight(), null);
		}

		for (int xx = 0; xx < this.playerModel.getBulletList().size(); xx++) {
			this.playerModel.getBulletList().get(xx).render(g2, rw, rh);
		}

		if (this.playerModel.getCobaltDt() > 0) {
			for (int xx = 0; xx < 30; xx++) {
				int drawx = Utils.randInt(0, ViewController.SCREEN_WIDTH);
				int drawy = Utils.randInt(0, ViewController.SCREEN_HEIGHT);
				g2.drawImage(this.playerModel.getCobaltBombImage(), drawx, drawy, (int) rw * this.playerModel.getCobaltBombImage().getWidth(), (int) rh
						* this.playerModel.getCobaltBombImage().getHeight(), null);
			}
		}

		this.gui.render(g2, rw, rh);

	}

	public HashMap<String, View> getVisibleViews() {
		return new HashMap<String, View>();
	}
}
