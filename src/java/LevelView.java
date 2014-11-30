import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class LevelView extends View{
    private ViewController viewController;
    private PlayerView playerView;
    private LevelModel levelModel;
    private BufferedImage levelMap;    
    
    private final int screenWidth;
    private final int screenHeight; 
    
    private enum Level { TEST, FIRST, SECOND };
    private  Level currentLevel;
    
    private ArrayList<EnemyView> enemyViews;

	LevelView(ViewController theViewController) {
		viewController = theViewController;
		levelModel = (LevelModel) viewController.getModelController().getMainModel();
		playerView = new PlayerView((PlayerModel) viewController.getModelController().getMainModel().getVisibleModels().get("playerModel"));
		enemyViews = new ArrayList<EnemyView>();
		
		currentLevel = Level.TEST;
		levelMap = loadLevelMap(currentLevel);
		
		screenWidth = ViewController.SCREEN_WIDTH;
	    screenHeight = ViewController.SCREEN_HEIGHT - (2 * levelModel.getTileMap().getTileWidth());
	}
    public void render(Graphics2D g2, float rw, float rh){
	
		if (levelModel.getDeathTimerDt() > 0) {
			g2.setBackground(Color.BLACK);
			g2.clearRect(0, 0, ViewController.SCREEN_WIDTH, ViewController.SCREEN_HEIGHT);
			g2.setColor(Color.LIGHT_GRAY);
			g2.setFont(new Font("Monospaced", Font.BOLD, 16));
			FontMetrics fm = g2.getFontMetrics();
			PlayerModel pm = (PlayerModel) viewController.getModelController().getMainModel().getVisibleModels().get("playerModel");
			g2.drawString("Lives: " + Integer.toString(pm.getLives()), ViewController.SCREEN_WIDTH / 2 - fm.stringWidth("Lives: " + Integer.toString(pm.getLives())) / 2, ViewController.SCREEN_HEIGHT
					/ 2 - fm.getHeight() / 2);
			return;
		}
	
	
		BufferedImage currentFrame = getFrame(levelMap);
		g2.drawImage(currentFrame, 0, 0, screenWidth, screenHeight, null);

		playerView.render(g2, rw, rh);

		enemyViews.clear();
		for (int xx = 0; xx < levelModel.getActiveEnemies().size(); xx++) {
			addEnemyView(new EnemyView(levelModel.getActiveEnemies().get(xx)));
		}

		for (int xx = 0; xx < enemyViews.size(); xx++) {
			if (enemyViews.get(xx).getEnemyModel().isDead() == true) {
				enemyViews.remove(xx);
				xx--;
			}
			else {
				enemyViews.get(xx).render(g2, rw, rh);
			}
		}
		for (int xx = 0; xx < levelModel.getEnemyBullets().size(); xx++) {
			levelModel.getEnemyBullets().get(xx).render(g2, rw, rh);
		}

		for (int xx = 0; xx < levelModel.getLevelPickups().size(); xx++) {
			levelModel.getLevelPickups().get(xx).render(g2, rw, rh);
		}

		if (levelModel.paused == true) {
			g2.setColor(Color.LIGHT_GRAY);
			g2.setFont(new Font("Monospaced", Font.BOLD, 36));
			FontMetrics fm = g2.getFontMetrics();
			g2.drawString("PAUSED", ViewController.SCREEN_WIDTH / 2 - fm.stringWidth("PAUSED") / 2, ViewController.SCREEN_HEIGHT / 2 - fm.getHeight() / 2);
		}

	}

	public HashMap<String, View> getVisibleViews() {
		HashMap<String, View> rv = new HashMap<String, View>();
		rv.put("playerView", playerView);
		return rv;
	}

	public void addEnemyView(EnemyView ev) {
		enemyViews.add(ev);
	}
	
	private BufferedImage loadLevelMap(Level level) {
		BufferedImage map = null;
		String pngLevelPath = "";

		switch (level) {
			case TEST:    pngLevelPath = LevelPath.TEST_PNG;
						  break;
						
			case FIRST:   pngLevelPath = LevelPath.FIRST_PNG;
						  break;
						 
			case SECOND:  pngLevelPath = LevelPath.SECOND_PNG;
						  break;		
		}
		
		try {
			map = ImageIO.read(new File(pngLevelPath));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return map;
		
	}
	
	private BufferedImage getFrame(BufferedImage mapImage) {
		BufferedImage subImage;
		subImage = mapImage.getSubimage(levelModel.getDistanceScrolled(), 0, screenWidth, screenHeight);
		return subImage;
	}
}
