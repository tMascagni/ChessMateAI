package ai.mate.chess.util;

import javax.swing.*;
import java.awt.event.ActionListener;

public final class ChessTimer {

    private final Timer timer;

    public interface EndOfTurnListener extends ActionListener {

    }

    public ChessTimer(int timeMillis, EndOfTurnListener endOfTurnListener) {
        timer = new Timer(timeMillis, endOfTurnListener);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

}