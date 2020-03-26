package org.wickedsource.docxstamper.util;

import org.docx4j.dml.chart.CTTx;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.vml.CTShape;
import org.docx4j.vml.CTTextbox;
import org.docx4j.wml.CTPictureBase;
import org.docx4j.wml.CTTxbxContent;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.P;
import org.docx4j.wml.Pict;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.wickedsource.docxstamper.api.coordinates.ParagraphCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TableCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TableRowCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.PictCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.ShapeCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.TextBoxContentCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.TextBoxCoordinates;

import java.util.HashMap;
import java.util.Map;

public class ObjectDeleter {

    private WordprocessingMLPackage document;

    private int objectsDeletedFromMainDocument = 0;

    private Map<ContentAccessor, Integer> deletedObjectsPerParent = new HashMap<>();

    private Map<CTPictureBase, Integer> deletedObjectsPerParentPict = new HashMap<>();

    private Map<CTShape, Integer> deletedObjectsPerParentShape = new HashMap<>();
    
    public ObjectDeleter(WordprocessingMLPackage document) {
        this.document = document;
    }

    public void deleteParagraph(ParagraphCoordinates paragraphCoordinates) {
        if (paragraphCoordinates.getParentTableCellCoordinates() != null) {
        	// paragraph within a table cell
        	Tc parentCell = paragraphCoordinates.getParentTableCellCoordinates().getCell();
        	deleteFromCell(parentCell, paragraphCoordinates.getIndex());

        } else if (paragraphCoordinates.getParentTextBoxContentCoordinates() != null) {
        	CTTxbxContent textBoxContent = paragraphCoordinates.getParentTextBoxContentCoordinates().getTextBoxContent();
        	deleteFromTextBoxContent(textBoxContent, paragraphCoordinates.getIndex());
        	
        } else {
        	// global paragraph
        	int indexToDelete = paragraphCoordinates.getIndex() - objectsDeletedFromMainDocument;
        	document.getMainDocumentPart().getContent().remove(indexToDelete);
        	objectsDeletedFromMainDocument++;
        }
    }
    
    private void deleteFromParagraph(P p, int index) {
    	Integer objectsDeletedFromParent = deletedObjectsPerParent.get(p);
    	if (objectsDeletedFromParent == null) {
    		objectsDeletedFromParent = 0;
    	}
    	index -= objectsDeletedFromMainDocument;
    	p.getContent().remove(index);
    	if (!ParagraphUtil.hasAtLeastOneRunOrPict(p)) {
    		ParagraphUtil.addEmptyRun(p);
    	}
    	deletedObjectsPerParent.put(p, objectsDeletedFromParent + 1);
    }
    
    private void deleteFromTextBoxContent(CTTxbxContent textBoxContent, int index) {
    	Integer objectsDeletedFromParent = deletedObjectsPerParent.get(textBoxContent);
    	if (objectsDeletedFromParent == null) {
    		objectsDeletedFromParent = 0;
    	}
    	index -= objectsDeletedFromParent;
    	textBoxContent.getContent().remove(index);
    	if (!TextBoxContentUtil.hasAtLeastOneParagraph(textBoxContent)) {
    		TextBoxContentUtil.addEmptyParagraph(textBoxContent);
    	}
    	deletedObjectsPerParent.put(textBoxContent, objectsDeletedFromParent + 1);
    }
    
    private void deleteFromCell(Tc cell, int index) {
        Integer objectsDeletedFromParent = deletedObjectsPerParent.get(cell);
        if (objectsDeletedFromParent == null) {
            objectsDeletedFromParent = 0;
        }
        index -= objectsDeletedFromParent;
        cell.getContent().remove(index);
        if (!TableCellUtil.hasAtLeastOneParagraphOrTable(cell)) {
            TableCellUtil.addEmptyParagraph(cell);
        }
        deletedObjectsPerParent.put(cell, objectsDeletedFromParent + 1);
        // TODO: find out why border lines are removed in some cells after having deleted a paragraph
    }

    public void deleteTable(TableCoordinates tableCoordinates) {
        if (tableCoordinates.getParentTableCellCoordinates() == null) {
            // global table
            int indexToDelete = tableCoordinates.getIndex() - objectsDeletedFromMainDocument;
            document.getMainDocumentPart().getContent().remove(indexToDelete);
            objectsDeletedFromMainDocument++;
        } else {
            // nested table within an table cell
            Tc parentCell = tableCoordinates.getParentTableCellCoordinates().getCell();
            deleteFromCell(parentCell, tableCoordinates.getIndex());
        }
    }

    public void deleteTableRow(TableRowCoordinates tableRowCoordinates) {
        Tbl table = tableRowCoordinates.getParentTableCoordinates().getTable();
        int index = tableRowCoordinates.getIndex();
        Integer objectsDeletedFromTable = deletedObjectsPerParent.get(table);
        if (objectsDeletedFromTable == null) {
            objectsDeletedFromTable = 0;
        }
        index -= objectsDeletedFromTable;
        table.getContent().remove(index);
        deletedObjectsPerParent.put(table, objectsDeletedFromTable + 1);
    }
    
    public void deletePict(PictCoordinates pictCoordinates) {
//    	if (pictCoordinates.getParentParagraphCoordinates() == null) {
//    		int indexToDelete = pictCoordinates.getIndex() - objectsDeletedFromMainDocument;
//    		document.getMainDocumentPart().getContent().remove(indexToDelete);
//    		objectsDeletedFromMainDocument++;
//    	} else {
//    		P p = pictCoordinates.getParentParagraphCoordinates().getParagraph();
//    		deleteFromParagraph(p, pictCoordinates.getIndex());
//    	}
    }
    
    public void deleteShape(ShapeCoordinates shapeCoordinates) {
    	Pict pict = shapeCoordinates.getParentPictCoordinates().getPict();
    	int index = shapeCoordinates.getIndex();
    	Integer objectsDeletedFromPict = deletedObjectsPerParentPict.get(pict);
    	if (objectsDeletedFromPict == null) {
    		objectsDeletedFromPict = 0;
    	}
    	index -= objectsDeletedFromPict;
    	pict.getAnyAndAny().remove(index);
    	deletedObjectsPerParentPict.put(pict, objectsDeletedFromPict + 1);
    }
    
    public void deleteTextBox(TextBoxCoordinates textBoxCoordinates) {
    	CTShape shape = textBoxCoordinates.getParentShapeCoordinates().getShape();
    	int index = textBoxCoordinates.getIndex();
    	Integer objectsDeletedFromShape = deletedObjectsPerParentShape.get(shape);
    	if (objectsDeletedFromShape == null) {
    		objectsDeletedFromShape = 0;
    	}
    	index -= objectsDeletedFromShape;
    	shape.getEGShapeElements().remove(index);
    	deletedObjectsPerParentShape.put(shape, objectsDeletedFromShape + 1);
    }
    
    public void deleteTextBoxContent(TextBoxContentCoordinates textBoxContentCoordinates) {
    	CTTextbox textBox = textBoxContentCoordinates.getParentTextBoxCoordinates().getTextBox();
    	textBox.setTxbxContent(new CTTxbxContent());
    }
}
