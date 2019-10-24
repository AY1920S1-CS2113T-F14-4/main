package spinbox.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ModuleListBox extends VBox {
    @FXML
    private Label moduleNameLabel;
    @FXML
    private Label moduleCodeLabel;

    private ModuleListBox(String moduleCode, String moduleName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/ModuleListBox/ModuleListBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setStyle("-fx-border-color: #FFF");
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(10.0);
        setMargin(this, new Insets(10, 10, 10, 10));
        this.moduleCodeLabel.setStyle("-fx-font-weight: bold");
        this.moduleCodeLabel.setTextFill(Color.WHITE);


        this.moduleCodeLabel.setText(moduleCode);
        this.moduleNameLabel.setText(moduleName);
    }

    static ModuleListBox getModuleListBox(String moduleCode, String moduleName) {
        return new ModuleListBox(moduleCode, moduleName);
    }
}
