package frc.team1091.robot.wrapper;

import edu.wpi.first.wpilibj.Encoder;

// Encoders cannot be created without a real robot,
// so we are using this to fake it out
public class EncoderWrapper {
    private Encoder encoder;

    public EncoderWrapper(Encoder encoder) {
        this.encoder = encoder;
    }

    public int get() {
        return encoder.get();
    }

}
