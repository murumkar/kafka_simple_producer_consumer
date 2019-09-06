/*
 * ADOBE CONFIDENTIAL. Copyright 2016 Adobe Systems Incorporated. All Rights Reserved. NOTICE: All information contained
 * herein is, and remains the property of Adobe Systems Incorporated and its suppliers, if any. The intellectual and
 * technical concepts contained herein are proprietary to Adobe Systems Incorporated and its suppliers and are protected
 * by all applicable intellectual property laws, including trade secret and copyright law. Dissemination of this
 * information or reproduction of this material is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */

package com.adobe.ids.producer;

import com.adobe.ids.settings.ProducerSettings;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import java.util.HashMap;
import java.util.Map;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adobe Systems Inc
 */
@Component
public class SimpleProducer implements Runnable {

  private final ProducerSettings producerSettings;
  private final Map<String, String> serdeConfig;
  private final Serde<String> keyGenericAvroSerde = Serdes.String();
  private final Serde<GenericRecord> valueGenericAvroSerde = new GenericAvroSerde();

  private static final Logger logger = LoggerFactory.getLogger(SimpleProducer.class);

  @Autowired
  public SimpleProducer(ProducerSettings producerSettings) {
    this.producerSettings = producerSettings;
    HashMap<String, String> producerProperties = new HashMap<String, String>();
    producerProperties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, producerSettings.getSchemaRegistryURL());
    producerProperties.put(AbstractKafkaAvroSerDeConfig.AUTO_REGISTER_SCHEMAS, "false");
    this.serdeConfig = producerProperties;
  }

  @Override
  public void run() {
    try {

      keyGenericAvroSerde.configure(serdeConfig, true);
      valueGenericAvroSerde.configure(serdeConfig, false);

      Producer<String, GenericRecord> producer = 
        new KafkaProducer<String, GenericRecord>(producerSettings.getProducerProperties(), 
          keyGenericAvroSerde.serializer(), valueGenericAvroSerde.serializer());
      TestCallback callback = new TestCallback();

      int keyCount = 1;
      String employeeSchema = "{\"type\":\"record\",\"name\":\"canary_topic\",\"fields\":"
        + "[{\"name\":\"name\",\"type\":\"string\"}, {\"name\":\"dept\",\"type\":\"int\"},"
        + "{\"name\":\"salary\",\"type\":\"float\"}]}";
      Schema.Parser parser = new Schema.Parser();
      Schema schema = parser.parse(employeeSchema);

      /*
      Produce Avro records in an infinite loop
       */
      while (true) {
        GenericRecord avroRecord = new GenericData.Record(schema);
        avroRecord.put("name", "name" + keyCount);
        avroRecord.put("dept",  keyCount);
        Float salary = new Float(keyCount + keyCount * 1000);
        avroRecord.put("salary",  salary);
        ProducerRecord<String, GenericRecord> record = new ProducerRecord<>(producerSettings.getTopicName(), 
          "key-" + String.valueOf(keyCount), avroRecord);
        producer.send(record, callback);
        if (producerSettings.isThrottleEnabled()) {
          logger.debug("Throttle Enabled, sleeping for " + producerSettings.getSleepInterval() + "ms");
          Thread.sleep(producerSettings.getSleepInterval());
        }
        keyCount++;
        keyCount = keyCount % producerSettings.getTotalPartitions();

      } 
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private static class TestCallback implements Callback {
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
      if (e != null) {
        logger.error("Error while producing message to topic :" + recordMetadata);
        e.printStackTrace();
      } else {
        String message = String.format("sent message to topic:%s partition:%s  offset:%s", recordMetadata.topic(),
          recordMetadata.partition(), recordMetadata.offset());
        logger.debug(message);
      }
    }
  }

}
