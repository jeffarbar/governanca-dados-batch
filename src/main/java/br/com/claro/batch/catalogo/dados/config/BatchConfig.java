package br.com.claro.batch.catalogo.dados.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Bean
	public ResourcelessTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}

	@Bean
	public MapJobRepositoryFactoryBean mapJobRepositoryFactory(
			ResourcelessTransactionManager txManager) throws Exception {
		
		MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean(
				txManager);
		factory.afterPropertiesSet();
		return factory;
	}
	
    @Bean
	public JobRepository jobRepository(MapJobRepositoryFactoryBean factory)
			throws Exception {
		return (JobRepository) factory.getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		return launcher;
	}

}
