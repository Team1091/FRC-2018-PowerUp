package frc.team1091.robot.autonomous.commands;

public class EatBox implements Command {
    @Override
    public Command execute() {
        // TODO: we don't actually need to do this in autonomous

        //Make sure platform is in close position
        //Tip the platform to the ground to scoop up cubes
        //Turn on suckers
        //Tell if cube is received
        //If cube received, flip up and turn off suckey wuckey
        return null;
    }

    @Override
    public String getMessage() {
        return "Eating a box";
    }
}
