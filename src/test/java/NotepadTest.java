import org.junit.jupiter.api.Test;
import spinbox.entities.Notepad;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.InvalidIndexException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotepadTest {

    @Test
    public void notepadCreation_oneNewNotepad_successfulCreationAndWorkingGetters() throws FileCreationException {
        Notepad testPad = new Notepad("testModule");
        assertEquals(testPad.getNotes().size(),0);
    }

    @Test
    public void notepadAddLine_addNewLineToNewNotepad_successfulAddition() throws FileCreationException,
            DataReadWriteException {
        Notepad testPad = new Notepad("testModule");
        testPad.addLine("test line");
        assertEquals(testPad.getNotes().get(0), "test line");
    }

    @Test
    public void notepadUpdateLine_AddThenUpdateLineInNewNotepad_successfulUpdateAndIndexException() throws
            DataReadWriteException, InvalidIndexException, FileCreationException {
        Notepad testPad = new Notepad("testModule");
        testPad.addLine("test line");

        testPad.updateLine(0, "test line2");
        assertEquals(testPad.getNotes().get(0), "test line2");
        assertThrows(InvalidIndexException.class, () -> {
            testPad.updateLine(3, "test line3");
        });
        assertEquals(testPad.getNotes().get(0), "test line2");
    }

    @Test
    public void notepadRemoveLine_AddMultipleLinesThenRemoveOne_successfulRemoveAndIndexException() throws
            InvalidIndexException, DataReadWriteException, FileCreationException {
        Notepad testPad = new Notepad("testModule");
        testPad.addLine("test line0");
        testPad.addLine("test line1");
        testPad.addLine("test line2");
        testPad.addLine("test line3");

        testPad.removeLine(2);
        assertEquals(testPad.getNotes().size(), 3);
        assertEquals(testPad.getNotes().get(2), "test line3");
        assertThrows(InvalidIndexException.class, () -> {
            testPad.removeLine(3);
        });
    }

    @Test
    public void loadDataSuccessful_AddLinesThenManualClear_successfulRepopulationOfData() throws
            DataReadWriteException, FileCreationException {
        Notepad testPad = new Notepad("testModule");
        testPad.addLine("test line0");
        testPad.addLine("test line1");
        testPad.addLine("test line2");
        testPad.addLine("test line3");

        testPad.getNotes().clear();
        testPad.loadData();

        assertEquals(testPad.getNotes().get(0), "test line0");
        assertEquals(testPad.getNotes().get(1), "test line1");
        assertEquals(testPad.getNotes().get(2), "test line2");
        assertEquals(testPad.getNotes().get(3), "test line3");
    }
}
