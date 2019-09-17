package duke;

import com.joestelmach.natty.Parser;
import duke.exceptions.DukeException;
import duke.exceptions.StorageException;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.Todo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Storage {
    private File dukeFile;

    /**
     * This constructor creates the file if needed.
     * @param fileLocation relative path of the text file to store data in.
     */
    public Storage(String fileLocation) throws DukeException {
        try {
            dukeFile = new File(fileLocation);
            if (dukeFile.getParentFile().mkdir()) {
                dukeFile.createNewFile();
            }
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }

    /**
     * This method retrieves data from the text file, and constructs objects to insert back into the list.
     * @return tasks
     */
    public List<Task> loadData() throws DukeException {
        Vector<Task> tasks = new Vector<>();
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(dukeFile));
            while (true) {
                String currentLine = inputStream.readLine();
                if (currentLine == null) {
                    break;
                } else {
                    String[] arguments = currentLine.split(" \\| ");
                    switch (arguments[0]) {
                    case "T":
                        tasks.addElement(new Todo(Integer.parseInt(arguments[1]), arguments[2]));
                        break;
                    case "D":
                        Date by = parser.parse(arguments[3]).get(0).getDates().get(0);
                        tasks.addElement(new Deadline(Integer.parseInt(arguments[1]), arguments[2], by));
                        break;
                    default:
                        Date start = parser.parse(arguments[3]).get(0).getDates().get(0);
                        Date end = parser.parse(arguments[4]).get(0).getDates().get(0);
                        tasks.addElement(new Event(Integer.parseInt(arguments[1]), arguments[2], start, end));
                    }
                }
            }
            inputStream.close();
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
        return tasks;
    }

    /**
     * This method takes in a vector and calls each task's storeString method to store its data in the correct format.
     * @param tasks A vector of tasks currently in the program.
     */
    public void setData(List<Task> tasks) throws DukeException {
        try {
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(dukeFile));
            for (Task task : tasks) {
                outputStream.write(task.storeString());
                outputStream.newLine();
            }
            outputStream.close();
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }
}
