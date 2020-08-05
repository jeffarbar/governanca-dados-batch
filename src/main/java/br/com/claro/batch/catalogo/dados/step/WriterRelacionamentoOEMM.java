package br.com.claro.batch.catalogo.dados.step;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.claro.batch.catalogo.dados.model.CatalogoDadosModel;
import br.com.claro.batch.catalogo.dados.repository.CatalogoDadosRepository;

@Component
public class WriterRelacionamentoOEMM implements ItemWriter<CatalogoDadosModel> {

	private static final Logger logger = LogManager.getLogger(WriterRelacionamentoOEMM.class);
	
	@Autowired
	private CatalogoDadosRepository catalogoDadosRepository;
	
	@Override
	public void write(List<? extends CatalogoDadosModel> listaCatalogoDadosModel) throws Exception {
     
		listaCatalogoDadosModel.forEach( catalogoDadosModel ->{
			try {
				
				catalogoDadosRepository.save(catalogoDadosModel);
				logger.info("CatalogoDadosModel: " + catalogoDadosModel + " Atualizado");
			}catch (Exception e) {
				logger.error("Erro ao escrever atualização do relacionamento no elasticsearch " + e);
			}
		} );	
    }
}
