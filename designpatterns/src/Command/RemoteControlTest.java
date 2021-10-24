package Command;

public class RemoteControlTest {    // 클라이언트 (손님)
    public static void main(String[] args) {
        SimpleRemoteControl remote = new SimpleRemoteControl();
        // Invoker(종업원)의 역할을 함. 필요한 작업을 요청 할 때 사용할 커맨드 객체를 인자로 전달 받음
        Light light = new Light();
        // 리시버(요리사)
        LightOnCommand lightOn = new LightOnCommand(light);
        // 커멘드 객체(주문서)를 생성. 이 때 리시버를 전달

        remote.setCommand(lightOn);
        // 커맨드 객체를 인보커에게 전달
        remote.buttonWasPressed();

        GarageDoor garageDoor = new GarageDoor();
        GarageDoorOpenCommand garageOpen = new GarageDoorOpenCommand(garageDoor);

        remote.setCommand(garageOpen);
        remote.buttonWasPressed();
    }
}
