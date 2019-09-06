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
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Adobe Systems Inc
 */
@Component
public class ConsumerSettings {

  private final Properties consumerProperties = new Properties();

  @Value("${kafka.bootstrap.server.config}") private String bootStrapServers;
  @Value("${kafka.c3.bootstrap.server.config}") private String c3bootStrapServers;
  @Value("${kafka.key.deserializer.class.config}") private String keyDeserializer;
  @Value("${kafka.value.deserializer.class.config}") private String valueDeserializer;
  @Value("${kafka.schema.registry.url}") private String schemaRegistryURL;
  @Value("${kafka.topic.name}") private String topicName;
  @Value("${kafka.consumer.group.id.config}") private String groupId;
  @Value("${kafka.consumer.interceptor.class.config}") private String interceptorConfig;
  @Value("${kafka.security.protocol}") private String securityProtocol;

  @PostConstruct
  void init() {
    consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
    consumerProperties.put("confluent.monitoring.interceptor.bootstrap.servers", c3bootStrapServers);
    consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
    consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
    consumerProperties.put("schema.registry.url", schemaRegistryURL);
    consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    consumerProperties.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptorConfig);
    consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    consumerProperties.put("security.protocol",securityProtocol);
    consumerProperties.put("sasl.kerberos.service.name", "kafka");
  }

  public String getBootStrapServers() {
    return bootStrapServers;
  }

  public void setBootStrapServers(String bootStrapServers) {
    this.bootStrapServers = bootStrapServers;
  }

  public String getc3BootStrapServers() {
    return c3bootStrapServers;
  }

  public void setc3BootStrapServers(String c3bootStrapServers) {
    this.c3bootStrapServers = c3bootStrapServers;
  }

  public String getKeyDeserializer() {
    return keyDeserializer;
  }

  public void setKeyDeserializer(String keyDeserializer) {
    this.keyDeserializer = keyDeserializer;
  }

  public String getValueDeserializer() {
    return valueDeserializer;
  }

  public void setValueDeserializer(String valueDeserializer) {
    this.valueDeserializer = valueDeserializer;
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

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getInterceptorConfig() {
    return interceptorConfig;
  }

  public void setInterceptorConfig(String interceptorConfig) {
    this.interceptorConfig = interceptorConfig;
  }

  public Properties getConsumerProperties() {
    return consumerProperties;
  }

  public String getSecurityProtocol() {
    return securityProtocol;
  }

  public void setSecurityProtocol(String securityProtocol) {
    this.securityProtocol = securityProtocol;
  }

}
