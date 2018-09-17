package gui;

import nnetwork.NeuralNetwork;

import javax.swing.*;
import java.awt.Color;
import java.util.*;

public class MainPanel extends JPanel {

    private Drawing panel;

    public MainPanel(NeuralNetwork nn) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        panel = new Drawing();
        JPanel panell = new JPanel();
        JPanel mainPanel = new JPanel();
        JComboBox<String> colorChoose = new JComboBox<>();
        colorChoose.addItem("Black");
        colorChoose.addItem("Cyan");
        colorChoose.addItem("Pink");
        colorChoose.addItem("Blue");
        colorChoose.addItem("Magenta");
        colorChoose.addItem("Green");
        colorChoose.addItem("Orange");
        colorChoose.addActionListener(l -> {
            switch (colorChoose.getSelectedItem().toString()) {
                case "Black":
                    panel.color = Color.black;
                    break;
                case "Cyan":
                    panel.color = Color.cyan;
                    break;
                case "Pink":
                    panel.color = Color.pink;
                    break;
                case "Blue":
                    panel.color = Color.blue;
                    break;
                case "Magenta":
                    panel.color = Color.magenta;
                    break;
                case "Green":
                    panel.color = Color.green;
                    break;
                case "Orange":
                    panel.color = Color.orange;
                    break;
            }
        });
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(colorChoose);
        panel.setBackground(Color.WHITE);
        mainPanel.add(panel);
        panell.add(mainPanel);
        JTextArea txtResult = new JTextArea(28, 24);
        txtResult.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(txtResult);
        panell.add(jScrollPane);
        add(panell);

        txtResult.append("Accuracy: " + nn.accuracy + "%\n");

        JPanel btnPanel = new JPanel();

        JButton btnReg = new JButton("Recognize");
        btnReg.addActionListener(e -> {
            List<Float> data = getData();
            txtResult.append("My guess is : " + nn.predict(data)+ "\n");
        });


        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e ->
                panel.clear()
        );

        btnPanel.add(btnReg);
        btnPanel.add(btnClear);

        add(btnPanel);

    }

    private List<Float> getData() {
        List<List<Float>> data = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            data.add(new ArrayList<>());
            for (int j = 0; j < 32; j++) {
                data.get(i).add((float) (panel.getGrid()[j][i] ? 1 : 0));
            }
        }
        List<Float> sumr = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                float a = 0;
                for (int o = 0; o < 4; o++) {
                    for (int o2 = 0; o2 < 4; o2++) {
                        a += data.get(i * 4 + o).get(j * 4 + o2);
                    }
                }
                sumr.add(a);
            }
        }
        return sumr;
    }

}
