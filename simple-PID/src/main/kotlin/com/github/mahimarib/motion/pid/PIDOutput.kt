package com.github.mahimarib.motion.pid

interface PIDOutput {
    fun write(output: Double)
}