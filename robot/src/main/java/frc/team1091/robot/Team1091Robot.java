package frc.team1091.robot;

import com.team1091.planning.StartingPos;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team1091.robot.autonomous.Planner;
import frc.team1091.robot.autonomous.commands.Command;
import frc.team1091.robot.systems.AutonomousSystem;
import frc.team1091.robot.systems.DriveSystem;

public class Team1091Robot {
    // Robot components
    private RobotComponents components;

    // Control Systems
    private AutonomousSystem autonomousSystem;
    private DriveSystem driveSystem;

    public static Team1091Robot getDefaultInstance() {
        RobotComponents rc = RobotComponents.getDefaultInstance();
        return new Team1091Robot(
                rc,
                new AutonomousSystem(),
                new DriveSystem(rc)
        );
    }

    public Team1091Robot(RobotComponents components, AutonomousSystem autonomousSystem, DriveSystem driveSystem) {
        this.components = components;
        this.autonomousSystem = autonomousSystem;
        this.driveSystem = driveSystem;
    }

    public void robotInit() {
    }

    public void autonomousInit() {

        // starting path - set fom dropdown?
        StartingPos start = StartingPos.LEFT;

        DriverStation driverStation = DriverStation.getInstance();

        // Create a plan
        Command plan = Planner.plan(
                start,
                driverStation.getAlliance(),
                driverStation.getGameSpecificMessage(),
                components,
                driveSystem);

        autonomousSystem.init(plan);
    }

    public void autonomousPeriodic() {
        autonomousSystem.drive();
    }


    public void teleopInit() {
        //Todo: Complete or Stop an Actions still in process for Autonomous
    }

    public void teleopPeriodic() {
        //Handle Driving the Robot
        driveSystem.drive();
        //Todo: Handle borfing and eating a box
        //Todo: Implement Lifting
        //Todo: Implement Climbing
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

