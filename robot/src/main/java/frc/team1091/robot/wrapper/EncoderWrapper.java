package frc.team1091.robot.wrapper;

import edu.wpi.first.wpilibj.Encoder;

// Encoders cannot be created without a real robot,
// so we are using this to fake it out
public class EncoderWrapper {
    private Encoder encoder;
    private double ticksPerDistanceUnit;

    public EncoderWrapper(Encoder encoder, double ticksPerDistanceUnit) {
        this.encoder = encoder;
        this.ticksPerDistanceUnit = ticksPerDistanceUnit;
    }

    public int get() {
        return encoder.get();
    }

    public double getDistance() {
        return encoder.get() / ticksPerDistanceUnit;
    }

    public void reset() {
        encoder.reset();
    }
}
