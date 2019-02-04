package com.github.mahimarib.motion.pid;

interface ReadableSource {
    void setSource(SourceType sourceType);
    SourceType getSourceType();
    double get();
}
