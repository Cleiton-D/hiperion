package br.com.cleitonkiper.hiperion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Project {
	private ByteArrayOutputStream out;
	private String name;
	
	public Project(String name, ByteArrayOutputStream out) {
		this.out = out;
		this.name = name;
	}
	
	public void save() throws IOException {
		save(null);
	}
	public void save(String directory) throws IOException {
		File file = directory != null ? new File(directory) : new File(this.name+".docx");
		OutputStream outp = new FileOutputStream(file);
		outp.write(out.toByteArray());
		outp.close();
	}
	
	public OutputStream getStream() {
		return out;
	}
}
