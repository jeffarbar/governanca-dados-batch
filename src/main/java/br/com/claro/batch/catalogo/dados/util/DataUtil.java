package br.com.claro.batch.catalogo.dados.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY_ hhmmss");
	
	public static String getDataHodaAtual() {
		return sdf.format(new Date());
	}
	
	public static void main(String[] args) {
		System.out.println(getDataHodaAtual());
	}
}
