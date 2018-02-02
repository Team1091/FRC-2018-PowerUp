package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;
import frc.team1091.robot.wrapper.DigitalInputWrapper;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class PlatformSystemTest {

    private RobotComponents robotComponents;
    private Joystick mockJoystick;
    private SpeedController mockPlatformMotor;
    private DigitalInputWrapper mockPlatformLimitSwitch;

    private PlatformSystem platformSystem;

    @Before
    public void setUp(){
        mockJoystick = mock(Joystick.class);

        robotComponents = null;
        platformSystem = spy(new PlatformSystem(robotComponents));
    }

}
