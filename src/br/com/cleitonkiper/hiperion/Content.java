package br.com.cleitonkiper.hiperion;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class Content {
	private final ByteArrayOutputStream out;
	
	
	public Content(ByteArrayOutputStream out) {
		this.out = out;
	}
	
	public ByteArrayOutputStream getStream() {
		return out;
	}
	
}
