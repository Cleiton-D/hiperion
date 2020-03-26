package org.wickedsource.docxstamper.processor.displayif;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.wickedsource.docxstamper.api.coordinates.ParagraphCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TableCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TableRowCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.PictCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.ShapeCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.TextBoxContentCoordinates;
import org.wickedsource.docxstamper.api.coordinates.TextBox.TextBoxCoordinates;
import org.wickedsource.docxstamper.processor.BaseCommentProcessor;
import org.wickedsource.docxstamper.processor.CommentProcessingException;
import org.wickedsource.docxstamper.util.ObjectDeleter;

import java.util.ArrayList;
import java.util.List;

public class DisplayIfProcessor extends BaseCommentProcessor implements IDisplayIfProcessor {

	private List<ParagraphCoordinates> paragraphsToBeRemoved = new ArrayList<>();

	private List<TableCoordinates> tablesToBeRemoved = new ArrayList<>();

	private List<TableRowCoordinates> tableRowsToBeRemoved = new ArrayList<>();
	
	private List<PictCoordinates> pictToBeRemoved = new ArrayList<>();
	
	private List<ShapeCoordinates> shapeToBeRemoved = new ArrayList<>();
	
	private List<TextBoxCoordinates> textBoxToBeRemoved = new ArrayList<>();
	
	private List<TextBoxContentCoordinates> textBoxContentToBeRemoved = new ArrayList<>();

	@Override
	public void commitChanges(WordprocessingMLPackage document) {
		ObjectDeleter deleter = new ObjectDeleter(document);
		removeParagraphs(deleter);
		removeTables(deleter);
		removeTableRows(deleter);
		removePicts(deleter);
		removeShapes(deleter);
		removeTextBox(deleter);
		removeTextBoxContent(deleter);
	}

	@Override
	public void reset() {
		paragraphsToBeRemoved = new ArrayList<>();
		tablesToBeRemoved = new ArrayList<>();
		tableRowsToBeRemoved = new ArrayList<>();
		pictToBeRemoved = new ArrayList<>();
		shapeToBeRemoved = new ArrayList<>();
		textBoxToBeRemoved = new ArrayList<>();
		textBoxContentToBeRemoved = new ArrayList<>();
	}

	private void removeParagraphs(ObjectDeleter deleter) {
		for (ParagraphCoordinates pCoords : paragraphsToBeRemoved) {
			deleter.deleteParagraph(pCoords);
		}
	}

	private void removeTables(ObjectDeleter deleter) {
		for (TableCoordinates tCoords : tablesToBeRemoved) {
			deleter.deleteTable(tCoords);
		}
	}

	private void removeTableRows(ObjectDeleter deleter) {
		for (TableRowCoordinates rCoords : tableRowsToBeRemoved) {
			deleter.deleteTableRow(rCoords);
		}
	}

	private void removePicts(ObjectDeleter deleter) {
		for (PictCoordinates pCoords : pictToBeRemoved) {
			deleter.deletePict(pCoords);
		}
	}
	
	private void removeShapes(ObjectDeleter deleter) {
		for (ShapeCoordinates sCoords : shapeToBeRemoved) {
			deleter.deleteShape(sCoords);
		}
	}
	
	private void removeTextBox(ObjectDeleter deleter) {
		for (TextBoxCoordinates tCoords : textBoxToBeRemoved) {
			deleter.deleteTextBox(tCoords);
		}
	}
	
	private void removeTextBoxContent(ObjectDeleter deleter) {
		for (TextBoxContentCoordinates tbcCoords : textBoxContentToBeRemoved) {
			deleter.deleteTextBoxContent(tbcCoords);
		}
	}
	
	
	@Override
	public void displayParagraphIf(Boolean condition) {
		if (!condition) {
			ParagraphCoordinates coords = getCurrentParagraphCoordinates();
			paragraphsToBeRemoved.add(coords);
		}
	}

	@Override
	public void displayTableIf(Boolean condition) {
		if (!condition) {
			ParagraphCoordinates pCoords = getCurrentParagraphCoordinates();
			if (pCoords.getParentTableCellCoordinates() == null ||
					pCoords.getParentTableCellCoordinates().getParentTableRowCoordinates() == null ||
					pCoords.getParentTableCellCoordinates().getParentTableRowCoordinates().getParentTableCoordinates() == null) {
				throw new CommentProcessingException("Paragraph is not within a table!", pCoords);
			}
			tablesToBeRemoved.add(pCoords.getParentTableCellCoordinates().getParentTableRowCoordinates().getParentTableCoordinates());
		}
	}

	@Override
	public void displayTableRowIf(Boolean condition) {
		if (!condition) {
			ParagraphCoordinates pCoords = getCurrentParagraphCoordinates();
			if (pCoords.getParentTableCellCoordinates() == null ||
					pCoords.getParentTableCellCoordinates().getParentTableRowCoordinates() == null) {
				throw new CommentProcessingException("Paragraph is not within a table!", pCoords);
			}
			tableRowsToBeRemoved.add(pCoords.getParentTableCellCoordinates().getParentTableRowCoordinates());
		}
	}
	
	@Override
	public void displayPictIf(Boolean condition) {
		if (!condition) {
			ParagraphCoordinates pCoords = getCurrentParagraphCoordinates();
			if (pCoords.getParentTextBoxContentCoordinates() == null ||
					pCoords.getParentTextBoxContentCoordinates().getParentTextBoxCoordinates() == null ||
					pCoords.getParentTextBoxContentCoordinates().getParentTextBoxCoordinates().getParentShapeCoordinates() == null ||
					pCoords.getParentTextBoxContentCoordinates().getParentTextBoxCoordinates().getParentShapeCoordinates().getParentPictCoordinates() == null) {
				throw new CommentProcessingException("Paragraph is not within a TextBox", pCoords);
			}
			pictToBeRemoved.add(pCoords.getParentTextBoxContentCoordinates().getParentTextBoxCoordinates().getParentShapeCoordinates().getParentPictCoordinates());
		}
	}
	
	@Override
	public void displayShapeIf(Boolean condition) {
		if (!condition) {
			ParagraphCoordinates pCoords = getCurrentParagraphCoordinates();
			if (pCoords.getParentTextBoxContentCoordinates() == null ||
					pCoords.getParentTextBoxContentCoordinates().getParentTextBoxCoordinates() == null ||
					pCoords.getParentTextBoxContentCoordinates().getParentTextBoxCoordinates().getParentShapeCoordinates() == null) {
				throw new CommentProcessingException("Paragraph is not within a TextBox", pCoords);
			}
			shapeToBeRemoved.add(pCoords.getParentTextBoxContentCoordinates().getParentTextBoxCoordinates().getParentShapeCoordinates());
		}
	}
	
	@Override
	public void displayTextBoxIf(Boolean condition) {
		if (!condition) {
			ParagraphCoordinates pCoords = getCurrentParagraphCoordinates();
			if (pCoords.getParentTextBoxContentCoordinates() == null ||
					pCoords.getParentTextBoxContentCoordinates().getParentTextBoxCoordinates() == null) {
				throw new CommentProcessingException("Paragraph is not within a TextBox", pCoords);
			}
			textBoxToBeRemoved.add(pCoords.getParentTextBoxContentCoordinates().getParentTextBoxCoordinates());
		}
	}
	
	@Override
	public void displayTextBoxContentIf(Boolean condition) {
		if (!condition) {
			ParagraphCoordinates pCoords = getCurrentParagraphCoordinates();
			if (pCoords.getParentTextBoxContentCoordinates() == null) {
				throw new CommentProcessingException("Paragraph is not within a TextBox", pCoords);
			}
			textBoxContentToBeRemoved.add(pCoords.getParentTextBoxContentCoordinates());
		}
	}
}
