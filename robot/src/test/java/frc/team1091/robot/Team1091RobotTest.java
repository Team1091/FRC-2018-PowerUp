package frc.team1091.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.systems.*;
import frc.team1091.robot.wrapper.DigitalInputWrapper;
import frc.team1091.robot.wrapper.EncoderWrapper;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class Team1091RobotTest {

    private RobotComponents components;

    public Joystick xboxController;
    public SpeedController leftMotor;
    public SpeedController rightMotor;
    public SpeedController elevatorMotor;
    public SpeedController suckerMotor;
    public SpeedController winchMotor;
    public SpeedController releaseMotor;

    public EncoderWrapper leftEncoder;
    public EncoderWrapper rightEncoder;
    public EncoderWrapper elevatorEncoder;

    public DigitalInputWrapper elevatorLimitSwitch;

    // Control Systems
    private AutonomousSystem autonomousSystem;
    private DriveSystem driveSystem;
    private BoxSystem boxSystem;
    private ElevatorSystem elevatorSystem;
    private ClimbSystem climbSystem;
    private SuckerSystem suckerSystem;

    // Communications with laptop
    private VisionSystem visionSystem;

    Team1091Robot testBot;

    @Before
    public void SetUp() {
        xboxController = mock(Joystick.class);

        leftMotor = mock(SpeedController.class);
        rightMotor = mock(SpeedController.class);

        elevatorMotor = mock(SpeedController.class);

        suckerMotor = mock(SpeedController.class);


        winchMotor = mock(SpeedController.class);

        releaseMotor = mock(SpeedController.class);


        leftEncoder = mock(EncoderWrapper.class);
        rightEncoder = mock(EncoderWrapper.class);

        elevatorEncoder = mock(EncoderWrapper.class);

        elevatorLimitSwitch = mock(DigitalInputWrapper.class);

        components = new RobotComponents(xboxController, leftMotor, rightMotor, elevatorMotor, suckerMotor, winchMotor, releaseMotor, leftEncoder, rightEncoder, elevatorEncoder, elevatorLimitSwitch);

        autonomousSystem = new AutonomousSystem();
        driveSystem = new DriveSystem(components);
        elevatorSystem = new ElevatorSystem(components);
        boxSystem = new BoxSystem(components, elevatorSystem);
        climbSystem = new ClimbSystem(components);
        suckerSystem = new SuckerSystem(components);

        visionSystem = mock(VisionSystem.class);

        testBot = new Team1091Robot(components, autonomousSystem, driveSystem, boxSystem, elevatorSystem, climbSystem, suckerSystem, visionSystem);
    }
}
