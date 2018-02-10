package frc.team1091.robot.wrapper;

import edu.wpi.first.wpilibj.Encoder;

// Encoders cannot be created without a real robot,
// so we are using this to fake it out
public class EncoderWrapper {
    private Encoder encoder;
    private boolean reverse;

    public EncoderWrapper(Encoder encoder) {
        this(encoder, false);
    }

    public EncoderWrapper(Encoder encoder, boolean reverse) {
        this.encoder = encoder;
        this.reverse = reverse;
    }

    public int get() {
        return encoder.get() * (reverse ? -1 : 1);
    }

    public void reset() {
        encoder.reset();
    }
}
