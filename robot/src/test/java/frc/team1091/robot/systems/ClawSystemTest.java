package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClawSystemTest {

    private RobotComponents robotComponents;
    private Joystick joystick;
    private SpeedController clawMotor;

    private ClawSystem clawSystem;

    @Before
    public void setUp() {
        joystick = mock(Joystick.class);
        clawMotor = mock(SpeedController.class);
        RobotComponents robotComponents = new RobotComponents(joystick,
                null,
                null,
                null,
                clawMotor,
                null,
                null,
                null,
                null,
                null,
                null
        );
        clawSystem = new ClawSystem(robotComponents);
    }

    @Test
    public void testNothingHappensIfBothButtonsArePressed() {
        when(joystick.getRawAxis(Xbox.rt)).thenReturn(1.0);
        when(joystick.getRawAxis(Xbox.lt)).thenReturn(1.0);
        clawSystem.updatePositionFromControllerInput();

        assert clawSystem.getGatePosition() == ClawPosition.OPEN;
    }


    @Test
    public void testWeGoDown() {
        when(joystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
        when(joystick.getRawAxis(Xbox.lt)).thenReturn(1.0);

        clawSystem.updatePositionFromControllerInput();
        clawSystem.updatePositionFromControllerInput();

        System.out.println(clawSystem.getGatePosition());
        assert (clawSystem.getGatePosition() == ClawPosition.OPEN);

        // Let go of button
        when(joystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
        when(joystick.getRawAxis(Xbox.lt)).thenReturn(0.0);
        clawSystem.updatePositionFromControllerInput();
        clawSystem.updatePositionFromControllerInput();

        System.out.println(clawSystem.getGatePosition());
        assert (clawSystem.getGatePosition() == ClawPosition.OPEN);

        // press again to get to the bottom
        when(joystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
        when(joystick.getRawAxis(Xbox.lt)).thenReturn(1.0);
        clawSystem.updatePositionFromControllerInput();
        clawSystem.updatePositionFromControllerInput();

        System.out.println(clawSystem.getGatePosition());

        assert (clawSystem.getGatePosition() == ClawPosition.CRUSH);
    }


    @Test
    public void testWeGoUp() {
        clawSystem.setGatePosition(ClawPosition.CRUSH);
        when(joystick.getRawAxis(Xbox.rt)).thenReturn(1.0);
        when(joystick.getRawAxis(Xbox.lt)).thenReturn(0.0);

        clawSystem.updatePositionFromControllerInput();
        clawSystem.updatePositionFromControllerInput();
        clawSystem.updatePositionFromControllerInput();

        System.out.println(clawSystem.getGatePosition());
        assert (clawSystem.getGatePosition() == ClawPosition.CRUSH);
    }

}
