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
            /*
            Color[][] myColors = gridMap.getMyColors();
            for (int row = 0; row < myColors.length; row++) {
                for (int col = 0; col < myColors[row].length; col++) {
                    System.out.print(myColors[row][col] + " ");
                }
                System.out.println();
            }
            System.out.println();
            */
        }
    }
}