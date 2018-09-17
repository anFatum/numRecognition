package functions;

public class Bipolar {

    public static float getActivation(float net) {
        return (float)((2/(1+Math.exp(-net)))-1);
    }

    public static float getDerivative(float f) {
        return 0.5f*(1-f*f);
    }

}
