include env.mk

export JAVA_HOME=/usr/lib/jvm/java-8-openjdk

WSO2_HOME ?= wso2sp-4.2.0

KAFKA_HOME ?= kafka_2.11-1.1.0

INBOUND_TOPIC ?= 'bets'
OUTBOUND_TOPIC ?= 'alerts'
TOPIC_TO_CREATE ?= 'test'

ZOOKEEPER_ADDR ?= 'localhost:2181'

#
# Kafka commands
#

zookeeper-start:
	"${KAFKA_HOME}/bin/zookeeper-server-start.sh" "${KAFKA_HOME}/config/zookeeper.properties"

kafka-start:
	"${KAFKA_HOME}/bin/kafka-server-start.sh" "${KAFKA_HOME}/config/server.properties"

kafka-topic-create:
	"${KAFKA_HOME}/bin/kafka-topics.sh" --create --zookeeper $(ZOOKEEPER_ADDR) --replication-factor 1 --partitions 1 --topic ${TOPIC_TO_CREATE}

kafka-topic-list:
	"${KAFKA_HOME}/bin/kafka-topics.sh" --list --zookeeper $(ZOOKEEPER_ADDR)

kafka-message-produce:
	"${KAFKA_HOME}/bin/kafka-console-producer.sh" --broker-list localhost:9092 --topic $(INBOUND_TOPIC)

kafka-message-consume:
	"${KAFKA_HOME}/bin/kafka-console-consumer.sh" --bootstrap-server localhost:9092 --topic $(OUTBOUND_TOPIC) --from-beginning

kafka-topic-set-retention:
	${KAFKA_HOME}/bin/kafka-configs.sh --zookeeper $(ZOOKEEPER_ADDR) --alter --entity-type topics --entity-name $(INBOUND_TOPIC) --add-config retention.ms=1000
#	${KAFKA_HOME}/bin/kafka-topics.sh --zookeeper $(ZOOKEEPER_ADDR) --alter --topic $(INBOUND_TOPIC) --config retention.ms=3000


#
# WSO2 commands
#

wso2-editor-start:
	${WSO2_HOME}/bin/editor.sh

wso2-worker-start:
	${WSO2_HOME}/bin/worker.sh

wso2-dashboard-start:
	${WSO2_HOME}/bin/dashboard.sh


env.mk:
	touch env.mk