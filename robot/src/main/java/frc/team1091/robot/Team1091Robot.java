package frc.team1091.robot;

import frc.team1091.robot.autonomous.Planner;
import frc.team1091.robot.autonomous.commands.Command;
import frc.team1091.robot.drive.AutonomousDriveSystem;
import frc.team1091.robot.drive.ManualDriveSystem;

public class Team1091Robot {
    // Robot components
    private RobotComponents components;

    // Control Systems
    private RobotControlSystems controlSystem;
    private AutonomousDriveSystem autonomousDriveSystem;
    private ManualDriveSystem manualDriveSystem;

    public static Team1091Robot getDefaultInstance() {
        RobotComponents rc = RobotComponents.getDefaultInstance();
        RobotControlSystems sy = RobotControlSystems.getDefaultInstance(rc);
        return new Team1091Robot(
                rc,
                sy,
                new AutonomousDriveSystem(),
                new ManualDriveSystem(rc, sy)
        );
    }

    public Team1091Robot(RobotComponents components, RobotControlSystems controlSystems, AutonomousDriveSystem autonomousDriveSystem, ManualDriveSystem manualDriveSystem) {
        this.components = components;
        this.controlSystem = controlSystems;
        this.autonomousDriveSystem = autonomousDriveSystem;
        this.manualDriveSystem = manualDriveSystem;
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
        //Todo: Complete or Stop an Actions still in process for Autonomous
    }

    public void teleopPeriodic() {
        //Handle Driving the Robot
        manualDriveSystem.drive();
        //Todo: Handle eating a box
        //Todo: Handle barfing a box
        //Todo: Hanlde Lifting
        //Todo: Handle Climbing
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

