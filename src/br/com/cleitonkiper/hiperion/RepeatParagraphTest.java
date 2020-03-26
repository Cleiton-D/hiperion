package br.com.cleitonkiper.hiperion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;
import org.wickedsource.docxstamper.api.coordinates.ParagraphCoordinates;

import br.com.cleitonkiper.hiperion.Beans.CharactersContext;
import br.com.cleitonkiper.hiperion.Beans.ProjectBean;

public class RepeatParagraphTest {

	public static void main(String[] args) throws Docx4JException, IOException {
		test();
	}
	
	
    public static void test() throws Docx4JException, IOException {
        CharactersContext context = new CharactersContext();
        context.getCharacters().add(new br.com.cleitonkiper.hiperion.Beans.Character("Homer Simpson", "Dan Castellaneta"));
        context.getCharacters().add(new br.com.cleitonkiper.hiperion.Beans.Character("Marge Simpson", "Julie Kavner"));
        context.getCharacters().add(new br.com.cleitonkiper.hiperion.Beans.Character("Bart Simpson", "Nancy Cartwright"));
        context.getCharacters().add(new br.com.cleitonkiper.hiperion.Beans.Character("Kent Brockman", "Harry Shearer"));
        context.getCharacters().add(new br.com.cleitonkiper.hiperion.Beans.Character("Disco Stu", "Hank Azaria"));
        context.getCharacters().add(new br.com.cleitonkiper.hiperion.Beans.Character("Krusty the Clown", "Dan Castellaneta"));
        
        InputStream template = new FileInputStream("RepeatParagraphTest.docx");
        OutputStream out = new ByteArrayOutputStream();
        
        DocxStamper stamper = new DocxStamper(new DocxStamperConfiguration());
        stamper.stamp(template, context, out);
        
        InputStream in = new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
        
        WordprocessingMLPackage document = WordprocessingMLPackage.load(in);

        final List<ParagraphCoordinates> titleCoords = new ArrayList<>();
        final List<ParagraphCoordinates> quotationCoords = new ArrayList<>();
        
        document.save(new File("teste789.docx"));
        
//        CoordinatesWalker walker = new BaseCoordinatesWalker(document) {
//            @Override
//            protected void onParagraph(ParagraphCoordinates paragraphCoordinates) {
//                if ("Titre2".equals(paragraphCoordinates.getParagraph().getPPr().getPStyle().getVal())) {
//                    titleCoords.add(paragraphCoordinates);
//                } else if ("Quotations".equals(paragraphCoordinates.getParagraph().getPPr().getPStyle().getVal())) {
//                    quotationCoords.add(paragraphCoordinates);
//                }
//            }
//        };
//        walker.walk();
    }
}
