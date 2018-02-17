package frc.team1091.robot.wrapper;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

// Encoders cannot be created without a real robot,
// so we are using this to fake it out
public class EncoderWrapper implements PIDSource {
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

    @Override
    public double pidGet(){
        return encoder.pidGet();
    }

    @Override
    public PIDSourceType getPIDSourceType(){
        return encoder.getPIDSourceType();
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource){
        encoder.setPIDSourceType(pidSource);
    }

    public void reset() {
        encoder.reset();
    }
}
