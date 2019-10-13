package duke;

import duke.commands.AddCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.MultipleCommand;
import duke.commands.DoneCommand;
import duke.commands.ExitCommand;
import duke.commands.UnknownCommand;
import duke.commands.ReminderCommand;
import duke.commands.ListCommand;
import duke.commands.ViewScheduleCommand;
import duke.commands.FileCommand;
import duke.commands.FindCommand;
import duke.commands.SnoozeCommand;
import duke.commands.SetCommand;
import duke.commands.FindFreeCommand;
import duke.exceptions.DukeException;
import duke.exceptions.InputException;

import java.util.Stack;

public class Parser {
    private static Stack<String> commandLog = new Stack<>();

    private static Stack<String> getCommandLog() {
        return commandLog;
    }

    /**
     * Builds a partial user input string into a full command string with the necessary details.
     * @param initialComponents String array of tokenized partial user input.
     * @return Prepended full user input with the necessary command details.
     * @throws InputException If the first user input is already incomplete.
     */
    public static String commandBuilder(String[] initialComponents) throws InputException {
        StringBuilder fullUserInput = new StringBuilder();
        for (int i = 1; i < initialComponents.length; i++) {
            if (i == 1) {
                fullUserInput.append(initialComponents[i]);
            } else {
                fullUserInput.append(" ");
                fullUserInput.append(initialComponents[i]);
            }
        }
        Stack<String> cloneLog = new Stack<>();
        cloneLog.addAll(getCommandLog());
        if (getCommandLog().empty()) {
            throw new InputException("Please input the full command in this format: <action> <page> "
                    + "<necessary information>. E.g. Delete modules CG1111 files 1");
        }
        while (!cloneLog.empty()) {
            String individualInput = cloneLog.peek();
            fullUserInput.insert(0, " ");
            fullUserInput.insert(0, individualInput);
            cloneLog.pop();
        }
        fullUserInput.insert(0, " ");
        fullUserInput.insert(0, initialComponents[0]);
        return fullUserInput.toString();
    }

    /**
     * Builds up the stack for the information necessary to be remembered when a full command is entered.
     * @param componentsLength Length of the String array containing the tokenized full user input.
     * @param components String array of the tokenized full user input.
     */
    public static void stackBuilder(int componentsLength, String[] components) {
        for (int i = 1; i < componentsLength - 1; i++) {
            getCommandLog().push(components[i]);
        }
    }

    /**
     * Parses an input string into a workable command.
     * @param input user typed in this string.
     * @return a Command that can executed.
     * @throws DukeException Storage errors or input errors.
     */
    public static Command parse(String input) throws DukeException {
        Command command;
        String[] initialComponents = input.split(" ");
        if (initialComponents[0].equals("bye")) {
            command = new ExitCommand();
            return command;
        }
        if (initialComponents[0].equals("list")) {
            command = new ListCommand();
            return command;
        }
        try {
            if (!initialComponents[1].equals("main") && !initialComponents[1].equals("modules")
                    && !initialComponents[1].equals("calendar")) {
                input = commandBuilder(initialComponents);
            }
            getCommandLog().clear();
            String[] components = input.split(" ");
            int componentsLength = components.length;
            switch (components[0]) {
            case "done":
                stackBuilder(componentsLength, components);
                command = new DoneCommand(Integer.parseInt(components[componentsLength - 1]) - 1);
                break;

            case "remind":
                command = new ReminderCommand();
                break;

            case "delete":
                stackBuilder(componentsLength, components);
                command = new DeleteCommand(Integer.parseInt(components[componentsLength - 1]) - 1);
                break;

            case "delete-multiple":
            case "done-multiple":
                stackBuilder(componentsLength, components);
                command = new MultipleCommand(components[0], components[componentsLength - 1]);
                break;

            case "find":
                command = new FindCommand(components, input);
                break;

            case "view-schedule":
                command = new ViewScheduleCommand(components, input);
                break;

            case "view":
                stackBuilder(componentsLength + 1, components);
                command = null; // to be developed
                break;

            case "todo":
            case "deadline":
            case "do-within":
            case "do-after":
            case "event":
            case "tentative":
            case "fixed":
            case "recurring":
                stackBuilder(componentsLength, components);
                command = new AddCommand(components, input);
                break;

            case "set-tentative":
                command = new SetCommand(components, input);
                break;

            case "snooze":
                command = new SnoozeCommand(Integer.parseInt(components[1]) - 1, components, input);
                break;

            case "free":
                command = new FindFreeCommand(components[1]);
                break;

            case "file":
                command = new FileCommand(components[1], input);
                break;

            default:
                command = new UnknownCommand();
            }
            return command;
        } catch (NumberFormatException e) {
            throw new InputException("Invalid index entered. Type 'list' to see your list.");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InputException("Please provide an index or action. Eg. 'done 5', 'delete 3', 'file view'");
        }
    }
}