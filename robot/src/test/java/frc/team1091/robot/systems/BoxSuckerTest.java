package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;
import frc.team1091.robot.wrapper.DigitalInputWrapper;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BoxSuckerTest {
    Joystick joystick;
    SpeedController boxSuckerMotor;
    DigitalInputWrapper elevatorSwitch;
    BoxSystem boxSystem;

    @Before
    public void setUp() {
        joystick = mock(Joystick.class);
        boxSuckerMotor = mock(SpeedController.class);
        elevatorSwitch = mock(DigitalInputWrapper.class);

        RobotComponents rc = new RobotComponents(joystick,
                null,
                null,
                null,
                null,
                boxSuckerMotor,
                null,
                null,
                null,
                null,
                elevatorSwitch
        );

        ElevatorSystem elevatorSystem = new ElevatorSystem(rc);
        boxSystem = new BoxSystem(rc, elevatorSystem);

    }


    @Test
    public void testNeither() {
        when(joystick.getRawButton(Xbox.x)).thenReturn(false);
        when(elevatorSwitch.get()).thenReturn(false);
        boxSystem.ingestBox(0.1);
        verify(boxSuckerMotor).set(0);
    }

    @Test
    public void testElevatorNotDown() {
        when(joystick.getRawButton(Xbox.x)).thenReturn(true);
        when(elevatorSwitch.get()).thenReturn(false);
        boxSystem.ingestBox(0.1);
        verify(boxSuckerMotor).set(0);
    }

    @Test
    public void testButtonNotDown() {
        when(joystick.getRawButton(Xbox.x)).thenReturn(false);
        when(elevatorSwitch.get()).thenReturn(true);
        boxSystem.ingestBox(0.1);
        verify(boxSuckerMotor).set(0);
    }

    @Test
    public void testButtonWorks() {
        when(joystick.getRawButton(Xbox.x)).thenReturn(true);
        when(elevatorSwitch.get()).thenReturn(true);
        boxSystem.ingestBox(0.1);
        verify(boxSuckerMotor).set(-1);
    }

}
