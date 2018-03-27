package ai.mate.chess.util;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public final class ChessTimer {

    private Timer timer;

    public interface EndOfTurnListener extends ActionListener {

    }

    public ChessTimer(int timeSeconds, ActionListener endOfTurnListener) {
        int millis = (int) TimeUnit.SECONDS.toMillis(timeSeconds);
        timer = new Timer(millis, endOfTurnListener);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

}