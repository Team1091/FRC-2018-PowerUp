package frc.team1091.robot;

import edu.wpi.first.wpilibj.*;
import frc.team1091.robot.wrapper.EncoderWrapper;

public class RobotComponents {

    public static RobotComponents getDefaultInstance() {
        return new RobotComponents(
                new Joystick(0),
                new Victor(0),
                new Victor(1),
                new Victor(2),
                new Victor (3),
                new Victor (4),
                new Victor (5),
                new Victor (6),
                new EncoderWrapper(new Encoder(2, 3)),
                new EncoderWrapper(new Encoder(4, 5)),
                new EncoderWrapper(new Encoder(6,7)),
                new DigitalInput(8),
                new DigitalInput(9),
                new DigitalInput(10),
                new DigitalInput (11),
                new AnalogInput (12));
    }

    public RobotComponents(Joystick xboxController,
                           SpeedController frontLeftMotor,
                           SpeedController frontRightMotor,
                           SpeedController elevatorMotor,
                           SpeedController lifterMotor,
                           SpeedController suckerMotor,
                           SpeedController winchMotor,
                           SpeedController releaseMotor,
                           EncoderWrapper frontLeftEncoder,
                           EncoderWrapper frontRightEncoder,
                           EncoderWrapper elevatorEncoder,
                           DigitalInput pickUpPositionDigitalInput,
                           DigitalInput gateClosePositionDigitalInput,
                           DigitalInput dropBoxPositionDigitalInput,
                           DigitalInput elevatorDigitalInput,
                           AnalogInput ultraSonicAnalogInput)
    {
        this.xboxController = xboxController;
        this.leftMotor = frontLeftMotor;
        this.rightMotor = frontRightMotor;
        this.elevatorMotor = elevatorMotor;
        this.gateMotor = lifterMotor;
        this.suckerMotor = suckerMotor;
        this.winchMotor = winchMotor;
        this.releaseMotor = releaseMotor;
        this.leftEncoder = frontLeftEncoder;
        this.rightEncoder = frontRightEncoder;
        this.elevatorEncoder = elevatorEncoder;
        this.pickUpPositionDigitalInput = pickUpPositionDigitalInput;
        this.gateClosePositionDigitalInput = gateClosePositionDigitalInput;
        this.dropBoxPositionDigitalInput = dropBoxPositionDigitalInput;
        this.elevatorDigitalInput = elevatorDigitalInput;
        this.ultraSonicAnalogInput = ultraSonicAnalogInput;
    }

    public final Joystick xboxController;
    public final SpeedController leftMotor;
    public final SpeedController rightMotor;
    public final SpeedController elevatorMotor;
    public final SpeedController gateMotor; //for box cam
    public final SpeedController suckerMotor;
    public final SpeedController winchMotor;
    public final SpeedController releaseMotor;

    public final EncoderWrapper leftEncoder;
    public final EncoderWrapper rightEncoder;
    public final EncoderWrapper elevatorEncoder;

    public final DigitalInput pickUpPositionDigitalInput;
    public final DigitalInput gateClosePositionDigitalInput;
    public final DigitalInput dropBoxPositionDigitalInput;
    public final DigitalInput elevatorDigitalInput;

    public final AnalogInput ultraSonicAnalogInput;
}