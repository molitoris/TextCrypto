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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.stage.FileChooser;
import textcrypto.ContentModel;
import textcrypto.Logic.Crypto;
import textcrypto.Logic.FileManager;
import verifier.Items.FormItem;
import verifier.Items.PairItem;
import verifier.Type;

/**
 * FXML Controller class
 *
 * @author Rafael S. Müller <rafael.mueller1@gmail.com>
 */
public class ControllerEncryption extends Controller implements Initializable {

    @FXML
    private PasswordField fldRepeat;

    public ControllerEncryption(ContentModel model) {
        super(model);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        formValidation.addItem(new FormItem(Type.NOT_EMPTY, fldPath));
        formValidation.addItem(new FormItem(Type.PASSWORD, fldPassword));
        formValidation.addItem(new PairItem(fldPassword, fldRepeat));

        lblErrorMessage.setText("Choose the file you want to decrypt, enter the password twice, and press save.");
    }

    @Override
    protected void runCryptography() throws InvalidKeyException, IOException, NoSuchAlgorithmException {

        Task<Void> encryptionTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                File f = new File(fldPath.getText());
                byte[] content = model.getContent().getBytes();
                byte[] cipherText = Crypto.encrypt(content, fldPassword.getText().toCharArray());
                FileManager.write(f, cipherText);
                return null;
            }
        };

        encryptionTask.setOnSucceeded((WorkerStateEvent event) -> {
            model.appendInfo("Mesage successfully encryped and safed to " + fldPath.getText());
            model.setSafed(true);
        });
    }

    @FXML
    private void browseBtnAction(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "/Dropbox"));
        File f = fc.showSaveDialog(btnBrowse.getScene().getWindow());
        if (f != null) {
            fldPath.setText(f.getAbsolutePath());
        }
    }
}
