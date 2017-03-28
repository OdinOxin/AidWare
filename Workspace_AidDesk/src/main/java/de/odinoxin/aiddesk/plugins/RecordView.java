package de.odinoxin.aiddesk.plugins;

import de.odinoxin.aiddesk.controls.SelectablePane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Hashtable;

public abstract class RecordView<T extends RecordItem<?>> extends GridPane {

    protected T record;
    protected Node root;
    protected Hashtable<String, SelectablePane> selectables = new Hashtable<>();

    public RecordView(T record, String res) {
        this.record = record;
        try {
            root = FXMLLoader.load(RecordView.class.getResource(res));
            this.getChildren().add(root);
            this.focusedProperty().addListener((observable, oldValue, newValue) ->
            {
                if (newValue) this.setFocused(false);
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public T getRecord() {
        return record;
    }

    public void bind(T record) {
        this.record = record;
    }

    public abstract void requestFocus();

    public Hashtable<String, SelectablePane> getSelectables() {
        return this.selectables;
    }
}
