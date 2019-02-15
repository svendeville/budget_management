/*
 * @Copyright 2016 Sylvain Vendeville.
 * This file is part of Budget Management.
 * Budget Management is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Budget Management is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Budget Management. If not, see <http://www.gnu.org/licenses/>.
 */

package com.snv.view;

import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Splash extends SplashScreen {

    private static String DEFAULT_IMAGE = "/splash/pig.jpg";

    @Override
    public Parent getParent() {
        ImageView imageView = new ImageView(this.getClass().getResource(this.getImagePath()).toExternalForm());
        imageView.setFitWidth((imageView.getImage().getWidth() / 7));
        imageView.setFitHeight((imageView.getImage().getHeight() / 7));
        ProgressBar splashProgressBar = new ProgressBar();
        splashProgressBar.setPrefWidth(imageView.getFitWidth());
        VBox vbox = new VBox();
        vbox.getChildren().addAll(new Node[]{imageView, splashProgressBar});
        return vbox;
    }

    @Override
    public String getImagePath() {
        return DEFAULT_IMAGE;
    }
}
