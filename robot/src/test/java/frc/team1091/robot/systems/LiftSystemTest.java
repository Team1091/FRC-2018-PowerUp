package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;
import frc.team1091.robot.systems.LiftSystem;
import frc.team1091.robot.wrapper.EncoderWrapper;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LiftSystemTest {

    private RobotComponents robotComponents;
    private Joystick mockJoystick;
    private SpeedController mockElevatorMotor;
    private EncoderWrapper mockElevatorEncoder;

    private LiftSystem liftSystem;

    @Before
    public void setUp(){
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
                null,
                null,
                null);
        liftSystem = new LiftSystem(robotComponents);
    }

    @Test
    public void controlLift_NoButtonPressed_ShouldDoNothing(){
        when(mockJoystick.getRawButton(Xbox.rb)).thenReturn(false);
        when(mockJoystick.getRawButton(Xbox.lb)).thenReturn(false);

        verify(mockElevatorMotor).set(0);
    }

    @Test
    public void controlLift_BothButtonsPressed_ShouldDoNothing(){
        when(mockJoystick.getRawButton(Xbox.rb)).thenReturn(true);
        when(mockJoystick.getRawButton(Xbox.lb)).thenReturn(true);

        verify(mockElevatorMotor).set(0);
    }

    @Test
    public void controlLift_RbPressed_ShouldMoveUp(){
        when(mockJoystick.getRawButton(Xbox.rb)).thenReturn(true);
        when(mockJoystick.getRawButton(Xbox.lb)).thenReturn(false);

        verify(mockElevatorMotor).set(.25);
    }

    @Test
    public void controlLift_RbPressed_ShouldMoveDown(){
        when(mockJoystick.getRawButton(Xbox.rb)).thenReturn(false);
        when(mockJoystick.getRawButton(Xbox.lb)).thenReturn(true);

        verify(mockElevatorMotor).set(-.25);
    }
}
