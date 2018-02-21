package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SuckerSystemTest {

    private RobotComponents robotComponents;
    private Joystick joystick;
    private SpeedController suckerMotor;

    private SuckerSystem suckerSystem;

    @Before
    public void setUp() {
        joystick = mock(Joystick.class);
        suckerMotor = mock(SpeedController.class);
        RobotComponents robotComponents = new RobotComponents(joystick,
                null,
                null,
                null,
                suckerMotor,
                null,
                null,
                null,
                null,
                null,
                null
        );
        suckerSystem = new SuckerSystem(robotComponents);
    }

//    @Test
//    public void testNothingHappensIfBothButtonsArePressed() {
//        when(joystick.getRawAxis(Xbox.rt)).thenReturn(1.0);
//        when(joystick.getRawAxis(Xbox.lt)).thenReturn(1.0);
//        suckerSystem.updatePositionFromControllerInput();
//
//        assert suckerSystem.getGatePosition() == SuckerPosition.OPEN;
//    }
//
//
//    @Test
//    public void testWeGoDown() {
//        when(joystick.getRawAxis(Xbox.lt)).thenReturn(1.0);
//        when(joystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
//
//        suckerSystem.updatePositionFromControllerInput();
//        suckerSystem.updatePositionFromControllerInput();
//
//        System.out.println(suckerSystem.getGatePosition());
//        assert (suckerSystem.getGatePosition() == SuckerPosition.OPEN);
//
//        // Let go of button
//        when(joystick.getRawAxis(Xbox.lt)).thenReturn(0.0);
//        when(joystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
//        suckerSystem.updatePositionFromControllerInput();
//        suckerSystem.updatePositionFromControllerInput();
//
//        System.out.println(suckerSystem.getGatePosition());
//        assert (suckerSystem.getGatePosition() == SuckerPosition.OPEN);
//
//        // press again to get to the bottom
//        when(joystick.getRawAxis(Xbox.lt)).thenReturn(1.0);
//        when(joystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
//        suckerSystem.updatePositionFromControllerInput();
//        suckerSystem.updatePositionFromControllerInput();
//
//        System.out.println(suckerSystem.getGatePosition());
//
//        assert (suckerSystem.getGatePosition() == SuckerPosition.CRUSH);
//    }
//
//
//    @Test
//    public void testWeGoUp() {
//        suckerSystem.setGatePosition(SuckerPosition.CRUSH);
//        when(joystick.getRawAxis(Xbox.lt)).thenReturn(0.0);
//        when(joystick.getRawAxis(Xbox.rt)).thenReturn(1.0);
//
//        suckerSystem.updatePositionFromControllerInput();
//        suckerSystem.updatePositionFromControllerInput();
//        suckerSystem.updatePositionFromControllerInput();
//
//        System.out.println(suckerSystem.getGatePosition());
//        assert (suckerSystem.getGatePosition() == SuckerPosition.CRUSH);
//    }

}
