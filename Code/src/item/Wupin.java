package item;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import frame.Start;

import javax.swing.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import static frame.Start.*;
import static player.player.Step;

public class Wupin {
    public Start start;

    public Wupin(Start start) {
        this.start = start;
        setBombProp();
        setShoes();
        setlife();
        setIceProp();
    }

    public static int bombPropCount = 6;//最大6个炸弹

    public static ConcurrentHashMap<String, JLabel> bombPropMap = new ConcurrentHashMap<String, JLabel>();

    public void setBombProp() {
        int ran = (int) (Math.random() * 100 + 1) % 10;            //设置随机生成道具：
        int ranCount = 1;                                    //辅助设计随机的计数器

        int temp_bombPropCount = 1;        //临时炸弹数量

        for (int i = 0; i < 15; i++) {
            if (temp_bombPropCount > bombPropCount)
                break;
            for (int j = 0; j < 15; j++) {
                if (start.datas[i][j] == 1 && start.datas_wupin[i][j] == 0) {
                    if (temp_bombPropCount > bombPropCount)
                        break;
                    if ((ranCount++) <= ran)
                        continue;
                    else {
                        System.out.println("炸弹位置在(y,x)：" + i + " " + j);
                        JLabel lab_Prop_bomb = new JLabel(new ImageIcon("img/Wupin_bomb.png"));
                        lab_Prop_bomb.setBounds(Step * j + 1, Step * i + 1, 63, 63);
                        start.datas_wupin[i][j] = ProbBomb_ROUTE;
                        start.add(lab_Prop_bomb);
                        lab_Prop_bomb.setVisible(true);
                        bombPropMap.put(String.valueOf(i) + String.valueOf(j), lab_Prop_bomb);
                        //    System.out.println(String.valueOf(i) + String.valueOf(j));
                        ran = (int) (Math.random() * 100 + 1) % 10;
                        ranCount = 1;
                        temp_bombPropCount++;
                        break;
                    }
                }
            }
        }
    }

    public static int icePropCount = 6;//最大6个冰块

    public static ConcurrentHashMap<String, JLabel> icePropMap = new ConcurrentHashMap<String, JLabel>();

    public void setIceProp() {
        int ran = (int) (Math.random() * 100 + 1) % 10;            //设置随机生成道具：
        int ranCount = 1;                                    //辅助设计随机的计数器

        int temp_icePropCount = 1;        //临时炸弹数量

        for (int i = 0; i < 15; i++) {
            if (temp_icePropCount > icePropCount)
                break;
            for (int j = 0; j < 15; j++) {
                if (start.datas[i][j] == 1 && start.datas_wupin[i][j] == 0) {
                    if (temp_icePropCount > icePropCount)
                        break;
                    if ((ranCount++) <= ran)
                        continue;
                    else {
                        System.out.println("冰块位置在(y,x)：" + i + " " + j);
                        JLabel lab_Prop_bomb = new JLabel(new ImageIcon("img/Wupin_ice.png"));
                        lab_Prop_bomb.setBounds(Step * j + 1, Step * i + 1, 63, 63);
                        start.datas_wupin[i][j] = ProbIce_ROUTE;
                        start.add(lab_Prop_bomb);
                        lab_Prop_bomb.setVisible(true);
                        icePropMap.put(String.valueOf(i) + String.valueOf(j), lab_Prop_bomb);
                        //    System.out.println(String.valueOf(i) + String.valueOf(j));
                        ran = (int) (Math.random() * 100 + 1) % 10;
                        ranCount = 1;
                        temp_icePropCount++;
                        break;
                    }
                }
            }
        }
    }


    public static int shoesPropCount = 6;//最大6个鞋子

    public static ConcurrentHashMap<String, JLabel> shoesPropMap = new ConcurrentHashMap<String, JLabel>();

    public void setShoes() {
        int ran = (int) (Math.random() * 100 + 1) % 10;            //设置随机生成道具：
        int ranCount = 1;                                    //辅助设计随机的计数器

        int temp_lifePropCount = 1;        //临时炸弹数量

        for (int i = 0; i < 15; i++) {
            if (temp_lifePropCount > shoesPropCount)
                break;
            for (int j = 0; j < 15; j++) {
                if (start.datas[i][j] == 1 && start.datas_wupin[i][j] == 0) {
                    if (temp_lifePropCount > shoesPropCount)
                        break;
                    if ((ranCount++) <= ran)
                        continue;
                    else {
                        System.out.println("鞋子位置在(y,x)：" + i + " " + j);
                        JLabel lab_Prop_shoes = new JLabel(new ImageIcon("img/Wupin_shoes.png"));
                        lab_Prop_shoes.setBounds(Step * j + 1, Step * i + 1, 63, 63);
                        start.datas_wupin[i][j] = ProbShoes_ROUTE;
                        start.add(lab_Prop_shoes);
                        lab_Prop_shoes.setVisible(true);
                        shoesPropMap.put(String.valueOf(i) + String.valueOf(j), lab_Prop_shoes);
                        //    System.out.println(String.valueOf(i) + String.valueOf(j));
                        ran = (int) (Math.random() * 100 + 1) % 10;
                        ranCount = 1;
                        temp_lifePropCount++;
                        break;
                    }
                }
            }
        }
    }


    public  JLabel  wayMap[][] = new JLabel[15][15];

    public static int LifeCount = 6;//最大6个鞋子

    public static ConcurrentHashMap<String, JLabel> ProbLifeMap = new ConcurrentHashMap<String, JLabel>();

    public void setlife() {
        int ran = (int) (Math.random() * 100 + 1) % 10;            //设置随机生成道具：
        int ranCount = 1;                                    //辅助设计随机的计数器

        int temp_lifePropCount = 1;        //临时炸弹数量

        for (int i = 0; i < 15; i++) {
            if (temp_lifePropCount > LifeCount)
                break;
            for (int j = 0; j < 15; j++) {
                if (start.datas[i][j] == 1 && start.datas_wupin[i][j] == 0) {
                    if (temp_lifePropCount > LifeCount)
                        break;
                    if ((ranCount++) <= ran)
                        continue;
                    else {
                        System.out.println("血瓶位置在(y,x)：" + i + " " + j);
                        JLabel lab_Prop_Life = new JLabel(new ImageIcon("img/Wupin_life.png"));
                        lab_Prop_Life.setBounds(Step * j + 1, Step * i + 1, 63, 63);
                        start.datas_wupin[i][j] = ProbLife_ROUTE;
                        start.add(lab_Prop_Life);
                        lab_Prop_Life.setVisible(true);
                        ProbLifeMap.put(String.valueOf(i) + String.valueOf(j), lab_Prop_Life);
                        //    System.out.println(String.valueOf(i) + String.valueOf(j));
                        ran = (int) (Math.random() * 100 + 1) % 10;
                        ranCount = 1;
                        temp_lifePropCount++;
                        break;
                    }
                }
            }
        }
    }
}
