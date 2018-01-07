package frc.team1091.robot.autonomous;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class SpinOutOfControl implements Command {

    private final DifferentialDrive differentialDrive;

    public SpinOutOfControl(DifferentialDrive differentialDrive) {
        this.differentialDrive = differentialDrive;
    }

    @Override
    public Command execute() {
        differentialDrive.arcadeDrive(0, 1);
        return this;
    }
}
