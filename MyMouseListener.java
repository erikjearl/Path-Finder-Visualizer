import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class MyMouseListener extends MouseAdapter {
    private GridMap gridMap;

    public MyMouseListener(GridMap colorGrid) {
        this.gridMap = colorGrid;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            gridMap.labelPressed((JLabel)e.getSource());
        } else if (e.getButton() == MouseEvent.BUTTON3) {

        }
    }
}