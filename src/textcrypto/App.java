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
package textcrypto;

import textcrypto.View.Controller.DocumentController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Rafael S. Müller <rafael.mueller1@gmail.com>
 */
public class App extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Text Crypto v1.1.0");

        loadImg();
        showMainView();
    }

    private void showMainView() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View/FXML/Document.fxml"));
            DocumentController controller = new DocumentController();
            loader.setController(controller);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (IOException ex) {
            throw new IOException("Could not load FXML document", ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void loadImg() {
        Image image16 = new Image(getClass().getClassLoader().
                getResourceAsStream("res/images/logo16.png"));
        Image image32 = new Image(getClass().getClassLoader().
                getResourceAsStream("res/images/logo32.png"));
        Image image48 = new Image(getClass().getClassLoader().
                getResourceAsStream("res/images/logo48.png"));
        Image image64 = new Image(getClass().getClassLoader().
                getResourceAsStream("res/images/logo64.png"));
        Image image128 = new Image(getClass().getClassLoader().
                getResourceAsStream("res/images/logo128.png"));
        primaryStage.getIcons().addAll(image16, image32, image48, image64, image128);
    }
}
