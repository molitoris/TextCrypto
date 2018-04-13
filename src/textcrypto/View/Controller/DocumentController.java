/*
 * Copyright (C) 2018 Rafael S. Müller <rafael.mueller1@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package textcrypto.View.Controller;

import java.io.IOException;
import textcrypto.ContentModel;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import textcrypto.View.DialogAbout;
import textcrypto.View.Dialog;
import textcrypto.View.DialogFactory;
import textcrypto.View.DialogType;

/**
 *
 * @author Rafael S. Müller <rafael.mueller1@gmail.com>
 */
public class DocumentController implements Initializable {

    private final ContentModel model;
    private final DialogFactory factory;
    private Dialog decryptDialog;
    private Dialog encryptDialoag;
    private DialogAbout aboutView;

    @FXML
    private Button btnOpen, btnSave, btnClear;
    @FXML
    private TextArea txtContent, txtInfo;
    @FXML
    private TextField txtSearch;
    @FXML
    private MenuItem itemOpen, itemSave, itemClear, itemSearch, itemAbout;

    public DocumentController() {
        this.model = new ContentModel();
        factory = DialogFactory.getInstance(model);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtContent.textProperty().bindBidirectional(model.contentProperty());
        txtInfo.textProperty().bind(model.infoProperty());

        decryptDialog = factory.getDialog(DialogType.DECRYPT);
        encryptDialoag = factory.getDialog(DialogType.ENCRYPT);
        decryptDialog.setNode(btnOpen);
        encryptDialoag.setNode(btnOpen);

        aboutView = new DialogAbout(btnOpen);

        itemOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        itemSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        itemClear.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        itemSearch.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            int index = txtContent.getText().indexOf(newValue);
            if (index != -1) {
                txtContent.positionCaret(index);
            }
        });

        txtSearch.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                txtContent.requestFocus();
            }
        });

        Platform.runLater(() -> {
            txtContent.requestFocus();
        });
    }

    @FXML
    private void openBtnPressed(ActionEvent ae) throws IOException {
        checkForOpen();
    }

    @FXML
    private void saveBtnPressed(ActionEvent e) throws IOException {
        if (!txtContent.getText().trim().isEmpty()) {
            model.appendInfo("Decryption dialog opened");
            encryptDialoag.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Content is empty");
            alert.setHeaderText("You can not safe an empty file");
            alert.setContentText("Please add some text before saving");
            alert.showAndWait();
        }
    }

    @FXML
    private void clearBtnPressed(ActionEvent e) throws IOException {
        if (!model.getSafed() && !txtContent.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Clear content");
            alert.setHeaderText("The content has not been saved. When you clear\nthe conent, it will be lost.");
            alert.setContentText("How do you want to proceed?");

            ButtonType buttonTypeSave = new ButtonType("Save");
            ButtonType buttonTypeContinue = new ButtonType("Continue");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeContinue, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeContinue) {
                model.appendInfo("Main content cleared");
                model.setContent("");
            } else if (result.get() == buttonTypeSave) {
                model.appendInfo("Encryption dialog opened");
                encryptDialoag.show();
            }
        } else if (!txtContent.getText().isEmpty()) {
            model.appendInfo("Main content cleared");
            model.setContent("");
        }
    }

    @FXML
    private void txtSearchFocus(ActionEvent e) {
        txtSearch.requestFocus();
    }

    @FXML
    private void aboutItemClicked(ActionEvent e) throws IOException {
        aboutView.show();
    }

    private void checkForOpen() throws IOException {
        boolean isEmpty = txtContent.getText().trim().isEmpty();

        if (isEmpty || model.getSafed()) {
            model.appendInfo("Encryption dialog opened");
            decryptDialog.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Discard changes");
            alert.setHeaderText("The content has not been saved. When you open\na file, the current content will be lost.");
            alert.setContentText("How do you want to proceed?");

            ButtonType buttonTypeSave = new ButtonType("Save");
            ButtonType buttonTypeContinue = new ButtonType("Continue");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeContinue, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeContinue) {
                model.appendInfo("Encryption dialog opened");
                decryptDialog.show();
            } else if (result.get() == buttonTypeSave) {
                model.appendInfo("Decryption dialog opened");
                encryptDialoag.show();
            }
        }
    }
}
