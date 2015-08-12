## java-jackson-serialize-example
The [Java Jackson library](https://github.com/FasterXML/jackson) is an awesome library for (de)serialization of json in Java. However, moving beyond the (de)serialization of simple json can quickly become complicated. To make the learning curve easier, I've developed simple Java application that demonstrates advanced usage of the Jackson json (de)serialization functionality. Hopefully this will save some poor soul from hours of frustration...

## Running Unit Tests
The application is written mainly so a working example can be disected, however, you can run the code if you desire to. By running `mvn install`, all dependencies will be resolved and all tests will be run. It's highly recommended that you run the tests in an IDE so you can debug them for a better understanding of what is happening. When debugging from an IDE, debug the _com.lewisdawson.example.service.NestedCarServiceTest_ class.

### Prereqs
Prior to running the tests, the following must be installed:

1. JDK 7+
1. Maven