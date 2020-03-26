package org.wickedsource.docxstamper.api.coordinates;

import org.docx4j.wml.P;
import org.wickedsource.docxstamper.api.coordinates.AbstractCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TableCellCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.TextBoxContentCoordinates;

public class ParagraphCoordinates extends AbstractCoordinates {

    private final P paragraph;

    private final int index;

    private final TableCellCoordinates parentTableCellCoordinates;

    private final TextBoxContentCoordinates parentTextBoxContentCoordinates;

    public ParagraphCoordinates(P paragraph, int index, TableCellCoordinates parentTableCellCoordinates) {
        this.paragraph = paragraph;
        this.index = index;
        this.parentTableCellCoordinates = parentTableCellCoordinates;
        this.parentTextBoxContentCoordinates = null;
    }
    
    public ParagraphCoordinates(P paragraph, int index, TextBoxContentCoordinates parentTextBoxContentCoordinates) {
        this.paragraph = paragraph;
        this.index = index;
        this.parentTableCellCoordinates = null;
        this.parentTextBoxContentCoordinates = parentTextBoxContentCoordinates;
    }

    public ParagraphCoordinates(P paragraph, int index) {
        this.paragraph = paragraph;
        this.index = index;
        this.parentTableCellCoordinates = null;
        this.parentTextBoxContentCoordinates = null;
    }

    public P getParagraph() {
        return paragraph;
    }

    public int getIndex() {
        return index;
    }

    public TableCellCoordinates getParentTableCellCoordinates() {
        return parentTableCellCoordinates;
    }
    
    public TextBoxContentCoordinates getParentTextBoxContentCoordinates() {
    	return parentTextBoxContentCoordinates;
    }

    public String toString() {
        String toString = String.format("paragraph at index %d", index);
        if (parentTableCellCoordinates != null) {
            toString = parentTableCellCoordinates.toString() + "\n" + toString;
        } else if (parentTextBoxContentCoordinates != null) {
        	toString = parentTextBoxContentCoordinates.toString() + "\n" + toString;
        }
        
        return toString;
    }
}
