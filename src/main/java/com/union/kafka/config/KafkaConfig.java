package com.union.kafka.config;

import com.union.kafka.consumer.UDKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kejw on 2017/9/21.
 */
@Configuration
public class KafkaConfig {

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;
    @Value("${session.timeout.ms}")
    private Integer sessionTimeoutMs;
    @Value("${enable.auto.commit}")
    private boolean enableAutoCommit;
    @Value("${auto.commit.interval.ms}")
    private Integer autoCommitIntervalMs;
    @Value("${auto.offset.reset}")
    private String autoOffsetReset;
    @Value("${group.id}")
    private String groupId;
    @Value("${kafka.concurrency}")
    private int concurrency;
    @Value("${max.partition.fetch.bytes}")
    private int maxpartitionfetchbytes;
    @Value("${heartbeat.interval.ms}")
    private int heartbeatintervalms;
    @Value("${request.timeout.ms}")
    private int requesttimeoutms;


    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setPollTimeout(sessionTimeoutMs);
        //factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.TIME);
        //factory.getContainerProperties().setAckTime(5000);
        //factory.setBatchListener(true);
        //factory.getContainerProperties().setAckCount(500);
        //factory.getContainerProperties().setIdleEventInterval(5000L);
        //factory.getContainerProperties().setListenerTaskExecutor();
        //factory.getContainerProperties().setConsumerTaskExecutor();
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMs);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxpartitionfetchbytes);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartbeatintervalms);
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, requesttimeoutms);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public UDKafkaConsumer uDKafkaConsumer() {
        return new UDKafkaConsumer();
    }
}
