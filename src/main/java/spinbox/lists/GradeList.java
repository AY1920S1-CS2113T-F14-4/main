package spinbox.lists;

import spinbox.Storage;
import spinbox.exceptions.SpinBoxException;
import spinbox.items.GradedComponent;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GradeList extends SpinBoxItemList<GradedComponent> {
    private static final String GRADE_LIST_FILE_NAME = "grades.txt";

    public GradeList(String parentName) throws SpinBoxException {
        super(parentName);
        localStorage = new Storage(this.getParentCode() + GRADE_LIST_FILE_NAME);
    }

    public GradeList(List<GradedComponent> grades, String parentName) throws SpinBoxException {
        super(grades, parentName);
        localStorage = new Storage(this.getParentCode() + GRADE_LIST_FILE_NAME);
    }

    /**
     * Order the grade components based on descending weight.
     */
    static class GradedComponentComparator implements Comparator<GradedComponent> {
        @Override
        public int compare(GradedComponent a, GradedComponent b) {
            return (a.getWeight() > b.getWeight()) ? -1 : 0;
        }
    }

    public void sort() {
        Collections.sort(list, new GradedComponentComparator());
    }
}
