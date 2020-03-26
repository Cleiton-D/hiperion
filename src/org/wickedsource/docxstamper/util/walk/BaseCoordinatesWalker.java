package org.wickedsource.docxstamper.util.walk;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.wickedsource.docxstamper.api.coordinates.*;
import org.wickedsource.docxstamper.api.coordinates.TextBox.PictCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.ShapeCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.TextBoxContentCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.TextBoxCoordinates;
import org.wickedsource.docxstamper.util.CommentWrapper;

public abstract class BaseCoordinatesWalker extends CoordinatesWalker {

    public BaseCoordinatesWalker(WordprocessingMLPackage document) {
        super(document);
    }

    @Override
    protected void onParagraph(ParagraphCoordinates paragraphCoordinates) {

    }

    @Override
    protected void onTable(TableCoordinates tableCoordinates) {

    }

    @Override
    protected void onTableCell(TableCellCoordinates tableCellCoordinates) {

    }

    @Override
    protected void onTableRow(TableRowCoordinates tableRowCoordinates) {

    }
    
    @Override
    protected void onPict(PictCoordinates pictCoordinates) {
    	
    }
    
    @Override
    protected void onShape(ShapeCoordinates shapeCoordinates) {
    	
    }

    @Override
    protected void onTextBox(TextBoxCoordinates textBoxCoordinates) {
    	
    }

    @Override
    protected void onTextBoxContent(TextBoxContentCoordinates textBoxContentCoordinates) {
    	
    }
    
    @Override
	protected CommentWrapper onRun(RunCoordinates runCoordinates, ParagraphCoordinates paragraphCoordinates) {
		return null;
	}
}
