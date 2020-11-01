package it.read.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = PRIVATE)
@Builder
@Getter
public class OggettoExcel {
	
	private String nome;
	private String cognome;
	private String livello;
	private Double paga;

}
