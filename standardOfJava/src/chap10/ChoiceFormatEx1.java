package chap10;

import java.text.ChoiceFormat;

public class ChoiceFormatEx1 {
    public static void main(String[] args) {
        double[] limits = {60, 70, 90, 90};

        String[] grades = {"D", "C", "B", "A"};

        int[] scores = {100, 95, 88, 70, 52, 60, 70};

        ChoiceFormat form = new ChoiceFormat(limits, grades);

        for(int i = 0; i <scores.length;i++) {
            System.out.println(scores[i] + " : " + form.format(scores[i]));
        }
    }
}
