package frc.team1091.robot.autonomous.commands;

public interface Command {

    /**
     * @param dt
     * @return the command we should do next, null if we are done.
     */
    Command execute(double dt);

    String getMessage();
}
