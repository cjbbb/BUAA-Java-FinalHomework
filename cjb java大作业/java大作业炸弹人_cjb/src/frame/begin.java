package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class begin {
    private Start start = null;
    private JLabel start_picture;
    private JLabel start_Button;

    public JButton b;

    public begin(Start start) {
        super();
        this.start = start;
        beginUi();
    }

    public void beginUi() {

        ImageIcon play_Icom = new ImageIcon("img/background/rect2.png");
        start_Button=new JLabel(play_Icom);
        start_Button.setBounds(400,340,300,100);
        start.add(start_Button);
        ImageIcon i = new ImageIcon("img/background/background.png");
        i.setImage(i.getImage().getScaledInstance(945, 980, Image.SCALE_DEFAULT));
        start_picture = new JLabel(i);
        start_picture.setBounds(start.getX(), start.getY(), 945, 980);
        start.add(start_picture);

    }

    public void clossAll() {
        start_picture.setVisible(false);
        start.remove(start_picture);
        start.remove(start_Button);
        start_Button.setVisible(false);
    }

}
