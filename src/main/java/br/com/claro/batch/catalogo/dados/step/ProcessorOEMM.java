package br.com.claro.batch.catalogo.dados.step;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import br.com.claro.batch.catalogo.dados.model.CatalogoDadosModel;


@Component
public class ProcessorOEMM implements ItemProcessor<CatalogoDadosModel, CatalogoDadosModel> {

	private static final Logger logger = LogManager.getLogger(ProcessorOEMM.class);
	
	@Override
	 public CatalogoDadosModel process(CatalogoDadosModel catalogoDadosModel) throws Exception {
		logger.info("Inserindo catalogoDadosModel: " + catalogoDadosModel);
		return catalogoDadosModel;
    }

}
