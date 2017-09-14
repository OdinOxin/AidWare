package de.odinoxin.aiddesk.plugins;

import de.odinoxin.aiddesk.controls.MergeablePane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Hashtable;

public abstract class RecordView<T extends RecordItem<?>> extends GridPane {

    private static final String[] PREFIXES = {
            "txf",
            "btn",
            "dtp",
            "refBox",
            "refList",
    };

    protected T record;
    protected Node root;
    private Hashtable<String, MergeablePane> mergeables;
    private ViewMode viewMode;

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

    public ViewMode getViewMode() {
        return viewMode;
    }

    public void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public Hashtable<String, MergeablePane> getMergeables() {
        if (this.mergeables == null) {
            this.mergeables = new Hashtable<>();
            this.findMergeables(this);
        }
        return this.mergeables;
    }

    private void findMergeables(Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof MergeablePane) {
                String id = "";
                if (((MergeablePane) node).getChildren().size() > 1)
                    id = ((MergeablePane) node).getChildren().get(1).getId();
                if (!id.isEmpty()) {
                    boolean replaced = false;
                    for (int i = 0; i < PREFIXES.length && !replaced; i++) {
                        if (id.startsWith(PREFIXES[i])) {
                            id = id.substring(PREFIXES[i].length());
                            replaced = true;
                        }
                    }
                    mergeables.put(id, (MergeablePane) node);
                }
            }
            if (node instanceof Parent)
                findMergeables((Parent) node);
        }
    }

    public enum ViewMode {
        DISPLAYING,
        EDITING,
        MERGING,
    }
}
