package br.com.claro.batch.catalogo.dados.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
public class ElasticsearchConfig {

	@Value("${elasticsearch.host}")
	private String host;

	@Value("${elasticsearch.port}")
	private int porta;

	@Value("${elasticsearch.cluster.name}")
	private String nomeCluster;
	
	@Value("${elasticsearch.client.transport.sniff}")
	private boolean isSniff;
	
	@Value("${elasticsearch.use.settings}")
	private boolean isUseSettings;
	
	@Bean
    public Client client() throws Exception {
		
		Settings settings = Settings.EMPTY;
		
		if(isUseSettings) {
		
			settings = Settings.builder()
					.put("cluster.name", nomeCluster)
			        .put("client.transport.sniff", isSniff).build();
		}
		
		return new PreBuiltTransportClient(settings)
		        .addTransportAddress(new TransportAddress(InetAddress.getByName(host), porta));
		
	}
 
    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }
}
