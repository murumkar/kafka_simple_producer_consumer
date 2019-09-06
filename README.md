# IDS Kafka simple Producer and Consumer Application

This is a simple spring boot application that uses kafka Java producer/consumer API to interact with brokers directly. All it does is ingest messages in Avro format in one thread and consume same messages in another thread. It's integrated with [confluent monitoring interceptors](https://docs.confluent.io/current/control-center/installation/clients.html) that helps us track it via [stream monitoring](https://docs.confluent.io/current/control-center/monitoring.html#controlcenter-userguide-monitoring) feature available in C3. Idea behind this tool is to identify any issues with the cluster in a proactive manner, before customers see noticeable performance degradation.

## Steps to run the application
1. Create a topic called "canary_topic" (if you choose to call the topic something else, you'll have to override this parameter at run time.
1. Register following Avro schema. Messages will be posted to the topic in this format.
    ```

    {  
       "schema":"{"   type":"record",
       "name":"test",
       "fields":[  
          {  
             "name":"name",
             "type":"string"
          },
          {  
             "name":"dept",
             "type":"int"
          },
          {  
             "name":"salary",
             "type":"float"
          }
       ]
    }"
    }

    ```
1. Run the application in the background from a client node. Notice that we are making use of spring boot profiles.

   * Unsecure Clusters: We have right properties (monitored brokers, c3 brokers, schema registry URL) baked into the profiles for Dev, Stage and Production environments already.
    ```

    nohup java -Dspring.profiles.active=dev -jar ids_kafka_simple_producer_consumer-1.0.jar &

    ```
   * Secure Clusters: We need to override properties (monitored brokers, schema registry URL) from command line. Also, we should send Kerberos credentials for the principal who has access to ingest/consume from canary topic. Finally, we also need to send any valid certificate to Schema Registry (since anyone can read schemas), so a connection can be established. Following example sends ```nex37045``` principal to brokers and ```appuser1``` certificate to schema registry.
    ```

    nohup java -Djava.security.auth.login.config=/home/nex37045/canary/kafka_client_jaas.properties -Djavax.net.ssl.keyStore=/user/nex37045/canary/appuser1.corp.adobe.com.jks -Djavax.net.ssl.keyStorePassword=changeit  -Dspring.profiles.active=dev -Dkafka.bootstrap.server.config=or1010051053014.corp.adobe.com:9093,or1010051053015.corp.adobe.com:9093,or1010051053016.corp.adobe.com:9093 -Dkafka.schema.registry.url=https://or1010051053018.corp.adobe.com:18091 -jar /home/nex37045/canary/ids_kafka_simple_producer_consumer-1.0.jar &


    ```
1. Look at the logs. By default, log file is called `canaryApplication.log` and is stored in ```/tmp``` directory. You can override this from command line. Also, application will rotate log file when it reaches 10MB in size and keeps only last 10 logs on the disk.

## Options that are useful 
This application provides number of parameters that can be overridden either on command line or in a configuration file.
   * logging.level.com.adobe.ids 
      * Useful values: INFO, DEBUG
      * Default: INFO
      * Notes: DEBUG level prints producer and consumer related messages in the log. This could fill the logs up very quickly and therefore should be used sparinly.
   * log.file.path
      * Default: /tmp/canaryApplication.log
      * Notes: If you want to keep the logs in a different directory, specify absolute path. Eg: /tmp/canaryApplication.log
   * kafka.topic.name
      * Default: canary_topic
   * kafka.producer.throttle.enable
      * Default: TRUE
      * Notes: This parameter is used to throttle ingestion rate. Set it to FALSE, if we want to check the performance of the cluster.
   * kafka.producer.sleep.interval.ms
      * Default: 10
      * Notes: This parameter is useful only when `kafka.producer.throttle.enable` is set to TRUE. It's used to specify sleep interval for producer thread and effectively controls message ingestion rate.
 
Please refer to application.properties files for additional parameters that can be overridden, either on command line or by passing them in a configuration file.

Example of over-riding some parameters from command line:
```

java -Dspring.profiles.active=dev -jar /tmp/ids_kafka_simple_producer_consumer-1.0.jar --kafka.producer.throttle.enable=true  --logging.level.com.adobe.ids=debug --kafka.producer.sleep.interval.ms=100

```
