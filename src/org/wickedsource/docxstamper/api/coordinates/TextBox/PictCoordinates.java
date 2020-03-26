package org.wickedsource.docxstamper.api.coordinates.TextBox;

import org.docx4j.wml.Pict;
import org.docx4j.wml.Tbl;
import org.wickedsource.docxstamper.api.coordinates.AbstractCoordinates;
import org.wickedsource.docxstamper.api.coordinates.ParagraphCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TableCellCoordinates;

public class PictCoordinates extends AbstractCoordinates {

	private final Pict pict;
	
	private final int index;
	
	private final ParagraphCoordinates parentParagraphCoordinates;
	
    public PictCoordinates(Pict pict, int index) {
        this.pict = pict;
        this.index = index;
        this.parentParagraphCoordinates = null;
    }

    public PictCoordinates(Pict pict, int index, ParagraphCoordinates parentParagraphCoordinates) {
        this.pict = pict;
        this.index = index;
        this.parentParagraphCoordinates = parentParagraphCoordinates;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        String toString = String.format("paragraph at index %d", index);
        if (parentParagraphCoordinates != null) {
            toString = parentParagraphCoordinates.toString() + "\n" + toString;
        }
        return toString;
    }

    public Pict getPict() {
        return pict;
    }

    public ParagraphCoordinates getParentParagraphCoordinates() {
        return parentParagraphCoordinates;
    }

}
