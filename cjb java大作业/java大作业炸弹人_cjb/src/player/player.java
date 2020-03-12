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

public class player {
    private Wupin wupin;
    private static int offset = 1; //初始火焰偏移量为1
    private static int Bomb_number = 1; //初始炸弹量为1
    private static int Now_Bomb_number = 0;
    private static int direction;//0,1,2,3,上下左右
    public static final int Step = 63;
    public static int speed = 4;//人物速度
    private Image img;
    private int moveX;
    private int moveY;
    private player_B playerB;
    int srcx;
    int srcy;
    private int width;
    private static int release = 0;
    private int height;//非常重要
    //public ConcurrentHashMap<String, Bomb> BombMap = new ConcurrentHashMap<>();
    ImageIcon play_img;
    private BufferedImage imagebuf;
    int dx = 0;
    int dy = 0;//movex = movex + dx
    private int curretZhenshu = 0;//当前帧数
    private int nextZhenshu;
    private Start start;
    private JLabel man;//人物图层

    boolean[] isKeyPress = new boolean[4];

    public player(Start start, int x, int y, player_B playerB) throws IOException {
        this.moveX = x * Step;
        this.moveY = y * Step;
        File temp = new File("img/man3.png");
        imagebuf = ImageIO.read(temp);
        this.start = start;//frame
        play_img = new ImageIcon("img/man3.png");
        img = play_img.getImage();
        getCanshu();
        this.man = new JLabel(play_img);
        man.setBounds(moveX, moveY, width, height);
        start.add(man);
        this.playerB = playerB;
        Check_isDanger();
        Check_isWupin();
        shuaxin1();
        //采用 匿名类 new Timee.Schedule(Time task{},0,20)
        shuaxin2();
        //刷新方向
        getCanshu();
        //  man.setVisible(true);*/

    }
    public void clean_myself() {
        man.setVisible(false);
        start.remove(man);
    }

    public void shuaxin1() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                //  System.out.println(srcy);

                img = imagebuf.getSubimage(srcx, srcy, width, height);
                ImageIcon temp1 = new ImageIcon(img);
                man.setIcon(temp1);//man 为bounds
                srcx = curretZhenshu * width;
                //由图片特判，direction 0,1,2,3//上下左右
                srcy = getSrcy();
                move();
                start.repaint();
                // Check_isDanger(moveX, moveX);
                try {
                    sleep(20);
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 10);

    }

    public void shuaxin2() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                if (release != 4&&direction!=5)
                    curretZhenshu = (curretZhenshu + nextZhenshu) % 3;//任务单方向帧数

            }
        }, 0, 50);

    }

    public void move() {
        if (dy == -speed && checkRout(direction)) {//上
            moveY += dy;
        }
        if (dy == speed && checkRout(direction)) {//下
            moveY += dy;
        }
        if (dx == -speed && checkRout(direction)) {//左
            moveX += dx;
        }
        if (dx == speed && checkRout(direction)) {//右
            moveX = moveX + dx;
        }
        man.setLocation(moveX, moveY);

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

    public boolean checkRout(int direction) {
        int x = man.getX();
        int y = man.getY();
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
                double Man_y = Math.round(moveY / (double) Step) * Step;
                double Man_x = Math.round(moveX / (double) Step) * Step;
                int real_x = (int) Man_x / Step;
                int real_y = (int) Man_y / Step;

                if (start.datas_fire[real_y][real_x] == FIRE_ROUTE) {
                    System.out.println("A收到了伤害，生命-1");
                    LIFE_PLAYERA--;
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (LIFE_PLAYERA <= 0) {
                    start.Player_isDead();
                    direction = 5;
                    try {
                        sleep(10000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 200);
    }

    public void Check_isWupin() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                double Man_y = Math.round(moveY / (double) Step) * Step;
                double Man_x = Math.round(moveX / (double) Step) * Step;
                int real_x = (int) Man_x / Step;
                int real_y = (int) Man_y / Step;

                if (start.datas_wupin[real_y][real_x] == ProbShoes_ROUTE) {
                    System.out.println("A吃到了鞋子。移动速度+2");
                    //          System.out.println(String.valueOf(real_x) + String.valueOf(real_y));
                    JLabel ashoes = wupin.shoesPropMap.get(String.valueOf(real_y) + String.valueOf(real_x));
                    speed += 2;
                    start.datas_wupin[real_y][real_x] = 0;
                    ashoes.setVisible(false);
                    start.remove(ashoes);

                }
                if (start.datas_wupin[real_y][real_x] == ProbIce_ROUTE) {
                    System.out.println("A吃到了冰块。B的移动速度-1");
                    //          System.out.println(String.valueOf(real_x) + String.valueOf(real_y));
                    JLabel aice = wupin.icePropMap.get(String.valueOf(real_y) + String.valueOf(real_x));
                    player_B.speed -= 1;
                    start.datas_wupin[real_y][real_x] = 0;
                    aice.setVisible(false);
                    start.remove(aice);
                }
                if (start.datas_wupin[real_y][real_x] == ProbBomb_ROUTE) {
                    System.out.println("A吃到了炸弹物品。炸弹数量+1");
                    //          System.out.println(String.valueOf(real_x) + String.valueOf(real_y));
                    JLabel aBomb = wupin.bombPropMap.get(String.valueOf(real_y) + String.valueOf(real_x));
                    BOMB_NUMBER_A++;
                    start.datas_wupin[real_y][real_x] = 0;

                    aBomb.setVisible(false);
                    start.remove(aBomb);
                }

                if (start.datas_wupin[real_y][real_x] == ProbLife_ROUTE) {
                    System.out.println("A吃到了血瓶。血量+1");
                    //          System.out.println(String.valueOf(real_x) + String.valueOf(real_y));
                    JLabel alife = wupin.ProbLifeMap.get(String.valueOf(real_y) + String.valueOf(real_x));
                    LIFE_PLAYERA++;
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

    private int getDirection() {
        return direction;
    }

    public void keypress(int key) throws InterruptedException {
        nextZhenshu = 1;
        release = 0;
        int x = man.getX();
        int y = man.getY();
        if (key == 32) {
            double boom_x = Math.round(x / (double) Step) * Step;
            double boom_y = Math.round(y / (double) Step) * Step;

            setBomb((int) boom_x, (int) boom_y);
        }
        if (key == KeyEvent.VK_LEFT) {
            dx = -speed;
            direction = 2;
            for (int i = 0; i < 4; i++) {
                isKeyPress[i] = false;
            }
            isKeyPress[direction] = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = +speed;
            direction = 3;
            for (int i = 0; i < 4; i++) {
                isKeyPress[i] = false;
            }
            isKeyPress[direction] = true;
        }
        if (key == KeyEvent.VK_UP) {
            dy = -speed;
            direction = 0;
            for (int i = 0; i < 4; i++) {
                isKeyPress[i] = false;
            }
            isKeyPress[direction] = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = +speed;
            direction = 1;
            for (int i = 0; i < 4; i++) {
                isKeyPress[i] = false;
            }
            isKeyPress[direction] = true;
        }
    }

    public void keyReleased(int key) {
        if (key == KeyEvent.VK_LEFT) {//2
            dx = 0;
            nextZhenshu = 1;
            release = 4;

        }
        if (key == KeyEvent.VK_RIGHT) {//3
            dx = 0;
            nextZhenshu = 1;
            release = 4;

        }
        if (key == KeyEvent.VK_UP) {//0
            dy = 0;
            nextZhenshu = 1;
            release = 4;

        }
        if (key == KeyEvent.VK_DOWN) {//1
            dy = 0;
            nextZhenshu = 1;
            release = 4;

        }
        //
        nextZhenshu=1;
    }

    public void getCanshu() {
        width = img.getWidth(null) / 3;
        height = img.getHeight(null) / 6;
    }

    //设置炸弹
    public void setBomb(int map_x, int map_y) throws InterruptedException {
        if (NOW_BOMB_NUMBER_A < BOMB_NUMBER_A) {
            NOW_BOMB_NUMBER_A++;

            JLabel Bomb = start.BombMap[map_y/Step][map_x/Step];
            Bomb.setVisible(true);

            bomb b = new bomb(map_x, map_y, start, offset, 0);
        }
    }
}