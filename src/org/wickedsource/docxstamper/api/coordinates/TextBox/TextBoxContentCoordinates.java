package org.wickedsource.docxstamper.api.coordinates.TextBox;

import org.docx4j.wml.CTTxbxContent;
import org.wickedsource.docxstamper.api.coordinates.AbstractCoordinates;

public class TextBoxContentCoordinates extends AbstractCoordinates {
    
	private final CTTxbxContent textBoxContent;
	
	private final int index;
	
	private final TextBoxCoordinates parentTextBoxCoordinates;
	
	
	public TextBoxContentCoordinates(CTTxbxContent textBoxContent, int index, TextBoxCoordinates parentTextBoxCoordinates) {
        this.textBoxContent = textBoxContent;
        this.index = index;
        this.parentTextBoxCoordinates = parentTextBoxCoordinates;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        String toString = String.format("paragraph at index %d", index);
        return parentTextBoxCoordinates.toString() + "\n" + toString;
    }

    public CTTxbxContent getTextBoxContent() {
        return textBoxContent;
    }

    public TextBoxCoordinates getParentTextBoxCoordinates() {
        return parentTextBoxCoordinates;
    }
}
