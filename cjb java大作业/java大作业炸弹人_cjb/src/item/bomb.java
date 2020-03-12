package item;

import frame.Start;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static frame.Start.*;
import static player.player.Step;

public class bomb extends item {
    public fire fire_jlabel;
    Lock lock = new ReentrantLock();
    private Start start;
    int Bomb_X, Bomb_Y;
    private JLabel way;
    int Bomb_map_X = Bomb_X / Step;
    int Bomb_map_Y = Bomb_Y / Step;
    public JLabel bomb_jlabel = new JLabel(new ImageIcon("img/item/boom.png"));
    public JLabel fire = new JLabel(new ImageIcon("img/item/fire.png"));
    public JLabel fire_up = new JLabel(new ImageIcon("img/item/fire.png"));
    public JLabel fire_down = new JLabel(new ImageIcon("img/item/fire.png"));
    public JLabel fire_left = new JLabel(new ImageIcon("img/item/fire.png"));
    public JLabel fire_right = new JLabel(new ImageIcon("img/item/fire.png"));
    public int isBombed = 0;
    private int offset;
    public ArrayList<JLabel> fire_list = new ArrayList<JLabel>();
    public int AorB = 0;//0为A，1为B
    //传递fire的偏移量offset
    public bomb(int bomb_x, int bomb_y, Start start, int offset,int AorB) {//传送位置为地图像素位置
        super(start);
        init(bomb_jlabel);
        this.Bomb_X = bomb_x;
        this.Bomb_Y = bomb_y;
        this.start = start;
        this.offset = offset;
        this.AorB =AorB;



        if (start.datas_wupin[Bomb_map_Y][Bomb_map_X] != FIRE_ROUTE) {//此处还没有炸弹
            start.datas_wupin[Bomb_map_Y][Bomb_map_X] = 0; // 设置为0不让进入
            count(); //爆炸计时
        }
    }

    public void fire(int Fire_x, int Fire_y, JLabel fire) {
      //  start.datas[Fire_x / Step][Fire_y / Step] = FIRE_ROUTE;
        init(fire);
        start.add(fire);
        setJLabel(fire, Fire_x, Fire_y);
        fire.setVisible(true);
        JLabel Bomb = start.wayMap[Fire_y/Step][Fire_x/Step];
        Bomb.setVisible(false);
        JLabel way = start.BombMap[Fire_y/Step][Fire_x/Step];
        way.setVisible(false);
        count_fire(fire, Fire_x, Fire_y);

    }

    public void remove_bomb() {
        bomb_jlabel.setVisible(false);
        JLabel way = start.wayMap[Bomb_map_Y][Bomb_map_X];
        way.setVisible(true);
      //  System.out.println(NOW_BOMB_NUMBER);
        if(AorB==0) {
            NOW_BOMB_NUMBER_A--;
        }
        if(AorB==1){
            NOW_BOMB_NUMBER_B--;
        }
        start.remove(bomb_jlabel);
    }

    public void remove_fire(JLabel fire, int fire_x, int fire_y) {
        fire.setVisible(false);
        JLabel way = start.wayMap[fire_y/Step][fire_x/Step];
        way.setVisible(true);
        start.datas_fire[fire_y / Step][fire_x / Step] = SAFE_ROUTE;
        start.remove(fire);
    }

    public void count_fire(JLabel fire, int fire_x, int fire_y) {
        Thread t2 = new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
                remove_fire(fire, fire_x, fire_y);
            }
        };
        t2.start();
    }

    public void judge_fire(int center_x, int center_y, int offset) {
        for(int i=1;i<=1;i++) {
            //    System.out.println(start.datas[center_y/Step-offset][center_x/Step] );
            if (start.datas[center_y / Step - i][center_x / Step] != BOX_ROUTE) { //上
                fire(center_x, center_y - i * Step, fire_up);
                start.datas_fire[center_y / Step - i][center_x / Step] = FIRE_ROUTE;
            }
            if (start.datas[center_y / Step + i][center_x / Step] != BOX_ROUTE) { //下
                fire(center_x, center_y + i * Step, fire_down);
                start.datas_fire[center_y / Step + i][center_x / Step] = FIRE_ROUTE;
            }
            if (start.datas[center_y / Step][center_x / Step - i] != BOX_ROUTE) { //上
                fire(center_x - i * Step, center_y, fire_left);
                start.datas_fire[center_y / Step][center_x / Step - i] = FIRE_ROUTE;
            }
            if (start.datas[center_y / Step][center_x / Step + i] != BOX_ROUTE) { //上
                fire(center_x + i * Step, center_y, fire_right);
                start.datas_fire[center_y / Step][center_x / Step + i] = FIRE_ROUTE;
            }
            //是BOX的情况
            if (start.datas[center_y / Step - i][center_x / Step] == BOX_ROUTE) { //上
                remove_Box(center_x / Step, center_y / Step - i);
                start.datas_fire[center_y / Step - i][center_x / Step] = FIRE_ROUTE;
                fire(center_x, center_y - i * Step, fire_up);
            }
            if (start.datas[center_y / Step + i][center_x / Step] == BOX_ROUTE) { //下
                remove_Box(center_x / Step, center_y / Step + i);
                start.datas_fire[center_y / Step + i][center_x / Step] = FIRE_ROUTE;
                fire(center_x, center_y + i * Step, fire_down);
            }
            if (start.datas[center_y / Step][center_x / Step - i] == BOX_ROUTE) { //上
                remove_Box(center_x / Step - i, center_y / Step);
                start.datas_fire[center_y / Step][center_x / Step - i] = FIRE_ROUTE;
                fire(center_x - i * Step, center_y, fire_left);
            }
            if (start.datas[center_y / Step][center_x / Step + i] == BOX_ROUTE) { //上
                remove_Box(center_x / Step + i, center_y / Step);
                start.datas_fire[center_y / Step][center_x / Step + i] = FIRE_ROUTE;
                fire(center_x + i * Step, center_y, fire_right);
            }
        }
    }
    public void remove_Box(int Box_x, int Box_y){
        JLabel abox = start.boxMap.get(String.valueOf(Box_y)+String.valueOf(Box_x)); //注意顺序
    //    System.out.println(String.valueOf(Box_x)+String.valueOf(Box_y));
        abox.setVisible(false);
        start.datas[Box_y ][Box_x] = SAFE_ROUTE;
        start.remove(abox);
    }
    public void count() {
        Thread t1 = new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
                remove_bomb();
                judge_fire(Bomb_X, Bomb_Y, offset);
                fire(Bomb_X, Bomb_Y, fire);
            }
        };
        t1.start();
    }
}


