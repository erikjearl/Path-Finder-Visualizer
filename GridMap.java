import java.awt.*;
import javax.swing.*;

public class GridMap extends JPanel {
    private JLabel[][] labels;
    private Color[][] colors;

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
                Color myColor = Main.DEFAULT_COLOR;
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

                if (label == labels[row][col]) {        //if label was clicked
                    Color myColor = null;
                    if(Main.setStartBoolean) {          // move start
                        resetGrid(true);
                        MapLocation start = Main.getStartLocation();
                        colors[start.y][start.x] = Main.DEFAULT_COLOR;
                        labels[start.y][start.x].setBackground(Main.DEFAULT_COLOR);
                        start = new MapLocation(col, row);
                        Main.setStartLocation(start);
                        myColor = Main.START_COLOR;
                        Main.setStartBoolean = false;
                    }else if(Main.setEndBoolean){       // move end
                        resetGrid(true);
                        MapLocation end = Main.getEndLocation();
                        colors[end.y][end.x] = Main.DEFAULT_COLOR;
                        labels[end.y][end.x].setBackground(Main.DEFAULT_COLOR);
                        end = new MapLocation(col,row);
                        Main.setEndLocation(end);
                        myColor = Main.END_COLOR;
                        Main.setEndBoolean = false;
                    } else {                            //toggle block
                        if (colors[row][col] == Main.BLOCK_COLOR) {
                            myColor = Main.DEFAULT_COLOR;
                        } else if (colors[row][col] == Main.START_COLOR || colors[row][col] == Main.END_COLOR) {
                           break;
                        } else {
                            myColor = Main.BLOCK_COLOR;
                        }
                    }
                    colors[row][col] = myColor;
                    labels[row][col].setBackground(myColor);
                }

            }
        }
    }

    public void resetGrid(boolean keepBlocks){ // resetBlocks 0:all, 1:remove path
        for (int row = 0; row < labels.length; row++) {
            for (int col = 0; col < labels[row].length; col++) {
                MapLocation loc = new MapLocation(col,row);
                if(!loc.equals(Main.getStartLocation()) && !loc.equals(Main.getEndLocation())) {
                    if(keepBlocks) {
                        if (colors[row][col].equals(Main.BLOCK_COLOR)) {
                            continue;
                        }
                    }
                    JLabel myLabel = labels[row][col];
                    Color myColor = Main.DEFAULT_COLOR;
                    colors[row][col] = myColor;
                    myLabel.setBackground(myColor);
                    labels[row][col] = myLabel;
                }
            }
        }
    }

    public Color[][] getLabelColors(){
        return colors;
    }

    public void setLabelColor(int row, int col, Color newColor){
        labels[row][col].setBackground(newColor);
        colors[row][col] = newColor;
    }

}
