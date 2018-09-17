package functions;

public class Unipolar {
    public static float getActivation(float net) {
        return (float) (1/(1+Math.exp(-net)));
    }

    public static float getDerivative(float f) {
        return (f * (1 - f));
    }
}
