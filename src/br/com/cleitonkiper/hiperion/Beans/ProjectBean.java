package br.com.cleitonkiper.hiperion.Beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectBean {

	private Integer id;
	
	private String title;
	
	private String subtitle;
	
	private List<AuthorBean> authors;
	
	private String city;
	
	private String uf;
	
	private Date date;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public List<AuthorBean> getAuthors() {
		if (authors == null) authors = new ArrayList<AuthorBean>(); 
		return authors;
	}
	public void setAuthors(List<AuthorBean> authors) {
		this.authors = authors;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
