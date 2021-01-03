# myjdi

***MYJDI*** (***M***YJDI ***Y***outh ***J***ava ***D***ebug ***I***mplementation) is JDI implementation.


## Structure Overview

### [Java Platform Debugger Architecture](https://docs.oracle.com/javase/8/docs/technotes/guides/jpda/architecture.html)

The Java Platform Debugger Architecture is structured as follows:

```
           Components                          Debugger Interfaces

                /    |--------------|
               /     |     VM       |
 debuggee ----(      |--------------|  <------- JVM TI - Java VM Tool Interface
               \     |   back-end   |
                \    |--------------|
                /           |
 comm channel -(            |  <--------------- JDWP - Java Debug Wire Protocol
                \           |
                     |--------------|
                     | front-end    |
                     |--------------|  <------- JDI - Java Debug Interface
                     |      UI      |
                     |--------------|
```

__JPDA__ is a multi-tiered debugging architecture
that allows tools developers to easily create debugger applications which run portably across platforms,
[virtual machine (VM)](https://docs.oracle.com/javase/8/docs/technotes/guides/jpda/architecture.html#vm)
implementations and JDK versions.

__JPDA__ consists of three layers:

- Java VM Tool Interface (__JVM TI__) -
Defines the debugging services a VM provides.
- Java Debug Wire Protocol (__JDWP__) -
Defines the communication between debuggee and debugger processes.
- Java Debug Interface (__JDI__) -
Defines a high-level Java language interface which tool developers can easily use to write remote debugger applications.
