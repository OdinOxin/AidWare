package de.odinoxin.aidware.aiddesk.controls.refbox;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.ListView;
import javafx.stage.Popup;

import java.io.IOException;

/**
 * Suggestion list as {@link Popup} below the {@link RefBox}.
 *
 * @param <T> The type of the records.
 */
class RefBoxList<T extends RecordItem> extends Popup {

    private ListView<RefBoxListItem<T>> lvSuggestions;

    RefBoxList(Point2D pos) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(RefBoxList.class.getResource("/controls/refboxlist.fxml"));
            this.lvSuggestions = fxmlLoader.load();
            this.lvSuggestions.setStyle("-fx-selection-bar: #03b5eb");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (pos != null) {
            setX(pos.getX());
            setY(pos.getY());
        }
        this.setAutoHide(true);
        this.getContent().add(this.lvSuggestions);


        this.lvSuggestions.getItems().addListener((ListChangeListener<Object>) c -> {
            Platform.runLater(() -> {
                double prefHeight = 2;
                VirtualFlow<?> flow = (VirtualFlow<?>) this.lvSuggestions.getChildrenUnmodifiable().get(0);
                for (int i = 0; i < flow.getCellCount(); i++)
                    prefHeight += flow.getCell(i).getHeight();
                this.lvSuggestions.setPrefHeight(prefHeight);
            });
        });
    }

    ListView<RefBoxListItem<T>> getSuggestionsList() {
        return this.lvSuggestions;
    }

    void setPrefWidth(double prefWidth) {
        super.setWidth(prefWidth);
        lvSuggestions.setPrefWidth(prefWidth);
    }
}
