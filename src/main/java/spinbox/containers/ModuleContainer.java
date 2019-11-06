package spinbox.containers;

import spinbox.entities.Module;
import spinbox.storage.Storage;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.storage.StorageContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleContainer implements StorageContainer {
    private static final String DIRECTORY_NAME = "SpinBoxData/";
    private static final String MODULES_FILE_NAME = "modules.txt";

    private HashMap<String, Module> modules;
    private Storage localStorage;

    /**
     * Constructor for a module container. Retrieves added modules, populates them and stores in program memory.
     * @throws FileCreationException Creation of file hierarchy failed, perhaps due to permissions.
     * @throws DataReadWriteException I/O error during file read/writes.
     * @throws CorruptedDataException Text files have been improperly modified (unexpected formatting).
     */
    public ModuleContainer() throws FileCreationException, DataReadWriteException, CorruptedDataException {
        modules = new HashMap<>();
        localStorage = new Storage(DIRECTORY_NAME + MODULES_FILE_NAME);
        this.loadData();
    }

    /**
     * Method call returns the modules contained within this moduleContainer instance.
     * @return HashMap of String : Module of modules.
     */
    public HashMap<String, Module> getModules() {
        return modules;
    }

    /**
     * Saves data using the localStorage instance to the relevant .txt file.
     * @throws DataReadWriteException I/O error.
     */
    @Override
    public void saveData() throws DataReadWriteException {
        List<String> dataToSave = new ArrayList<>();
        for (Map.Entry<String, Module> entry : modules.entrySet()) {
            dataToSave.add(entry.getValue().storeString());
        }
        localStorage.saveData(dataToSave);
    }

    /**
     * Adds a module to be saved to the module container.
     * @param module Module object to be added.
     * @return the Module object which has been stored.
     * @throws DataReadWriteException I/O Error.
     */
    public Module addModule(Module module) throws DataReadWriteException {
        this.getModules().put(module.getModuleCode(), module);
        this.saveData();
        return module;
    }

    /**
     * Check the existence of a module within SpinBox.
     * @param moduleCode A String denoting the module code.
     * @return True if the module already exists.
     */
    public boolean checkModuleExists(String moduleCode) {
        return this.getModules().containsKey(moduleCode);
    }

    /**
     * Gets a specific module from within the container.
     * @param moduleCode A string containing the module code to be used as the key.
     * @return a Module object, or null if no such module exists.
     */
    public Module getModule(String moduleCode) {
        if (this.checkModuleExists(moduleCode)) {
            return this.getModules().get(moduleCode);
        } else {
            return null;
        }
    }

    /**
     * Loads data using the localStorage instance from the relevant .txt file.
     * @throws DataReadWriteException I/O error.
     * @throws CorruptedDataException polluted data within txt files.
     */
    @Override
    public void loadData() throws DataReadWriteException, CorruptedDataException {
        List<String> savedData = localStorage.loadData();
        for (String datum : savedData) {
            Module module = new Module();
            module.fromStoredString(datum);
            this.modules.put(module.getModuleCode(), module);
        }
    }
}
