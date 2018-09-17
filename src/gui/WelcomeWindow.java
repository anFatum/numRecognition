package gui;

import nnetwork.Initializer;
import nnetwork.NeuralNetwork;

import javax.swing.*;
import java.awt.*;

public class WelcomeWindow extends JFrame {

    public WelcomeWindow() {
        super();

        JLabel neuronNumLabel = new JLabel("Neuron number (default = 20): ");
        JTextField neuronNumField = new JTextField(16);

        JLabel lerningRateLabel = new JLabel("Learning rate (default = 0.01): ");
        JTextField lerningRateField = new JTextField(16);

        JLabel epochNumLabel = new JLabel("Epochs number (default = 25): ");
        JTextField epochNumField = new JTextField(16);

        JLabel functionLabel = new JLabel("Activation function: ");
        JComboBox<String> function = new JComboBox<>();
        function.addItem("Bipolar");
        function.addItem("Unipolar");

        JPanel params = new JPanel();
        params.setLayout(new GridLayout(4, 2));
        params.add(neuronNumLabel);
        params.add(neuronNumField);
        params.add(lerningRateLabel);
        params.add(lerningRateField);
        params.add(epochNumLabel);
        params.add(epochNumField);
        params.add(functionLabel);
        params.add(function);

        JPanel mainPanel = new JPanel();
        JPanel button = new JPanel();
        JButton jb = new JButton("OK");
        jb.addActionListener(e -> {
                    try {
                        NeuralNetwork.setNeuronNum(Integer.parseInt(neuronNumField.getText()));
                    } catch (NumberFormatException n) {
                        NeuralNetwork.setNeuronNum(20);
                    }

                    try {
                        NeuralNetwork.setLearningRate(Float.parseFloat(lerningRateField.getText()));
                    } catch (NumberFormatException n) {
                        NeuralNetwork.setLearningRate(0.01f);
                    }

                    try {
                        NeuralNetwork.setEpochNum(Integer.parseInt(epochNumField.getText()));
                    } catch (NumberFormatException n) {
                        NeuralNetwork.setEpochNum(25);
                    }

                    NeuralNetwork.setFunction(function.getSelectedItem().toString().equals("Unipolar"));
                    Initializer.setFunction(function.getSelectedItem().toString().equals("Unipolar"));
                    Initializer.initialize();
                    this.dispose();

                }
        );
        button.add(jb);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        mainPanel.add(params);
        mainPanel.add(button);

        add(mainPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);


    }

}
