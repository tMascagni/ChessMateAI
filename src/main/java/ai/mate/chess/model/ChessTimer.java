package ai.mate.chess.model;

import java.util.Timer;
import java.util.TimerTask;

public final class ChessTimer {

    private Timer timer;

    public ChessTimer(final int timeMillis) {

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(timeMillis + "ms has passed.");
            }
        }, 0, timeMillis);
    }


}