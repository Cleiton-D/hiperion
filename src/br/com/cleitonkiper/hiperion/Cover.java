package br.com.cleitonkiper.hiperion;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import br.com.cleitonkiper.hiperion.builders.CoverBuilder;

public class Cover {
	private ByteArrayOutputStream out;
	
	public Cover(ByteArrayOutputStream out) {
		this.out = out;
	}
	
	public void save(String directory) {}
	
	public ByteArrayOutputStream getStream() {
		return out;
	}
}
