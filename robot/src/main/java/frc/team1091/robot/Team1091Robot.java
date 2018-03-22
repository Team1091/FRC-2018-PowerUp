package frc.team1091.robot;

import com.team1091.planning.StartingPos;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1091.robot.autonomous.Planner;
import frc.team1091.robot.autonomous.commands.Command;
import frc.team1091.robot.systems.AutonomousSystem;
import frc.team1091.robot.systems.BoxSystem;
import frc.team1091.robot.systems.ClimbSystem;
import frc.team1091.robot.systems.DriveSystem;
import frc.team1091.robot.systems.ElevatorSystem;
import frc.team1091.robot.systems.SuckerSystem;
import frc.team1091.robot.systems.VisionSystem;

import static frc.team1091.robot.Utils.clamp;

public class Team1091Robot {
    // Robot components
    private RobotComponents components;

    // Control Systems
    private AutonomousSystem autonomousSystem;
    private DriveSystem driveSystem;
    private ElevatorSystem elevatorSystem;
    private ClimbSystem climbSystem;
    private SuckerSystem suckerSystem;

    // Communications with laptop
    private VisionSystem visionSystem;
    private SendableChooser<StartingPos> startingPositionChooser;
    // Int for checking if auto is over

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
                new SuckerSystem(rc),
                new VisionSystem()
        );
    }

    public Team1091Robot(RobotComponents components, AutonomousSystem autonomousSystem, DriveSystem driveSystem, BoxSystem boxsystem, ElevatorSystem elevatorSystem, ClimbSystem climbSystem, SuckerSystem suckerSystem, VisionSystem visionSystem) {
        this.components = components;
        this.autonomousSystem = autonomousSystem;
        this.driveSystem = driveSystem;
        //       this.boxSystem = boxsystem;
        this.elevatorSystem = elevatorSystem;
        this.climbSystem = climbSystem;
        this.suckerSystem = suckerSystem;
        this.visionSystem = visionSystem;
    }

    public void robotInit() {

        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        // camera.setResolution(640, 480);
        camera.setBrightness(50);

        camera.setExposureAuto();
//        camera.setExposureManual(20);
//        camera.setWhiteBalanceManual(50);
//        camera.enumerateProperties();

        visionSystem.init();

        startingPositionChooser = new SendableChooser<>();
        for (StartingPos p : StartingPos.values()) {
            startingPositionChooser.addObject(p.name(), p);
        }
        startingPositionChooser.addDefault(StartingPos.CENTER.name(), StartingPos.CENTER);
        SmartDashboard.putData(startingPositionChooser);
    }

    public void autonomousInit() {
        StartingPos start = startingPositionChooser.getSelected();
        DriverStation driverStation = DriverStation.getInstance();
        String fieldConfig = driverStation.getGameSpecificMessage();

        // Create a plan
        Command plan = Planner.plan(
                start,
                driverStation.getAlliance(),
                fieldConfig == null ? "RRR" : fieldConfig,
                components,
                driveSystem,
                visionSystem,
                suckerSystem,
                elevatorSystem);

        autonomousSystem.init(plan);
    }

    public void autonomousPeriodic() {
        printTelemetry();
        double dt = getTime();
        autonomousSystem.drive(dt);
    }


    public void teleopInit() {

        //Todo: Complete or Stop an Actions still in process for Autonomous

    }

    public void teleopPeriodic() {
        printTelemetry();
        double dt = getTime();

        //Handle Driving the Robot
        driveSystem.manualDrive(dt);
        //      boxSystem.ingestBox(dt);
        elevatorSystem.controlLift(dt);
        climbSystem.climbUp(dt);
        suckerSystem.manualControl(dt);

    }

    private void printTelemetry() {

        SmartDashboard.putNumber("L-D", components.leftEncoder.getDistance());
        SmartDashboard.putNumber("L-TX", components.leftEncoder.get());

        SmartDashboard.putNumber("R-D", components.rightEncoder.getDistance());
        SmartDashboard.putNumber("R-TX", components.rightEncoder.get());

        SmartDashboard.putNumber("Elevator Motor Power", components.elevatorMotor.get());
        SmartDashboard.putNumber("elevator Encoder ", components.elevatorEncoder.getDistance());
        SmartDashboard.putNumber("elevator Target Position", elevatorSystem.getTargetPosition().inches);
        SmartDashboard.putString("Elevator Position", elevatorSystem.getTargetPosition().toString());
        SmartDashboard.putBoolean("Elevator Limit Switch", components.elevatorLimitSwitch.get());

        SmartDashboard.putNumber("Sucker Power", components.suckerMotor.get());
//        SmartDashboard.putString("Sucker Position", suckerSystem.getGatePosition().toString());
    }

    public void disabledInit() {
    }

    public void disabledPeriodic() {
    }

    public void testInit() {
    }

    public void testPeriodic() {
        components.rightMotor.set(clamp(SmartDashboard.getNumber("rightMotor - 0", 0.0)));
        components.leftMotor.set(clamp(SmartDashboard.getNumber("leftMotor - 1", 0.0)));
        components.elevatorMotor.set(clamp(SmartDashboard.getNumber("elevatorMotor - 2", 0.0)));
        components.suckerMotor.set(clamp(SmartDashboard.getNumber("suckerMotor 3", 0.0)));
//        components.suckerMotor.set(clamp(SmartDashboard.getNumber("suckerMotor - 4", 0.0)));
        components.winchMotor.set(clamp(SmartDashboard.getNumber("winchMotor - 5", 0.0)));
        components.releaseMotor.set(clamp(SmartDashboard.getNumber("releaseMotor - 6", 0.0)));

        printTelemetry();

    }

    private long lastFrameTime = System.nanoTime();

    private double getTime() {
        long currentTime = System.nanoTime();
        double dt = ((double) currentTime - (double) lastFrameTime) / 1000000000.0;
        lastFrameTime = currentTime;
        return dt;
    }
}

