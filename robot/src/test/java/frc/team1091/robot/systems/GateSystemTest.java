package frc.team1091.robot.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team1091.robot.RobotComponents;
import frc.team1091.robot.Xbox;
import frc.team1091.robot.wrapper.DigitalInputWrapper;
import frc.team1091.robot.wrapper.EncoderWrapper;
import org.junit.Before;
import org.junit.Test;

import java.io.DataInput;

import static org.mockito.Mockito.*;

public class GateSystemTest {

    private RobotComponents robotComponents;
    private Joystick mockJoystick;
    private SpeedController mockGateMotor;
    private DigitalInputWrapper mockPickUpLimitSwitch;
    private DigitalInputWrapper mockClosedLimitSwitch;
    private DigitalInputWrapper mockDropLimitSwitch;

    private GateSystem gateSystem;

    @Before
    public void setUp(){
        mockJoystick = mock(Joystick.class);
        mockGateMotor = mock(SpeedController.class);
        mockPickUpLimitSwitch = mock(DigitalInputWrapper.class);
        mockClosedLimitSwitch = mock(DigitalInputWrapper.class);
        mockDropLimitSwitch = mock(DigitalInputWrapper.class);

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
        gateSystem = spy(new GateSystem(robotComponents));
    }

    @Test
    public void controlGate_GateUp_ShouldMoveToGateUp(){
        when(mockJoystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
        when(mockJoystick.getRawAxis(Xbox.lt)).thenReturn(0.0);
        when(mockClosedLimitSwitch.get()).thenReturn(false, false, false, true);

        gateSystem.SetGatePosition(GateSystemState.GateUp);
        gateSystem.controlGate();
        verify(mockGateMotor).set(.25);
        gateSystem.controlGate();
        gateSystem.controlGate();
        gateSystem.controlGate();
        verify(mockGateMotor).set(0);
    }

    @Test
    public void controlGate_PickupPosition_ShouldMoveToPickup(){
        when(mockJoystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
        when(mockJoystick.getRawAxis(Xbox.lt)).thenReturn(0.0);
        when(mockPickUpLimitSwitch.get()).thenReturn(false, false, false, true);
        when(mockClosedLimitSwitch.get()).thenReturn(true, false, false, false);

        gateSystem.SetGatePosition(GateSystemState.PickupPosition);
        gateSystem.controlGate();
        verify(mockGateMotor).set(-.25);
        gateSystem.controlGate();
        verify(mockGateMotor).set(.25);
        gateSystem.controlGate();
        gateSystem.controlGate();
        verify(mockGateMotor).set(0);
    }

    @Test
    public void controlGate_DropPosition_ShouldMoveToDrop(){
        when(mockJoystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
        when(mockJoystick.getRawAxis(Xbox.lt)).thenReturn(0.0);
        when(mockDropLimitSwitch.get()).thenReturn(false, false, false, true);

        gateSystem.SetGatePosition(GateSystemState.DropPosition);
        gateSystem.controlGate();
        verify(mockGateMotor).set(-.25);
        gateSystem.controlGate();
        gateSystem.controlGate();
        gateSystem.controlGate();
        verify(mockGateMotor).set(0);
    }

    @Test
    public void UpdatePositionFromConrollerInput_RtPressed_ShouldSetGateUp(){
        when(mockJoystick.getRawAxis(Xbox.rt)).thenReturn(1.0);
        when(mockJoystick.getRawAxis(Xbox.lt)).thenReturn(0.0);

        gateSystem.UpdatePositionFromConrollerInput();
        verify(gateSystem).SetGatePosition(GateSystemState.GateUp);
    }

    @Test
    public void UpdatePositionFromConrollerInput_LtPressed_ShouldSetPickupPosition(){
        when(mockJoystick.getRawAxis(Xbox.rt)).thenReturn(0.0);
        when(mockJoystick.getRawAxis(Xbox.lt)).thenReturn(1.0);

        gateSystem.UpdatePositionFromConrollerInput();
        verify(gateSystem).SetGatePosition(GateSystemState.PickupPosition);
    }

    @Test
    public void UpdatePositionFromConrollerInput_LtAndRtPressed_ShouldSetDropPosition(){
        when(mockJoystick.getRawAxis(Xbox.rt)).thenReturn(1.0);
        when(mockJoystick.getRawAxis(Xbox.lt)).thenReturn(1.0);

        gateSystem.UpdatePositionFromConrollerInput();
        verify(gateSystem).SetGatePosition(GateSystemState.DropPosition);
    }
}
