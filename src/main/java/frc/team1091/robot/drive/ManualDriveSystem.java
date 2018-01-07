package frc.team1091.robot.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class ManualDriveSystem {

    private Joystick joystick;
    private DifferentialDrive differentialDrive;

    public ManualDriveSystem(Joystick joystick, DifferentialDrive drive) {
        this.joystick = joystick;
        this.differentialDrive = drive;
    }

    public void init() {
        // There is not to much that actually needs to get initialized in here,
        // but for things like resetting timers it could be useful
    }

    public void drive() {
        double x = joystick.getRawAxis(1);
        differentialDrive.arcadeDrive(x, 0);

    }
}
