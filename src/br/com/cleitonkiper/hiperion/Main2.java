package br.com.cleitonkiper.hiperion;

import java.io.File;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.wml.Body;
import org.docx4j.wml.FldChar;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.P.Hyperlink;
import org.docx4j.wml.R;
import org.docx4j.wml.STFldCharType;
import org.docx4j.wml.Text;

public class Main2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			ObjectFactory factory = Context.getWmlObjectFactory();

			P p = factory.createP();             
			P.Hyperlink h = new Hyperlink();
			h.setAnchor("_Toc236597049");
			h.setHistory(true);

			// You aren't adding your hyperlink to the P
			// You'll need something like
			p.getParagraphContent().add(h);
			// In what follows, you should be adding the content
			// to the h, not the p, but I'll leave that to you

			R r = factory.createR();
			r.setRsidRPr("TOC entry text");
			R.Tab tab = new R.Tab();
			r.getRunContent().add(tab);             
			p.getParagraphContent().add(r);


			FldChar fldchar = factory.createFldChar();
			fldchar.setFldCharType(STFldCharType.BEGIN);
			r.getRunContent().add(getWrappedFldChar(fldchar));

			Text txt = new Text();
			txt.setValue("PAGEREF _Toc236597049 \\h");
			txt.setSpace("preserve");

			// You aren't adding this anywhere, so ..
			r.getRunContent().add(
					factory.createRInstrText(txt) );             

			FldChar fldcharSep = factory.createFldChar();
			fldcharSep.setFldCharType(STFldCharType.SEPARATE);
			R r1 = factory.createR();
			r1.getRunContent().add(getWrappedFldChar(fldcharSep));


			R.Tab pageTab = new R.Tab();
			r1.getRunContent().add(pageTab);
			p.getParagraphContent().add(r1);


			FldChar fldcharEnd = factory.createFldChar();

			fldcharEnd.setFldCharType(STFldCharType.SEPARATE);
			R r3 = factory.createR();
			r.getRunContent().add(getWrappedFldChar(fldcharEnd));
			p.getParagraphContent().add(r3);


			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File("Teste paragrafo.docx"));
			MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

			org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document)documentPart.getJaxbElement();
			Body body =  wmlDocumentEl.getBody();
			body.getEGBlockLevelElts().add(p);
			wordMLPackage.save(new File("AddTOC.docx"));


		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}


	}

	public static JAXBElement getWrappedFldChar(FldChar fldchar) {

		return new JAXBElement( new QName(Namespaces.NS_WORD12, "fldChar"),
				FldChar.class, fldchar);

	}
}
