package player;

import frame.Start;
import item.Wupin;
import item.bomb;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import static frame.Start.*;
import static java.lang.Thread.sleep;
import static player.player.Step;

public class player_B {
    private static int offset = 1; //初始火焰偏移量为1
    private static int Bomb_number = 1; //初始炸弹量为1
    private static int direction;//0,1,2,3,上下左右
    public static int speed = 4;//人物速度
    private static  int release;
    private Image img;
    private int move_B_X;
    private int move_B_Y;
    int srcx;
    private player playerA;
    int srcy;
    private int width;
    private int height;//非常重要
    //public ConcurrentHashMap<String, Bomb> BombMap = new ConcurrentHashMap<>();
    ImageIcon play_img;
    private BufferedImage imagebuf;
    int dx = 0;
    int dy = 0;//move_B_X = move_B_X + dx
    private int curretZhenshu = 0;//当前帧数
    private int nextZhenshu;
    private Start start;
    private JLabel man_B;//人物图层
    private Wupin wupin;
    boolean[] isKeyPress = new boolean[4];

    public player_B(Start start, int x, int y, player playerA) throws IOException {
        this.move_B_X = x * Step;
        this.move_B_Y = y * Step;
        File temp = new File("img/man4.png");
        imagebuf = ImageIO.read(temp);
        this.start = start;//frame
        play_img = new ImageIcon("img/man4.png");
        img = play_img.getImage();
        getCanshu();
        this.man_B = new JLabel(play_img);
        man_B.setBounds(move_B_X, move_B_Y, width, height);
        start.add(man_B);
        Check_isWupin();
        Check_isDanger();
        this.playerA = playerA;
        shuaxin1();
        //采用 匿名类 new Timee.Schedule(Time task{},0,20)
        shuaxin2();
        //刷新方向
        getCanshu();
        //  man_B.setVisible(true);*/

    }

    public void clean_myself() {
        man_B.setVisible(false);
        start.remove(man_B);
    }

    public void shuaxin1() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                //  System.out.println(srcy);

                img = imagebuf.getSubimage(srcx, srcy, width, height);
                ImageIcon temp1 = new ImageIcon(img);
                man_B.setIcon(temp1);//man_B 为bounds
                srcx = curretZhenshu * width;
                //由图片特判，direction 0,1,2,3//上下左右
                srcy = getSrcy();

                //     srcy = (3-direction) * height;
                //System.out.println(direction);
                move();
                start.repaint();
                // Check_isDanger(move_B_X, move_B_X);
                try {
                    sleep(20);
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 20);

    }

    public void shuaxin2() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                if (direction!=5&&release!=4)
                    curretZhenshu = (curretZhenshu + nextZhenshu) % 3;//任务单方向帧数

            }
        }, 0, 50);

    }

    public void move() {
        if (dy == -speed && checkRout(direction)) {//上
            move_B_Y += dy;
        }
        if (dy == speed && checkRout(direction)) {//下
            move_B_Y += dy;
        }
        if (dx == -speed && checkRout(direction)) {//左
            move_B_X += dx;
        }
        if (dx == speed && checkRout(direction)) {//右
            move_B_X = move_B_X + dx;
        }
        man_B.setLocation(move_B_X, move_B_Y);

    }

    //与找到的图片适配
    public int getSrcy( ) {
        if (direction == 0&&release!=4) return height * 1;
        if (direction == 1&&release!=4) return height * 0;
        if (direction == 2&&release!=4) return height * 2;
        if (direction == 3&&release!=4) return height * 5;
        if (release == 4&&direction!=5) return height * 3;
        if (direction==5) return  height*4;
        return 0;
    }

    public void Check_isWupin() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                double Man_y = Math.round(move_B_Y / (double) Step) * Step;
                double Man_x = Math.round(move_B_X / (double) Step) * Step;
                int real_x = (int) Man_x / Step;
                int real_y = (int) Man_y / Step;
                if (start.datas_wupin[real_y][real_x] == ProbBomb_ROUTE) {
                    //        System.out.println("A吃到了炸弹物品。炸弹数量+1");
                    //          System.out.println(String.valueOf(real_x) + String.valueOf(real_y));
                    JLabel aBomb = wupin.bombPropMap.get(String.valueOf(real_y) + String.valueOf(real_x));
                    BOMB_NUMBER_B++;
                    aBomb.setVisible(false);
                    start.remove(aBomb);
                    start.datas_wupin[real_y][real_x] = 0;
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (start.datas_wupin[real_y][real_x] == ProbIce_ROUTE) {
                    System.out.println("B吃到了冰块。A的移动速度-1");
                    //          System.out.println(String.valueOf(real_x) + String.valueOf(real_y));
                    JLabel aice = wupin.icePropMap.get(String.valueOf(real_y) + String.valueOf(real_x));
                    player.speed -= 1;
                    start.datas_wupin[real_y][real_x] = 0;
                    aice.setVisible(false);
                    start.remove(aice);
                }
                if (start.datas_wupin[real_y][real_x] == ProbShoes_ROUTE) {
                    //        System.out.println("A吃到了炸弹物品。炸弹数量+1");
                    //          System.out.println(String.valueOf(real_x) + String.valueOf(real_y));
                    JLabel ashoes = wupin.shoesPropMap.get(String.valueOf(real_y) + String.valueOf(real_x));
                    speed++;
                    start.datas_wupin[real_y][real_x] = 0;
                    ashoes.setVisible(false);
                    start.remove(ashoes);
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (start.datas_wupin[real_y][real_x] == ProbLife_ROUTE) {
                    System.out.println("B吃到了血瓶。血量+1");
                    //          System.out.println(String.valueOf(real_x) + String.valueOf(real_y));
                    JLabel alife = wupin.ProbLifeMap.get(String.valueOf(real_y) + String.valueOf(real_x));
                    LIFE_PLAYERB++;
                    start.datas_wupin[real_y][real_x] = 0;
                    alife.setVisible(false);
                    start.remove(alife);
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 200);
    }

    public boolean checkRout(int direction) {
        int x = man_B.getX();
        int y = man_B.getY();
        int temp;
        double tempx, tempy;
        switch (direction) {
            case 0://上
                tempy = (int) Math.floor((y - 1) / (double) Step);//返回》=该数 的最小整数
                tempx = (int) Math.floor((x - 1) / (double) Step);
                temp = start.datas[(int) tempy][(int) tempx];
                if (temp == 4 || temp == 3 || temp == FIRE_ROUTE) {
                    return true;
                }
                break;
            case 1://下
                tempy = (int) Math.ceil((y + 1) / (double) Step);//返回》=该数 的最小整数
                tempx = Math.round(x / (double) Step);
                temp = start.datas[(int) tempy][(int) tempx];
                if (temp == 4 || temp == 3 || temp == FIRE_ROUTE) {
                    return true;
                }
                break;
            case 2://左
                tempy = Math.round(y / (double) Step);
                tempx = (int) Math.floor((x - 1) / (double) Step);
                temp = start.datas[(int) tempy][(int) tempx];
                if (temp == 4 || temp == 3 || temp == FIRE_ROUTE) {
                    return true;
                }
                break;
            case 3://右
                tempy = Math.round(y / (double) Step);
                tempx = (int) Math.ceil((x + 1) / (double) Step);
                temp = start.datas[(int) tempy][(int) tempx];
                if (temp == 4 || temp == 3 || temp == FIRE_ROUTE) {
                    return true;
                }
                break;
        }
        return false;
    }

    public void Check_isDanger() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                double man_B_y = Math.round(move_B_Y / (double) Step) * Step;
                double man_B_x = Math.round(move_B_X / (double) Step) * Step;
                int real_x = (int) man_B_x / Step;
                int real_y = (int) man_B_y / Step;

                if (start.datas_fire[real_y][real_x] == FIRE_ROUTE) {
                    System.out.println("角色B生命-1");
                    LIFE_PLAYERB--;
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (LIFE_PLAYERB <= 0) {
                    System.out.println("角色B已经死亡");
                    direction=5;
                    start.Player_isDead();
                    try {
                        sleep(100000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, 0, 20);
    }


    private int getDirection() {
        return direction;
    }

    public void keypress(int key) {
        nextZhenshu = 1;
        release = 0;
        // isKeyPress[direction]=true;
        int x = man_B.getX();
        int y = man_B.getY();
        if (key == KeyEvent.VK_J) {
            double boom_x = Math.round(x / (double) Step) * Step;
            double boom_y = Math.round(y / (double) Step) * Step;

            setBomb((int) boom_x, (int) boom_y);
        }
        if (key == KeyEvent.VK_A) {
            dx = -speed;
            direction = 2;
        }
        if (key == KeyEvent.VK_D) {
            dx = +speed;
            direction = 3;
        }
        if (key == KeyEvent.VK_W) {
            dy = -speed;
            direction = 0;
        }
        if (key == 83) {
            dy = +speed;
            direction = 1;
        }
    }

    public void keyReleased(int key) {

        if (key == KeyEvent.VK_A) {//2
            dx = 0;
            release = 1;
        }
        if (key == KeyEvent.VK_D) {//3
            dx = 0;
            release = 4;
        }
        if (key == KeyEvent.VK_W) {//0
            dy = 0;
            release = 4;
        }
        if (key == 83) {//1
            dy = 0;
            release = 4;
        }
        //
        for (int i = 0; i < isKeyPress.length; i++) {
            if (isKeyPress[i]) {
                nextZhenshu = 1;
                for (int j = 0; j < 3; j++) {
                    isKeyPress[j] = false;
                }
                break;
            }
            if (i >= isKeyPress.length) {
                nextZhenshu = 0;
            }
        }
    }

    public void getCanshu() {
        width = img.getWidth(null) / 3;
        height = img.getHeight(null) / 6;
    }

    //设置炸弹
    public void setBomb(int map_x, int map_y) {
        if (NOW_BOMB_NUMBER_B < BOMB_NUMBER_B) {
            NOW_BOMB_NUMBER_B++;
            JLabel Bomb = start.BombMap[map_y/Step][map_x/Step];
            Bomb.setVisible(true);
            bomb b = new bomb(map_x, map_y, start, offset, 1);

        }
    }
}