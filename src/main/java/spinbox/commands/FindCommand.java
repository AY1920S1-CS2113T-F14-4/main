package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.containers.lists.GradeList;
import spinbox.containers.lists.SpinBoxList;
import spinbox.entities.Module;
import spinbox.entities.items.tasks.Task;
import spinbox.exceptions.InputException;
import spinbox.containers.lists.TaskList;
import spinbox.Ui;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayDeque;
import java.util.HashMap;

public class FindCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(FindCommand.class.getName());
    private static final String LOG_MODULE_CODE = "Module code is ";
    private static final String LOG_NO_MODULE_CODE = "No module code indicated.";
    private static final String LOG_EMPTY_KEYWORD = "Keyword is empty.";
    private static final String LOG_NON_EXISTENT_MODULE = "Module does not exist.";
    private static final String LOG_UNKNOWN_ITEM_TYPE = "Unknown item type.";
    private static final String LOG_FILE_LIST = "Get file list.";
    private static final String LOG_TASK_LIST = "Get task list.";
    private static final String LOG_GRADE_LIST = "Get grade list.";

    private static final String UNKNOWN_ITEM_TYPE = "Sorry, unknown item type to add.";
    private static final String FIND_ERROR_MESSAGE = "Please ensure that you enter "
            + "the full command for find commands:\n";
    private static final String FIND_FORMAT = "find <moduleCode> / <type> <keyword>\n";
    private static final String NO_MODULE_CODE = "No module code indicated.";
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";

    private String type;
    private String moduleCode;
    private String content;
    private String keyword;

    /**
     * Constructor for finding tasks using a keyword.
     * @param pageDataComponents pageDataComponents.
     * @param content A string containing the content of the processed user input.
     * @throws InputException missing keyword
     */
    public FindCommand(String[] pageDataComponents, String content) throws InputException {
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(true);
        LOGGER.entering(getClass().getName(), "Constructor");

        if (pageDataComponents.length > 1) {
            this.moduleCode = pageDataComponents[1];
            LOGGER.fine(LOG_MODULE_CODE + moduleCode);
        } else {
            LOGGER.severe(LOG_NO_MODULE_CODE);
            throw new InputException(FIND_ERROR_MESSAGE + FIND_FORMAT);
        }

        assert !moduleCode.isEmpty();

        this.content = content;
        this.type = content.split(" ")[0].toLowerCase();

        LOGGER.exiting(getClass().getName(), "Constructor");
    }

    /**
     * Finds the items in the list containing keyword in the name.
     * @param moduleContainer Container of all the modules.
     * @param pageTrace Contains information on the current page.
     * @param ui Instance of UI.
     * @param guiMode Boolean to check if in gui mode.
     * @return The display once it has been changed.
     * @throws SpinBoxException If the item type is unknown.
     */
    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) throws
            SpinBoxException {
        LOGGER.entering(getClass().getName(), "execute");

        String[] contentComponents = content.split(" ", 2);
        try {
            keyword = contentComponents[1].trim();
            assert !keyword.isEmpty();
        } catch (IndexOutOfBoundsException e) {
            LOGGER.severe(LOG_EMPTY_KEYWORD);
            return FIND_ERROR_MESSAGE + FIND_FORMAT;
        }

        checkIfOnModulePage(moduleCode);
        assert checkIfOnModulePage(moduleCode) : NO_MODULE_CODE;

        List<String> contains;
        Module module;
        HashMap<String, Module> modules;

        if (moduleContainer.checkModuleExists(moduleCode)) {
            modules = moduleContainer.getModules();
            module = modules.get(moduleCode);
            switch (type) {
            case "file":
                FileList fileList = module.getFiles();
                contains = fileList.containsKeyword(keyword);
                LOGGER.fine(LOG_FILE_LIST);
                break;
            case "task":
                TaskList taskList = module.getTasks();
                contains = taskList.containsKeyword(keyword);
                LOGGER.fine(LOG_TASK_LIST);
                break;
            case "grade":
                GradeList gradeList = module.getGrades();
                contains = gradeList.containsKeyword(keyword);
                LOGGER.fine(LOG_GRADE_LIST);
                break;
            default:
                LOGGER.severe(LOG_UNKNOWN_ITEM_TYPE);
                throw new InputException(UNKNOWN_ITEM_TYPE);
            }
        } else {
            LOGGER.severe(LOG_NON_EXISTENT_MODULE);
            return NON_EXISTENT_MODULE;
        }

        LOGGER.exiting(getClass().getName(), "execute");
        return ui.showFormatted(contains);
    }
}
