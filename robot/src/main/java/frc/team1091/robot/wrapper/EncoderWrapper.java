package frc.team1091.robot.wrapper;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

// Encoders cannot be created without a real robot,
// so we are using this to fake it out
public class EncoderWrapper {
    private Encoder encoder;
    private double ticksPerDistanceUnit;

    public EncoderWrapper(Encoder encoder, double ticksPerDistanceUnit) {
        this.encoder = encoder;
        this.ticksPerDistanceUnit = ticksPerDistanceUnit;
    }

    public double get() {
        return encoder.get();
    }

    public double getDistance() {
        return (double)encoder.get() / ticksPerDistanceUnit;
    }

    public void reset() {
        encoder.reset();
    }

    public void printDistanceTo(){
        System.out.println(ticksPerDistanceUnit);
    }
}
