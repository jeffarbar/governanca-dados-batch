package br.com.claro.batch.catalogo.dados.repository;


import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import br.com.claro.batch.catalogo.dados.model.CatalogoDadosModel;

@Repository
public interface CatalogoDadosRepository extends ElasticsearchRepository <CatalogoDadosModel, String>{

	List<CatalogoDadosModel> findByName(String name);
	
}
