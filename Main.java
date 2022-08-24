import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Main implements ActionListener{
    final static int rows = 15;
    final static int cols = 15;
    final static int cellWidth = 25;
    final static Color DEFAULT_COLOR = Color.LIGHT_GRAY;;
    final static Color BLOCK_COLOR = Color.RED;
    final static Color START_COLOR = Color.GREEN;
    final static Color END_COLOR = Color.ORANGE;
    final static Color PATH_COLOR = Color.CYAN;
    static GridMap gridPanel = new GridMap(rows, cols, cellWidth);

    static JFrame frame;
    static JPanel mainPanel;
    static JPanel sidePanel;
    static JButton runButton;
    static JButton setStartButton;
    static JButton setEndButton;
    static JButton resetButton;
    static JButton generateBlocksButton;

    static MapLocation start;
    static MapLocation end;
    static boolean setStartBoolean = false;
    static boolean setEndBoolean = false;

    static Set<MapLocation> blockLocations;

    public static void main(String[] args) {
        Main runner = new Main();

        blockLocations = new HashSet<MapLocation>();
        start = new MapLocation(2,2);
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
        runButton = new JButton("Find Path");
        runButton.addActionListener(this);
        setStartButton = new JButton("Set Start");
        setStartButton.addActionListener(this);
        setEndButton = new JButton("Set End");
        setEndButton.addActionListener(this);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        generateBlocksButton = new JButton("Generate Blocks");
        generateBlocksButton.addActionListener(this);
        sidePanel.add(title);
        sidePanel.add(runButton);
        sidePanel.add(setStartButton);
        sidePanel.add(setEndButton);
        sidePanel.add(resetButton);
        sidePanel.add(generateBlocksButton);

        mainPanel.add(gridPanel);
        mainPanel.add(sidePanel);

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private void updateBlockLocations() {
        Color[][] labels = gridPanel.getLabelColors();
        blockLocations = new HashSet<MapLocation>();
        for (int row = 0; row < labels.length; row++) {
            for (int col = 0; col < labels[row].length; col++) {
                if (labels[row][col].equals(BLOCK_COLOR)) {
                    blockLocations.add(new MapLocation(col,row));
                }
            }
        }
        //System.out.println("BLOCKS " + blockLocations);
    }

    protected void generatePath() {
        updateBlockLocations();
        AStarSearch aStar = new AStarSearch(start, end, cols-1, rows-1, blockLocations);
        Stack<MapLocation> path = aStar.getPath();
        //System.out.println("PATH: " + path);
        if(path != null) {
            drawPath(path);
        }
    }

    private void drawPath(Stack<MapLocation> pathStack){
        System.out.print("PATH: ");
        resetGrid(false);
        Iterator pathIterator = pathStack.iterator();
        while (pathIterator.hasNext()) {
            MapLocation pathLoc = (MapLocation) pathIterator.next();
            System.out.print(pathLoc);
            gridPanel.setLabelColor(pathLoc.y,pathLoc.x,PATH_COLOR);
        }
        System.out.println();
    }

    private void generateBlocks(){
        System.out.println("GENERATING BLOCKS");
        Color[][] colors = gridPanel.getLabelColors();
        Random rand = new Random();
        for (int row = 0; row < colors.length; row++) {
            for (int col = 0; col < colors[row].length; col++) {
                int num = rand.nextInt(5);
                if ( (colors[row][col] == DEFAULT_COLOR || colors[row][col] == PATH_COLOR) && (num == 0 || num == 1)) {
                        gridPanel.setLabelColor(col, row, BLOCK_COLOR);
                        updateBlockLocations();
                        AStarSearch aStar = new AStarSearch(start, end, cols-1, rows-1, blockLocations);
                        Stack<MapLocation> path = aStar.getPath();
                        if (path == null) {
                            gridPanel.setLabelColor(col, row, DEFAULT_COLOR);
                            System.out.println("NOPATH UNDO--------------------");
                        } else {
                            System.out.println(col + "," + row + " HAS A PATH: " + path);
                        }
                }else if (colors[row][col] == BLOCK_COLOR && num == 0) {
                    gridPanel.setLabelColor(col, row, DEFAULT_COLOR);
                }
            }
        }
    }

    private void setStartButton(){
        setStartBoolean = true;
    }

    private void setEndButton(){
        setEndBoolean = true;
    }

    private void resetGrid(boolean resetBlocks){
        gridPanel.resetGrid(resetBlocks);
    }


    //getters and setters
    public static MapLocation getStartLocation(){
        return start;
    }
    public static void setStartLocation(MapLocation location){
        start = location;
    }

    public static MapLocation getEndLocation(){
        return end;
    }
    public static void setEndLocation(MapLocation location){
        end = location;
    }

    // Action Listener
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
        if(e.getSource().equals(resetButton)) {
            resetGrid(true);
        }
        if(e.getSource().equals(generateBlocksButton)) {
            generateBlocks();
        }
    }
}
