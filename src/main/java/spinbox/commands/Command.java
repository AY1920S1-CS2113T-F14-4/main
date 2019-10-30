package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.exceptions.InputException;
import spinbox.exceptions.SpinBoxException;
import spinbox.storage.Storage;
import spinbox.Ui;
import spinbox.containers.lists.TaskList;

import java.util.ArrayDeque;

public abstract class Command {
    private static final String NOT_ON_MODULE_PAGE = "Please be on a module page or indicate "
            + "the specific module this action is for.";
    private boolean isExit;

    public abstract String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui,
           boolean guiMode) throws SpinBoxException;

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    /**
     * Check when there is a moduleCode indicated from the constructor.
     * @param moduleCode the moduleCode variable.
     * @return true if there is a moduleCode indicated, false otherwise.
     * @throws InputException if there is no moduleCode indicated, tell User.
     */
    public boolean checkIfOnModulePage(String moduleCode) throws InputException {
        if (moduleCode == null) {
            throw new InputException(NOT_ON_MODULE_PAGE);
        }
        return true;
    }
}
