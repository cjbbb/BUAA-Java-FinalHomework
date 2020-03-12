import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;

//测试 repaint

public class test extends JFrame {
    int x = 40, y = 50;

    test() {
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.GREEN);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new test();
    }

    //  public void paint
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.fillOval(x, y, 20, 20);

        x++;
        y++;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            repaint();

    }
}