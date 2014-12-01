import java.awt.event.KeyEvent;

public interface InputResponder{
	// refers to when a key is initially pressed down
    public void keyDownResponse(KeyEvent e);
    
    // refers to when a pressed down key is released (think holding down fire key to constantly fire)
    public void keyUpResponse(KeyEvent e);
}