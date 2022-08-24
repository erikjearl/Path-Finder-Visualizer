import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
    private Main main;

    public MyKeyListener(Main main) {
        this.main = main;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("key3");
        System.out.println(e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            main.generatePath();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key1");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key2");
    }
}