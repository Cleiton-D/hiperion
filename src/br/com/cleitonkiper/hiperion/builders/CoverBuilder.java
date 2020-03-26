package br.com.cleitonkiper.hiperion.builders;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;
import org.wickedsource.docxstamper.replace.typeresolver.DateResolver;

import br.com.cleitonkiper.hiperion.Cover;
import br.com.cleitonkiper.hiperion.Beans.AuthorBean;
import br.com.cleitonkiper.hiperion.Beans.ProjectBean;
import br.com.cleitonkiper.hiperion.interfaces.Builder;

public class CoverBuilder implements Builder<Cover> {
	private InputStream template;
	private List<String> integrants = new ArrayList<String>();
	
	private String title;
	private String subTitle;
	private String city;
	private String state;
	private String advisor;
	private String course;
	private Date date = new Date();
	
	public CoverBuilder withTitle(String title) {
		this.title = title;
		
		return this;
	}
	
	public CoverBuilder withSubTitle(String subTitle) {
		this.subTitle = subTitle;
		
		return this;
	}
	public CoverBuilder withCity(String city) {
		this.city = city;
		
		return this;
	}
	public  CoverBuilder withState(String state) {
		this.state = state;
		
		return this;
	}
	public CoverBuilder withDate(Date date) {
		this.date = date;
		
		return this;
	}
	public CoverBuilder withIntegrantsList(List<String> integrats) {
		this.integrants.addAll(integrats);
		
		return this;
	}
	public CoverBuilder addIntegrant(String name) {
		this.integrants.add(name);
		
		return this;
	}
	public CoverBuilder withAdvisor(String advisor) {
		this.advisor = advisor;
		return this;
	}
	public CoverBuilder withCourse(String course) {
		this.course = course;
		return this;
	}

	public CoverBuilder withTemplate(InputStream template) {
		this.template = template;
		
		return this;
	}
	
	@Override
	public Cover build() throws FileNotFoundException {
		return build(this.template);
	}

	@Override
	public Cover  build(InputStream template) throws FileNotFoundException {
		DateResolver dateResolver = new DateResolver("yyyy");
		
		DocxStamperConfiguration config = new DocxStamperConfiguration();
		config.addTypeResolver(Date.class, dateResolver);
		
		DocxStamper<ProjectBean> stamper = new DocxStamper<ProjectBean>(config);
		ProjectBean projectBean = new ProjectBean();
		projectBean.setTitle(this.title);
		projectBean.setSubtitle(this.subTitle);
		projectBean.setCity(this.city);
		projectBean.setUf(this.state);
		projectBean.setDate(this.date);
		
		createAuthorList(projectBean);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		stamper.stamp(template, projectBean, out);
		
		return new Cover(out);
	}
	
	private void createAuthorList(ProjectBean prjBean) {
		for (String author : integrants) {
			AuthorBean authorBean = new AuthorBean();
			authorBean.setNome(author);
			
			prjBean.getAuthors().add(authorBean);
		}
	}

}
