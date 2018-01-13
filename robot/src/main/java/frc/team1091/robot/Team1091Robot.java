package frc.team1091.robot;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team1091.robot.autonomous.Planner;
import frc.team1091.robot.autonomous.commands.Command;
import frc.team1091.robot.autonomous.commands.CommandList;
import frc.team1091.robot.autonomous.commands.DriveForwards;
import frc.team1091.robot.autonomous.commands.SpinOutOfControl;
import frc.team1091.robot.drive.AutonomousDriveSystem;

public class Team1091Robot {
    // Robot components
    private RobotComponents components;

    // Control Systems
    private RobotControlSystems controlSystem;
    private AutonomousDriveSystem autonomousDriveSystem;

    public static Team1091Robot getDefaultInstance() {
        RobotComponents rc = RobotComponents.getDefaultInstance();
        RobotControlSystems sy = RobotControlSystems.getDefaultInstance(rc);
        return new Team1091Robot(
                rc,
                sy,
                new AutonomousDriveSystem()
        );
    }

    public Team1091Robot(RobotComponents components, RobotControlSystems controlSystems, AutonomousDriveSystem autonomousDriveSystem) {
        this.components = components;
        this.controlSystem = controlSystems;
        this.autonomousDriveSystem = autonomousDriveSystem;
    }

    public void robotInit() {
    }

    public void autonomousInit() {

        // Create a plan
        Planner planner = new Planner();
        Command plan = planner.plan(components, controlSystem);

        autonomousDriveSystem.init(plan);
    }

    public void autonomousPeriodic() {
        autonomousDriveSystem.drive();
    }


    public void teleopInit() {

    }

    public void teleopPeriodic() {
        double x = components.xboxController.getRawAxis(1);
        controlSystem.differentialDrive.arcadeDrive(x, 0);
    }

    public void disabledInit() {
    }

    public void disabledPeriodic() {
    }

    public void testInit() {
    }

    public void testPeriodic() {
    }
}

