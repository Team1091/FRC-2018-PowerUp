package frc.team1091.robot.autonomous.commands;

public interface Command {

    /**
     * @return the command we should do next, null if we are done.
     */
    Command execute();
}
