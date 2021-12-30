package main;


import frame.Start;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class main {
    public static Start start;
    public static void main(String[] args) throws IOException, InterruptedException {
        new Start();
      //  t1.start();
    }
    public static void restart(Start frame) throws IOException, InterruptedException {
        start = frame;
        start.dispose();
        sleep(100);
        new Start();
    }

}
