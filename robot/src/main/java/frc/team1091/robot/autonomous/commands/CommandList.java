package frc.team1091.robot.autonomous.commands;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandList implements Command {

    private ArrayList<Command> commands;

    public CommandList(Command... commands) {
        this.commands = new ArrayList<>(Arrays.asList(commands));
    }

    public CommandList(ArrayList<Command> commandList) {
        this.commands = commandList;
    }

    @Override
    public Command execute(double dt) {

        if (commands.size() == 0) {
            return null;
        }

        // do the first one, if it's done remove it
        Command first = commands.get(0);
        Command next = first.execute(dt);

        if (next == null) {
            // Current command is done, go to the next
            if (commands.size() == 1)
                return null; // List done

            commands.remove(0);
        } else if (next != first) {
            // Replace current command
            commands.set(0, next);
        }
        return this;
    }


    @Override
    public String getMessage() {
        return commands.get(0).getMessage();
    }
}
