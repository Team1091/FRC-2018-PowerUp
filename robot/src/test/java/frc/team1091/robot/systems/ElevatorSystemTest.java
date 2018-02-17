package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.RobotComponents;
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

    private ElevatorSystem elevatorSystem;

    @Before
    public void setUp() {
        mockJoystick = mock(Joystick.class);
        mockElevatorMotor = mock(SpeedController.class);
        mockElevatorEncoder = mock(EncoderWrapper.class);

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
                null
        );
        elevatorSystem = new ElevatorSystem(robotComponents);
    }

    @Test
    public void control_liftGoDown(){
        elevatorSystem.setElevatorPosition(ElevatorPositions.GROUND_HEIGHT);
        elevatorSystem.setHoldPosition(24);
        for(int i = 0; i < 30; i++){
            when(mockElevatorEncoder.getDistance()).thenReturn(24.0-i);
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
}
