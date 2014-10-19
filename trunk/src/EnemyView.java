import java.awt.*;
import java.util.HashMap;

public class EnemyView extends View{
	
	private EnemyModel enemyModel;
	
	EnemyView(EnemyModel enemyModel){
		this.enemyModel = enemyModel;
	}
	
	public void render(Graphics2D g2, float rw, float rh){ 
		g2.drawImage(this.enemyModel.getEnemyImage(),this.enemyModel.getXPos(),this.enemyModel.getYPos(),(int)rw*this.enemyModel.getWidth(),(int)rh * this.enemyModel.getHeight(),null);
    }
    
    public HashMap<String,View> getVisibleViews(){
		return new HashMap<String,View>();
    }
    
    public EnemyModel getEnemyModel(){
	return this.enemyModel;
    }
	
}
