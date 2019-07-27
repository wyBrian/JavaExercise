# KAFKA CLUSTER

```$xslt
kafka-topics --zookeeper 127.0.0.1:12181 --create --topic twitter_tweets --partitions 6 --replication-factor 2

kafka-topics --zookeeper 127.0.0.1:12181 twitter_tweets --describe

kafka-console-consumer --bootstrap-server 127.0.0.1:19093 --topic twitter-tweets
```