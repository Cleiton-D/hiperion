package org.wickedsource.docxstamper.util;

import javax.xml.bind.JAXBElement;

import org.docx4j.jaxb.Context;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.Pict;
import org.docx4j.wml.R;
import org.docx4j.wml.Tc;

public class ParagraphUtil {

    private static ObjectFactory objectFactory = Context.getWmlObjectFactory();

    private ParagraphUtil() {

    }

    public static boolean hasAtLeastOneRunOrPict(P p) {
        for (Object contentElement : p.getContent()) {
            if (contentElement instanceof R ||
                    (contentElement instanceof JAXBElement && ((JAXBElement) contentElement).getValue() instanceof Pict)) {
                return true;
            }
        }
        return false;
    }
    
    public static void addEmptyRun(P p) {
        p.getContent().add(objectFactory.createR());
    }
    
    
    /**
     * Creates a new paragraph.
     *
     * @param texts the text of this paragraph. If more than one text is specified each text will be placed within its own Run.
     * @return a new paragraph containing the given text.
     */
    public static P create(String... texts) {
        P p = objectFactory.createP();
        for (String text : texts) {
            R r = RunUtil.create(text, p);
            p.getContent().add(r);
        }
        return p;
    }


}
