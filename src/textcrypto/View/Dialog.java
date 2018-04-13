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
package textcrypto.View;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import textcrypto.View.Controller.Controller;

/**
 *
 * @author Rafael S. Müller <rafael.mueller1@gmail.com>
 */
public class Dialog {

    private final Controller controller;
    private final String path;
    private Node node;

    public Dialog(Controller controller, String path) {
        this.controller = controller;
        this.path = path;
    }
    
    public void setNode(Node node){
        this.node = node;
    }

    public void show() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(path));
            loader.setController(controller);
            Parent root = loader.load();
            
            Stage stage = new Stage(StageStyle.UNDECORATED);
            
            stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                if (KeyCode.ESCAPE.equals(event.getCode())) {
                    stage.close();
                }
            });
            
            //locks underlying stage
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(node.getScene().getWindow());
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            throw new IOException("Could not load Dialog", ex);
        }
    }
}