package frc.team1091.robot.autonomous.commands;

import java.util.ArrayList;
import java.util.Arrays;

public class Or implements Command {

    private ArrayList<Command> commands;

    public Or(Command... commands) {
        this.commands = new ArrayList<>(Arrays.asList(commands));
    }

    public Or(ArrayList<Command> commandList) {
        this.commands = commandList;
    }

    @Override
    public Command execute(double dt) {

        if (commands.size() == 0) {
            return null;
        }

        // do them all, until one returns null.  Right now this doesnt clean up after itself

        for(Command command:commands){
           Command result = command.execute(dt);
            if(result==null){
                return null;
            }
        }
        return this;
    }


    @Override
    public String getMessage() {
        return commands.get(0).getMessage();
    }
}
