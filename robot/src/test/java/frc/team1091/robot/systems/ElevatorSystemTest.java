package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.wrapper.DigitalInputWrapper;
import frc.team1091.robot.wrapper.EncoderWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ElevatorSystemTest {

    private RobotComponents robotComponents;
    private Joystick mockJoystick;
    private SpeedController mockElevatorMotor;
    private EncoderWrapper mockElevatorEncoder;
    private DigitalInputWrapper mockElevatorLimitSwitch;
    private ElevatorSystem elevatorSystem;

    @Before
    public void setUp() {
        mockJoystick = mock(Joystick.class);
        mockElevatorMotor = mock(SpeedController.class);
        mockElevatorEncoder = mock(EncoderWrapper.class);
        mockElevatorLimitSwitch = mock(DigitalInputWrapper.class);

        robotComponents = new RobotComponents(mockJoystick,
                null,
                null,
                mockElevatorMotor,
                null,
                null,
                null,
                null,
                null,
                null,
                mockElevatorEncoder,
                null,
                null,
                mockElevatorLimitSwitch
        );
        elevatorSystem = new ElevatorSystem(robotComponents);
    }

    @Test
    public void control_liftGoDown(){
        elevatorSystem.setElevatorPosition(ElevatorPositions.GROUND_HEIGHT);
        elevatorSystem.setHoldPosition(ElevatorPositions.SCALE_HEIGHT.inches);
        when(mockElevatorLimitSwitch.get()).thenReturn(false);
        for(int i = 0; i < 100; i++){
            double dummieReadHeight = elevatorSystem.getHoldPosition() + .2;
            when(mockElevatorEncoder.getDistance()).thenReturn(dummieReadHeight);
            elevatorSystem.controlLift(i*.02);
            //verify(mockElevatorMotor).set(1);
        }
    }

    @Test
    public void control_liftGoUp(){
        elevatorSystem.setElevatorPosition(ElevatorPositions.SWITCH_HEIGHT);
        elevatorSystem.setHoldPosition(0);
        when(mockElevatorLimitSwitch.get()).thenReturn(false);
        for(int i = 0; i < 100; i++){
            double dummieReadHeight = elevatorSystem.getHoldPosition() - .2;
            when(mockElevatorEncoder.getDistance()).thenReturn((double)dummieReadHeight);
            elevatorSystem.controlLift(i*.02);
            //verify(mockElevatorMotor).set(1);
        }
    }

    @Test
    public void determineMotorSpeed_ShouldReturnOne() {
        double speed = elevatorSystem.determineMotorSpeed(0, 1000);
        Assert.assertEquals(1, speed, .00001);
    }

    @Test
    public void determineMotorSpeed_ShouldStepDown() {
        double result1 = elevatorSystem.determineMotorSpeed(15, 20.0);
        double result2 = elevatorSystem.determineMotorSpeed(19, 20.0);
        double result3 = elevatorSystem.determineMotorSpeed(20, 20.0);
        double result4 = elevatorSystem.determineMotorSpeed(100, 20.0);

        Assert.assertTrue(result1 == 1);
        Assert.assertTrue(result2 < result1);
        Assert.assertTrue(result3 ==0);
        Assert.assertTrue(result4 < 0);
    }

    @Test
    public void testDetermineMotorSpeed(){
        ElevatorSystem elevatorSystem = new ElevatorSystem(null);

        assert elevatorSystem.determineMotorSpeed(10,20)==1;
        assert elevatorSystem.determineMotorSpeed(30,20)==-1;

        assert elevatorSystem.determineMotorSpeed(9.5,10)==0.25;
        assert elevatorSystem.determineMotorSpeed(9.0,10)==0.5;

        assert elevatorSystem.determineMotorSpeed(10.5,10)==-0.25;
        assert elevatorSystem.determineMotorSpeed(11,10)==-0.5;

        assert elevatorSystem.determineMotorSpeed(10,10)==0;

    }
}
