package br.com.cleitonkiper.hiperion.builders;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.ArrayUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.Body;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.Br;
import org.docx4j.wml.CTColumns;
import org.docx4j.wml.CTDocGrid;
import org.docx4j.wml.CTPageNumber;
import org.docx4j.wml.CTSimpleField;
import org.docx4j.wml.Document;
import org.docx4j.wml.FldChar;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STBrType;
import org.docx4j.wml.STFldCharType;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.SectPr.PgMar;
import org.docx4j.wml.SectPr.PgSz;
import org.docx4j.wml.Text;

import br.com.cleitonkiper.hiperion.Content;
import br.com.cleitonkiper.hiperion.Beans.ContentBean;
import br.com.cleitonkiper.hiperion.interfaces.Builder;
import br.com.cleitonkiper.hiperion.wrappers.ContentWrapper;
import br.com.cleitonkiper.hiperion.wrappers.TitleWrapper;
import br.com.cleitonkiper.hiperion.wrappers.TitleWrapper.Level;

public class ContentBuilder implements Builder<Content>{

	private WordprocessingMLPackage wordPackage;
	private ObjectFactory factory;

	private final List<ContentBean> contents;

	public ContentBuilder(List<ContentBean> contents, InputStream doc) throws Docx4JException {

		this.contents = contents;
		this.wordPackage = WordprocessingMLPackage.load(doc);
		this.factory = Context.getWmlObjectFactory();

	}


	@Override
	public Content build() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		addTOC();
		addSectionBreak();

		buildContent(Level.Title, this.contents);

		
		wordPackage.save(out);

		return new Content(out);
	}

	@Override
	public Content build(InputStream doc) throws Exception {
		return null;
	}

	private void buildContent(Level level, List<ContentBean> items) throws Docx4JException {
		MainDocumentPart main = this.wordPackage.getMainDocumentPart();


		Level[] list = Level.values();

		int index = ArrayUtils.indexOf(list, level);
		for (ContentBean cont : items) {

			if (level.equals(Level.Title)) {
				addBreakPage();
			} else {
				addBreakLine();
			}				


			P title = new TitleWrapper(this.factory, cont.getName(), level).getWml();
			P content = new ContentWrapper(factory, cont.getContent()).getWml();

			main.addObject(title);
			main.addObject(content);

			if (level.equals(Level.Terceiro) == false) buildContent(list[index+1], cont.getChildrens());
		}
		
	}


	private void addBreakPage() {
		MainDocumentPart main = this.wordPackage.getMainDocumentPart();

		Br pgBreak = this.factory.createBr();
		pgBreak.setType(STBrType.PAGE);

		R r = this.factory.createR();
		r.getContent().add(pgBreak);

		P p = this.factory.createP();
		p.getContent().add(r);

		main.addObject(p);
	}

	private void addBreakLine() {
		MainDocumentPart main = this.wordPackage.getMainDocumentPart();

		R r = this.factory.createR();

		P p = this.factory.createP();
		p.getContent().add(r);

		main.addObject(p);
	}

	
	private void addTOC() {
		MainDocumentPart main = this.wordPackage.getMainDocumentPart();

		addBreakPage();
		
		Text instrText = factory.createText();
		instrText.setValue("TOC \\o \"1-3\" \\h \\z \\u");
		instrText.setSpace("preserve");
		
		
		JAXBElement<Text>  jaxbInscrText = this.factory.createRInstrText(instrText);
		
		FldChar fldChar1 = this.factory.createFldChar();
		fldChar1.setFldCharType(STFldCharType.BEGIN);
		
		Text text = factory.createText();
		text.setValue("Clique com botão direito e vá até \"Atualizar campo\" para exibir o sumário");
		
		
		
		FldChar fldChar2 = this.factory.createFldChar();
		fldChar2.setFldCharType(STFldCharType.SEPARATE);
		
		FldChar fldChar3 = this.factory.createFldChar();
		fldChar3.setFldCharType(STFldCharType.END);
		
		R r = this.factory.createR();
		r.getContent().add(fldChar1);
		r.getContent().add(jaxbInscrText);
		r.getContent().add(fldChar2);
		r.getContent().add(text);
		r.getContent().add(fldChar3);
		
		P p = this.factory.createP();
		p.getContent().add(r);
		
		
		Text titleText = this.factory.createText();
		titleText.setValue("SUMÁRIO");
		
		
		RPr rpr = this.factory.createRPr();
		rpr.setB(new BooleanDefaultTrue());
		
		R titleRun = this.factory.createR();
		titleRun.getContent().add(titleText);
		titleRun.setRPr(rpr);
		
		
		Jc jcTitle = this.factory.createJc();
		jcTitle.setVal(JcEnumeration.CENTER);
		
		
		PPr ppr = this.factory.createPPr();
		ppr.setJc(jcTitle);
		
		P title = this.factory.createP();
		title.setPPr(ppr);
		title.getContent().add(titleRun);
		
		
		main.getContent().add(title);
		addBreakLine();
		main.getContent().add(p);
	}
	

	private void addSectionBreak() throws Docx4JException {
		MainDocumentPart main = this.wordPackage.getMainDocumentPart();
		Document doc = main.getContents();
		Body body = doc.getBody();

		PPr coverPpr = factory.createPPr();
		coverPpr.setSectPr(body.getSectPr());
		
		P coverP = this.factory.createP();
		coverP.setPPr(coverPpr);
		
		body.getContent().add(coverPpr);




		SectPr sectPr = null;
		//		if(isLastSection){
		//		    List<SectionWrapper> sections = this.wordPackage.getDocumentModel().getSections();
		//		    sectPr = sections.get(sections.size() - 1).getSectPr();
		//		    if (sectPr==null ) {
		//		        sectPr = this.factory.createSectPr();
		//		        this.wordPackage.getMainDocumentPart().addObject(sectPr);
		//		        sections.get(sections.size() - 1).setSectPr(sectPr);
		//		    }
		//		}
		//		else{
		sectPr = this.factory.createSectPr();
		//		}

		

		// NÚMEROS DE PÁGINA
		RPr rpr = this.factory.createRPr();
		rpr.setNoProof(new BooleanDefaultTrue());
		
		R run = this.factory.createR();
		run.getContent().add(rpr);
		
		CTSimpleField pgmum = this.factory.createCTSimpleField();
		pgmum.setInstr("PAGE \\* MERGEFORMAT");
		pgmum.getContent().add(run);
		
		Jc jc = factory.createJc();
		jc.setVal(JcEnumeration.RIGHT);

		PPr ppr = factory.createPPr();
		ppr.setJc(jc);
		
		PPrBase.Spacing pprbase = this.factory.createPPrBaseSpacing();
		pprbase.setBefore(BigInteger.valueOf(240));
		pprbase.setAfter(BigInteger.valueOf(0));
		ppr.setSpacing(pprbase);		
		
		JAXBElement<CTSimpleField> fldSimple = this.factory.createPFldSimple(pgmum);
		P para = this.factory.createP();
		para.getContent().add(fldSimple);
		para.setPPr(ppr);
		
		
		// HEADER
		HeaderPart headerPart = new HeaderPart(new PartName("/word/contentHeader.xml"));
		headerPart.setPackage(this.wordPackage);
		headerPart.setRelationshipType(Namespaces.HEADER);
		headerPart.setJaxbElement(this.factory.createHdr());
		headerPart.getContent().add(para);
		
		Relationship relationship = main.addTargetPart(headerPart);
		HeaderReference headerReference = this.factory.createHeaderReference();
		
		headerReference.setId(relationship.getId());
		headerReference.setType(HdrFtrRef.DEFAULT);
		sectPr.getEGHdrFtrReferences().add(headerReference);
		
		CTPageNumber ctPageNumber = this.factory.createCTPageNumber();
		ctPageNumber.setStart(BigInteger.ONE);
				
		PgSz pageSize = this.factory.createSectPrPgSz();
		pageSize.setH(BigInteger.valueOf(16839));
		pageSize.setW(BigInteger.valueOf(11907));
		pageSize.setCode(BigInteger.valueOf(9));
		
		PgMar pageMargin = this.factory.createSectPrPgMar();
		pageMargin.setGutter(BigInteger.valueOf(0));
		pageMargin.setLeft(BigInteger.valueOf(1701));
		pageMargin.setRight(BigInteger.valueOf(1134));
		pageMargin.setTop(BigInteger.valueOf(1701));
		pageMargin.setBottom(BigInteger.valueOf(1134));
		pageMargin.setHeader(BigInteger.valueOf(289));
		pageMargin.setFooter(BigInteger.valueOf(680));
		
		CTColumns columns = this.factory.createCTColumns();
		columns.setSpace(BigInteger.valueOf(720));
		
		CTDocGrid docgrid = this.factory.createCTDocGrid();
		docgrid.setLinePitch(BigInteger.valueOf(299));
		
		sectPr.setPgNumType(ctPageNumber);
		sectPr.setPgSz(pageSize);
		sectPr.setPgMar(pageMargin);
		sectPr.setCols(columns);
		sectPr.setDocGrid(docgrid);
		
		body.setSectPr(sectPr);

	}
}
