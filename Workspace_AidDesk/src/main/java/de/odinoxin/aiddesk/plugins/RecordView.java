package de.odinoxin.aiddesk.plugins;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

import java.io.IOException;

public abstract class RecordView<T extends RecordItem> extends ScrollPane {

    protected Node root;

    public RecordView(String res) {
        try {
            root = FXMLLoader.load(RecordView.class.getResource(res));
            this.setContent(root);
            this.focusedProperty().addListener((observable, oldValue, newValue) ->
            {
                if (newValue) this.setFocused(false);
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public abstract void bind(T record);
    public abstract void requestFocus();
}
