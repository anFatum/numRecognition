package nnetwork;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private List<Neuron> outputLayer;
    private static int EPOCH_NUM = 25;
    private static int NEURON_NUM = 32;
    private static float LEARNING_RATE = 0.03f;
    public int accuracy;

    public static void setEpochNum(int epochNum) {
        EPOCH_NUM = epochNum;
    }

    public static void setNeuronNum(int neuronNum) {
        NEURON_NUM = neuronNum;
    }

    public static void setLearningRate(float learningRate) {
        LEARNING_RATE = learningRate;
    }

    public static void setFunction(boolean FUNCTION) {
        NeuralNetwork.FUNCTION = FUNCTION;
    }

    private static boolean FUNCTION = true;


    public NeuralNetwork(List<List<List<Float>>> trainSet, List<List<List<Float>>> testSet){
        // Initializing network with train and test sets

        Neuron.setFunction(FUNCTION);
        Neuron.setLearningRate(LEARNING_RATE);

        List<Neuron> hiddenLayer = new ArrayList<>();
        for (int i = 0; i < NEURON_NUM; i++)
            hiddenLayer.add(new Neuron());
        // adding neurons to hidden layer
        outputLayer = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            outputLayer.add(new Neuron(hiddenLayer));
        // adding neurons to Output layer, connected to previous (hidden) layer


        for (int i = 0; i < EPOCH_NUM; i++)
            for (List<List<Float>> ls : trainSet) {
                for (int j = 0; j < 10; j++)
                    // learning output layer
                    outputLayer.get(j).trainWeights(ls.get(0), ls.get(1).get(j));
                for (int j = 0; j < NEURON_NUM; j++)
                    // learning hidden layer
                    hiddenLayer.get(j).trainWeights(ls.get(0));

        }

        int totalGuessed = 0;
        for (List<List<Float>> ls : testSet) {
            int expected = 0;
            for (int i = 0; i < 10; i++) {
                float value = ls.get(1).get(i);
                expected += value == 1.0f ? i : 0;
            }
            int calculated = predict(ls.get(0));
            if (calculated == expected) totalGuessed += 1;
        }

        accuracy = totalGuessed * 100 / 1797;
    }

    public int predict(List<Float> input){
        float max = -100;
        int calculated = -1;
        for (int j = 0; j < 10; j++) {
            float pred = outputLayer.get(j).predictAll(input);
            if (pred > max) {
                max = pred;
                calculated = j;
            }
        }
        return calculated;
    }

}
