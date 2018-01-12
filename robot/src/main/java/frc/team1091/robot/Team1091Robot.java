package frc.team1091.robot;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team1091.robot.autonomous.Command;
import frc.team1091.robot.autonomous.CommandList;
import frc.team1091.robot.autonomous.DriveForwards;
import frc.team1091.robot.autonomous.SpinOutOfControl;
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

        // initialize the autonomous with a list of things to do.
        // This depends on our starting position and goal we want to go for

        DriverStation driverStation = DriverStation.getInstance();
        DriverStation.Alliance alliance = driverStation.getAlliance();

        // I think this is where we are starting this year, could be only the driverstation tho
        int driverStationLocation = driverStation.getLocation();


        // http://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/826278-2018-game-data-details
        // this will be a 3 character string with your goals side marked, index 0 being the closest.
        // L = left side
        // R = right side
        //
        // Ex: "LRL", "RRR", "LRR"

        String gameGoalData = DriverStation.getInstance().getGameSpecificMessage();

        // Feed it into a planner, that should return a command

        Command plan = new CommandList(
                new DriveForwards(123.0, components, controlSystem),
                new SpinOutOfControl(components, controlSystem)
        );

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

