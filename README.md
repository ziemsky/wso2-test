Basic quick'n'dirty project for quick'n'dirty tests of Siddhi applications with Intellij.

# Prerequisites
Siddhi applications can be developed and tested using either [Stream Processor Studio] (a web based
IDE) or [IntelliJ plugin]. 

Stream Processor Studio requires a running instance of the `editor` application to be running with
[support for Kafka] configured. IntelliJ plugin has this integration configured out of the box and
does not require a standalone instance of WSO2 SP `editor` and runs Siddhi applications itself.

## Common prerequisites       
- Kafka installed; download and unzip the following in a location of choice:
  https://www.apache.org/dyn/closer.cgi?path=/kafka/1.1.0/kafka_2.11-1.1.0.tgz 

## Prerequsites for developing in IntelliJ
- Java 1.8 JDK installed and configured as SDK in Intellij,
- [IntelliJ plugin] for Siddhi installed and [Siddhi SDK] configured. In this project, make sure to
  only set Siddhi SDK [for the `siddhi` module] rather than for the whole project (slight departure
  from what the plugin's documentation describes for vanilla configurations).
- IntelliJ run configuration set up for the application under test; see [Intellij plugin] docs for
  instructions on how to set it up.   
  
## Prerequisites for developing in Stream Processing Studio    
- WSO2 installed; download and unzip the following in a location of choice:
  https://github.com/wso2/product-sp/releases/download/v4.2.0-rc2/wso2sp-4.2.0.zip
- [Support for Kafka] configured.
  
# Development cycle
Have Kafka running with topics required by your application created.

Edit and run your Siddhi application.

Have an integration test written for the Siddhi application under test, making it feed events to
appliation's input Kafka topics Kafka and monitor events emitted by the application with assertions;
see [MarketMoversTest] for an example.

Rinse and repeat.
  
# Feeding events in to applications via Kafka
  
Events can be fed to Kafka topics that Siddhi applications listen on using classes from
`main/test/feeders`. These 'feeder' classes are driven by JUnit for easy execution from the IDE.

# Monitoring events emitted from the applications via Kafka  

Events emitted from the Siddhi applications under tests can be easily read using using
[kafka-console-producer].

# Running Kafka and WSO2

To easily start and stop Kafka and WSO2 components provided [makefile] can be used (obviously
requires that Make is available on the host; it's installed by default on Mac and Linux systems).
Before using it for the first time create file `env.mk` next to the `makefile` and set `KAFKA_HOME`
and `WSO2_HOME` variables there, populating them with paths where you deployed Kafka and WSO2 SP in.
Remember to start Zookeeper before starting Kafka.

See the `makefile` for available goals. Example syntax for executing a goal is:
```bash
make zookeeper-start
``` 

Arguments to goals that require them can be provided as follows:
```bash
make kafka-topic-create TOPIC_TO_CREATE=my-topic-name
```  

Start each component in a separate terminal window. Stop them by `Ctrl+C`.

The same `makefile` can be used to start Kafka commandline consumer to monitor events
emitted by applications under test.

As an alternative to the `makefile`, follow instructions in 
[WSO2 SP Quick Start Guide] and [WSO2 SP Tutorials]. Basic instructions for Kafka can be
found in [Kafka Quick Start].      

# Documentation introducing to WSO2 SP
To grasp basics of WSO2 SP it's good to refer to [WSO2 SP Quick Start Guide] and
[WSO2 SP Tutorials]. They provide very good starting point, gradually introducing more and more
advanced concepts in an easy to digest manner.

[WSO2 Stream Processor online documentation] is a key source of information. It includes the
aforementioned tutorials alongside the content of a more reference-style nature; in particular:
- [User Guide] going deeper into selected topics
  than the tutorials; in particular:
  - [Developing Streaming Applications].    
- [Writing custom Siddhi extensions]
- [Samples] pointing to example applications available to review and run via the
  [Stream Processor Studio].

To complement the above (in some places, overlapping), [Siddhi engine]'s GitHub site provides more
reference material; in particular:
- [Siddhi query guide]
- [Siddhi extensions] available out of the box.
         

[kafka-console-producer]:            https://kafka.apache.org/quickstart
                                     
[WSO2 Stream Processor online documentation]: https://docs.wso2.com/display/SP420/Stream+Processor+Documentation                                     
[WSO2 SP Quick Start Guide]:         https://docs.wso2.com/display/SP420/Quick+Start+Guide
[WSO2 SP Tutorials]:                 https://docs.wso2.com/display/SP420/Tutorials
[User Guide]:                        https://docs.wso2.com/display/SP420/User+Guide
[Developing Streaming Applications]: https://docs.wso2.com/display/SP420/Developing+Streaming+Applications
[Writing custom Siddhi extensions]:  https://docs.wso2.com/display/SP420/Writing+Custom+Siddhi+Extensions
[Samples]:                           https://docs.wso2.com/display/SP420/Samples?src=sidebar
                                     
[Siddhi engine]:                     https://wso2.github.io/siddhi/
[Siddhi query guide]:                https://wso2.github.io/siddhi/documentation/siddhi-4.0/
[Siddhi extensions]:                 https://wso2.github.io/siddhi/extensions/                 
                                     
[Kafka Quick Start]:                 https://kafka.apache.org/quickstart
                                     
                                     
[IntelliJ plugin]:                   https://wso2.github.io/siddhi-plugin-idea/
[Siddhi SDK]:                        https://wso2.github.io/siddhi-plugin-idea/
[Stream Processor Studio]:           https://docs.wso2.com/display/SP420/Stream+Processor+Studio+Overview
[support for Kafka]:                 https://wso2-extensions.github.io/siddhi-io-kafka/
[makefile]:                          file://makefile 
[for the `siddhi` module]:           https://www.jetbrains.com/help/idea/sdk.html#change'module-sdk
                                     
[MarketMoversTest]:                  file://src/main/test/java/feeders/movers/MarketMoversTest.java
