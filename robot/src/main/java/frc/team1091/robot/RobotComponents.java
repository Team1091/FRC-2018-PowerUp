package frc.team1091.robot;

import edu.wpi.first.wpilibj.*;
import frc.team1091.robot.wrapper.DigitalInputWrapper;
import frc.team1091.robot.wrapper.EncoderWrapper;

public class RobotComponents {

    // USB
    public static final int xboxControllerPort = 0;

    //Motors
    public static final int rightDriveMotorChannel = 0;
    public static final int leftDriveMotorChannel = 1;
    public static final int boxElevatorMotorChannel = 2;
    public static final int boxPlatformMotorChannel = 3;
    public static final int boxSuckerMotorChannel = 4;
    public static final int robotClimbMotorChannel = 5;
    public static final int releaseMotorChannel = 6;

    //Encoders
    public static final int rightDriveMotorEncoderChannel1 = 0;
    public static final int rightDriveMotorEncoderChannel2 = 1;
    public static final int leftDriveMotorEncoderChannel1 = 2;
    public static final int leftDriveMotorEncoderChannel2 = 3;
    public static final int boxElevatorEncoderChannel1 = 4;
    public static final int boxElevatorEncoderChannel2 = 5;
    public static final int boxPlatformEncoderChannel1 = 6;
    public static final int boxPlatformEncoderChannel2 = 7;

    //Digital Inputs
    public static final int boxPlatformLimitSwitchChannel = 8;
    public static final int boxElevatorLimitSwitchChannel = 9;

    public static final double wheelBaseDiameter = 29.0;
    public static final double wheelDiameter = 6.0;

    //Anolog Inputs
//    public static final int boxConsumtionUltraSonicSensor = 1;

    public static RobotComponents getDefaultInstance() {
        return new RobotComponents(
                new Joystick(xboxControllerPort),
                new Victor(leftDriveMotorChannel),
                new Victor(rightDriveMotorChannel),
                new Victor(boxElevatorMotorChannel),
                new Victor(boxPlatformMotorChannel),
                new Victor(boxSuckerMotorChannel),
                new Victor(robotClimbMotorChannel),
                new Victor(releaseMotorChannel),
                new EncoderWrapper(new Encoder(leftDriveMotorEncoderChannel1, leftDriveMotorEncoderChannel2), -360.0 / (wheelDiameter * Math.PI)),
                new EncoderWrapper(new Encoder(rightDriveMotorEncoderChannel1, rightDriveMotorEncoderChannel2), 110.0 / (wheelDiameter * Math.PI)),
                new EncoderWrapper(new Encoder(boxElevatorEncoderChannel1, boxElevatorEncoderChannel2), 141.5),
                new EncoderWrapper(new Encoder(boxPlatformEncoderChannel1, boxPlatformEncoderChannel2), 1),
                new DigitalInputWrapper(new DigitalInput(boxPlatformLimitSwitchChannel)),
                new DigitalInputWrapper(new DigitalInput(boxElevatorLimitSwitchChannel))//,
                //       new AnalogInput(boxConsumtionUltraSonicSensor)
        );
    }

    public RobotComponents(Joystick xboxController,
                           SpeedController frontLeftMotor,
                           SpeedController frontRightMotor,
                           SpeedController elevatorMotor,
                           SpeedController platformMotor,
                           SpeedController suckerMotor,
                           SpeedController winchMotor,
                           SpeedController releaseMotor,
                           EncoderWrapper frontLeftEncoder,
                           EncoderWrapper frontRightEncoder,
                           EncoderWrapper elevatorEncoder,
                           EncoderWrapper platformEncoder,
                           DigitalInputWrapper platformLimitSwitch,
                           DigitalInputWrapper elevatorLimitSwitch//,
                           //                       AnalogInput ultraSonicAnalogInput
    ) {
        this.xboxController = xboxController;
        this.leftMotor = frontLeftMotor;
        this.rightMotor = frontRightMotor;
        this.elevatorMotor = elevatorMotor;
        this.platformMotor = platformMotor;
        this.suckerMotor = suckerMotor;
        this.winchMotor = winchMotor;
        this.releaseMotor = releaseMotor;
        this.leftEncoder = frontLeftEncoder;
        this.rightEncoder = frontRightEncoder;
        this.elevatorEncoder = elevatorEncoder;
        this.platformEncoder = platformEncoder;
        this.platformLimitSwitch = platformLimitSwitch;
        this.elevatorLimitSwitch = elevatorLimitSwitch;
//        this.ultraSonicAnalogInput = ultraSonicAnalogInput;
    }

    public final Joystick xboxController;
    public final SpeedController leftMotor;
    public final SpeedController rightMotor;
    public final SpeedController elevatorMotor;
    public final SpeedController platformMotor; //for box cam
    public final SpeedController suckerMotor;
    public final SpeedController winchMotor;
    public final SpeedController releaseMotor;

    public final EncoderWrapper leftEncoder;
    public final EncoderWrapper rightEncoder;
    public final EncoderWrapper elevatorEncoder;
    public final EncoderWrapper platformEncoder;

    public final DigitalInputWrapper platformLimitSwitch;
    public final DigitalInputWrapper elevatorLimitSwitch;

    //public final AnalogInput ultraSonicAnalogInput;
}