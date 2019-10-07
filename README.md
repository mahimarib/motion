# Simple PID Controller

[![](https://jitpack.io/v/mahimarib/simple-PID.svg?style=flat-square)](https://jitpack.io/#mahimarib/simple-PID)

### How to get library

You can download from the release tag of the repo or use [jitpack.io](https://jitpack.io/#mahimarib/simple-PID).

### For Gradle projects

Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
        maven {
            url 'https://jitpack.io'
        }
    }
}
```

Add the dependency:

```gradle
dependencies {
    implementation 'com.github.mahimarib:simple-PID:0.0.1'
}
```

### How to use

In order to use [`Controller.java`](src/main/java/com/github/mahimarib/pid/Controller.java), 
you need your system to implement [`Source`](src/main/java/com/github/mahimarib/pid/Source.java)
so the controller can read the sensor values:

```java
import com.github.mahimarib.pid.Source;

public class PIDMotor extends DcMotor implements Source {

    ...

    @Override
    public SourceType getSourceType() {
        return SourceType.Displacement;
    }

    @Override
    public void setSourceType(SourceType sourceType) {}

    @Override
    public double get() {
        // just some method to feed in sensor values.
        return getPosition();
    }
}
```

To output the PID output, you can either make the system class implement the [`Output`](src/main/java/com/github/mahimarib/pid/Output.java) interface or if you want to use it somewhere else just create an
inner class that implements it:

```java
import com.github.mahimarib.pid.Output;

public class SomeClass {
    
    ...
    
    private class MotorOutput implements Output {
        @Override
        public void write(double output) {
            motor.set(output);
        }
    }
}
```

Then you can finally create a PID Controller:


```java
import com.github.mahimarib.pid.*;

public class RunMotor {
    private double kP = 0.001;
    private double kI = 0;
    private double kD = 0;
    
    // this class implements Source.
    private PIDMotor motor = new PIDMotor();
    
    private Controller motorController;
    
    private class MotorOutput implements Output {
        @Override
        public void write(double output) {
            motor.set(output);
        }
    }
    
    public void init() {
        motorController = new Controller(kP, kI, kD, motor, new MotorOutput());
        motorController.setOutputLimit(-1.0, 1.0);
        // setting the absolute tolerance, meaning that the raw sensor value
        // should be off by the given value from the setpoint. In this case
        // if we are using encoders and want the motor to spin to a position
        // then the desired position will be in between:
        // sepoint - tolerance ≤ position ≤ setpoint + tolerance
        motorController.setAbsoluteTolerance(100);
        motorController.setSetpoint(4000);
        motorController.enable();
    }
    
    // running the PID until it reaches setpoint then stop, it can hold the
    // position if you don't. The run() method must be called inside a loop.
    public void loop() {
        if (!motorController.onTarget()) {
            motorController.run();
        }
        motorController.disable();
        motor.set(0.0);
    }
}
```
