package nnetwork;

import gui.MainPanel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Initializer {
    static List<List<List<Float>>> trainSet = null;
    static List<List<List<Float>>> testSet = null;
    static boolean function = true;

    public static void setFunction(boolean function) {
        Initializer.function = function;
    }

    public static void initialize() {
        try {
            trainSet = makeSet("./optdigits.tra");
            testSet = makeSet("./optdigits.tes");
        } catch (IOException e) {
            e.printStackTrace();
        }

        NeuralNetwork nn = new NeuralNetwork(trainSet, testSet);

        JFrame frame = new JFrame();
        MainPanel dp = new MainPanel(nn);
        frame.add(dp);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

    }


    public static List<List<List<Float>>> makeSet(String namefile) throws IOException {
        List<List<List<Float>>> trainSet = new ArrayList<>();
        File[] filesToRead = {new File(namefile),
        };
        for (File f : filesToRead) {
            FileChannel fc = FileChannel.open(f.toPath());
            ByteBuffer bb = ByteBuffer.allocate(1024);
            StringBuffer stringBuffer = new StringBuffer();
            int numsToRead = fc.read(bb);
            while (numsToRead != -1) {
                bb.flip();
                CharBuffer cb = Charset.forName("UTF-8").decode(bb);
                stringBuffer.append(cb.toString());
                bb.clear();
                numsToRead = fc.read(bb);
            }
            String[] rows = stringBuffer.toString().split("\n");
            for (String row : rows)
                trainSet.add(translate(row));
        }

        return trainSet;
    }

    public static List<List<Float>> translate(String str) {
        List<List<Float>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        result.add(new ArrayList<>());
        String inputs[] = str.split(",");
        for (int i = 0; i < 64; i++)
            result.get(0).add(Float.parseFloat(inputs[i]));
        for (int i = 0; i < 10; i++)
            result.get(1).add(Float.parseFloat(inputs[inputs.length - 1]) == i ? 1.0f : function ? 0f : -1.0f);
        return result;
    }

}
