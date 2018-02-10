package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

public class ClimbSystem {
    private RobotComponents robotComponents;

    public ClimbSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }

    private boolean isClimberReleased = false;

    //while button held, climb up
    public void climbUp(double dt) {
        boolean climbButtonPressed = robotComponents.xboxController.getRawButton(Xbox.y);
        boolean releaseButtonPressed = robotComponents.xboxController.getRawButton(Xbox.start);
        if (releaseButtonPressed) {
            robotComponents.releaseMotor.set(1);
            isClimberReleased = true;
        } else {
            robotComponents.releaseMotor.set(0);
        }
        if (climbButtonPressed && isClimberReleased) {
            robotComponents.winchMotor.set(0.75);
        } else {
            robotComponents.winchMotor.set(0);
        }
    }
}