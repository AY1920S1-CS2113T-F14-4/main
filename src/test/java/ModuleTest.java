import org.junit.jupiter.api.Test;
import spinbox.entities.Module;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.StorageException;
import spinbox.entities.items.File;
import spinbox.entities.items.GradedComponent;
import spinbox.entities.items.tasks.Todo;
import spinbox.containers.ModuleContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuleTest {

    @Test
    public void moduleCreation_variousModules_successfulCreationWithWorkingGetters() throws FileCreationException,
            CorruptedDataException, DataReadWriteException {
        ModuleContainer testContainer = new ModuleContainer();
        Module testModuleOne = new Module("testMod1", "Engineering Principles & Practice I");

        assertEquals(testModuleOne.getModuleCode(), "testMod1");
        assertEquals(testModuleOne.getModuleName(), "Engineering Principles & Practice I");
        assertEquals(testModuleOne.storeString(), "testMod1 | Engineering Principles & Practice I");

        Module testModuleTwo = new Module("testMod2", "Discrete Structures");

        assertEquals(testModuleTwo.getModuleCode(), "testMod2");
        assertEquals(testModuleTwo.getModuleName(), "Discrete Structures");
        assertEquals(testModuleTwo.storeString(), "testMod2 | Discrete Structures");

        Module testModuleThree = new Module("testMod3", "Engineering Principles & Practice II");

        assertEquals(testModuleThree.getModuleCode(), "testMod3");
        assertEquals(testModuleThree.getModuleName(), "Engineering Principles & Practice II");
        assertEquals(testModuleThree.storeString(), "testMod3 | Engineering Principles & Practice II");
    }

    @Test
    public void storageStringRecreation_oneModule_expectedRecreatedObject() throws StorageException {
        ModuleContainer testContainer = new ModuleContainer();
        Module testModuleOne = new Module("testMod4", "Engineering Principles & Practice I");

        assertEquals(testModuleOne.getModuleCode(), "testMod4");
        assertEquals(testModuleOne.getModuleName(), "Engineering Principles & Practice I");

        Module testModuleOneRecreated = new Module(testModuleOne.storeString());
        assertEquals(testModuleOneRecreated.getModuleCode(), "testMod4");
        assertEquals(testModuleOneRecreated.getModuleName(), "Engineering Principles & Practice I");
        assertEquals(testModuleOneRecreated.storeString(), "testMod4 | Engineering Principles & Practice I");
    }

    @Test
    public void loadDataFromStorageSuccessful_oneModule_expectedFilesLoaded() throws StorageException {
        ModuleContainer testContainer = new ModuleContainer();
        Module testModuleOne = new Module("testMod5", "Engineering Principles & Practice III");

        testModuleOne.getFiles().add(new File(0, "testFile1"));
        testModuleOne.getGrades().add(new GradedComponent("Essay", 20));
        testModuleOne.getTasks().add(new Todo("test todo"));
        testModuleOne.getNotepad().addLine("hello123");

        testModuleOne.getFiles().getList().clear();
        testModuleOne.getGrades().getList().clear();
        testModuleOne.getTasks().getList().clear();
        testModuleOne.getNotepad().getNotes().clear();

        testModuleOne.loadData();

        assertEquals(testModuleOne.getFiles().getList().remove(0).storeString(),
                new File(0, "testFile1").storeString());

        assertEquals(testModuleOne.getGrades().getList().remove(0).storeString(),
                new GradedComponent("Essay", 20).storeString());

        assertEquals(testModuleOne.getTasks().getList().remove(0).storeString(),
                new Todo("test todo").storeString());

        assertEquals(testModuleOne.getNotepad().getNotes().remove(0), "hello123");
    }
}
