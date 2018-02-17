package frc.team1091.robot;

import com.team1091.planning.Facing;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.systems.*;
import frc.team1091.robot.wrapper.DigitalInputWrapper;
import frc.team1091.robot.wrapper.EncoderWrapper;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.mockito.Mockito.mock;

public class Team1091RobotTest {

    private RobotComponents components;

    public Joystick xboxController;
    public SpeedController leftMotor;
    public SpeedController rightMotor;
    public SpeedController elevatorMotor;
    public SpeedController platformMotor;
    public SpeedController suckerMotor;
    public SpeedController winchMotor;
    public SpeedController releaseMotor;

    public EncoderWrapper leftEncoder;
    public EncoderWrapper rightEncoder;
    public EncoderWrapper elevatorEncoder;
    public EncoderWrapper platformEncoder;

    public DigitalInputWrapper platformLimitSwitch;
    public DigitalInputWrapper elevatorLimitSwitch;

    // Control Systems
    private AutonomousSystem autonomousSystem;
    private DriveSystem driveSystem;
    private BoxSystem boxSystem;
    private ElevatorSystem elevatorSystem;
    private ClimbSystem climbSystem;
    private PlatformSystem platformSystem;

    // Communications with laptop
    private VisionSystem visionSystem;

    Team1091Robot testBot;

    @Before
    public void SetUp(){
        xboxController = mock(Joystick.class);

        leftMotor = mock(SpeedController.class);
        rightMotor = mock(SpeedController.class);;
        elevatorMotor = mock(SpeedController.class);;
        platformMotor = mock(SpeedController.class);;
        suckerMotor = mock(SpeedController.class);;
        winchMotor = mock(SpeedController.class);;
        releaseMotor = mock(SpeedController.class);;

        leftEncoder = mock(EncoderWrapper.class);
        rightEncoder = mock(EncoderWrapper.class);;
        elevatorEncoder = mock(EncoderWrapper.class);;
        platformEncoder = mock(EncoderWrapper.class);;

        platformLimitSwitch = mock(DigitalInputWrapper.class);
        elevatorLimitSwitch = mock(DigitalInputWrapper.class);

        components = new RobotComponents(xboxController, leftMotor, rightMotor, elevatorMotor, platformMotor, suckerMotor, winchMotor, releaseMotor, leftEncoder, rightEncoder, elevatorEncoder, platformEncoder, platformLimitSwitch, elevatorLimitSwitch);

        autonomousSystem = new AutonomousSystem();
        driveSystem = new DriveSystem(components);
        elevatorSystem = new ElevatorSystem(components);
        boxSystem = new BoxSystem(components, elevatorSystem);
        climbSystem = new ClimbSystem(components);
        platformSystem = new PlatformSystem(components);

        visionSystem = mock(VisionSystem.class);

        testBot = new Team1091Robot(components, autonomousSystem, driveSystem, boxSystem, elevatorSystem, climbSystem, platformSystem, visionSystem);
    }
}
