package org.wickedsource.docxstamper.api.coordinates.TextBox;

import org.docx4j.vml.CTShape;
import org.wickedsource.docxstamper.api.coordinates.AbstractCoordinates;

public class ShapeCoordinates extends AbstractCoordinates {
   
	private final CTShape shape;
	
	private final int index;
	
	private final PictCoordinates parentPictCoordinates;
	
	
	public ShapeCoordinates(CTShape shape, int index, PictCoordinates parentPictCoordinates) {
        this.shape = shape;
        this.index = index;
        this.parentPictCoordinates = parentPictCoordinates;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        String toString = String.format("paragraph at index %d", index);
        return parentPictCoordinates.toString() + "\n" + toString;
    }

    public CTShape getShape() {
        return shape;
    }

    public PictCoordinates getParentPictCoordinates() {
        return parentPictCoordinates;
    }
}
