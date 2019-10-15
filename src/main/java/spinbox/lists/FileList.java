package spinbox.lists;

import spinbox.Storage;
import spinbox.exceptions.SpinBoxException;
import spinbox.items.File;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileList extends SpinBoxItemList<File> {
    private static final String FILE_LIST_FILE_NAME = "files.txt";

    public FileList(String parentName) throws SpinBoxException {
        super(parentName);
        localStorage = new Storage(this.getParentCode() + FILE_LIST_FILE_NAME);
    }

    public FileList(List<File> files, String parentName) throws SpinBoxException {
        super(files, parentName);
        localStorage = new Storage(this.getParentCode() + FILE_LIST_FILE_NAME);
    }

    /**
     * Does not order the files at the moment as not sure how to order yet.
     */
    static class FileComparator implements Comparator<File> {
        @Override
        public int compare(File a, File b) {
            return -1;
        }
    }

    public void sort() {
        Collections.sort(list, new FileComparator());
    }
}
