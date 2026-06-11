correct commands for Kafka 4.x on Windows:->
cd C:\kafka_2.13-4.3.0
#The UUID identifies your Kafka Cluster as a whole. Multiple brokers can join the same cluster by sharing the same UUID.
bin\windows\kafka-storage.bat random-uuid

#created storage directory with single kafka server(standalone) under above uuid, uses configuration from server.properties.
bin\windows\kafka-storage.bat format --standalone -t <UUID> -c config\server.properties
#start kafka server
bin\windows\kafka-server-start.bat config\server.properties

Check if port 9092 is listening
netstat -ano | findstr :9092

create topic:->
bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic my-topic

produce messages:->
bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic user-topic

consume message:->
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic user-topic --from-beginning

in this project flow is,
postman->deliveryboy->enduser
