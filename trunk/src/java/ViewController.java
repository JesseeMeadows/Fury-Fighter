import javax.swing.*;
import java.awt.*;

public class ViewController{
    
    public static final int SCREEN_WIDTH = 512;
    public static final int SCREEN_HEIGHT = 512; 

    private JFrame mainWindow;
    private DrawSurface drawPanel;
    
    private ModelController modelController;

    
    ViewController(){
	this.drawPanel = new DrawSurface(this,SCREEN_WIDTH,SCREEN_HEIGHT);
	this.drawPanel.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
	this.mainWindow = new JFrame("Fury Fighter");
	this.mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.mainWindow.setLayout(new BorderLayout());
	this.mainWindow.add(this.drawPanel,BorderLayout.CENTER);
	this.drawPanel.requestFocusInWindow();
	this.mainWindow.pack();
	this.mainWindow.setResizable(false);
	this.mainWindow.setVisible(true);
    }

    public void render(){
	this.mainWindow.repaint();
	this.mainWindow.setVisible(true);
	
    }

    public void setModelController(ModelController m){
	this.modelController = m;
    }
    public ModelController getModelController(){
	return this.modelController;
    }
    public void setMainView(View v){
	this.drawPanel.setView(v);
    }
    public View getMainView(){
	return this.drawPanel.getMainView();
    }
    public DrawSurface getDrawPanel(){
	return this.drawPanel;
    }
}
