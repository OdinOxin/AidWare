package de.odinoxin.aidware.aiddesk.dialogs;

import de.odinoxin.aidware.aiddesk.controls.MergeablePane;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aidware.aiddesk.controls.reflist.RefList;
import de.odinoxin.aidware.aiddesk.controls.translateable.Button;
import de.odinoxin.aidware.aiddesk.plugins.Plugin;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
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
            editor.getRecord().setChanged(false);
            editor.attemptLoadRecord(resultView.getRecord());
            editor.setOriginalRecord(serverView.getRecord());
            editor.getRecord().setChanged(true);
            this.close();
        });
        ((Button) root.lookup("#btnCancel")).setOnAction(event -> this.close());
        SplitPane verticalSplitter = ((SplitPane) this.root.lookup("#verticalSplitter"));
        this.original = editor.getOriginalRecord();
        this.serverView = editor.newView(editor.getServerRecord());
        this.localView = editor.newView(editor.getRecord());
        this.resultView = editor.newView(editor.getClonedRecord());
        this.serverView.setViewMode(RecordView.ViewMode.MERGING);
        this.localView.setViewMode(RecordView.ViewMode.MERGING);
        this.resultView.setViewMode(RecordView.ViewMode.MERGING);
        panesServer = this.serverView.getMergeables();
        panesLocal = this.localView.getMergeables();
        panesResult = this.resultView.getMergeables();
        this.panesResult.keySet().forEach(key ->
        {
            panesServer.get(key).setContentEditable(false);
            panesLocal.get(key).setContentEditable(false);
            Node contentServer = panesServer.get(key).getChildren().get(1);
            Node contentLocal = panesLocal.get(key).getChildren().get(1);
            Node contentResult = panesResult.get(key).getChildren().get(1);
            if (contentResult instanceof TextInputControl)
                ((TextInputControl) contentResult).textProperty().addListener((observable, oldValue, newValue) -> {
                    markGreen(key);
                    panesServer.get(key).setSelected(((TextInputControl) contentServer).getText().equals(newValue));
                    panesLocal.get(key).setSelected(((TextInputControl) contentLocal).getText().equals(newValue));
                });
            else if (contentResult instanceof CheckBox)
                ((CheckBox) contentResult).selectedProperty().addListener((observable, oldValue, newValue) -> {
                    markGreen(key);
                    panesServer.get(key).setSelected(((CheckBox) contentServer).isSelected() == newValue);
                    panesLocal.get(key).setSelected(((CheckBox) contentLocal).isSelected() == newValue);
                });
            else if (contentResult instanceof RefBox<?>)
                ((RefBox<?>) contentResult).recordProperty().addListener((observable, oldValue, newValue) -> {
                    markGreen(key);
                    panesServer.get(key).setSelected((((RefBox<?>) contentServer).getRecord() == null && newValue == null)
                            || (((RefBox<?>) contentServer).getRecord() != null && newValue != null
                            && ((RefBox<?>) contentServer).getRecord().getId() == newValue.getId()));
                    panesLocal.get(key).setSelected((((RefBox<?>) contentLocal).getRecord() == null && newValue == null)
                            || (((RefBox<?>) contentLocal).getRecord() != null && newValue != null
                            && ((RefBox<?>) contentLocal).getRecord().getId() == newValue.getId()));
                });
            else if (contentResult instanceof RefList<?>) {
                ((RefList<?>) contentResult).addListener((ListChangeListener<Object>) c -> {
                    markGreen(key);
                    RefList<? extends RecordItem<?>> listServer = ((RefList<? extends RecordItem<?>>) contentServer);
                    RefList<? extends RecordItem<?>> listLocal = ((RefList<? extends RecordItem<?>>) contentLocal);
                    RefList<? extends RecordItem<?>> listResult = ((RefList<? extends RecordItem<?>>) contentResult);
                    boolean equals = listServer.size() == listResult.size();
                    for (int i = 0; i < listResult.size() && equals; i++) {
                        if ((listServer.get(i) == null && listResult.get(i) == null)
                                || (listServer.get(i) != null && listResult.get(i) != null && listServer.get(i).getId() != listResult.get(i).getId()))
                            equals = false;
                    }
                    panesServer.get(key).setSelected(equals);
                    equals = listLocal.size() == listResult.size();
                    for (int i = 0; i < listResult.size() && equals; i++) {
                        if ((listLocal.get(i) == null && listResult.get(i) == null)
                                || (listLocal.get(i) != null && listResult.get(i) != null && listLocal.get(i).getId() != listResult.get(i).getId()))
                            equals = false;
                    }
                    panesLocal.get(key).setSelected(equals);
                });
            }
        });
        // Lookup not avaiable here
        ((ScrollPane) ((VBox) ((SplitPane) verticalSplitter.getItems().get(0)).getItems().get(0)).getChildren().get(1)).setContent(this.serverView);
        ((ScrollPane) ((VBox) ((SplitPane) verticalSplitter.getItems().get(0)).getItems().get(1)).getChildren().get(1)).setContent(this.localView);
        ((ScrollPane) ((VBox) verticalSplitter.getItems().get(1)).getChildren().get(1)).setContent(this.resultView);
        this.setOnShown(ev -> {
            // Request content to relayout.
            panesResult.keySet().forEach(key -> {
                panesServer.get(key).requestLayout();
                panesLocal.get(key).requestLayout();
                panesResult.get(key).requestLayout();
            });
        });
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
            panesServer.get(s).setSelectedListener((observable, oldValue, newValue) ->
            {
                if (newValue) {
                    panesResult.get(s).set(panesServer.get(s).get());
                    panesLocal.get(s).setSelected(false);
                    markGreen(s);
                }
            });
            panesLocal.get(s).setSelectedListener((observable, oldValue, newValue) ->
            {
                if (newValue) {
                    panesServer.get(s).setSelected(false);
                    panesResult.get(s).set(panesLocal.get(s).get());
                    markGreen(s);
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

    private void checkAccepted() {
        boolean accept = true;
        for (String key : panesResult.keySet()) {
            if (panesResult.get(key).getBorder() == RED) {
                accept = false;
                panesResult.get(key).getChildren().get(1).requestFocus();
                break;
            }
        }
        btnMerge.setDisable(!accept);
    }

    private void markGreen(String key) {
        panesServer.get(key).setBorder(GREEN);
        panesLocal.get(key).setBorder(GREEN);
        panesResult.get(key).setBorder(GREEN);
        checkAccepted();
    }
}
