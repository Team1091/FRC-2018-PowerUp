package frc.team1091.robot;

import com.team1091.planning.StartingPos;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    // Int for checking if auto is over
    private int autoEnd = 0;

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

        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        // camera.setResolution(640, 480);
        camera.setBrightness(50);

        camera.setExposureAuto();
//        camera.setExposureManual(20);
//        camera.setWhiteBalanceManual(50);
//        camera.enumerateProperties();

        visionSystem.init();

        startingPositionChooser = new SendableChooser<>();
        startingPositionChooser.addDefault(StartingPos.CENTER.name(), StartingPos.CENTER);
        for (StartingPos p : StartingPos.values()) {
            startingPositionChooser.addObject(p.name(), p);
        }
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
                platformSystem,
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
        boxSystem.ingestBox(dt);
        elevatorSystem.controlLift(dt);
        climbSystem.climbUp(dt);
        platformSystem.controlGate(dt);

    }

    private void printTelemetry() {
        SmartDashboard.putNumber("left", components.leftEncoder.getDistance());
        SmartDashboard.putNumber("right", components.rightEncoder.getDistance());
        SmartDashboard.putNumber("Elevator Motor Power", components.elevatorMotor.get());
        SmartDashboard.putNumber("platform", components.platformEncoder.getDistance());
        SmartDashboard.putNumber("elevator encoder ", components.elevatorEncoder.getDistance());
        SmartDashboard.putNumber("elevator Target Position", elevatorSystem.getTargetPosition().inches);
        SmartDashboard.putString("Elevator posistion", elevatorSystem.getTargetPosition().toString());
        SmartDashboard.putBoolean("Elevator Limit Switch", components.elevatorLimitSwitch.get());
        SmartDashboard.putNumber("Platform Power", components.platformMotor.get());
        SmartDashboard.putString("Platform Position", platformSystem.getGatePosition().toString());
    }

    public void disabledInit() {
    }

    public void disabledPeriodic() {
    }

    public void testInit() {
    }

    public void testPeriodic() {
        components.rightMotor.set(limit(SmartDashboard.getNumber("rightMotor - 0", 0.0)));
        components.leftMotor.set(limit(SmartDashboard.getNumber("leftMotor - 1", 0.0)));
        components.elevatorMotor.set(limit(SmartDashboard.getNumber("elevatorMotor - 2", 0.0)));
        components.platformMotor.set(limit(SmartDashboard.getNumber("platformMotor 3", 0.0)));
        components.suckerMotor.set(limit(SmartDashboard.getNumber("suckerMotor - 4", 0.0)));
        components.winchMotor.set(limit(SmartDashboard.getNumber("winchMotor - 5", 0.0)));
        components.releaseMotor.set(limit(SmartDashboard.getNumber("releaseMotor - 6", 0.0)));

        printTelemetry();

    }

    private double limit(double val) {
        return Math.max(-1, Math.min(1, val));
    }

    private long lastFrameTime = System.nanoTime();

    private double getTime() {
        long currentTime = System.nanoTime();
        double dt = ((double) currentTime - (double) lastFrameTime) / 1000000000.0;
        lastFrameTime = currentTime;
        return dt;
    }
}

