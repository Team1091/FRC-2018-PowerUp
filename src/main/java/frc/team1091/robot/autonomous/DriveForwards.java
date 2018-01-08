package frc.team1091.robot.autonomous;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.RobotControlSystems;
import frc.team1091.robot.wrapper.EncoderWrapper;

public class DriveForwards implements Command {

    private final double distanceInInches;
    private final RobotComponents robotComponents;
    private final RobotControlSystems controlSystems;

    public DriveForwards(double distanceInInches, RobotComponents robotComponents, RobotControlSystems controlSystems) {
        this.distanceInInches = distanceInInches;
        this.robotComponents = robotComponents;
        this.controlSystems = controlSystems;
    }


    @Override
    public Command execute() {
        if (distanceInInches > robotComponents.leftEncoder.get()) {
            controlSystems.differentialDrive.arcadeDrive(1, 0);
            return this;
        }
        controlSystems.differentialDrive.arcadeDrive(0, 0);
        return null;
    }
}
