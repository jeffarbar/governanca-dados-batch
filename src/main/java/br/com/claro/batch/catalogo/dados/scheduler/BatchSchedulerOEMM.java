package br.com.claro.batch.catalogo.dados.scheduler;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import br.com.claro.batch.catalogo.dados.model.CatalogoDadosModel;
import br.com.claro.batch.catalogo.dados.policy.ParseCSVSkipperPolicy;
import br.com.claro.batch.catalogo.dados.step.ProcessorOEMM;
import br.com.claro.batch.catalogo.dados.step.ReaderOEMM;
import br.com.claro.batch.catalogo.dados.step.WriterOEMM;
import br.com.claro.batch.catalogo.dados.util.DataUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@EnableScheduling
@Configuration
public class BatchSchedulerOEMM {
    
	private static final Logger logger = LogManager.getLogger(BatchSchedulerOEMM.class);
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;
	
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SimpleJobLauncher jobLauncher;
    
    @Autowired
    private ReaderOEMM reader;
    
    @Autowired
    private WriterOEMM writer;
    
    @Autowired
    private ProcessorOEMM processor;
    
	@Value("${csv.dir.processado}")
    private String csvDirProcessado;
	
	@Value("${csv.dir.erro}")
    private String csvDirErro;
    
	@Value("${csv.arquivo}")
    private String arquivoCSV;
	
	@Value("${batch.quantidade.lote}")
	private int qtdeLote;
    
	@Scheduled(cron = "${scheduling.job.cron}")	
	public void inicia() throws Exception {
		
		if( Files.exists(Paths.get(arquivoCSV)) ) {
		
			logger.info("Job inicial :"+ new Date());
			
			JobParameters param = new JobParametersBuilder().addString("JobID",
			String.valueOf(System.currentTimeMillis())).toJobParameters();
			JobExecution execution = jobLauncher.run(job(), param);
			
			logger.info("Job finalizado status: " + execution.getStatus());
			
			if( execution.getStatus() == BatchStatus.COMPLETED ) {
				Files.move(Paths.get(arquivoCSV), Paths.get(csvDirProcessado, geraNomeSaida() ), StandardCopyOption.REPLACE_EXISTING);
			}else {
				Files.move(Paths.get(arquivoCSV), Paths.get(csvDirErro, geraNomeSaida() ), StandardCopyOption.REPLACE_EXISTING);
			}
		}else {
			logger.warn("Não tem o arquivo ["+Paths.get(arquivoCSV)+"]");
		}
	}
	
	private String geraNomeSaida() {
		return "arquivo_" + DataUtil.getDataHodaAtual()+".csv";
	}
	
    public Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .preventRestart()
                .build();
    }

    @Bean
    public SkipPolicy skipParseCSVSkipper() {
        return new ParseCSVSkipperPolicy();
    }
  
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<CatalogoDadosModel, CatalogoDadosModel> chunk(qtdeLote)
                .reader(reader.reader()).faultTolerant().skipPolicy(skipParseCSVSkipper())
                .processor(processor)
                .writer(writer)
                .build();
    }
    
 
}