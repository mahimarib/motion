package com.github.mahimarib.pid;

/**
 * This interface must be applied by systems that have sensor inputs that
 * the PID needs to use.
 */
interface Source {
    /**
     * Getter for the sensor source type {@link SourceType}.
     *
     * @return source type of sensor.
     */
    SourceType getSourceType();

    /**
     * This method sets the source type to either displacement, or rate
     * {@link SourceType}.
     *
     * @param sourceType source type of sensor.
     */
    void setSourceType(SourceType sourceType);

    /**
     * This method is used to get the sensor value to feed into the PID
     * algorithm.
     *
     * @return the sensor input.
     */
    double get();
}
