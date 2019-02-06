package com.github.mahimarib.pid;

public class PID {
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double kF = 0;

    private double maxOutput = 0;
    private double minOutput = 0;

    private double setpoint = 0;

    private Source source;
    private Output output;

    public PID(double kP, double kI, double kD, double kF,
               Source source, Output output) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.source = source;
        this.output = output;
    }

    public PID(double kP, double kI, double kD, Source source,
               Output output) {
        this(kP, kI, kD, 0.0, source, output);
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

    public void calculate() {
        double kP_Output;
        double kI_Output;
        double kD_Output;
        double kF_Output;

        double PID_output;

        kF_Output = kF * setpoint;

        kP_Output = kP * getError();

    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }
}
