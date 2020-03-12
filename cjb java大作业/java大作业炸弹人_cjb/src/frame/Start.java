package frame;


import item.Wupin;
import main.main;
import player.player;
import player.player_B;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import static player.player.Step;

public class Start extends JFrame implements KeyListener {
    public main mian;
    public player play_A;
    public player_B play_B;
    public begin Begin;
    public player player_AA;
    public static long startTime;
    public player_B player_BB;
    public boolean STILLLIVE = true;
    public boolean again = false;

    public GameOver End;
    public JLayeredPane jLayeredPane;
    public static int ProbBomb_ROUTE = 7;
    public static int LIFE_PLAYERA = 1;
    public static int LIFE_PLAYERB = 1;
    public static int ProbShoes_ROUTE = 6;
    public static int ProbLife_ROUTE = 5;
    public static int NOW_BOMB_NUMBER_A = 0;
    public static int NOW_BOMB_NUMBER_B = 0;
    public static int BOMB_NUMBER_A = 1;
    public static int BOMB_NUMBER_B = 1;
    public static int ONE_STEP = 62;
    public static int SAFE_ROUTE = 4;
    public static int FIRE_ROUTE = 9;
    public static int BOX_ROUTE = 1;
    public static int STONE_ROUTE = 0;
    public static int BOMB_ROUTE = 8;
    public static int ProbIce_ROUTE = 2;
    public static boolean gamestart = false;
    public HashMap<String, JLabel> boxMap = new HashMap<String, JLabel>();
    public int[][] datas_wupin = new int[15][15];
    public int[][] datas_fire = new int[15][15];
    public int[][] datas = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 3, 1, 4, 1, 1, 1, 1, 1, 4, 3, 1, 0},
            {0, 4, 4, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0},
            {0, 1, 0, 0, 0, 4, 3, 3, 4, 4, 0, 0, 0, 0, 0},
            {0, 4, 4, 4, 4, 4, 4, 3, 0, 4, 4, 4, 4, 4, 0},
            {0, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0},
            {0, 1, 1, 0, 0, 0, 0, 4, 4, 1, 1, 1, 0, 0, 0},
            {0, 4, 4, 4, 3, 4, 4, 3, 4, 3, 4, 1, 4, 4, 0},
            {0, 4, 1, 4, 1, 1, 4, 1, 4, 4, 4, 1, 4, 1, 0},
            {0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0},
            {0, 1, 1, 0, 1, 1, 0, 4, 4, 0, 0, 4, 0, 4, 0},
            {0, 4, 1, 1, 1, 4, 4, 4, 1, 4, 4, 4, 1, 1, 0},
            {0, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public Start() throws IOException, InterruptedException {
        start();
    }

    public void start() throws IOException, InterruptedException {
        Begin = new begin(this);
        Time time = new Time(this);
        End = new GameOver(this); //顺序很关键 //最先放置优先级最高
        musicInit();
        gamefram();
        playerInit();
        mapInit();
        wupinInit();
        BombInit();
        wayInit();
    }

    public JLabel wayMap[][] = new JLabel[15][15];

    public void wayInit() {
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                JLabel way = new JLabel(new ImageIcon("img/item/way.png"));
                way.setBounds(Step * j, Step * i, Step, Step);

                way.setVisible(true);
                this.add(way);
                wayMap[i][j] = way;
                //     jLayeredPane.add(way,0);
                // way.setOpaque(true);
            }
        }
    }

    public JLabel BombMap[][] = new JLabel[15][15];

    public void BombInit() {
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                JLabel Bomb = new JLabel(new ImageIcon("img/item/boom.png"));
                Bomb.setBounds(Step * j, Step * i, Step, Step);

                Bomb.setVisible(false);
                this.add(Bomb);
                BombMap[i][j] = Bomb;
            }
        }
    }

    public void wupinInit() {
        new Wupin(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void musicInit() {
        Music music = new Music(this);
        music.start();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        play_A.keyReleased(key);
        play_B.keyReleased(key);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key != 0) {
            Begin.clossAll();
            gamestart = true;
        }
        if (STILLLIVE) {
            try {
                play_A.keypress(key);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            play_B.keypress(key);

        }
        if (!STILLLIVE) {
            if (key == KeyEvent.VK_ENTER) {
                try {
                    STILLLIVE = true;
                    re_Start();
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void re_Start() throws IOException, InterruptedException {
        play_B.clean_myself();
        play_A.clean_myself();
        LIFE_PLAYERA = 1;
        LIFE_PLAYERB = 1;
        STILLLIVE = true;
        Time.needtime = 5 * 60;
        again = true;
        mian.restart(this);

    }


    public void gamefram() {
        this.setLayout(null);//绝对布局！
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭状态
        this.setTitle("崔建彬的大作业——泡泡堂pro,按下任意键开始游戏/死亡后按回车重新开始");//调皮——也是为了证明自己写点
        this.setSize(945, 980);//设置大小
        this.setLocation(500, 180);
        this.setResizable(false);//不可随意修改大小
        // this.add(jp);
        this.getContentPane().setBackground(Color.green);
        this.getContentPane().setVisible(true);
        this.setLocationRelativeTo(null);//居中
        this.setVisible(true);
        this.addKeyListener(this);
    }

    public ConcurrentHashMap<String, JLabel> WayMap = new ConcurrentHashMap<String, JLabel>();

    public void mapInit() {
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                if (datas[i][j] == 1) {
                    ImageIcon boxx = new ImageIcon("img/item/box.png");

                    JLabel box = new JLabel(boxx);
                    boxMap.put(String.valueOf(i) + String.valueOf(j), box);
                    //        System.out.println(String.valueOf(i)+String.valueOf(j));
                    box.setBounds(Step * j, Step * i, Step, Step);
                    //   jLayeredPane.add(box,2);
                    this.add(box);
                    box.setOpaque(true);

                }
                if (datas[i][j] == 0) {
                    JLabel stone = new JLabel(new ImageIcon("img/item/stone.png"));
                    stone.setBounds(Step * j, Step * i, Step, Step);
                    //  jLayeredPane.add(stone,1);
                    this.add(stone);
                    stone.setOpaque(false);
                }

                if (datas[i][j] == 3) {
                    JLabel tree = new JLabel(new ImageIcon("img/item/tree.png"));
                    tree.setBounds(Step * j, Step * i, Step, Step);
                    // jLayeredPane.add(tree,0);
                    this.add(tree);
                    tree.setOpaque(false);
                }
            }
        }
        this.repaint();
    }

    public void Player_isDead() {
        End.lab_over.setVisible(true);
        STILLLIVE = false;
        gamestart = false;
    }

    private void manInit() throws IOException {
        play_A = new player(this, 1, 2, play_B);
        play_B = new player_B(this, 10, 10, play_A);
    }

    private void playerInit() throws IOException {
        play_A = new player(this, 1, 2, play_B);//初始位置
        play_B = new player_B(this, 10, 10, play_A);
    }
}
