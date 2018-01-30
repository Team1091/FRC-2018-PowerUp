package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;
import frc.team1091.robot.wrapper.EncoderWrapper;
import org.junit.Before;
import org.junit.Test;

import java.io.DataInput;

import static org.mockito.Mockito.*;

public class GateSystemTest {

    private RobotComponents robotComponents;
    private Joystick mockJoystick;
    private SpeedController mockGateMotor;
    private DigitalInput mockPickUpLimitSwitch;
    private DigitalInput mockClosedLimitSwitch;
    private DigitalInput mockDropLimitSwitch;

    private GateSystem gateSystem;

    @Before
    public void setUp(){
        mockJoystick = mock(Joystick.class);
        mockGateMotor = mock(SpeedController.class);
        mockPickUpLimitSwitch = mock(DigitalInput.class);
        mockClosedLimitSwitch = mock(DigitalInput.class);
        mockDropLimitSwitch = mock(DigitalInput.class);

        robotComponents = new RobotComponents(mockJoystick,
                null,
                null,
                null,
                mockGateMotor,
                null,
                null,
                null,
                null,
                null,
                null,
                mockPickUpLimitSwitch,
                mockClosedLimitSwitch,
                mockDropLimitSwitch,
                null,
                null);
        gateSystem = new GateSystem(robotComponents);
    }
}
