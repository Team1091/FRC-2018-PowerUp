package frc.team1091.robot.autonomous.commands;

public class Wait implements Command {

    private long timeToWaitMillis;
    private boolean firstTime = true;
    private long startTime = 0;

    public Wait(long timeToWaitMillis) {
        this.timeToWaitMillis = timeToWaitMillis;
    }

    @Override
    public Command execute(double dt) {
        if (firstTime) {
            startTime = System.currentTimeMillis();
            firstTime = false;
        }

        if (System.currentTimeMillis() > startTime + timeToWaitMillis) {
            return null;
        }
        return this;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
