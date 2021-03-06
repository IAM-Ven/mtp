package com.vedri.mtp.processor;

import com.vedri.mtp.core.CoreProperties;
import com.vedri.mtp.processor.support.spark.CoreSparkProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {
		"com.vedri.mtp.core.country",
		"com.vedri.mtp.core.transaction",
		"com.vedri.mtp.core.rate",
		"com.vedri.mtp.core.support.serializer",
		"com.vedri.mtp.processor.streaming",
		"com.vedri.mtp.processor.transaction"
})
@Configuration
@EnableConfigurationProperties({ ProcessorProperties.class })
public class ProcessorConfig {

	@Autowired
	private ProcessorProperties processorProperties;

	@Bean
	public CoreProperties.Cluster clusterProperties() {
		return processorProperties.getCluster();
	}

	@Bean
	public CoreProperties.Akka akkaProperties() {
		return processorProperties.getAkka();
	}

	@Bean
	public CoreProperties.Cassandra cassandraProperties() {
		return processorProperties.getCassandra();
	}

	@Bean
	public CoreSparkProperties.Spark spark() {
		return processorProperties.getSpark();
	}
}
