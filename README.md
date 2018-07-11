Basic quick'n'dirty project for quick'n'dirty tests of Siddhi applications with Intellij.


# Prerequisites
- Java 1.8 JDK installed and configured as SDK in Intellij,
- IntelliJ Siddhi plugin installed and Siddhi SDK configured following instructions
  available in the plugin's website: https://wso2.github.io/siddhi-plugin-idea/
- Kafka installed; download and unzip the following in a location of choice:
  https://www.apache.org/dyn/closer.cgi?path=/kafka/1.1.0/kafka_2.11-1.1.0.tgz
- WSO2 installed; download and unzip the following in a location of choice:
  https://github.com/wso2/product-sp/releases/download/v4.2.0-rc2/wso2sp-4.2.0.zip
  
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

# Intro to WSO2 SP
To grasp basics of WSO2 SP it's good to refer to [WSO2 SP Quick Start Guide] and
[WSO2 SP Tutorials]. They provide very good starting point, gradually introducing more and more
advanced concepts in an easy to digest manner.  


[kafka-console-producer]:           https://kafka.apache.org/quickstart

[makefile]:                         file://makefile 

[WSO2 SP Quick Start Guide]:        https://docs.wso2.com/display/SP420/Quick+Start+Guide
[WSO2 SP Tutorials]:                https://docs.wso2.com/display/SP420/Tutorials

[Kafka Quick Start](https://kafka.apache.org/quickstart)