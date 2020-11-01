package it.read.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class OggettoReader {
	
	
	public static final Logger LOGGER = Logger.getLogger(OggettoReader.class.getName());
	
	public static void main(String[] args) {
		
		try {
			
			LOGGER.info("INIZIO LETTURA FILE...");
			
			List<Object[]> dati = leggi("ANAGRAFICA", new int[] {0,1,2,3}, new Class[] {String.class, String.class, Integer.class, Double.class});
			
			LOGGER.info("FINE LETTURA FILE.");
			
			dati.stream().map(Arrays::deepToString).forEach(System.out::println);
			
			
		} 
		catch (IOException e) {
			
			LOGGER.log(Level.SEVERE, "Eccezione in ", e);
			e.printStackTrace();
		}
	}
	
	
	public static List<Object[]> leggi (String foglio, int[] indici, Class<?>[] tipi) throws IOException {
		
		
		List<Object[]> dati = new ArrayList<Object[]>();
		
		//recupera nel classpath un file  chiamato dati.xlsx
		try (InputStream inp = OggettoReader.class.getResourceAsStream("/dati.xlsx"); 
				Workbook wb = WorkbookFactory.create(inp);)
		{
			
			Sheet sheet = wb.getSheet(foglio);
			
			for (int r = 1; sheet.getRow(r) != null && sheet.getRow(r).getCell(0) != null; r++ ) {
				
				List<Object> dato = new ArrayList<>();
				
				for (int c = 0; c < indici.length; c++ ) {
					
					switch (tipi[c].getName()) {
					
					case "java.lang.String":
						
						dato.add(stringa(sheet.getRow(r).getCell(c)));
						break;
					case "java.lang.Integer":
						
						dato.add(intero(sheet.getRow(r).getCell(c)));
						break;
					case "java.lang.Double":
						
						dato.add(decimale(sheet.getRow(r).getCell(c)));
						break;

					default:
						break;
					}
				}
				
				if(dato.get(0) != null) {
					
					dati.add(dato.toArray());
				}
				
			}
			
			
		}
		
		return dati;
	}
	
	private static Integer intero (Cell c) {
		
		return c != null && CellType.NUMERIC.equals(c.getCellType()) ? 
				Double.valueOf(c.getNumericCellValue()).intValue() : null;
		
		
	}
	
	private static String stringa (Cell c) {
		
		return c != null && CellType.STRING.equals(c.getCellType()) ? 
				c.getStringCellValue() : null;
		
		
	}
	
	private static Double decimale (Cell c) {
		
		return c != null && CellType.NUMERIC.equals(c.getCellType()) ? 
				Double.valueOf(c.getNumericCellValue()) : null;
		
		
	}
}
