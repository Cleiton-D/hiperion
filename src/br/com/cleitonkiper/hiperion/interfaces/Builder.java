package br.com.cleitonkiper.hiperion.interfaces;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface Builder<T> {
	T build() throws Exception ;
	T build(InputStream template) throws Exception ;
	
//	 withTemplate(InputStream template);
}
