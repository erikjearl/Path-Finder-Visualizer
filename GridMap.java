import java.awt.*;
import javax.swing.*;

public class GridMap extends JPanel {
    private JLabel[][] labels;
    private Color[][] colors;

    final Color DEFAULT_COLOR = Color.LIGHT_GRAY;;
    final Color BLOCK_COLOR = Color.RED;

    public GridMap(int rows, int cols, int cellWidth) {
        labels = new JLabel[rows][cols];
        colors = new Color[rows][cols];

        MyMouseListener myListener = new MyMouseListener(this);
        Dimension labelPrefSize = new Dimension(cellWidth, cellWidth);
        setLayout(new GridLayout(rows, cols));
        //initialize grid of labels
        for (int row = 0; row < labels.length; row++) {
            for (int col = 0; col < labels[row].length; col++) {
                JLabel myLabel = new JLabel();
                myLabel = new JLabel();
                myLabel.setOpaque(true);
                myLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                Color myColor = DEFAULT_COLOR;
                colors[row][col] = myColor;
                myLabel.setBackground(myColor);
                myLabel.addMouseListener(myListener);
                myLabel.setPreferredSize(labelPrefSize);
                add(myLabel);
                labels[row][col] = myLabel;
            }
        }
    }


    public void labelPressed(JLabel label) {
        for (int row = 0; row < labels.length; row++) {
            for (int col = 0; col < labels[row].length; col++) {
                if (label == labels[row][col]) {    //if label was clicked
                    Color myColor = null;
                    if(Main.setStartBoolean){ // move start
                        MapLocation start = Main.getStartLocation();
                        colors[start.y][start.x] = Main.DEFAULT_COLOR;
                        labels[start.y][start.x].setBackground(Main.DEFAULT_COLOR);
                        start = new MapLocation(col,row);
                        Main.setStartLocation(start);
                        myColor = Main.START_COLOR;
                        Main.setStartBoolean = false;
                    } else { //toggle block
                        if (colors[row][col] == DEFAULT_COLOR) {
                            myColor = BLOCK_COLOR;
                        } else if (colors[row][col] == BLOCK_COLOR) {
                            myColor = DEFAULT_COLOR;
                        }
                    }
                    colors[row][col] = myColor;
                    labels[row][col].setBackground(myColor);
                }
            }
        }
    }


    public Color[][] getLabelColors(){
        return colors;
    }

    public void setLabelColor(int row, int col, Color newColor){
        labels[row][col].setBackground(newColor);
    }

}
