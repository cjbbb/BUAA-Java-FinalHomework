package item;

import frame.Start;

import static frame.Start.FIRE_ROUTE;
import static player.player.Step;

import javax.swing.*;

public class item {

    public Start start;

    public item(Start start) {
        this.start = start;
    }

    public static void init(JLabel jlabel) {
        jlabel.setBounds(Step, Step, Step, Step);
    }

    public void setDanger(int x, int y) {
        start.datas[y][x] = FIRE_ROUTE;
    }//注意x,y的关系

    public void setJLabel(JLabel jlabel, int x, int y) {
        jlabel.setLocation(x, y);
    }
}
