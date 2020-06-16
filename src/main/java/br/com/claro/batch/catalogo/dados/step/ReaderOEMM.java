package br.com.claro.batch.catalogo.dados.step;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import br.com.claro.batch.catalogo.dados.model.CatalogoDadosModel;


@Component
public class ReaderOEMM {

	private static final Logger logger = LogManager.getLogger(ReaderOEMM.class);
	
	@Value("${csv.arquivo}")
    private String arquivoCSV;	
	
	@Value("${csv.delimitador}")
    private String delimitadorCSV;

    public FlatFileItemReader<CatalogoDadosModel> reader(){
		
    	FlatFileItemReader<CatalogoDadosModel> itemReader = new FlatFileItemReader<CatalogoDadosModel>();
    	
    	try {
    	
	        itemReader.setLineMapper(lineMapper());
	        itemReader.setLinesToSkip(1);
	        itemReader.setResource(new FileSystemResource(arquivoCSV));
        
    	}catch (Exception e) {
    		logger.error("Erro ao ler o registro do arquivo " + e);
		}
        
        return itemReader;
        
    	
	}
		
	
    public LineMapper<CatalogoDadosModel> lineMapper() throws Exception {
 
    	DefaultLineMapper<CatalogoDadosModel> lineMapper = new DefaultLineMapper<CatalogoDadosModel>();
    	
	    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	    BeanWrapperFieldSetMapper<CatalogoDadosModel> fieldSetMapper = new BeanWrapperFieldSetMapper<CatalogoDadosModel>();
	    lineTokenizer.setNames(new String[]{"parentPath","name","definition","status","type","abbreviation","alternativeAbbreviation","documentation","dataType","state","dataOwner","dataSteward",
	        		"dominioNegocio","assunto","origem","dominioDados","subDominioDados","confidencialidade","grupoDominioDados","importancia","linkDocuments","producao"});
	    lineTokenizer.setIncludedFields(new int[]{1,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23});
	    lineTokenizer.setDelimiter(delimitadorCSV);
	        
	    fieldSetMapper.setTargetType(CatalogoDadosModel.class);
	    lineMapper.setLineTokenizer(lineTokenizer);
	    lineMapper.setFieldSetMapper(fieldSetMapper);
        
    	return lineMapper;
    }
	
}
