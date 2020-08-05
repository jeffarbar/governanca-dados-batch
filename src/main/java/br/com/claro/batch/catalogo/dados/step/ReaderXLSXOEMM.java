package br.com.claro.batch.catalogo.dados.step;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.batch.item.excel.support.rowset.RowSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import br.com.claro.batch.catalogo.dados.dto.CatalogoDadosDto;

import org.springframework.batch.item.excel.RowMapper;

@Component
public class ReaderXLSXOEMM {
	
	@Value("${xlsx.arquivo}")
    private String arquivoXLSX;
	
	@Bean
	public ItemReader<CatalogoDadosDto> reader() {
		
        PoiItemReader<CatalogoDadosDto> reader = new PoiItemReader<>();
        reader.setResource(new FileSystemResource(arquivoXLSX));
        reader.setLinesToSkip(1);
        reader.setRowMapper(excelRowMapper());
        return reader;
    }
 

    private RowMapper<CatalogoDadosDto> excelRowMapper() {
       return new CatalogoDadosExcelRowMapper();
    }
    
}

class CatalogoDadosExcelRowMapper implements RowMapper<CatalogoDadosDto> {
	 
	private static final Logger logger = LogManager.getLogger(CatalogoDadosExcelRowMapper.class);
	
    public CatalogoDadosDto mapRow(RowSet rowSet) throws Exception {
    	
    	CatalogoDadosDto cd = new CatalogoDadosDto();
    	
    	try {
	    	
	    	if (rowSet == null || rowSet.getCurrentRow() == null) {
	            return cd;
	        }
    		
	    	int tamanhoVetor = rowSet.getCurrentRow().length;
	    	
	    	if(tamanhoVetor > 1)cd.setParentPath(rowSet.getColumnValue(1));
	    	if(tamanhoVetor > 3)cd.setName( rowSet.getColumnValue(3) );
	    	if(tamanhoVetor > 4)cd.setDefinition( rowSet.getColumnValue(4) );
	    	if(tamanhoVetor > 5)cd.setStatus(rowSet.getColumnValue(5) );
	    	if(tamanhoVetor > 6)cd.setType( rowSet.getColumnValue(6)  );
	    	if(tamanhoVetor > 7)cd.setAbbreviation( rowSet.getColumnValue(7)  );
	    	if(tamanhoVetor > 8)cd.setAlternativeAbbreviation(rowSet.getColumnValue(8) );
	    	if(tamanhoVetor > 9)cd.setDocumentation(rowSet.getColumnValue(9) );
	    	if(tamanhoVetor > 10)cd.setDataType(rowSet.getColumnValue(10) );
	    	if(tamanhoVetor > 11)cd.setState(rowSet.getColumnValue(11) );
	    	if(tamanhoVetor > 12)cd.setDataOwner(rowSet.getColumnValue(12) );
	    	if(tamanhoVetor > 13)cd.setDataSteward(rowSet.getColumnValue(13) );
	    	if(tamanhoVetor > 14)cd.setDominioNegocio(rowSet.getColumnValue(14));
	    	if(tamanhoVetor > 15)cd.setAssunto(rowSet.getColumnValue(15));
	    	if(tamanhoVetor > 16)cd.setOrigem(rowSet.getColumnValue(16));
	    	if(tamanhoVetor > 17)cd.setDominioDados(rowSet.getColumnValue(17));
	    	if(tamanhoVetor > 18)cd.setSubDominioDados(rowSet.getColumnValue(18));
	    	if(tamanhoVetor > 19)cd.setConfidencialidade(rowSet.getColumnValue(19));
	    	if(tamanhoVetor > 20)cd.setGrupoDominioDados(rowSet.getColumnValue(20));
	    	if(tamanhoVetor > 21)cd.setImportancia(rowSet.getColumnValue(21));
	    	if(tamanhoVetor > 22)cd.setLinkDocuments(rowSet.getColumnValue(22));
	    	if(tamanhoVetor > 23)cd.setProducao(rowSet.getColumnValue(23));
	    	
        
    	}catch (Exception e) {
    		e.printStackTrace();
    		logger.error("Erro ao ler o registro do arquivo " + e);
		}
    	return cd;
    }
}

