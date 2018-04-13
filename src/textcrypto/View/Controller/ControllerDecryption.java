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
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import textcrypto.ContentModel;
import textcrypto.Logic.Crypto;
import textcrypto.Logic.FileManager;
import verifier.Items.FormItem;
import verifier.Type;

/**
 * FXML Controller class
 *
 * @author Rafael S. Müller <rafael.mueller1@gmail.com>
 */
public class ControllerDecryption extends Controller {

    public ControllerDecryption(ContentModel model) {
        super(model);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        formValidation.addItem(new FormItem(Type.NOT_EMPTY, fldPath));
        formValidation.addItem(new FormItem(Type.NOT_EMPTY, fldPassword));
        lblErrorMessage.setText("Choose the file you want to encrypt, enter the password, and press open.");
    }

    @FXML
    private void browseBtnAction(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open file");
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "/Dropbox"));
        File f = fc.showOpenDialog(btnBrowse.getScene().getWindow());
        if (f != null) {
            fldPath.setText(f.getAbsolutePath());
        }
    }

    @Override
    protected void runCryptography() throws InvalidKeyException, IOException {

        Task<String> decryptionTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                byte[] cipherText = FileManager.read(new File(fldPath.getText()));
                byte[] plain =  Crypto.decrypt(cipherText, fldPassword.getText().toCharArray());
                
                return new String(plain);
            }
        };
        
        model.contentProperty().bind(decryptionTask.valueProperty());
        
        decryptionTask.setOnSucceeded((WorkerStateEvent e) -> {            
            model.appendInfo("Message was successfully decrypted");
            model.contentProperty().unbind();
        });

        new Thread(decryptionTask).start();
    }
}
