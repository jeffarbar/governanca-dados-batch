package br.com.claro.batch.catalogo.dados.step;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ser.std.StdJdkSerializers.AtomicBooleanSerializer;

import br.com.claro.batch.catalogo.dados.dto.CatalogoDadosDto;
import br.com.claro.batch.catalogo.dados.dto.CatalogoDadosRelacionamentoDto;
import br.com.claro.batch.catalogo.dados.model.CatalogoDadosModel;
import br.com.claro.batch.catalogo.dados.repository.CatalogoDadosRepository;

@Component
public class ProcessorRelacionamentoXLSXOEMM implements ItemProcessor<CatalogoDadosRelacionamentoDto, CatalogoDadosModel> {

	private static final Logger logger = LogManager.getLogger(ProcessorRelacionamentoXLSXOEMM.class);
	
	@Autowired
	private CatalogoDadosRepository catalogoDadosRepository;
	
	@Override
	public CatalogoDadosModel process(CatalogoDadosRelacionamentoDto item) throws Exception {
		
		String nomeAtributo = recuperaNomeAtributo(item.getDestinationPath());
		
		String[] caminhoETabela = recuperaCaminhoENomeTabela(item.getSourcePath());
		
		List<CatalogoDadosModel> listaCatalogoDadosModel = catalogoDadosRepository.findByName(nomeAtributo);
		
		if( listaCatalogoDadosModel != null && !listaCatalogoDadosModel.isEmpty() ) {
			
			listaCatalogoDadosModel.forEach( m ->{
				
				if(caminhoETabela[0].equals( m.getParentPath() )) {
					m.setNameTable(caminhoETabela[1]);
					catalogoDadosRepository.save(m);
					logger.info("Atualizando catalogoDadosModel: " + m);
				}
				
			} );	
		}else {
			logger.info("Não foi encontrado o catalogoDadosModel para atributo: " + nomeAtributo +", caminho da tabela: " +caminhoETabela[0]);
		}
				
		return null;
	}
	
	private String[] recuperaCaminhoENomeTabela(String origem) {
		
		String[] vetor = origem.split("/");
		
		String[] resultado = new String[2];
		
		resultado[0] = "/" + vetor[1] + "/" + vetor[2];
		resultado[1] = vetor[3];
				
		return resultado;
	}
	
	
	private String recuperaNomeAtributo(String destino) {
		
		String[] vetor = destino.split("/");
		
		if(vetor.length < 3 ) {
			return null;
		}	
		return vetor[3].trim();
	}
	
	public static void main(String[] args) {
	
		ProcessorRelacionamentoXLSXOEMM x = new ProcessorRelacionamentoXLSXOEMM();
		
		String[] d = x.recuperaCaminhoENomeTabela( "/Dicionário de Dados/Dicionário de Dados - DWH/BI_DIM_CORE_TIPO_SEGMENTO");
		System.out.println(d[0]);
		System.out.println(d[1]);
		
		//String f = x.recuperaNomeAtributo("/Dicionário de Dados/Dicionário de Dados - DWH/DSC_TIPO_SEGMENTO");
		//System.out.println(f);
		
		
	}
}
