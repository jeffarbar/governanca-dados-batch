package br.com.claro.batch.catalogo.dados.repository;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import br.com.claro.batch.catalogo.dados.model.CatalogoDadosModel;


public interface CatalogoDadosRepository extends ElasticsearchRepository <CatalogoDadosModel, String>{

	
	
}
