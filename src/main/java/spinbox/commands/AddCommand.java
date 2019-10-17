package spinbox.commands;

import spinbox.DateTime;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.entities.Notepad;
import spinbox.entities.items.File;
import spinbox.entities.Module;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Storage;
import spinbox.Ui;
import spinbox.entities.items.tasks.Deadline;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Task;
import spinbox.entities.items.tasks.Todo;
import spinbox.entities.items.tasks.Schedulable;
import spinbox.containers.lists.TaskList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddCommand extends Command {
    private static final String MODULE_ADDED = "The following module has been added to SpinBox: ";
    private static final String MODULE_NOT_ADDED = "A module with this code already exists in SpinBox.";
    private static final String NON_EXISTENT_MODULE = "This module does not exist.";
    private static final String NOTE_ADDED = "A new note has been successfully added to ";
    private String type;
    private String fullCommand;

    private String moduleCode;
    private String content;

    /**
     * Constructor for initialization of variables to support addition of entities.
     * @param moduleCode A String denoting the module code.
     * @param content A string containing the content of the processed user input.
     */
    public AddCommand(String moduleCode, String content) {
        this.moduleCode = moduleCode;
        this.content = content;
        this.type = content.split(" ")[0];
    }

    /**
     * Constructor for creation of Task objects, does some input checking.
     * @param components  Components of the full command
     * @param fullCommand the full command
     * @throws SpinBoxException Can throw from invalid input or storage errors
     */
    public AddCommand(String[] components, String fullCommand) throws SpinBoxException {
        this.type = components[0];
        this.fullCommand = fullCommand;

        if (components.length == 1) {
            throw new InputException("☹ OOPS!!! The description of a task cannot be empty.");
        } else if (this.type.equals("deadline") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of a deadline cannot be empty.");
        } else if (this.type.equals("event") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an event cannot be empty.");
        } else if (this.type.equals("exam") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an exam cannot be empty.");
        } else if (this.type.equals("tutorial") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an tutorial cannot be empty.");
        } else if (this.type.equals("lab") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an lab cannot be empty.");
        } else if (this.type.equals("lecture") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an lecture cannot be empty.");
        }
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui) throws
            SpinBoxException {
        Task added;
        DateTime start;
        DateTime end;
        try {
            switch (type) {
            case "file":
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    FileList files = module.getFiles();
                    String fileName = content.replace(type.concat(" "), "");
                    files.add(new File(0, fileName));
                    return "Added into " + module.toString() + " file: " + fileName;
                } else {
                    return NON_EXISTENT_MODULE;
                }

            case "note":
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    Notepad notepad = module.getNotepad();
                    String noteContent = content.replace(type.concat(" "), "");
                    notepad.addLine(noteContent);
                    return NOTE_ADDED + moduleCode;
                } else {
                    return NON_EXISTENT_MODULE;
                }

            case "todo":
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    TaskList tasks = module.getTasks();
                    String taskDescription = content.replace(type.concat(" "), "");
                    added = tasks.add(new Todo(taskDescription));
                    return "Added into " + module.toString() + " task: " + added.toString() + "\n"
                            + "You currently have " + tasks.getList().size()
                            + ((tasks.getList().size() == 1) ? " task in the list." : " tasks in the list.");
                } else {
                    return NON_EXISTENT_MODULE;
                }

            case "deadline":
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    TaskList tasks = module.getTasks();
                    String taskDescription = content.replace(type.concat(" "), "");
                    start = new DateTime(taskDescription.split("/by ")[1]);
                    added = tasks.add(new Deadline(taskDescription.substring(0, taskDescription.lastIndexOf(" /by")),
                            start));
                    return "Added into " + module.toString() + " task: " + added.toString() + "\n"
                            + "You currently have " + tasks.getList().size()
                            + ((tasks.getList().size() == 1) ? " task in the list." : " tasks in the list.");
                } else {
                    return NON_EXISTENT_MODULE;
                }

            case "exam":
            case "tutorial":
            case "lecture":
            case "lab":
                if (moduleContainer.checkModuleExists(moduleCode)) {
                    HashMap<String, Module> modules = moduleContainer.getModules();
                    Module module = modules.get(moduleCode);
                    TaskList tasks = module.getTasks();
                    String taskDescription = content.replace(type.concat(" "), "");
                    start = new DateTime(taskDescription.split("/at ")[1], 0);
                    end = new DateTime(taskDescription.split("/at ")[1], 1);
                    List<Task> tasksList = tasks.getList();
                    for (int i = 0; i < tasksList.size(); i++) {
                        Task currentTask = tasksList.get(i);
                        if (currentTask.isSchedulable()) {
                            if (((Schedulable) currentTask).isOverlapping(start, end)) {
                                throw new InputException("Time conflicting with:\n"
                                        + "    " + (i + 1) + "." + currentTask.toString() + "\n"
                                        + "Please choose another time interval.");
                            }
                        }
                    }
                    added = tasks.add(new Event(taskDescription.substring(0, taskDescription.lastIndexOf(" /at")),
                            start, end));
                    return "Added into " + module.toString() + " task: " + added.toString() + "\n"
                            + "You currently have " + tasks.getList().size()
                            + ((tasks.getList().size() == 1) ? " task in the list." : " tasks in the list.");
                } else {
                    return NON_EXISTENT_MODULE;
                }

            default:
                if (!moduleContainer.checkModuleExists(moduleCode)) {
                    String moduleName = this.content;
                    Module module = new Module(this.moduleCode, moduleName);
                    moduleContainer.addModule(module);
                    return MODULE_ADDED + module.toString();
                } else {
                    return MODULE_NOT_ADDED;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Please ensure that you enter the full command.\n"
                    + "SpinBox.Tasks.Deadline: deadline <task name> /by <MM/DD/YYYY HH:MM>\n"
                    + "SpinBox.Tasks.Do-After: do-after <task name> /after <do-after event or time>\n"
                    + "SpinBox.Tasks.Event: event <task name> /at <start as MM/DD/YYYY HH:MM> "
                    + "to <end as MM/DD/YYYY HH:MM>\n"
                    + "SpinBox.Tasks.Fixed: fixed <task name> /needs <fixed task duration>\n"
                    + "SpinBox.Tasks.Tentative: tentative <task name> /around <start as MM/DD/YYYY HH:MM> "
                    + "to <end as MM/DD/YYYY HH:MM>\n"
                    + "SpinBox.Tasks.Within: do-within <task name> /between <start as MM/DD/YYYY HH:MM> "
                    + "and <end as MM/DD/YYYY HH:MM>\n"
                    + "SpinBox.Tasks.Recurring: recurring <task name> /at <start as MM/DD/YYYY HH:MM> "
                    + "to <end as MM/DD/YYYY HH:MM> /every DD:HH:MM"
            );
        }
    }
}