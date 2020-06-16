package br.com.claro.batch.catalogo.dados.policy;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;


public class ParseCSVSkipperPolicy implements SkipPolicy {
    
	private static final Logger logger = LogManager.getLogger(ParseCSVSkipperPolicy.class);
 
    @Override
    public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
        if (exception instanceof FileNotFoundException) {
            return false;
        } else if (exception instanceof FlatFileParseException && skipCount <= 5) {
            FlatFileParseException ffpe = (FlatFileParseException) exception;
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Ocorreu um erro ao processar o "+ ffpe.getLineNumber() + 
            		" linha do arquivo. Abaixo estava a entrada defeituosa. \n");
            errorMessage.append(ffpe.getInput() + "\n");
            logger.error("{}", errorMessage.toString());
            return true;
        } else {
            return false;
        }
    }
 
}