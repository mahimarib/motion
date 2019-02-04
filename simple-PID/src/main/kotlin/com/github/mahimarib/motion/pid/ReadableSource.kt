package com.github.mahimarib.motion.pid

interface ReadableSource {
    var type: SourceType
    fun get(): Double
}