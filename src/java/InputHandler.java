import java.awt.event.KeyEvent;
import java.util.ArrayList;

/* This class is to handle input from the user. A given class must implement the 
 * inputResponder interface in order to register, and unregister user controls.
 */
public class InputHandler implements InputResponder {

	private ArrayList<InputResponder> inputResponders;

	InputHandler() {
		inputResponders = new ArrayList<InputResponder>();
	}

	public void registerInputResponder(InputResponder i) {
		inputResponders.add(i);
	}

	public void unregisterInputResponder(InputResponder inResponder) {
		for (int i = 0; i < this.inputResponders.size(); i++) {
			if (inputResponders.get(i) == inResponder) {
				inputResponders.remove(i);
				i--;
			}
		}
	}

	public void keyDownResponse(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);

		for (InputResponder ir : inputResponders) {
			ir.keyDownResponse(e);
		}
	}

	public void keyUpResponse(KeyEvent e) {
		for (InputResponder ir : inputResponders) {
			ir.keyUpResponse(e);
		}
	}

}
