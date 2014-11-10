import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class View{
    public abstract void render(Graphics2D g2,float rw, float rh);
    public abstract HashMap<String,View> getVisibleViews();
}