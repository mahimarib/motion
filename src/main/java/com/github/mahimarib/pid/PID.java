package com.github.mahimarib.pid;

public class PID {
    private double kP;
    private double kI;
    private double kD;
    private double kF;

    private double maxOutput = 0;
    private double minOutput = 0;

    private double setpoint = 0;

    private double errorSum = 0;
    private double prevError = 0;
    private double output = 0;
    private double clampedOutput = 0;

    private Source source;
    private Output outputObj;

    public PID(
            double kP, double kI, double kD, double kF,
            Source source, Output outputObj) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.source = source;
        this.outputObj = outputObj;
    }

    public PID(
            double kP, double kI, double kD, Source source,
            Output outputObj) {
        this(kP, kI, kD, 0.0, source, outputObj);
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

    public void setOutputLimit(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException(
                    "The minimum outputObj is greater" +
                    " the the maximum outputObj");
        }
        this.maxOutput = max;
        this.minOutput = min;
    }

    public void setOutputLimit(double outputLimit) {
        setOutputLimit(-outputLimit, outputLimit);
    }

    public double getSetpoint() {
        return setpoint;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    protected double getError() {
        return setpoint - source.get();
    }

    public void calculate() {
        double error = getError();

        double kF_Output = kF * setpoint;
        double kP_Output = kP * error;
        double kI_Output = kI * errorSum;
        double kD_Output = kD * (error - prevError);

        boolean isSaturated = output > clampedOutput;
        int signOfOutput = output > 0 ? 1 : -1;
        int signOfError = error > 0 ? 1 : -1;

        if (isSaturated && signOfError == signOfOutput) {
            kI_Output = 0;
        }

        output = kP_Output + kI_Output + kD_Output + kF_Output;
        clampedOutput = clamp(output, minOutput, maxOutput);

        errorSum += error;
        prevError = error;
    }

    public void run() {
        calculate();
        outputObj.write(clampedOutput);
    }

    public void reset() {
        setpoint = 0;
        output = 0;
        clampedOutput = 0;
        prevError = 0;
        errorSum = 0;
    }
}
