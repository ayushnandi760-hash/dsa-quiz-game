package com.quizgame;

import javax.swing.Timer;
import java.awt.event.ActionListener;

public class TimerManager {
    private Timer timer;
    private int secondsLeft;
    private Runnable onTick;
    private Runnable onTimeout;

    public TimerManager(int initialSeconds, Runnable onTick, Runnable onTimeout) {
        this.secondsLeft = initialSeconds;
        this.onTick = onTick;
        this.onTimeout = onTimeout;

        ActionListener taskPerformer = evt -> {
            secondsLeft--;
            if (this.onTick != null) {
                this.onTick.run();
            }
            if (secondsLeft <= 0) {
                stopTimer();
                if (this.onTimeout != null) {
                    this.onTimeout.run();
                }
            }
        };
        // 1000 ms = 1 second
        timer = new Timer(1000, taskPerformer);
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void resetTimer(int seconds) {
        timer.stop();
        this.secondsLeft = seconds;
        if (this.onTick != null) {
            this.onTick.run();
        }
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }
}
