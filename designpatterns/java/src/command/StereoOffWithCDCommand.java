package command;

public class StereoOffWithCDCommand implements Command{
    Stereo stereo;

    public StereoOffWithCDCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.off();
        stereo.setCd();
        stereo.setVolume(0);
    }

    @Override
    public void undo() {
        stereo.on();
    }
}
