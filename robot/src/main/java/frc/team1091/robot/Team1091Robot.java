package frc.team1091.robot;

import com.team1091.planning.StartingPos;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.team1091.robot.autonomous.Planner;
import frc.team1091.robot.autonomous.commands.Command;
import frc.team1091.robot.systems.*;

public class Team1091Robot {
    // Robot components
    private RobotComponents components;

    // Control Systems
    private AutonomousSystem autonomousSystem;
    private DriveSystem driveSystem;
    private BoxSystem boxSystem;
    private ElevatorSystem elevatorSystem;
    private ClimbSystem climbSystem;
    private PlatformSystem platformSystem;

    // Communications with laptop
    private VisionSystem visionSystem;
    private SendableChooser<StartingPos> startingPositionChooser;


    public static Team1091Robot getDefaultInstance() {
        RobotComponents rc = RobotComponents.getDefaultInstance();
        ElevatorSystem es = new ElevatorSystem(rc);

        return new Team1091Robot(
                rc,
                new AutonomousSystem(),
                new DriveSystem(rc),
                new BoxSystem(rc, es),
                es,
                new ClimbSystem(rc),
                new PlatformSystem(rc),
                new VisionSystem()
        );
    }

    public Team1091Robot(RobotComponents components, AutonomousSystem autonomousSystem, DriveSystem driveSystem, BoxSystem boxsystem, ElevatorSystem elevatorSystem, ClimbSystem climbSystem, PlatformSystem platformSystem, VisionSystem visionSystem) {
        this.components = components;
        this.autonomousSystem = autonomousSystem;
        this.driveSystem = driveSystem;
        this.boxSystem = boxsystem;
        this.elevatorSystem = elevatorSystem;
        this.climbSystem = climbSystem;
        this.platformSystem = platformSystem;
        this.visionSystem = visionSystem;
    }

    public void robotInit() {

        visionSystem.init();

        startingPositionChooser = new SendableChooser<>();
        startingPositionChooser.addDefault(StartingPos.CENTER.name(), StartingPos.CENTER);
        for (StartingPos p : StartingPos.values()) {
            startingPositionChooser.addObject(p.name(), p);
        }

    }

    public void autonomousInit() {

        StartingPos start = startingPositionChooser.getSelected();
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
        boxSystem.ingestBox();
        elevatorSystem.controlLift();
        climbSystem.climbUp();
        platformSystem.controlGate();

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

