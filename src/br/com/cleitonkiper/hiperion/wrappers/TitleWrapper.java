package br.com.cleitonkiper.hiperion.wrappers;

import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Br;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;
import org.docx4j.wml.STBrType;
import org.docx4j.wml.Text;
import org.docx4j.wml.PPrBase.PStyle;

public class TitleWrapper {
	public final String title;
	public final Level level;
	public final ObjectFactory factory;
	
	
	public TitleWrapper(ObjectFactory factory, String title, Level level) {
		this.level = level;
		this.factory = factory;

		if (level.equals(Level.Title)) {
			title = title.toUpperCase();
		}
		
		this.title = title;
	}
	
	public P getWml() {
		Text text = this.factory.createText();
		text.setValue(this.title);
		
		R run = this.factory.createR();
		run.getContent().add(text);
		
		PStyle pstyle = this.factory.createPPrBasePStyle();
		pstyle.setVal(level.value);
		
		PPr ppr = this.factory.createPPr();
		ppr.setPStyle(pstyle);
		
		
		P p = this.factory.createP();
		p.setPPr(ppr);
		p.getContent().add(run);
		
		return p;
	}
	
	

	public enum Level {
		Title("Ttulo1"), Subtitle("Ttulo2"), Terceiro("Ttulo3");
		
		public String value;
		
		Level(String level) {
			value = level;
		}
	}
	
}

