package net.alive.utils.world;

public class TimeUtils {

    private long lastTime;

    public TimeUtils() {
        reset();
    }

    public long getCurrentTime() {
        return System.nanoTime() / 1000000;
    }

    public long getDifference() {
        return getCurrentTime() - lastTime;
    }

    public boolean time(float cPS) {
        return getDifference() >= cPS;
    }

    public void reset() {
        lastTime = getCurrentTime();
    }
}
