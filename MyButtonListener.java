import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class MyButtonListener implements ActionListener {
    private JButton button;

    public MyButtonListener(JButton button) {
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("PRESSED "+ e.getID());
    }
}
