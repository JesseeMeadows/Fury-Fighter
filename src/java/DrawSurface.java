import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DrawSurface extends JComponent implements KeyListener {

	private int width;
	private int height;
	private ViewController viewController;
	private View mainView;
	private InputHandler inputHandler;

	DrawSurface(ViewController v, int surfaceWidth, int surfaceHeight) {
		setFocusable(true);
		inputHandler = new InputHandler();
		addKeyListener(this);
		viewController = v;
		width = surfaceWidth;
		height = surfaceHeight;

	}

	// Escape terminates game
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}

	public void keyPressed(KeyEvent e) {
        if (e!=null)
            this.inputHandler.keyDownResponse(e);
	}

	public void keyReleased(KeyEvent e) {
        if (e!=null)
            this.inputHandler.keyUpResponse(e);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		// float rw = ((float)this.getWidth()/this.width);
		// float rh = ((float)this.getHeight()/this.height);

		if (this.mainView != null)
			this.mainView.render(g2, 1, 1);
	}

	public void setView(View v) {
		this.mainView = v;
	}

	public View getMainView() {
		return this.mainView;
	}

	public InputHandler getInputHandler() {
		return this.inputHandler;
	}
}
