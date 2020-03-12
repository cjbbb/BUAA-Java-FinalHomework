package item;

import frame.Start;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static frame.Start.*;
import static player.player.Step;


public class fire extends item{
    Lock lock = new ReentrantLock();
    private static final int FIRE_EXIST_TIME = 2000;
    private int fire_center_x;
    private int fire_center_y;
    public Start gameframe;
    ArrayList<JLabel> fire_set = null;
    bomb bomb_obj = null;

    public fire(bomb bomb, Start gameframe,
                int fire_center_x, int fire_center_y, int single_offset) {
        super(gameframe);
        this.bomb_obj = bomb;
        this.gameframe = gameframe;

        this.single_offset = single_offset;

        //火焰一旦初始化，就倒计时火焰消失
        this.fire_center_x = fire_center_x;
        this.fire_center_y = fire_center_y;
        countFireDown();
    }

    private void setFirePlaceSafe(int map_x, int map_y) {
        if (gameframe.datas[map_x][map_y] == FIRE_ROUTE) {
            gameframe.datas[map_x][map_y] = SAFE_ROUTE;
        }
    }
    private void setBombPlaceSafe(int map_x, int map_y) {
        if (gameframe.datas[map_x][map_y] == BOMB_ROUTE) {
            gameframe.datas[map_x][map_y] = SAFE_ROUTE;
        }
    }


    private void countFireDown() {

        calculateExplodeArea(fire_center_x, fire_center_y);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                cleanFire(); //火焰消失
            }
        }, FIRE_EXIST_TIME); //2火焰秒后火焰消失
    }

    /**
     * 清除火焰
     */
    public void  cleanFire() {
        try {
            // lock.lock();
            if(fire_set.size() > 0)
                for (Iterator<JLabel> iterator = fire_set.iterator(); iterator.hasNext(); ) { //遍历火焰集合
                    JLabel fire = iterator.next();
                    fire.setVisible(false);
                    setFirePlaceSafe(fire.getY() / Step, fire.getX() / Step);
                    //  setBombPlaceSafe(fire_center_y / ONE_STEP, fire_center_x / ONE_STEP);
                    try {
                 /*   String str = Integer.toString(fire_center_x) + Integer.toString(fire_center_y);
                    Bomb temp_bomb = gameframe.aBomberMan.bombMap.get(str);
                    if (temp_bomb != null && temp_bomb.bomb_obj != null) {
                        gameframe.aBomberMan.bombMap.remove(str);
                    }*/
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("cleanFire:数组越界" + e);
                    } catch (NullPointerException e) {
                        System.out.println("cleanFire:空指针"+ e);
                    } finally {
                    }
                }
        } finally {
            //      lock.unlock();
        }
    }

    //用于防止火焰穿墙
    boolean flag_left = true;
    boolean flag_right = true;
    boolean flag_up = true;
    boolean flag_down = true;
    private int single_offset;

    /**
     * 基于炸弹的位置bomb_x, fire_center_y,计算火焰范围
     */
    public void calculateExplodeArea(int bomb_x, int bomb_y) {

        int fire_area = single_offset * Step; //fire_area为火焰偏移量
        explode(bomb_x, bomb_y, 0, 0);

        for (int i = Step; i <= fire_area; i += Step) {
            if (flag_up) {
                explode(bomb_x, bomb_y, 0, -i);
            }
            if (flag_left) {
                explode(bomb_x, bomb_y, -i, 0);
            }
            if (flag_right) {
                explode(bomb_x, bomb_y, i, 0);
            }
            if (flag_down) {
                explode(bomb_x, bomb_y, 0, i);
            }
        }
    }


    private void explode(int bomb_x, int bomb_y, int offsetX, int offsetY) {

        int coordX = bomb_x + offsetX;
        int coordY = bomb_y + offsetY;
        int map_y = coordX / Step;
        int map_x = coordY / Step;


        //如果是平地，设置该位置为危险，用FIRE_IN_ROUTE表示
        if (gameframe.datas[map_x][map_y] == SAFE_ROUTE || gameframe.datas[map_x][map_y] == BOMB_ROUTE) {
            setDanger(map_x, map_y);
        }

        JLabel fire = new JLabel(new ImageIcon("img/item/fire.png"));
        init(fire); //初始化火焰的大小


        boolean isBox = true;
        //炸到箱子或者石头，该方向停止
        if (gameframe.datas[map_x][map_y] == 0 || gameframe.datas[map_x][map_y] == 1) {
            if (offsetX > 0) {
                flag_right = false;
            } else if (offsetX < 0) {
                flag_left = false;
            }
            if (offsetY > 0) {
                flag_down = false;
            } else if (offsetY < 0) {
                flag_up = false;
            }

            //如果是箱子，移除箱子
            if (gameframe.datas[map_x][map_y] == BOX_ROUTE) {
                gameframe.datas[map_x][map_y] = SAFE_ROUTE; //如果是箱子，转换成平地

                //先拿到箱子， 再移除箱子
                isBox = true;
                //JLabel abox = gameframe..get(String.valueOf(map_y) + String.valueOf(map_x));
                //箱子爆开的位置设为危险
                setDanger(map_x, map_y);
           //     gameframe.remove(abox);
            }
        }

        fire.setLocation(coordX, coordY); //放置火焰到地图
        gameframe.add(fire);
        fire_set.add(fire);  //把火焰存进集合， 便于清除
    }
}
