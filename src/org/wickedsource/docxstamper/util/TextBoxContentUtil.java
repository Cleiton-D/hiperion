package org.wickedsource.docxstamper.util;

import org.docx4j.wml.CTTxbxContent;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.Tc;

public class TextBoxContentUtil {

    private static ObjectFactory objectFactory = new ObjectFactory();

    public static boolean hasAtLeastOneParagraph(CTTxbxContent textBoxContent) {
        for (Object contentElement : textBoxContent.getContent()) {
            if (contentElement instanceof P) {
                return true;
            }
        }
        return false;
    }

    public static void addEmptyParagraph(CTTxbxContent textBoxContent) {
        P paragraph = objectFactory.createP();
        paragraph.getContent().add(objectFactory.createR());
        textBoxContent.getContent().add(paragraph);
    }
	
}
