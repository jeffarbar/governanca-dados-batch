package br.com.claro.batch.catalogo.dados.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

//analogia indexName==banco de dados, type==tabela
@Document(indexName = "governanca", type = "catalogodados")
public class CatalogoDadosModel {

	@Id
	private String id;
	
	private String bancoDado;
	
	private String parentPath; 
	
	private String name; 
	
	private String definition; 
	
	private String status; 
	
	private String type; 
	
	private String abbreviation; 
	
	private String alternativeAbbreviation; 
	
	private String documentation; 
	
	private String dataType; 
	
	private String state; 
	
	private String dataOwner; 
	
	private String dataSteward; 
	
	private String dominioNegocio; 
	
	private String assunto; 
	
	private String origem; 
	
	private String dominioDados; 
	
	private String subDominioDados; 
	
	private String confidencialidade; 
	
	private String grupoDominioDados; 
	
	private String importancia; 
	
	private String linkDocuments; 

	private String producao;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBancoDado() {
		return bancoDado;
	}

	public void setBancoDado(String bancoDado) {
		this.bancoDado = bancoDado;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getAlternativeAbbreviation() {
		return alternativeAbbreviation;
	}

	public void setAlternativeAbbreviation(String alternativeAbbreviation) {
		this.alternativeAbbreviation = alternativeAbbreviation;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDataOwner() {
		return dataOwner;
	}

	public void setDataOwner(String dataOwner) {
		this.dataOwner = dataOwner;
	}

	public String getDataSteward() {
		return dataSteward;
	}

	public void setDataSteward(String dataSteward) {
		this.dataSteward = dataSteward;
	}

	public String getDominioNegocio() {
		return dominioNegocio;
	}

	public void setDominioNegocio(String dominioNegocio) {
		this.dominioNegocio = dominioNegocio;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDominioDados() {
		return dominioDados;
	}

	public void setDominioDados(String dominioDados) {
		this.dominioDados = dominioDados;
	}

	public String getSubDominioDados() {
		return subDominioDados;
	}

	public void setSubDominioDados(String subDominioDados) {
		this.subDominioDados = subDominioDados;
	}

	public String getConfidencialidade() {
		return confidencialidade;
	}

	public void setConfidencialidade(String confidencialidade) {
		this.confidencialidade = confidencialidade;
	}

	public String getGrupoDominioDados() {
		return grupoDominioDados;
	}

	public void setGrupoDominioDados(String grupoDominioDados) {
		this.grupoDominioDados = grupoDominioDados;
	}

	public String getImportancia() {
		return importancia;
	}

	public void setImportancia(String importancia) {
		this.importancia = importancia;
	}

	public String getLinkDocuments() {
		return linkDocuments;
	}

	public void setLinkDocuments(String linkDocuments) {
		this.linkDocuments = linkDocuments;
	}

	public String getProducao() {
		return producao;
	}

	public void setProducao(String producao) {
		this.producao = producao;
	}

	@Override
	public String toString() {
		return "CatalogoDadosModel [bancoDado=" + bancoDado + ", parentPath=" + parentPath + ", name=" + name
				+ ", definition=" + definition + ", status=" + status + ", type=" + type + ", abbreviation="
				+ abbreviation + ", alternativeAbbreviation=" + alternativeAbbreviation + ", documentation="
				+ documentation + ", dataType=" + dataType + ", state=" + state + ", dataOwner=" + dataOwner
				+ ", dataSteward=" + dataSteward + ", dominioNegocio=" + dominioNegocio + ", assunto=" + assunto
				+ ", origem=" + origem + ", dominioDados=" + dominioDados + ", subDominioDados=" + subDominioDados
				+ ", confidencialidade=" + confidencialidade + ", grupoDominioDados=" + grupoDominioDados
				+ ", importancia=" + importancia + ", linkDocuments=" + linkDocuments + ", producao=" + producao + "]";
	} 
	
	
}
