/*
 * ADOBE CONFIDENTIAL. Copyright 2016 Adobe Systems Incorporated. All Rights Reserved. NOTICE: All information contained
 * herein is, and remains the property of Adobe Systems Incorporated and its suppliers, if any. The intellectual and
 * technical concepts contained herein are proprietary to Adobe Systems Incorporated and its suppliers and are protected
 * by all applicable intellectual property laws, including trade secret and copyright law. Dissemination of this
 * information or reproduction of this material is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */

package com.adobe.ids.consumer;

import com.adobe.ids.settings.ConsumerSettings;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import java.util.Collections;
import java.util.Map;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
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
public class SimpleConsumer implements Runnable {

  private final ConsumerSettings consumerSettings;
  private final Map<String, String> serdeConfig;
  private final Serde<String> keyGenericAvroSerde = Serdes.String();
  private final Serde<GenericRecord> valueGenericAvroSerde = new GenericAvroSerde();

  private static final Logger logger = LoggerFactory.getLogger(SimpleConsumer.class);

  @Autowired
  public SimpleConsumer(ConsumerSettings consumerSettings) {
    this.consumerSettings = consumerSettings;
    this.serdeConfig = Collections.singletonMap("schema.registry.url", consumerSettings.getSchemaRegistryURL());
  }

  @Override
  public void run() {
    keyGenericAvroSerde.configure(serdeConfig, true);
    valueGenericAvroSerde.configure(serdeConfig, false); 
    Consumer<String, GenericRecord> consumer = 
      new KafkaConsumer<String, GenericRecord>(consumerSettings.getConsumerProperties(), 
        keyGenericAvroSerde.deserializer(), valueGenericAvroSerde.deserializer());
    consumer.subscribe(Collections.singletonList(consumerSettings.getTopicName()));
    /*
    Consume the records in an infinite loop
     */
    while (true) {
      ConsumerRecords<String, GenericRecord> records = consumer.poll(1);
       for (ConsumerRecord<String, GenericRecord> record : records) {
         logger.debug("Consumed offset = " + record.offset() +
                 " key = " + record.key() +
                 " value = " + record.value());
       }
    }

  }

}
