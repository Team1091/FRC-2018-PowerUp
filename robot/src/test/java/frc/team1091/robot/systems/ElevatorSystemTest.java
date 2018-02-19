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
    public void control_liftGoDown() {
        elevatorSystem.setElevatorPosition(ElevatorPositions.GROUND_HEIGHT);
        elevatorSystem.setHoldPosition(ElevatorPositions.SCALE_HEIGHT.inches);
        when(mockElevatorLimitSwitch.get()).thenReturn(false);
        for (int i = 0; i < 100; i++) {
            double dummieReadHeight = elevatorSystem.getHoldPosition() + .2;
            when(mockElevatorEncoder.getDistance()).thenReturn(dummieReadHeight);
            elevatorSystem.controlLift(i * .02);
            //verify(mockElevatorMotor).set(1);
        }
    }

    @Test
    public void control_liftGoUp() {
        elevatorSystem.setElevatorPosition(ElevatorPositions.SWITCH_HEIGHT);
        elevatorSystem.setHoldPosition(0);
        when(mockElevatorLimitSwitch.get()).thenReturn(false);
        for (int i = 0; i < 100; i++) {
            double dummieReadHeight = elevatorSystem.getHoldPosition() - .2;
            when(mockElevatorEncoder.getDistance()).thenReturn((double) dummieReadHeight);
            elevatorSystem.controlLift(i * .02);
            //verify(mockElevatorMotor).set(1);
        }
    }

}
