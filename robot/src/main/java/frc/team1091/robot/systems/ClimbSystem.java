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
        Boolean goUp = robotComponents.xboxController.getRawButton(Xbox.start);
        Boolean goDown = robotComponents.xboxController.getRawButton(Xbox.select);
        if (goUp) {
            robotComponents.winchMotor.set(-1);
            return;
        }
        if (goDown) {
            robotComponents.winchMotor.set(1);
            return;
        }
        robotComponents.winchMotor.set(0);
    }
}