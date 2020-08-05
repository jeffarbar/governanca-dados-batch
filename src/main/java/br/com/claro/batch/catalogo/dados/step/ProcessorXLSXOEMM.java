package br.com.claro.batch.catalogo.dados.step;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import br.com.claro.batch.catalogo.dados.dto.CatalogoDadosDto;
import br.com.claro.batch.catalogo.dados.model.CatalogoDadosModel;


@Component
public class ProcessorXLSXOEMM implements ItemProcessor<CatalogoDadosDto, CatalogoDadosModel> {

	private static final Logger logger = LogManager.getLogger(ProcessorXLSXOEMM.class);
	

	@Override
	public CatalogoDadosModel process(CatalogoDadosDto item) throws Exception {
		
		CatalogoDadosModel catalogoDadosModel = new CatalogoDadosModel(item);
		logger.info("Inserindo catalogoDadosModel: " + catalogoDadosModel);
		return catalogoDadosModel;
	}

}
