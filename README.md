# Kafka 4.x Setup & Project Guide

> **Project Flow:** `Postman → deliveryboy (Producer) → Kafka → enduser (Consumer)`

---

## Prerequisites

- Java 17+
- [Apache Kafka 4.3.0](https://kafka.apache.org/downloads) extracted to `C:\kafka_2.13-4.3.0`
- Spring Boot applications: `deliveryboy` (producer) and `enduser` (consumer)

---

## Kafka Setup (First Time Only)

> These steps are only needed once. After the first setup, skip to **Starting Kafka**.

### Step 1 — Navigate to Kafka Directory

```bash
cd C:\kafka_2.13-4.3.0
```

### Step 2 — Generate a Cluster UUID

The UUID is the **unique identity of your Kafka cluster**. Multiple brokers can share the same UUID to form one cluster.

```bash
bin\windows\kafka-storage.bat random-uuid
```

**Output:**
```
xHz3kLmP9QrTvWnB2sYuJA
```

Copy this UUID — you'll need it in the next step.

### Step 3 — Format Storage Directory

Creates and initializes the storage directory with a **standalone** single-broker setup using the UUID generated above.

```bash
bin\windows\kafka-storage.bat format --standalone -t <YOUR-UUID> -c config\server.properties
```

Replace `<YOUR-UUID>` with the value from Step 2.

**Output:**
```
Formatting /tmp/kraft-combined-logs with metadata.version 4.0
```

> Kafka 4.x uses **KRaft mode** — no Zookeeper required.

---

## Starting Kafka

Run this every time you want to use Kafka:

```bash
bin\windows\kafka-server-start.bat config\server.properties
```

### Verify Kafka is Running

```bash
netstat -ano | findstr :9092
```

If port `9092` is listening, Kafka is up and ready.

---

## Topic Management

### Create a Topic

```bash
bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic user-topic
```

### List All Topics

```bash
bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
```

---

## Testing via Console

### Produce Messages (Console)

```bash
bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic user-topic
```

Type messages and press `Enter` to send each one.

### Consume Messages (Console)

```bash
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic user-topic --from-beginning
```

`--from-beginning` reads all messages from the start, not just new ones.

---

## Spring Boot Configuration

### deliveryboy — Producer (`application.properties`)

```properties
spring.application.name=deliveryboy

spring.kafka.producer.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
```

### enduser — Consumer (`application.properties`)

```properties
spring.application.name=enduser
server.port=8081

spring.kafka.consumer.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=group-1
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
```

---

## Project Flow

```
Postman
  │
  │  HTTP POST /send  (with message body)
  ▼
deliveryboy (Spring Boot - Producer)
  │
  │  Publishes message to Kafka topic: user-topic
  ▼
Kafka Broker (localhost:9092)
  │
  │  Delivers message to consumers in group-1
  ▼
enduser (Spring Boot - Consumer, port 8081)
  │
  │  @KafkaListener reads and processes the message
  ▼
Message Processed ✅
```

---

## Startup Order

Always follow this order to avoid connection errors:

```
1. Start Kafka broker
2. Start enduser (consumer) app
3. Start deliveryboy (producer) app
4. Send requests via Postman
```

> Starting the consumer before the producer ensures no messages are missed.

---

## Troubleshooting

| Error | Cause | Fix |
|---|---|---|
| `Connection to node -1 could not be established` | Kafka broker not running | Run `kafka-server-start.bat` first |
| `Rebootstrapping with localhost:9092` | Broker unreachable | Check `netstat -ano \| findstr :9092` |
| Consumer not receiving messages | Wrong topic name | Verify topic name matches in producer and consumer |
| `Storage already formatted` | Running format again | Skip Step 3 — already done |
| Port 9092 not listening | Kafka crashed or not started | Restart the broker |

---

## Quick Reference

| Task | Command |
|---|---|
| Generate UUID | `bin\windows\kafka-storage.bat random-uuid` |
| Format storage | `bin\windows\kafka-storage.bat format --standalone -t <UUID> -c config\server.properties` |
| Start Kafka | `bin\windows\kafka-server-start.bat config\server.properties` |
| Check port | `netstat -ano \| findstr :9092` |
| Create topic | `bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic user-topic` |
| Console producer | `bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic user-topic` |
| Console consumer | `bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic user-topic --from-beginning` |
