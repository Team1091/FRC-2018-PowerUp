package frc.team1091.robot;

import frc.team1091.robot.autonomous.Command;
import frc.team1091.robot.autonomous.CommandList;
import frc.team1091.robot.autonomous.DriveForwards;
import frc.team1091.robot.autonomous.SpinOutOfControl;
import frc.team1091.robot.drive.AutonomousDriveSystem;

public class Team1091Robot {
    // Robot components
    RobotComponents components;

    // Control Systems
    RobotControlSystems controlSystem;
    AutonomousDriveSystem autonomousDriveSystem;

    public static Team1091Robot getDefaultInstance(){
        RobotComponents rc = RobotComponents.getDefaultInstance();
        RobotControlSystems sy = RobotControlSystems.getDefaultInstance(rc);
        return new Team1091Robot(
              rc,
              sy,
              new AutonomousDriveSystem()
      );
    };

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

        //DriverStation.getInstance().getAlliance()

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

