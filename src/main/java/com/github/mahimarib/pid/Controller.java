package com.github.mahimarib.pid;

public class Controller extends PID {
    private Tolerance tolerance;
    private boolean enabled = false;

    public Controller(
            double kP, double kI, double kD, double kF, Source source,
            Output outputObj) {
        super(kP, kI, kD, kF, source, outputObj);
    }

    public Controller(
            double kP, double kI, double kD, Source source, Output outputObj) {
        super(kP, kI, kD, source, outputObj);
    }

    private interface Tolerance {
        boolean onTarget();
    }

    private class PercentTolerance implements Tolerance {
        private double percent;

        private PercentTolerance(double percent) {
            this.percent = percent;
        }

        @Override
        public boolean onTarget() {
            return Math.abs(getError() / getSetpoint()) * 100 <= percent;
        }
    }

    private class AbsoluteTolerance implements Tolerance {
        private double value;

        public AbsoluteTolerance(double value) {
            this.value = value;
        }

        @Override
        public boolean onTarget() {
            return Math.abs(getError()) <= value;
        }
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    @Override
    public void run() {
        if (enabled) {
            super.run();
        }
    }

    public void setAbsoluteTolerance(double value) {
        tolerance = new AbsoluteTolerance(value);
    }

    public void setPercentTolerance(double percent) {
        tolerance = new PercentTolerance(percent);
    }

    public boolean onTarget() {
        return tolerance.onTarget();
    }
}
