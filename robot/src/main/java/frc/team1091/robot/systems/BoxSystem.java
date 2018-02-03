package frc.team1091.robot.systems;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;

/**
 * Thia ia for sucking a box in.
 */
public class BoxSystem {
    private RobotComponents robotComponents;

    public BoxSystem(RobotComponents robotComponents) {
        this.robotComponents = robotComponents;
    }

    public void ingestBox() {

        boolean xButton = robotComponents.xboxController.getRawButton(Xbox.x);
        if (xButton) {
            robotComponents.suckerMotor.set(1);
        } else
            robotComponents.suckerMotor.set(0);
    }


}