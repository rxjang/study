package Command;

public class RemoteLoader {
    public static void main(String[] args) {
//        RemoteControl remoteControl = new RemoteControl();
//
//        Light livingRoomLight = new Light("Living Room");
//        Light kitchenLight = new Light("Kitchen");
//
//        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
//        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
//        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
//        LightOnCommand kitchenLightOff = new LightOnCommand(kitchenLight);
//
//        remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
//        remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff);
//
//        System.out.println(remoteControl);
//
//        remoteControl.onButtonWasPushed(0);
//        remoteControl.offButtonWasPressed(0);
//        remoteControl.onButtonWasPushed(1);
//        remoteControl.offButtonWasPressed(1);
//
//
//        // ----------------- undo test -----------------
//        RemoteControlWithUndo remoteControlWithUndo = new RemoteControlWithUndo();
//
//        Light toiletLight = new Light("toilet");
//        LightOnCommand toiletLightOn = new LightOnCommand(toiletLight);
//        LightOffCommand toiletLightOff = new LightOffCommand(toiletLight);
//
//        remoteControlWithUndo.setCommand(0, toiletLightOn, toiletLightOff);
//
//        remoteControlWithUndo.onButtonWasPushed(0);
//        remoteControlWithUndo.offButtonWasPressed(0);
//        System.out.println(remoteControlWithUndo);
//        remoteControlWithUndo.undoButtonWasPushed();
//        remoteControlWithUndo.offButtonWasPressed(0);
//        remoteControlWithUndo.onButtonWasPushed(0);
//        System.out.println(remoteControlWithUndo);
//        remoteControlWithUndo.undoButtonWasPushed();


        RemoteControlWithUndo remoteControl = new RemoteControlWithUndo();

        CeilingFan ceilingFan = new CeilingFan("Living Room");

        CeilingFanMediumCommand ceilingFanMediumCommand = new CeilingFanMediumCommand(ceilingFan);
        CeilingFanHighCommand ceilingFanHighCommand = new CeilingFanHighCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOffCommand = new CeilingFanOffCommand(ceilingFan);

        remoteControl.setCommand(0, ceilingFanMediumCommand, ceilingFanOffCommand);
        remoteControl.setCommand(1, ceilingFanHighCommand, ceilingFanOffCommand);

        remoteControl.onButtonWasPushed(0);
        remoteControl.offButtonWasPressed(0);
        System.out.println(remoteControl);
        remoteControl.undoButtonWasPushed();

        remoteControl.onButtonWasPushed(1);
        System.out.println(remoteControl);
        remoteControl.undoButtonWasPushed();

    }
}
