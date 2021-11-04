package templatemethod.barista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CoffeeWithHook extends CaffeineBeverageWithHook{
    @Override
    void brew() {
        System.out.println("필터로 커피를 우리는 중");
    }

    @Override
    void addCondiments() {
        System.out.println("우유와 설탕을 추가하는 중");
    }

    @Override
    public boolean customerWantsCondiments() {
        String answer = getUserInput();

        return answer.toLowerCase().startsWith("y");
    }

    private String getUserInput() {
        String answer = null;

        System.out.println("커피에 우유와 설탕을 넣어 드릴까요? (y/n)");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            answer = br.readLine();
        } catch (IOException e) {
            System.out.println("IO 오류");
        }

        if(answer == null ){
            return "no";
        }

        return answer;
    }

}
