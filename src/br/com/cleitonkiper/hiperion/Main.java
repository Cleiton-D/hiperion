package br.com.cleitonkiper.hiperion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.cleitonkiper.hiperion.Beans.ContentBean;
import br.com.cleitonkiper.hiperion.builders.CoverBuilder;
import br.com.cleitonkiper.hiperion.builders.ProjectBuilder;

public class Main {
	public static void main(String[] args) throws Exception {
		InputStream template = new FileInputStream(new File("cover_template2.docx"));
		
		List<String> autores = new ArrayList<String>();
		autores.add("Cleiton Dione Ahnerth Kiper");
		autores.add("Algum aluno por ai");
		
		List<ContentBean> cont = new ArrayList<ContentBean>();
		
		cont.add(new ContentBean("teste", new ArrayList<ContentBean>(), "testando break page"));
		
		
		Project pr = new ProjectBuilder()
				.withTemplate(template)
				.withCover(new CoverBuilder()
						.withCity("Pimenta Bueno")
						.withState("RO")
						.withDate(new Date()))
				.withTitle("Teste paragrafo")
				.withSubTitle("testando subtitulo")
				.withIntegrantsList(autores)
				.withContents(cont)
				.build();
				
		pr.save();
	}
}
