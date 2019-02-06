package com.github.mahimarib.pid;

/**
 * This interface is used to write to output to the system after PID calculations.
 */
public interface Output {
    /**
     * This method is use to feed in the pid output.
     *
     * @param output PID output.
     */
    void write(double output);

    /**
     * Method is called when the system reaches the tolerable zone around
     * the setpoint.
     */
    void end();
}
