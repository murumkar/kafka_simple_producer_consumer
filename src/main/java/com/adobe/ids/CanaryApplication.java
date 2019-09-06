/*
 * ADOBE CONFIDENTIAL. Copyright 2016 Adobe Systems Incorporated. All Rights Reserved. NOTICE: All information contained
 * herein is, and remains the property of Adobe Systems Incorporated and its suppliers, if any. The intellectual and
 * technical concepts contained herein are proprietary to Adobe Systems Incorporated and its suppliers and are protected
 * by all applicable intellectual property laws, including trade secret and copyright law. Dissemination of this
 * information or reproduction of this material is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */

package com.adobe.ids;

import com.adobe.ids.consumer.SimpleConsumer;
import com.adobe.ids.producer.SimpleProducer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.joda.ser.IntervalSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;



/**
 * @author Adobe Systems Inc
 */
@SpringBootApplication
public class CanaryApplication {

  public static void main(String... args) {
    ApplicationContext applicationContext = new SpringApplicationBuilder(CanaryApplication.class)
      .web(false)
      .bannerMode(Banner.Mode.CONSOLE)
      .run(args);
    SimpleConsumer simpleConsumer = applicationContext.getBean(SimpleConsumer.class);
    Thread consumerThread = new Thread(simpleConsumer);
    consumerThread.start();
    SimpleProducer simpleProducer = applicationContext.getBean(SimpleProducer.class);
    Thread producerThread = new Thread(simpleProducer);
    producerThread.start();
  }

  @Bean
  public DispatcherServlet dispatcherServlet() {
    DispatcherServlet dispatcherServlet = new DispatcherServlet();
    dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    return dispatcherServlet;
  }

  @Autowired
  public void configureJodaJacksonConverter(MappingJackson2HttpMessageConverter messageConverter) {
    messageConverter.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    messageConverter.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    messageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    messageConverter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    //messageConverter.getObjectMapper().registerModule(new JSR310Module());
    messageConverter.getObjectMapper().registerModule(getJodaModule());
  }

  private JodaModule getJodaModule() {
    JodaModule jodaModule = new JodaModule();
    jodaModule.addSerializer(new IntervalSerializer());
    return jodaModule;
  }

}
