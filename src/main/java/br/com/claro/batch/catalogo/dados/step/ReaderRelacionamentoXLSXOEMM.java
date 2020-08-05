package br.com.claro.batch.catalogo.dados.step;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.batch.item.excel.support.rowset.RowSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import br.com.claro.batch.catalogo.dados.dto.CatalogoDadosRelacionamentoDto;

@Component
public class ReaderRelacionamentoXLSXOEMM {

	@Value("${xlsx.arquivo.relacionamento}")
    private String arquivoRelacionamentoXLSX;
	
	
	@Bean
	public ItemReader<CatalogoDadosRelacionamentoDto> reader() {
		
        PoiItemReader<CatalogoDadosRelacionamentoDto> reader = new PoiItemReader<>();
        reader.setResource(new FileSystemResource(arquivoRelacionamentoXLSX));
        reader.setLinesToSkip(1);
        reader.setRowMapper(excelRowMapper());
        return reader;
    }
 

    private RowMapper<CatalogoDadosRelacionamentoDto> excelRowMapper() {
       return new RelacionamentoCatalogoDadosExcelRowMapper();
    }
}

class RelacionamentoCatalogoDadosExcelRowMapper implements RowMapper<CatalogoDadosRelacionamentoDto> {
	 
	private static final Logger logger = LogManager.getLogger(RelacionamentoCatalogoDadosExcelRowMapper.class);
	
    public CatalogoDadosRelacionamentoDto mapRow(RowSet rowSet) throws Exception {
    	
    	CatalogoDadosRelacionamentoDto cd = new CatalogoDadosRelacionamentoDto();
    	
    	try {
	    	
	    	if (rowSet == null || rowSet.getCurrentRow() == null) {
	            return cd;
	        }
    		
	    	int tamanhoVetor = rowSet.getCurrentRow().length;
	    	
	    	if(tamanhoVetor > 0)cd.setLinkType(rowSet.getColumnValue(0));
	    	if(tamanhoVetor > 1)cd.setSourcePath( rowSet.getColumnValue(1) );
	    	if(tamanhoVetor > 2)cd.setDestinationPath( rowSet.getColumnValue(2) );
	    	
    	}catch (Exception e) {
    		e.printStackTrace();
    		logger.error("Erro ao ler o registro do arquivo " + e);
		}
    	return cd;
    }
}
