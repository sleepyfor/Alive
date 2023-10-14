package net.alive.utils.math;

public class MathUtils {

    public static float calculateGaussianValue(float x, float sigma) {
        double PI = Math.PI;
        double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    public static double random(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
}
