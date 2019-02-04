package com.github.mahimarib.motion.pid

interface PIDSource {
    var type: SourceType
    fun get(): Double
}