package frc.team1091.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class RobotControlSystems {

    public final DifferentialDrive differentialDrive;

    public static RobotControlSystems getDefaultInstance(RobotComponents components){
        return new RobotControlSystems(
                new DifferentialDrive(components.leftMotor, components.rightMotor)
        );
    }

    public RobotControlSystems(DifferentialDrive differentialDrive) {
        this.differentialDrive = differentialDrive;
    }
}
