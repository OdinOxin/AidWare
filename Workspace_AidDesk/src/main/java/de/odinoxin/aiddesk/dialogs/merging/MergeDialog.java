package de.odinoxin.aiddesk.dialogs.merging;

import de.odinoxin.aiddesk.controls.SelectablePane;
import de.odinoxin.aiddesk.controls.translateable.Button;
import de.odinoxin.aiddesk.dialogs.Callback;
import de.odinoxin.aiddesk.plugins.Plugin;
import de.odinoxin.aiddesk.plugins.RecordItem;
import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

import java.util.Hashtable;
import java.util.List;

public class MergeDialog<T extends RecordItem<?>> extends Plugin {

    private T original;
    private RecordView<T> serverView, localView, resultView;
    private static final Border RED = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DOTTED, new CornerRadii(5), new BorderWidths(3)));
    private static final Border GREEN = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.DOTTED, new CornerRadii(5), new BorderWidths(3)));

    private Button btnMerge;

    private Hashtable<String, SelectablePane> panesServer;
    private Hashtable<String, SelectablePane> panesLocal;
    private Hashtable<String, SelectablePane> panesResult;

    public MergeDialog(T original, RecordView<T> serverView, RecordView<T> localView, RecordView<T> resultView) {
        super("/dialogs/mergedialog.fxml", "Merging");
        this.initModality(Modality.APPLICATION_MODAL);
        btnMerge = ((Button) root.lookup("#btnMerge"));
        btnMerge.setOnAction(event -> this.close());
        ((Button) root.lookup("#btnCancel")).setOnAction(event -> this.close());
        SplitPane horizontalSplitter = ((SplitPane) this.root.lookup("#horizontalSplitter"));
        SplitPane verticalSplitter = ((SplitPane) this.root.lookup("#verticalSplitter"));
        this.original = original;
        this.serverView = serverView;
        this.localView = localView;
        this.resultView = resultView;
        panesServer = this.serverView.getSelectables();
        panesLocal = this.localView.getSelectables();
        panesResult = this.resultView.getSelectables();
        // Lookup not avaiable here
        ((ScrollPane) ((VBox) ((SplitPane) verticalSplitter.getItems().get(0)).getItems().get(0)).getChildren().get(1)).setContent(this.serverView);
        ((ScrollPane) ((VBox) ((SplitPane) verticalSplitter.getItems().get(0)).getItems().get(1)).getChildren().get(1)).setContent(this.localView);
        ((ScrollPane) ((VBox) verticalSplitter.getItems().get(1)).getChildren().get(1)).setContent(this.resultView);

        this.serverView.getRecord().setChanged(false);
        this.localView.getRecord().setChanged(false);
        this.resultView.getRecord().setChanged(false);
        this.evaluateDifferences();
        this.checkAccepted();
    }

    private void evaluateDifferences() {
        List<String> diffOriginalServer = this.original.getDifferentPropertyNames(this.serverView.getRecord());
        List<String> diffOriginalLocal = this.original.getDifferentPropertyNames(this.localView.getRecord());
        List<String> diffServerLocal = this.serverView.getRecord().getDifferentPropertyNames(this.localView.getRecord());

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
