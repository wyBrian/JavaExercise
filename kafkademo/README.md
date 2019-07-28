# KAFKA CLUSTER

```
kafka-topics --zookeeper 127.0.0.1:12181 --create --topic twitter_tweets --partitions 3 --replication-factor 2

kafka-topics --zookeeper 127.0.0.1:12181 twitter_tweets --describe

kafka-console-consumer --bootstrap-server 127.0.0.1:19093 --topic twitter_tweets
```

```
kafka-topics --zookeeper 127.0.0.1:12181 --create --topic another_topic --partitions 1 --replication-factor 1

kafka-console-producer --broker-list 127.0.0.1:19092 --topic another_topic

kafka-console-consumer --bootstrap-server 127.0.0.1:19092 --topic another_topic
```