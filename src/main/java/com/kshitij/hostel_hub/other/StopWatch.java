package com.kshitij.hostel_hub.other;

public class StopWatch {
    private long startTime;
    private long stopTime;
    private boolean running;

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    public long getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = System.currentTimeMillis() - startTime;
        } else {
            elapsed = stopTime - startTime;
        }
        return elapsed;
    }

    public long getElapsedTimeSecs() {
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime) / 1000;
        } else {
            elapsed = (stopTime - startTime) / 1000;
        }
        return elapsed;
    }
}
