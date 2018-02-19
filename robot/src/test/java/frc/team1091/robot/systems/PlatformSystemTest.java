package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;
import frc.team1091.robot.wrapper.DigitalInputWrapper;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlatformSystemTest {

    private RobotComponents robotComponents;
    private Joystick joystick;
    private SpeedController platformMotor;
    private DigitalInputWrapper platformLimitSwitch;

    private PlatformSystem platformSystem;

    @Before
    public void setUp() {
        joystick = mock(Joystick.class);
        platformMotor = mock(SpeedController.class);
        RobotComponents robotComponents = new RobotComponents(joystick,
                null,
                null,
                null,
                platformMotor,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        platformSystem = new PlatformSystem(robotComponents);
    }

    @Test
    public void testNothingHappensIfBothButtonsArePressed() {
        when(joystick.getRawAxis(Xbox.rt)).thenReturn(1.0);
        when(joystick.getRawAxis(Xbox.lt)).thenReturn(1.0);
        platformSystem.updatePositionFromControllerInput();

        assert platformSystem.getGatePosition() == PlatformPosition.UP;
    }


    @Test
    public void testWeGoDown() {
        when(joystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
        when(joystick.getRawAxis(Xbox.lt)).thenReturn(1.0);

        platformSystem.updatePositionFromControllerInput();
        platformSystem.updatePositionFromControllerInput();

        System.out.println(platformSystem.getGatePosition());
        assert (platformSystem.getGatePosition() == PlatformPosition.CENTER);

        // Let go of button
        when(joystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
        when(joystick.getRawAxis(Xbox.lt)).thenReturn(0.0);
        platformSystem.updatePositionFromControllerInput();
        platformSystem.updatePositionFromControllerInput();

        System.out.println(platformSystem.getGatePosition());
        assert (platformSystem.getGatePosition() == PlatformPosition.CENTER);

        // press again to get to the bottom
        when(joystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
        when(joystick.getRawAxis(Xbox.lt)).thenReturn(1.0);
        platformSystem.updatePositionFromControllerInput();
        platformSystem.updatePositionFromControllerInput();

        System.out.println(platformSystem.getGatePosition());

        assert (platformSystem.getGatePosition() == PlatformPosition.DOWN);
    }


    @Test
    public void testWeGoUp() {
        platformSystem.setGatePosition(PlatformPosition.DOWN);
        when(joystick.getRawAxis(Xbox.rt)).thenReturn(1.0);
        when(joystick.getRawAxis(Xbox.lt)).thenReturn(0.0);

        platformSystem.updatePositionFromControllerInput();
        platformSystem.updatePositionFromControllerInput();
        platformSystem.updatePositionFromControllerInput();

        System.out.println(platformSystem.getGatePosition());
        assert (platformSystem.getGatePosition() == PlatformPosition.CENTER);
    }

}
