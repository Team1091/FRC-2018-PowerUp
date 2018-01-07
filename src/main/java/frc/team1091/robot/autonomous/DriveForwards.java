package frc.team1091.robot.autonomous;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team1091.robot.wrapper.EncoderWrapper;

public class DriveForwards implements Command {

    private final double distanceInInches;
    private final DifferentialDrive drive;
    private final EncoderWrapper encoder;

    public DriveForwards(double distanceInInches, DifferentialDrive drive, EncoderWrapper encoder) {
        this.distanceInInches = distanceInInches;
        this.drive = drive;
        this.encoder = encoder;
    }


    @Override
    public Command execute() {
        if (distanceInInches > encoder.get()) {
            drive.arcadeDrive(1, 0);
            return this;
        }
        drive.arcadeDrive(0, 0);
        return null;
    }
}
