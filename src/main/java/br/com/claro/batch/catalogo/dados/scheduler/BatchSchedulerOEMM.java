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

import br.com.claro.batch.catalogo.dados.dto.CatalogoDadosDto;
import br.com.claro.batch.catalogo.dados.dto.CatalogoDadosRelacionamentoDto;
import br.com.claro.batch.catalogo.dados.model.CatalogoDadosModel;
import br.com.claro.batch.catalogo.dados.policy.ParseCSVSkipperPolicy;
import br.com.claro.batch.catalogo.dados.step.ProcessorCSVOEMM;
import br.com.claro.batch.catalogo.dados.step.ProcessorRelacionamentoXLSXOEMM;
import br.com.claro.batch.catalogo.dados.step.ProcessorXLSXOEMM;
import br.com.claro.batch.catalogo.dados.step.ReaderCSVOEMM;
import br.com.claro.batch.catalogo.dados.step.ReaderRelacionamentoXLSXOEMM;
import br.com.claro.batch.catalogo.dados.step.ReaderXLSXOEMM;
import br.com.claro.batch.catalogo.dados.step.WriterRelacionamentoOEMM;
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
    private ReaderXLSXOEMM readerXLSX;
    
    @Autowired
    private ReaderRelacionamentoXLSXOEMM readerRelacionamentoXLSXOEMM;
    
    @Autowired
    private ProcessorXLSXOEMM processorXLSX;
    
    @Autowired
    private ProcessorRelacionamentoXLSXOEMM processorRelacionamentoXLSXOEMM;
    
    @Autowired
    private ReaderCSVOEMM readerCSV;
    
 
    @Autowired
    private ProcessorCSVOEMM processorCSV;

    @Autowired
    private WriterOEMM writer;
    
    @Autowired
    private WriterRelacionamentoOEMM writerRelacionamento;
    
	@Value("${dir.processado}")
    private String dirProcessado;
	
	@Value("${dir.erro}")
    private String dirErro;
    
	@Value("${csv.arquivo}")
    private String arquivoCSV;

	@Value("${xlsx.arquivo}")
    private String arquivoXLSX;

	@Value("${xlsx.arquivo.relacionamento}")
    private String arquivoRelacionamentoXLSX;
	
	@Value("${batch.quantidade.lote}")
	private int qtdeLote;
    
	@Scheduled(cron = "${scheduling.xlsx.job.cron}")	
	public void iniciaXLSX() throws Exception {
		
		if( Files.exists(Paths.get(arquivoXLSX)) ) {
		
			logger.info("Job inicial :"+ new Date());
			
			JobParameters param = new JobParametersBuilder().addString("JobID",
			String.valueOf(System.currentTimeMillis())).toJobParameters();
			JobExecution execution = jobLauncher.run(jobXLSX(), param);
			
			logger.info("Job finalizado status: " + execution.getStatus());
			
			if( execution.getStatus() == BatchStatus.COMPLETED ) {
				Files.move(Paths.get(arquivoXLSX), Paths.get(dirProcessado, "xlsx_" + geraNomeSaidaXLSX() ), StandardCopyOption.REPLACE_EXISTING);
				iniciaRelacionamentoXLSX();
			}else {
				Files.move(Paths.get(arquivoXLSX), Paths.get(dirErro, "xlsx_" + geraNomeSaidaXLSX() ), StandardCopyOption.REPLACE_EXISTING);
			}
		}else {
			logger.warn("Não tem o arquivo ["+Paths.get(arquivoXLSX)+"]");
			iniciaRelacionamentoXLSX();
		}
	}
	
	private void iniciaRelacionamentoXLSX() throws Exception {
		
		if( Files.exists(Paths.get(arquivoRelacionamentoXLSX)) ) {
		
			logger.info("Job inicial relacionamento :"+ new Date());
			
			JobParameters param = new JobParametersBuilder().addString("JobID",
			String.valueOf(System.currentTimeMillis())).toJobParameters();
			JobExecution execution = jobLauncher.run(jobRelacionamentoXLSX(), param);
			
			logger.info("Job finalizado relacionamento status: " + execution.getStatus());
			
			if( execution.getStatus() == BatchStatus.COMPLETED ) {
				Files.move(Paths.get(arquivoRelacionamentoXLSX), Paths.get(dirProcessado, "xlsxRelacionamento_" + geraNomeSaidaXLSX() ), StandardCopyOption.REPLACE_EXISTING);
			}else {
				Files.move(Paths.get(arquivoRelacionamentoXLSX), Paths.get(dirErro, "xlsxRelacionamento_" + geraNomeSaidaXLSX() ), StandardCopyOption.REPLACE_EXISTING);
			}
		}else {
			logger.warn("Não tem o arquivo relacionamento ["+Paths.get(arquivoRelacionamentoXLSX)+"]");
		}
	}
	
	@Scheduled(cron = "${scheduling.csv.job.cron}")	
	public void iniciaCSV() throws Exception {
		
		if( Files.exists(Paths.get(arquivoCSV)) ) {
		
			logger.info("Job inicial :"+ new Date());
			
			JobParameters param = new JobParametersBuilder().addString("JobID",
			String.valueOf(System.currentTimeMillis())).toJobParameters();
			JobExecution execution = jobLauncher.run(jobCSV(), param);
			
			logger.info("Job finalizado status: " + execution.getStatus());
			
			if( execution.getStatus() == BatchStatus.COMPLETED ) {
				Files.move(Paths.get(arquivoCSV), Paths.get(dirProcessado, "csv_" + geraNomeSaidaCSV() ), StandardCopyOption.REPLACE_EXISTING);
			}else {
				Files.move(Paths.get(arquivoCSV), Paths.get(dirErro, "csv_" + geraNomeSaidaCSV() ), StandardCopyOption.REPLACE_EXISTING);
			}
		}else {
			logger.warn("Não tem o arquivo ["+Paths.get(arquivoCSV)+"]");
		}
	}
	
	
	
	private String geraNomeSaidaCSV() {
		return "arquivo_" + DataUtil.getDataHodaAtual()+".csv";
	}
	
	private String geraNomeSaidaXLSX() {
		return "arquivo_" + DataUtil.getDataHodaAtual()+".xlsx";
	}
	
    public Job jobCSV() {
        return jobBuilderFactory.get("jobCSV")
                .incrementer(new RunIdIncrementer())
                .flow(stepCSV())
                .end()
                .preventRestart()
                .build();
    }
    
    public Job jobXLSX() {
        return jobBuilderFactory.get("jobXLSX")
                .incrementer(new RunIdIncrementer())
                .flow(stepXLSX())
                .end()
                .preventRestart()
                .build();
    }
    
    public Job jobRelacionamentoXLSX() {
        return jobBuilderFactory.get("jobRelacionamentoXLSX")
                .incrementer(new RunIdIncrementer())
                .flow(stepRelacionamentoXLSX())
                .end()
                .preventRestart()
                .build();
    }

    @Bean
    public SkipPolicy skipParseCSVSkipper() {
        return new ParseCSVSkipperPolicy();
    }
  
    public Step stepXLSX() {
        return stepBuilderFactory.get("stepXLSX")
                .<CatalogoDadosDto, CatalogoDadosModel> chunk(qtdeLote)
                .reader(readerXLSX.reader())
                .processor(processorXLSX)
                .writer(writer)
                .build();
    }
    
    public Step stepRelacionamentoXLSX() {
        return stepBuilderFactory.get("stepRelacionamentoXLSX")
                .<CatalogoDadosRelacionamentoDto, CatalogoDadosModel> chunk(qtdeLote)
                .reader(readerRelacionamentoXLSXOEMM.reader())
                .processor(processorRelacionamentoXLSXOEMM)
                .writer(writerRelacionamento)
                .build();
    }
    
    
    public Step stepCSV() {
        return stepBuilderFactory.get("stepCSV")
                .<CatalogoDadosModel, CatalogoDadosModel> chunk(qtdeLote)
                .reader(readerCSV.reader()).faultTolerant().skipPolicy(skipParseCSVSkipper())
                .processor(processorCSV)
                .writer(writer)
                .build();
    }
    
 
}