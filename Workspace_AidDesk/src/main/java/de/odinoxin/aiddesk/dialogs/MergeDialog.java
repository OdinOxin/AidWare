package de.odinoxin.aiddesk.dialogs;

import de.odinoxin.aiddesk.controls.MergeablePane;
import de.odinoxin.aiddesk.controls.translateable.Button;
import de.odinoxin.aiddesk.dialogs.Callback;
import de.odinoxin.aiddesk.plugins.Plugin;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordItem;
import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MergeDialog<T extends RecordItem<?>> extends Plugin {

    private T original;
    private RecordView<T> serverView, localView, resultView;
    private static final Border RED = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DOTTED, new CornerRadii(5), new BorderWidths(3)));
    private static final Border GREEN = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.DOTTED, new CornerRadii(5), new BorderWidths(3)));

    private Button btnMerge;

    private Hashtable<String, MergeablePane> panesServer;
    private Hashtable<String, MergeablePane> panesLocal;
    private Hashtable<String, MergeablePane> panesResult;

    public MergeDialog(RecordEditor<T> editor) {
        super("/dialogs/mergedialog.fxml", "Merging");
        this.initModality(Modality.APPLICATION_MODAL);
        btnMerge = ((Button) root.lookup("#btnMerge"));
        btnMerge.setOnAction(event ->
        {
            editor.getRecordItem().setChanged(false);
            editor.attemptLoadRecord(resultView.getRecord());
            editor.setOriginalRecordItem(serverView.getRecord());
            editor.getRecordItem().setChanged(true);
            this.close();
        });
        ((Button) root.lookup("#btnCancel")).setOnAction(event -> this.close());
        SplitPane verticalSplitter = ((SplitPane) this.root.lookup("#verticalSplitter"));
        this.original = editor.getOriginalRecordItem();
        this.serverView = editor.newView(editor.getServerRecordItem());
        this.localView = editor.newView(editor.getRecordItem());
        this.resultView = editor.newView(editor.getClonedRecordItem());
        panesServer = this.serverView.getMergeables();
        panesLocal = this.localView.getMergeables();
        panesResult = this.resultView.getMergeables();
        panesServer.values().forEach(mergeablePane -> mergeablePane.setContentDisabled(true));
        panesLocal.values().forEach(mergeablePane -> mergeablePane.setContentDisabled(true));
        // Lookup not avaiable here
        ((ScrollPane) ((VBox) ((SplitPane) verticalSplitter.getItems().get(0)).getItems().get(0)).getChildren().get(1)).setContent(this.serverView);
        ((ScrollPane) ((VBox) ((SplitPane) verticalSplitter.getItems().get(0)).getItems().get(1)).getChildren().get(1)).setContent(this.localView);
        ((ScrollPane) ((VBox) verticalSplitter.getItems().get(1)).getChildren().get(1)).setContent(this.resultView);
        this.evaluateDifferences();
        this.checkAccepted();
    }

    private void evaluateDifferences() {
        List<String> diffOriginalServer = this.original.getDifferentPropertyNames(this.serverView.getRecord());
        List<String> diffOriginalLocal = this.original.getDifferentPropertyNames(this.localView.getRecord());
        List<String> diffServerLocal = new ArrayList<>();
        if (this.serverView.getRecord() != null)
            diffServerLocal.addAll(this.serverView.getRecord().getDifferentPropertyNames(this.localView.getRecord()));

        panesResult.keySet().forEach(s ->
        {
            boolean selectable = diffOriginalServer.contains(s) || diffOriginalLocal.contains(s);
            panesServer.get(s).setSelectable(selectable);
            panesLocal.get(s).setSelectable(selectable);
            panesResult.get(s).setSelectable(false);
            panesServer.get(s).setSelected(false);
            panesLocal.get(s).setSelected(false);
            panesResult.get(s).setSelected(false);
            Callback markGreen = () ->
            {
                panesServer.get(s).setBorder(GREEN);
                panesLocal.get(s).setBorder(GREEN);
                panesResult.get(s).setBorder(GREEN);
                checkAccepted();
            };
            panesServer.get(s).setSelectedListener((observable, oldValue, newValue) ->
            {
                if (newValue) {
                    panesResult.get(s).set(panesServer.get(s).get());
                    panesLocal.get(s).setSelected(false);
                    markGreen.call();
                }
            });
            panesLocal.get(s).setSelectedListener((observable, oldValue, newValue) ->
            {
                if (newValue) {
                    panesServer.get(s).setSelected(false);
                    panesResult.get(s).set(panesLocal.get(s).get());
                    markGreen.call();
                }
            });

            if (selectable) {
                boolean mergeServer = diffOriginalServer.contains(s) && (!diffOriginalLocal.contains(s) || !diffServerLocal.contains(s));
                boolean mergeLocal = !diffOriginalServer.contains(s) && diffOriginalLocal.contains(s);
                panesServer.get(s).setSelected(mergeServer);
                panesLocal.get(s).setSelected(mergeLocal);
                if (!mergeServer && !mergeLocal) {
                    panesServer.get(s).setBorder(RED);
                    panesLocal.get(s).setBorder(RED);
                    panesResult.get(s).setBorder(RED);
                }
            }
        });
    }

    public void setRecords(T serverRecord, T localRecord, T resultRecord) {
        this.serverView.bind(serverRecord);
        this.localView.bind(localRecord);
        this.resultView.bind(resultRecord);
    }

    private void checkAccepted() {
        boolean accept = true;
        for (String key : panesResult.keySet()) {
            if (panesServer.get(key).isSelectable() && panesLocal.get(key).isSelectable()
                    && !panesServer.get(key).isSelected() && !panesLocal.get(key).isSelected())
                accept = false;
        }
        btnMerge.setDisable(!accept);
    }
}
