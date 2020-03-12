package frame;

import javax.swing.*;

public class GameOver {
    public static JLabel lab_over;
    public Start start;
    public GameOver(Start start){
        this.start =start;
        Icon i = new ImageIcon("img/gameOver.png");	//加载输了后结束界面图片
        this.lab_over = new JLabel(i);
        lab_over.setBounds(300, 400, 288, 192);
        start.add(lab_over);
        lab_over.setVisible(false);
    }
    public void clean(){
        lab_over.setVisible(false);
        start.remove(lab_over);
    }
}
