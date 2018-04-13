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

import textcrypto.ContentModel;
import textcrypto.View.Controller.Controller;
import textcrypto.View.Controller.ControllerDecryption;
import textcrypto.View.Controller.ControllerEncryption;

/**
 *
 * @author Rafael S. Müller <rafael.mueller1@gmail.com>
 */
public class DialogFactory {

    private volatile static DialogFactory factory;
    private static ContentModel model;

    private DialogFactory() {
    }

    public static DialogFactory getInstance(ContentModel model) {
        if (factory == null) {
            synchronized (DialogFactory.class) {
                if (factory == null) {
                    DialogFactory.model = model;
                    factory = new DialogFactory();
                }
            }
        }
        return factory;
    }

    public Dialog getDialog(DialogType type) {
        Controller con;
        switch (type) {
            case DECRYPT:
                con = new ControllerDecryption(model);
                return new Dialog(con, "textcrypto/View/FXML/Decryption.fxml");
            case ENCRYPT:
                con = new ControllerEncryption(model);
                return new Dialog(con, "textcrypto/View/FXML/Encryption.fxml");
            default:
                throw new IllegalArgumentException("Dialog type does not exist " + type);
        }
    }
}
