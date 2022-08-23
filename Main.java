import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Main implements ActionListener {
    final static int rows = 25;
    final static int cols = 25;
    final static int cellWidth = 25;
    final static Color DEFAULT_COLOR = Color.LIGHT_GRAY;;
    final static Color BLOCK_COLOR = Color.RED;
    final static Color START_COLOR = Color.GREEN;
    final static Color END_COLOR = Color.ORANGE;
    static GridMap gridPanel = new GridMap(rows, cols, cellWidth);

    static JFrame frame;
    static JPanel mainPanel;
    static JPanel sidePanel;
    static JButton runButton;
    static JButton setStartButton;
    static JButton setEndButton;

    static MapLocation start;
    static MapLocation end;
    static boolean setStartBoolean = false;

    public static void main(String[] args) {
        Main runner = new Main();

        start = new MapLocation(3,3);
        end = new MapLocation(cols-3,rows-3);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {runner.createAndShowGui();}
        });
    }

    private void createAndShowGui() {
        mainPanel = new JPanel();
        frame = new JFrame("Path Finder Visualizer");
        gridPanel.setLabelColor(start.y,start.x,START_COLOR);
        gridPanel.setLabelColor(end.y,end.x,END_COLOR);

        //side panel
        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(100, cols * cellWidth));
        sidePanel.setBackground(Color.WHITE);
        JLabel title = new JLabel("CONTROLS");
        runButton = new JButton("Start/Stop");
        runButton.addActionListener(this);
        setStartButton = new JButton("Set Start");
        setStartButton.addActionListener(this);
        setEndButton = new JButton("Set End");
        setEndButton.addActionListener(this);
        sidePanel.add(title);
        sidePanel.add(runButton);
        sidePanel.add(setStartButton);
        sidePanel.add(setEndButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.add(gridPanel);
        mainPanel.add(sidePanel);
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private void generatePath() {
        Set<MapLocation> blockLocations = new HashSet<MapLocation>();

        Color[][] labels = gridPanel.getLabelColors();
        for (int row = 0; row < labels.length; row++) {
            for (int col = 0; col < labels[row].length; col++) {
                if (labels[row][col].equals(BLOCK_COLOR)) {
                    blockLocations.add(new MapLocation(col,row));
                }
            }
        }
        System.out.println("BLOCKS " + blockLocations);

        AStarSearch aStar = new AStarSearch(start, end, cols, rows, blockLocations);
        Stack<MapLocation> path = aStar.getPath();
        drawPath(path);
    }

    private void drawPath(Stack<MapLocation> path){
        //TODO draw path method
        System.out.println("PATH " + path);
    }

    private void setStartButton(){
        //TODO start
        System.out.println("START");
        setStartBoolean = true;
    }

    private void setEndButton(){
        //TODO end
        System.out.println("END");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(runButton)) {
            generatePath();
        }
        if(e.getSource().equals(setStartButton)) {
            setStartButton();
        }
        if(e.getSource().equals(setEndButton)) {
            setEndButton();
        }
    }

    //getters and setters
    public static MapLocation getStartLocation(){
        return start;
    }
    public static void setStartLocation(MapLocation location){
        start = location;
    }

    public static MapLocation getEndLocation(){
        return start;
    }
    public static void setEndLocation(MapLocation location){
        start = location;
    }

}
