package frame;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;

public class Time {
    public static int needtime = 5 * 60;
    public Start start;
    public JLabel time;

    public Time(Start start) throws InterruptedException {
        this.start = start;
        Init();

    }

    public static String usedTime = null;

    public void Init() throws InterruptedException {
        Time();

        time = new JLabel();

        Font font = new Font("黑体", Font.PLAIN, 40);//创建1个字体实例
        time.setFont(font);
        time.setForeground(Color.black);
        time.setBounds(40, 60, 600, 40);
        time.setVisible(true);
        start.add(time);
    }

    private void Time() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (start.gamestart == true && start.again ==false) {
                    needtime--;
                }
                if (start.gamestart == false) {
                    usedTime = ("GameOver");
                }
                long mm = needtime / 60 % 60;
                long ss = needtime % 60;
                if (needtime > 0)
                    usedTime = (mm + "分钟" + ss + "秒");
                if (needtime <= 0) {
                    start.STILLLIVE = false;
                    start.Player_isDead();
                    usedTime = ("GameOver");
                }

                time.setText("倒计时： " + (usedTime));
            }
        }, 0, 1000);
    }
}