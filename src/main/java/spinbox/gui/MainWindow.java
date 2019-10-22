package spinbox.gui;

import javafx.scene.control.TabPane;
import spinbox.SpinBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private TabPane tabPane;
    @FXML
    private VBox urgentTasks;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private SpinBox spinBox;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
    }

    public void setSpinBox(SpinBox d) {
        spinBox = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing SpinBox's reply and then appends
     * them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = spinBox.getResponse(input, true);
        switch (response) {
        case "/main":
            tabPane.getSelectionModel().select(0);
            break;
        case "/calendar":
            tabPane.getSelectionModel().select(1);
            break;
        case "/modules":
            tabPane.getSelectionModel().select(2);
            break;
        default:
            break;
        }
        userInput.clear();
        if (spinBox.isShutdown()) {
            System.exit(0);
        }
    }

    private void update() {
        updateMain();
        updateCalendar();
        updateModules();
    }

    private void updateMain() {
        updateUrgentTask();
        updateExams();
        updateModules();
    }

    private void updateUrgentTask() {
        urgentTasks.getChildren().clear();
    }

    private void updateExams() {
        assert true;
    }

    private void updateCalendar() {
        assert true;
    }

    private void updateModules() {
        assert true;
    }
}
