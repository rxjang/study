package command;

public class MacroCommandRemoteLoader {
    public static void main(String[] args) {

        Light light = new Light("Living Room");
        Stereo stereo = new Stereo();
        CeilingFan ceilingFan = new CeilingFan("Living Room");

        LightOnCommand lightOn = new LightOnCommand(light);
        StereoOnWithCDCommand stereoOn = new StereoOnWithCDCommand(stereo);
        CeilingFanHighCommand ceilingFanOn = new CeilingFanHighCommand(ceilingFan);

        LightOffCommand lightOff = new LightOffCommand(light);
        StereoOffWithCDCommand stereoOff = new StereoOffWithCDCommand(stereo);
        CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);

        Command[] partyOn = { lightOn, stereoOn, ceilingFanOn};
        Command[] partyOff = {lightOff, stereoOff, ceilingFanOff};

        MacroCommand partyOnMacro = new MacroCommand(partyOn);
        MacroCommand partyOffMacro = new MacroCommand(partyOff);

        RemoteControlWithUndo remoteControl = new RemoteControlWithUndo();

        remoteControl.setCommand(0, partyOnMacro, partyOffMacro);

        System.out.println(remoteControl);
        System.out.println(" ------ Pushing Macro On ------");
        remoteControl.onButtonWasPushed(0);
        System.out.println(" ------ Pushing Macro Off ------");
        remoteControl.offButtonWasPressed(0);
        System.out.println(" ------ Pushing Macro Undo ------");
        remoteControl.undoButtonWasPushed();
    }


}
