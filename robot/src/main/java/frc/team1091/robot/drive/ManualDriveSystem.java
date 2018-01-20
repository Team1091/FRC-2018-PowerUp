package frc.team1091.robot.drive;

import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.RobotControlSystems;

import static frc.team1091.robot.Xbox.*;

public class ManualDriveSystem {

    private RobotComponents robotComponents;
    private RobotControlSystems controlSystems;

    public ManualDriveSystem(RobotComponents robotComponents, RobotControlSystems controlSystems) {
        this.robotComponents = robotComponents;
        this.controlSystems = controlSystems;
    }

    public void init() {
        // There is not to much that actually needs to get initialized in here,
        // but for things like resetting timers it could be useful
    }

    double maxAcceleration = 1;
    double lastPower = 0;
    long lastTime = 0;

    public void drive() {
        long currentTime = System.nanoTime();
        double dt = (currentTime - lastTime) / 1000000000;
        lastTime = currentTime;

        boolean boostPushed = robotComponents.xboxController.getRawButton(l3);
        double desiredTurn = robotComponents.xboxController.getRawAxis(leftStickHorizontal);
        double desiredSpeed = robotComponents.xboxController.getRawAxis(leftStickVertical);
        double nextSpeed;
        double accel = maxAcceleration * (boostPushed ? 1.0 : 0.6);
        if (desiredSpeed > lastPower) {
            nextSpeed = lastPower + accel * dt;
        } else {
            nextSpeed = lastPower - accel * dt;
        }
        controlSystems.differentialDrive.arcadeDrive(nextSpeed, desiredTurn);


    }
}
