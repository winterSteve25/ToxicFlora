package wintersteve25.toxicflora.common.helper;

public class MathHelper {
    public static double getRangedRandom(double min, double max) {
        return (double) ((Math.random() * (max - min)) + min);
    }

    public static float getRangedRandom(float min, float max) {
        return (float) ((Math.random() * (max - min)) + min);
    }
}
