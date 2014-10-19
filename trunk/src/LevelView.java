import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class LevelView extends View{
    private ViewController viewController;
    private PlayerView playerView;
    private LevelModel levelModel;
    
    private final int screenTileWidth = 16;
    private final int screenTileHeight = 14;
    
    private ArrayList<EnemyView> enemyViews;

    LevelView(ViewController v){
	this.viewController = v;
	this.levelModel = (LevelModel)this.viewController.getModelController().getMainModel();
	this.playerView = new PlayerView((PlayerModel)this.viewController.getModelController().getMainModel().getVisibleModels().get("playerModel"));
	this.enemyViews = new ArrayList<EnemyView>();
	
	
    }
    public void render(Graphics2D g2, float rw, float rh){
	
	if(this.levelModel.getDeathTimerDt() > 0){
	    g2.setBackground(Color.BLACK);
	    g2.clearRect(0,0,ViewController.SCREEN_WIDTH,ViewController.SCREEN_HEIGHT);
	    g2.setColor(Color.LIGHT_GRAY);
	    g2.setFont(new Font("Monospaced",Font.BOLD,16));
	    FontMetrics fm = g2.getFontMetrics();
	    PlayerModel pm = (PlayerModel)this.viewController.getModelController().getMainModel().getVisibleModels().get("playerModel");
	    g2.drawString("Lives: " + Integer.toString(pm.getLives()),ViewController.SCREEN_WIDTH/2 - fm.stringWidth("Lives: " + Integer.toString(pm.getLives()))/2,ViewController.SCREEN_HEIGHT/2 - fm.getHeight()/2);
	    return; 
	}
	
	int mapWidth = this.levelModel.getTileMapWidth();
	int mapHeight = this.levelModel.getTileMapHeight();
	int tileWidth = this.levelModel.getTileWidth();
	int tileHeight = this.levelModel.getTileHeight();

	
	for(int yy =0; yy < this.screenTileHeight; yy++){
	    for(int xx = 0; xx < this.screenTileWidth; xx++){
		int imageIndex = this.levelModel.getTileMap()[yy * mapWidth + xx];
		BufferedImage img = this.levelModel.getTileImage(imageIndex);
		g2.drawImage(img,xx * tileWidth, yy * tileHeight, (int)(tileWidth * rw), (int)(tileHeight * rh), null);
	    }
	}
	playerView.render(g2,rw,rh);
	
	this.enemyViews.clear();
	for(int xx=0;xx<this.levelModel.getEnemyModels().size();xx++){
	this.addEnemyView(new EnemyView(this.levelModel.getEnemyModels().get(xx)));
	}
	
	for(int xx=0;xx<this.enemyViews.size();xx++){
	    if(this.enemyViews.get(xx).getEnemyModel().getDead() == true){
		this.enemyViews.remove(xx);
		xx--;
	    }
	    else{
		this.enemyViews.get(xx).render(g2,rw,rh);
	    }
	}
	for(int xx=0;xx<this.levelModel.getEnemyBullets().size();xx++){
	    this.levelModel.getEnemyBullets().get(xx).render(g2,rw,rh);
	}
	
	for(int xx=0;xx<this.levelModel.getLevelPickups().size();xx++){
	    this.levelModel.getLevelPickups().get(xx).render(g2,rw,rh);
	}
	
	if(this.levelModel.paused == true){
		g2.setColor(Color.LIGHT_GRAY);
		g2.setFont(new Font("Monospaced",Font.BOLD,36));
		FontMetrics fm = g2.getFontMetrics();
		g2.drawString("PAUSED",ViewController.SCREEN_WIDTH/2 - fm.stringWidth("PAUSED")/2,ViewController.SCREEN_HEIGHT/2 - fm.getHeight()/2);
	}
	
    }

    public HashMap<String,View> getVisibleViews(){
	HashMap<String,View> rv = new HashMap<String,View>();
	rv.put("playerView",this.playerView);
	return rv;
    }
    
    public void addEnemyView(EnemyView ev){
	this.enemyViews.add(ev);
    }
}
