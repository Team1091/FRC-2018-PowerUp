package frc.team1091.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import frc.team1091.robot.wrapper.EncoderWrapper;

public class RobotComponents {

    public static RobotComponents getDefaultInstance() {
        return new RobotComponents(
                new Joystick(0 ),
                new Victor(0),
                new Victor(1),
                new EncoderWrapper(new Encoder(2, 3)),
                new EncoderWrapper(new Encoder(4, 5)));
    }

    public RobotComponents(Joystick xboxController, SpeedController frontLeftMotor, SpeedController frontRightMotor, EncoderWrapper frontLeftEncoder, EncoderWrapper frontRightEncoder) {
        this.xboxController = xboxController;
        this.leftMotor = frontLeftMotor;
        this.rightMotor = frontRightMotor;
        this.leftEncoder = frontLeftEncoder;
        this.rightEncoder = frontRightEncoder;
    }

    public final Joystick xboxController;
    public final SpeedController leftMotor;
    public final SpeedController rightMotor;
    public final EncoderWrapper leftEncoder;
    public final EncoderWrapper rightEncoder;
}
