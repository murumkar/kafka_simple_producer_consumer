/*
 * ADOBE CONFIDENTIAL. Copyright 2016 Adobe Systems Incorporated. All Rights Reserved. NOTICE: All information contained
 * herein is, and remains the property of Adobe Systems Incorporated and its suppliers, if any. The intellectual and
 * technical concepts contained herein are proprietary to Adobe Systems Incorporated and its suppliers and are protected
 * by all applicable intellectual property laws, including trade secret and copyright law. Dissemination of this
 * information or reproduction of this material is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */
package com.adobe.ids.settings;

import java.util.Properties;
import javax.annotation.PostConstruct;

import com.adobe.ids.producer.SimpleProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;


/**
 * @author Adobe Systems Inc
 */
@Component
public class ProducerSettings {

    private final Properties producerProperties = new Properties();

    @Value("${kafka.bootstrap.server.config}") private String bootStrapServers;
    @Value("${kafka.c3.bootstrap.server.config}") private String c3bootStrapServers;
    @Value("${kafka.key.serializer.class.config}") private String keySerializer;
    @Value("${kafka.value.serializer.class.config}") private String valueSerializer;
    @Value("${kafka.schema.registry.url}") private String schemaRegistryURL;
    @Value("${kafka.topic.name}") private String topicName;
    @Value("${kafka.no.partitions}") private int totalPartitions;
    @Value("${kafka.producer.acks.config}") private String acksConfig;
    @Value("${kafka.producer.batch.size}") private String batchSize;
    @Value("${kafka.producer.linger.ms.config}") private String lingerConfig;
    @Value("${kafka.producer.buffer.memory.size}") private String bufferMemorySize;
    @Value("${kafka.producer.retry.count}") private String retryCount;
    @Value("${kafka.producer.interceptor.class.config}") private String interceptorConfig;
    @Value("${kafka.producer.throttle.enable}") private boolean isThrottleEnabled;
    @Value("${kafka.producer.sleep.interval.ms}") private long sleepInterval;
    @Value("${kafka.security.protocol}") private String securityProtocol;

    @PostConstruct
    void init() {
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        producerProperties.put("confluent.monitoring.interceptor.bootstrap.servers", c3bootStrapServers);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        producerProperties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryURL);
        producerProperties.put("key.converter.schema.registry.url", schemaRegistryURL);
        producerProperties.put("value.converter.schema.registry.url", schemaRegistryURL);
        producerProperties.put(ProducerConfig.ACKS_CONFIG, acksConfig);
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, lingerConfig);
        producerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemorySize);
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, retryCount);
        producerProperties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptorConfig);
        producerProperties.put("security.protocol",securityProtocol);
        producerProperties.put("sasl.kerberos.service.name", "kafka");
    }


    public String getBootStrapServers() {
        return bootStrapServers;
    }

    public void setBootStrapServers(String c3bootStrapServers) {
        this.bootStrapServers = c3bootStrapServers;
    }

    public String getc3BootStrapServers() {
        return c3bootStrapServers;
    }

    public void setc3BootStrapServers(String c3bootStrapServers) {
        this.c3bootStrapServers = c3bootStrapServers;
    }

    public String getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
    }

    public String getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public String getSchemaRegistryURL() {
        return schemaRegistryURL;
    }

    public void setSchemaRegistryURL(String schemaRegistryURL) {
        this.schemaRegistryURL = schemaRegistryURL;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getTotalPartitions() {
        return totalPartitions;
    }

    public void setTotalPartitions(int totalPartitions) {
        this.totalPartitions = totalPartitions;
    }

    public String getAcksConfig() {
        return acksConfig;
    }

    public void setAcksConfig(String acksConfig) {
        this.acksConfig = acksConfig;
    }

    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(String batchSize) {
        this.batchSize = batchSize;
    }

    public String getLingerConfig() {
        return lingerConfig;
    }

    public void setLingerConfig(String lingerConfig) {
        this.lingerConfig = lingerConfig;
    }

    public String getBufferMemorySize() {
        return bufferMemorySize;
    }

    public void setBufferMemorySize(String bufferMemorySize) {
        this.bufferMemorySize = bufferMemorySize;
    }

    public String getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(String retryCount) {
        this.retryCount = retryCount;
    }

    public String getInterceptorConfig() {
        return interceptorConfig;
    }

    public void setInterceptorConfig(String interceptorConfig) {
        this.interceptorConfig = interceptorConfig;
    }

    public Properties getProducerProperties() {
        return producerProperties;
    }

    public boolean isThrottleEnabled() {
        return isThrottleEnabled;
    }

    public void setThrottleEnabled(boolean isThrottleEnabled) {
        this.isThrottleEnabled = isThrottleEnabled;
    }

    public long getSleepInterval() {
        return sleepInterval;
    }

    public void setSleepInterval(long sleepInterval) {
        this.sleepInterval = sleepInterval;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

}
