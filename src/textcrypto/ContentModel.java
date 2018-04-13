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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is used to pass the content between the different controllers.
 *
 * @author Rafael S. Müller <rafael.mueller1@gmail.com>
 */
public class ContentModel {

    private final StringProperty content;
    private final StringProperty info;
    private final BooleanProperty isSafed;

    public ContentModel() {
        this.content = new SimpleStringProperty(this, "content", "");
        this.isSafed = new SimpleBooleanProperty(this, "safed", false);
        this.info = new SimpleStringProperty(this, "info", "");
    }

    public final void setContent(String content) {
        this.content.set(content);
    }

    public final String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public final void setSafed(boolean status) {
        this.isSafed.set(status);
    }

    public final boolean getSafed() {
        return this.isSafed.get();
    }

    public final void setInfo(String info) {
        this.content.set(info);
    }

    public final void appendInfo(String info) {
        this.info.set(info + "\n" + this.info.get());
    }

    public final String getInfo() {
        return info.get();
    }

    public StringProperty infoProperty() {
        return info;
    }
}
