package com.mygdx.pmd;


import com.mygdx.pmd.Controller.Controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
public class Timer
{
    Controller controller;

    public static int tickRate = 20;

    public Timer(Controller controller)
    {
        this.controller = controller;
    }

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public void ticking()
    {
        final ScheduledFuture<?> tickHandler = scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                controller.update();
            }
        }
        , 0, tickRate, MILLISECONDS);
    }
}