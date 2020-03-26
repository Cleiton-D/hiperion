package br.com.cleitonkiper.hiperion.wrappers;

import java.math.BigInteger;

import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;
import org.docx4j.wml.Text;
import org.docx4j.wml.PPrBase.Ind;

public class ContentWrapper {
	private final String content;
	private final ObjectFactory factory;
	
	public ContentWrapper(ObjectFactory factory, String content) {
		this.content = content;
		this.factory = factory;
	}
	
	public P getWml() {
		Text text = this.factory.createText();
		text.setValue(this.content);
		
		R run = this.factory.createR();
		run.getContent().add(text);
		
		// espaçamento na primeira linha do parágrafo
		Ind baseInd = factory.createPPrBaseInd();
		baseInd.setFirstLine(BigInteger.valueOf(709));
		PPr ppr = this.factory.createPPr();
		ppr.setInd(baseInd);
		
		P p = this.factory.createP();
		p.setPPr(ppr);
		p.getContent().add(run);
		
		return p;
	}
}
