package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class ClimbSystem {
    private RobotComponents robotComponents;

    public ClimbSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }

    //while button held, climb up
    public void climbUp() {
        boolean climbButtonPressed = robotComponents.xboxController.getRawButton(Xbox.y);

        if (climbButtonPressed) {
            robotComponents.winchMotor.set(0.75);
        } else {
            robotComponents.winchMotor.set(0);
        }
    }
}