package ai.mate.chess.util;

import java.util.Timer;
import java.util.TimerTask;

public final class ChessTimer {

    private final Timer timer;
    private final TimerTask task;
    private final long timeMillis;


    public ChessTimer(long timeMillis) {
        this.timeMillis = timeMillis;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("omfg!");
            }
        };
    }

    public void start() {
        timer.scheduleAtFixedRate(task, 0, timeMillis);
    }

    public void stop() {
        timer.cancel();
    }

}