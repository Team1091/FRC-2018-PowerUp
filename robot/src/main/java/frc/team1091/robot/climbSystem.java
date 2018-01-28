package frc.team1091.robot;

public class climbSystem {
    private RobotComponents robotComponents;

    public climbSystem(RobotComponents robotCompenents) {
        this.robotComponents = robotCompenents;
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