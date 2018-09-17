package nnetwork;

import functions.Bipolar;
import functions.Unipolar;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    public static void setLearningRate(float learningRate) {
        Neuron.learningRate = learningRate;
    }

    public static void setFunction(boolean function) {
        Neuron.function = function;
    }

    private static float learningRate = 0.03f;
    private static boolean function = true; // true - Unipolar, false - Bipolar

    private float[] wages;
    private List<Neuron> connectedNeurons;
    private float error;

    Neuron() {
        wages = new float[65]; // Initializing 65 wages for Neuron
        generateWages(); // Generate random wages
    }

    Neuron(List<Neuron> connectedNeurons) {
        wages = new float[connectedNeurons.size() + 1]; // Wages number + theta
        generateWages();
        this.connectedNeurons = connectedNeurons;
    }

    private void generateWages() {  // Generating random wages from -1 to 1
        for (int i = 0; i < wages.length; i++)
            wages[i] = Math.random() * ((int) (Math.random() * 2)) % 2 == 0 ? -1f : 1f;
    }

    private float predict(List<Float> input) {
        return function ? Unipolar.getActivation(getNet(input)) : Bipolar.getActivation(getNet(input));
    }

    public float predictAll(List<Float> input) {
        List<Float> calculatedInput = new ArrayList<>();
        if (connectedNeurons != null) {
            for (int i = 0; i < connectedNeurons.size(); i++) // making input from parent's output
                calculatedInput.add(connectedNeurons.get(i).predict(input));
        } else throw new NullPointerException();
        return function ? Unipolar.getActivation(getNet(calculatedInput)) : Bipolar.getActivation(getNet(calculatedInput));
    }

    private float getNet(List<Float> input) { // calculating NET
        float activation = wages[0];
        for (int i = 1; i < wages.length; i++)
            activation += wages[i] * input.get(i - 1);
        return activation;
    }

    void trainWeights(List<Float> input, float expected) { // training output layer
        if (connectedNeurons != null) {
            List<Float> calculatedInput = new ArrayList<>();
            for (int i = 0; i < connectedNeurons.size(); i++)
                calculatedInput.add(connectedNeurons.get(i).predict(input));

            float error = (expected - predict(calculatedInput)) * (function ? Unipolar.getDerivative(predict(calculatedInput))
                    : Bipolar.getDerivative(predict(calculatedInput)));

            for (int i = 0; i < connectedNeurons.size(); i++)
                connectedNeurons.get(i).error += error * wages[i + 1]; // spreading error to connected neurons in hidden layer

            wages[0] = wages[0] + error * learningRate;
            for (int i = 1; i < wages.length; i++)
                wages[i] = wages[i] + error * learningRate * calculatedInput.get(i - 1);
            // updating wages values
        }
    }

    void trainWeights(List<Float> input) { // training hidden layer

        if (connectedNeurons == null) {
            error *= (function ? Unipolar.getDerivative(predict(input)) : Bipolar.getDerivative(predict(input)));
            wages[0] = wages[0] + error * learningRate;
            for (int i = 1; i < wages.length; i++)
                wages[i] = wages[i] + error * learningRate * input.get(i - 1);
        }

        error = 0;

    }

}
