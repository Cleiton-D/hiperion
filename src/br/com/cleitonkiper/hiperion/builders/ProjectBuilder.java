package br.com.cleitonkiper.hiperion.builders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import br.com.cleitonkiper.hiperion.Content;
import br.com.cleitonkiper.hiperion.Cover;
import br.com.cleitonkiper.hiperion.Project;
import br.com.cleitonkiper.hiperion.Beans.ContentBean;
import br.com.cleitonkiper.hiperion.interfaces.Builder;

public class ProjectBuilder implements Builder<Project> {
	private Cover cover;
	private CoverBuilder coverBuilder;
	private InputStream template;
	private List<String> integrants = new ArrayList<String>();
	private List<ContentBean> contents = new ArrayList<ContentBean>();
	
	private String title;
	private String subtitle;
	private String institute;
	private String advisor;
	private String course;
	
	
	public ProjectBuilder withCover(Cover cover) {
		this.cover = cover;
		return this;
	}
	
	public ProjectBuilder withCover(CoverBuilder builder) throws FileNotFoundException {
		this.coverBuilder = builder;
		
		return this;
	}

	public ProjectBuilder withTitle(String title) {
		this.title = title;
		return this;
	}
	public ProjectBuilder withSubTitle(String subtitle) {
		this.subtitle = subtitle;
		return this;
	}
	public ProjectBuilder withInstitute(String institute) {
		this.institute = institute;
		return this;
	}
	public ProjectBuilder withAdvisor(String advisor) {
		this.advisor = advisor;
		return this;
	}
	
	public ProjectBuilder withIntegrantsList(List<String> integrats) {
		this.integrants.addAll(integrats);
		
		return this;
	}
	public ProjectBuilder addIntegrant(String name) {
		this.integrants.add(name);
		
		return this;
	}
	public ProjectBuilder withCourse(String course) {
		this.course = course;
		return this;
	}
	
	
	public ProjectBuilder withTemplate(InputStream template) {
		this.template = template;
		
		return this;
	}
	
	public ProjectBuilder withContents(List<ContentBean> contents) {
		this.contents.addAll(contents);
		return this;
	}
	
	
	
	@Override
	public Project build() throws Exception {
		return build(template);
	}

	@Override
	public Project build(InputStream template) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		this.cover = this.coverBuilder.withTitle(this.title)
		.withSubTitle(this.subtitle)
		.withAdvisor(this.advisor)
		.withCourse(this.course)
		.withIntegrantsList(this.integrants)
		.withTemplate(this.template)
		.build();
		

		Content cont = new ContentBuilder(this.contents, new ByteArrayInputStream(cover.getStream().toByteArray())).build();
		
		return new Project(this.title, cont.getStream());
	}
}
