package frc.team1091.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team1091.robot.autonomous.Command;
import frc.team1091.robot.autonomous.CommandList;
import frc.team1091.robot.autonomous.DriveForwards;
import frc.team1091.robot.autonomous.SpinOutOfControl;
import frc.team1091.robot.drive.AutonomousDriveSystem;
import frc.team1091.robot.drive.ManualDriveSystem;
import frc.team1091.robot.wrapper.EncoderWrapper;

public class Robot extends IterativeRobot {

    // Robot components
    Joystick joystick = new Joystick(0);
    SpeedController leftMotor = new Victor(0);
    SpeedController rightMotor = new Victor(1);
    EncoderWrapper leftEncoder = new EncoderWrapper(new Encoder(2, 3));
    EncoderWrapper rightEncoder = new EncoderWrapper(new Encoder(4, 5));

    DifferentialDrive differentialDrive = new DifferentialDrive(leftMotor, rightMotor);

    // Control Systems
    AutonomousDriveSystem autonomousDriveSystem = new AutonomousDriveSystem();
    ManualDriveSystem manualDriveSystem = new ManualDriveSystem(joystick, differentialDrive);

    @Override
    public void robotInit() {
    }

    @Override
    public void autonomousInit() {

        // initialize the autonomous with a list of things to do.
        // This depends on our starting position and goal we want to go for

//        DriverStation.getInstance().getAlliance()

        Command plan = new CommandList(
                new DriveForwards(123.0, differentialDrive, leftEncoder),
                new SpinOutOfControl(differentialDrive)
        );

        autonomousDriveSystem.init(plan);
    }

    @Override
    public void autonomousPeriodic() {
        autonomousDriveSystem.drive();
    }


    @Override
    public void teleopInit() {
        manualDriveSystem.init();
    }

    @Override
    public void teleopPeriodic() {
        manualDriveSystem.drive();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }
}