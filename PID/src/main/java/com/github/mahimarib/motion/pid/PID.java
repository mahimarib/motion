package com.github.mahimarib.motion.pid;

public class PID {
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double kF = 0;

    private double maxOutput = 0;
    private double minOutput = 0;

    private double setpoint = 0;

    private ReadableSource source;
    private Writable writable;

    public PID(double kP, double kI, double kD, double kF,
               ReadableSource source, Writable writable) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.source = source;
        this.writable = writable;
    }

    public PID(double kP, double kI, double kD, ReadableSource source,
               Writable writable) {
        this(kP, kI, kD, 0.0, source, writable);
    }

    public void setOutputLimit(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("The minimum output is greater" +
                    " the the maximum output");
        }
        this.maxOutput = max;
        this.minOutput = min;
    }

    public void setOutputLimit(double outputLimit) {
        setOutputLimit(-outputLimit, outputLimit);
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    private double getError() {
        return setpoint - source.get();
    }
}
