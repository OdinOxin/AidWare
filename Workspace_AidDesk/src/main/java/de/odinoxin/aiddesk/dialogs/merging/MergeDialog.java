package de.odinoxin.aiddesk.dialogs.merging;

import de.odinoxin.aiddesk.controls.translateable.Button;
import de.odinoxin.aiddesk.plugins.Plugin;
import de.odinoxin.aiddesk.plugins.RecordItem;
import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.scene.control.SplitPane;

public abstract class MergeDialog<T extends RecordItem> extends Plugin {

    private RecordView<T> originalView, serverView, localView;

    public MergeDialog(RecordView<T> originalView, RecordView<T> serverView, RecordView<T> localView) {
        super("/dialogs/mergedialog.fxml", "Merging");
        ((Button) root.lookup("#btnCancel")).setOnAction(event -> this.close());
        SplitPane mergeSplitter = ((SplitPane) this.root.lookup("#mergeSplitter"));
        this.originalView = originalView;
        this.serverView = serverView;
        this.localView = localView;
        mergeSplitter.getItems().addAll(originalView, serverView, localView);
    }

    public void setRecords(T original, T server, T local) {
        this.originalView.bind(original);
        this.serverView.bind(server);
        this.localView.bind(local);
    }
}
