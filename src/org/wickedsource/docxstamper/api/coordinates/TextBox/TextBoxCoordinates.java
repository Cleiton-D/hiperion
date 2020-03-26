package org.wickedsource.docxstamper.api.coordinates.TextBox;

import org.docx4j.vml.CTTextbox;
import org.wickedsource.docxstamper.api.coordinates.AbstractCoordinates;

public class TextBoxCoordinates extends AbstractCoordinates {
	
	private final CTTextbox textBox;
	
	private final int index;
	
	private final ShapeCoordinates parentShapeCoordinates;
	
	
	public TextBoxCoordinates(CTTextbox textBox, int index, ShapeCoordinates parentShapeCoordinates) {
        this.textBox = textBox;
        this.index = index;
        this.parentShapeCoordinates = parentShapeCoordinates;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        String toString = String.format("paragraph at index %d", index);
        return parentShapeCoordinates.toString() + "\n" + toString;
    }

    public CTTextbox getTextBox() {
        return textBox;
    }

    public ShapeCoordinates getParentShapeCoordinates() {
        return parentShapeCoordinates;
    }

}
