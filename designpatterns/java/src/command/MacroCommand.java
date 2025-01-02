package command;

public class MacroCommand implements Command{
    Command[] commands;

    public MacroCommand(Command[] commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        for(Command c : commands) {
            c.execute();
        }
    }

    @Override
    public void undo() {
        for(Command c : commands) {
            c.undo();
        }
    }
}
