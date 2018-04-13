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
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import textcrypto.ContentModel;
import verifier.FormValidation;
import verifier.ValidationException;
import verifier.Verifier;

/**
 *
 * @author Rafael S. Müller <rafael.mueller1@gmail.com>
 */
public abstract class Controller implements Initializable {

    protected final ContentModel model;
    protected final FormValidation formValidation;

    @FXML
    private Button btnAction;
    @FXML
    protected Button btnCancel;
    @FXML
    protected Button btnBrowse;
    @FXML
    protected TextField fldPath;
    @FXML
    protected PasswordField fldPassword;
    @FXML
    protected Label lblErrorMessage;

    public Controller(ContentModel model) {
        this.model = model;
        this.formValidation = new FormValidation(new Verifier());;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblErrorMessage.setWrapText(true);
        lblErrorMessage.setTextAlignment(TextAlignment.JUSTIFY);
    }

    @FXML
    protected void cancelBtnAction(ActionEvent e) {
        model.appendInfo("Dialog closed");
        closeStage();
    }

    @FXML
    protected void runAction(ActionEvent e) {
        try {
            formValidation.validate();
            runCryptography(); // implemented by child class
            closeStage();
        } catch (ValidationException ex) {
            lblErrorMessage.setStyle("-fx-text-fill: red;");
            lblErrorMessage.setText(ex.getLocalizedMessage());
        } catch (InvalidKeyException | IOException | NoSuchAlgorithmException ex) {
            lblErrorMessage.setStyle("-fx-text-fill: red;");
            lblErrorMessage.setText(ex.getMessage());
        }
    }

    protected void closeStage() {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    protected abstract void runCryptography() throws InvalidKeyException, IOException, NoSuchAlgorithmException;
}
