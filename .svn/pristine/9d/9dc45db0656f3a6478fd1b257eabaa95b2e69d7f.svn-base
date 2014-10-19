import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class InputHandler implements InputResponder{
    
    private ArrayList<InputResponder> inputResponders;
    InputHandler(){
	this.inputResponders = new ArrayList<InputResponder>();
    }
    
    public void registerInputResponder(InputResponder i){
	this.inputResponders.add(i);
    }
	
	public void unregisterInputResponder(InputResponder i){
		for(int xx=0;xx<this.inputResponders.size();xx++){
			if(this.inputResponders.get(xx) == i){
				this.inputResponders.remove(xx);
				xx--;
			}
		}
	}
    
    public void keyDownResponse(KeyEvent e){
	if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
	    System.exit(0);
	    
	for (InputResponder ir : this.inputResponders){
	    ir.keyDownResponse(e);
	}
    }

    public void keyUpResponse(KeyEvent e){
	for (InputResponder ir : this.inputResponders){
	    ir.keyUpResponse(e);
	}
    }

}
