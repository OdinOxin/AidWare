package de.odinoxin.aiddesk.dialogs.merging;

import de.odinoxin.aiddesk.controls.SelectablePane;
import de.odinoxin.aiddesk.controls.translateable.Button;
import de.odinoxin.aiddesk.plugins.Plugin;
import de.odinoxin.aiddesk.plugins.RecordItem;
import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

import java.util.Hashtable;
import java.util.List;

public class MergeDialog<T extends RecordItem<?>> extends Plugin {

    private RecordView<T> originalView, serverView, localView;
    private static final Border RED = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DOTTED, new CornerRadii(5), new BorderWidths(3)));
    private static final Border GREEN = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.DOTTED, new CornerRadii(5), new BorderWidths(3)));

    public MergeDialog(RecordView<T> originalView, RecordView<T> serverView, RecordView<T> localView) {
        super("/dialogs/mergedialog.fxml", "Merging");
        this.initModality(Modality.APPLICATION_MODAL);
        ((Button) root.lookup("#btnCancel")).setOnAction(event -> this.close());
        SplitPane mergeSplitter = ((SplitPane) this.root.lookup("#mergeSplitter"));
        this.originalView = originalView;
        this.serverView = serverView;
        this.localView = localView;
        // Lookup not avaiable here
        ((ScrollPane) ((VBox) mergeSplitter.getItems().get(0)).getChildren().get(1)).setContent(this.originalView);
        ((ScrollPane) ((VBox) mergeSplitter.getItems().get(1)).getChildren().get(1)).setContent(this.serverView);
        ((ScrollPane) ((VBox) mergeSplitter.getItems().get(2)).getChildren().get(1)).setContent(this.localView);
        this.originalView.getRecord().setChanged(false);
        this.serverView.getRecord().setChanged(false);
        this.localView.getRecord().setChanged(false);
        this.evaluateDifferences();
    }

    private void evaluateDifferences() {
        List<String> diffOriginalServer = this.originalView.getRecord().getDifferentPropertyNames(this.serverView.getRecord());
        List<String> diffOriginalLocal = this.originalView.getRecord().getDifferentPropertyNames(this.localView.getRecord());
        List<String> diffServerLocal = this.serverView.getRecord().getDifferentPropertyNames(this.localView.getRecord());
//        List<String> diffAll = new ArrayList<>();
//        diffAll.addAll(diffOriginalServer);
//        diffOriginalLocal.stream().filter(s -> !diffAll.contains(s)).forEach(s -> diffAll.add(s));
//        diffServerLocal.stream().filter(s -> !diffAll.contains(s)).forEach(s -> diffAll.add(s));

        Hashtable<String, SelectablePane> panesOriginal = this.originalView.getSelectables();
        Hashtable<String, SelectablePane> panesServer = this.serverView.getSelectables();
        Hashtable<String, SelectablePane> panesLocal = this.localView.getSelectables();

        panesOriginal.keySet().forEach(s ->
        {
            boolean selectable = diffOriginalServer.contains(s) || diffOriginalLocal.contains(s);
            panesOriginal.get(s).setSelectable(selectable);
            panesServer.get(s).setSelectable(selectable);
            panesLocal.get(s).setSelectable(selectable);
            panesOriginal.get(s).setSelected(false);
            panesServer.get(s).setSelected(false);
            panesLocal.get(s).setSelected(false);
            ChangeListener<Boolean> marker = (observable, oldValue, newValue) -> {
                if (newValue) {
                    panesOriginal.get(s).setBorder(GREEN);
                    panesServer.get(s).setBorder(GREEN);
                    panesLocal.get(s).setBorder(GREEN);
                }
            };
            panesOriginal.get(s).setSelectedListener(marker);
            panesServer.get(s).setSelectedListener(marker);
            panesLocal.get(s).setSelectedListener(marker);

            if (selectable) {
                boolean mergeServer = diffOriginalServer.contains(s) && !diffOriginalLocal.contains(s);
                boolean mergeLocal = !diffOriginalServer.contains(s) && diffOriginalLocal.contains(s);
                panesServer.get(s).setSelected(mergeServer);
                panesLocal.get(s).setSelected(mergeLocal);
                if (!mergeServer && !mergeLocal) {
                    panesOriginal.get(s).setBorder(RED);
                    panesServer.get(s).setBorder(RED);
                    panesLocal.get(s).setBorder(RED);
                }
            }
        });
//        List<String> realDifferences = diffOriginalServer.parallelStream().filter(s -> diffOriginalLocal.contains(s)).collect(Collectors.toList());
    }

    public void setRecords(T originalRecord, T serverRecord, T localRecord) {
        this.originalView.bind(originalRecord);
        this.serverView.bind(serverRecord);
        this.localView.bind(localRecord);
    }
}
