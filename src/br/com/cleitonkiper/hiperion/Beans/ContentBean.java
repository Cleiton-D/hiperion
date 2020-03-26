package br.com.cleitonkiper.hiperion.Beans;

import java.util.ArrayList;
import java.util.List;

public class ContentBean {
	
	private String name;
	private List<ContentBean> childrens;
	private String content;
	
	public ContentBean() {
		this.childrens = new ArrayList<ContentBean>();
	}
	
	public ContentBean(String name, List<ContentBean> childrens, String content) {
		this.childrens = new ArrayList<ContentBean>();
		
		this.name = name;
		this.childrens = childrens;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ContentBean> getChildrens() {
		return childrens;
	}

	public void addChildren(ContentBean children) {
		this.childrens.add(children);
	}
	
	public void setChildrens(List<ContentBean> childrens) {
		this.childrens = childrens;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
