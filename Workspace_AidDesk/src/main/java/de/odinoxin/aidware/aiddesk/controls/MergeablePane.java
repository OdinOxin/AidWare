package de.odinoxin.aidware.aiddesk.controls;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aidware.aiddesk.controls.reflist.RefList;
import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Collection;

public class MergeablePane extends HBox {

    private static final Background TRANSPARENT = new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null));

    private BooleanProperty selectable = new SimpleBooleanProperty(true);
    private final RadioButton rbtSelected;
    private ChangeListener<Boolean> lastListener = null;
    private BooleanProperty contentDisabled = new SimpleBooleanProperty(false);
    private BooleanProperty contentEditable = new SimpleBooleanProperty(true);

    public MergeablePane() {
        FXMLLoader fxmlLoader = new FXMLLoader(MergeablePane.class.getResource("/controls/mergeablepane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        rbtSelected = ((RadioButton) this.lookup("#rbtSelected"));
        rbtSelected.managedProperty().bind(rbtSelected.visibleProperty());
        selectable.addListener((observable, oldValue, newValue) -> {
            rbtSelected.setVisible(newValue);
            if (!newValue)
                this.setBackground(TRANSPARENT);
        });
        setSelectable(false); // Toggle once to default
        this.getChildren().addListener((ListChangeListener.Change<? extends Node> c) -> {
            double maxWidth = 0, prefWidth = 0;
            for (int i = 1; i < this.getChildren().size(); i++) {
                this.getChildren().get(i).disableProperty().bind(this.contentDisabled);
                if (this.getChildren().get(i) instanceof TextInputControl)
                    ((TextInputControl) this.getChildren().get(i)).editableProperty().bind(this.contentEditable);
                else if (this.getChildren().get(i) instanceof RefBox<?>)
                    ((RefBox<?>) this.getChildren().get(i)).changeableProperty().bind(this.contentEditable);
                else if (this.getChildren().get(i) instanceof ButtonBase)
                    this.getChildren().get(i).disableProperty().bind(this.contentEditable.not());
                HBox.setHgrow(this.getChildren().get(i), Priority.ALWAYS);
                double localMax = this.getChildren().get(i).maxWidth(-1);
                double localPref = this.getChildren().get(i).prefWidth(-1);
                if (maxWidth < localMax)
                    maxWidth = localMax;
                if (prefWidth < localPref)
                    prefWidth = localPref;
            }
            RadioButton rbnSelection = ((RadioButton) this.getChildren().get(0));
            if (maxWidth > 0)
                this.maxWidthProperty().bind(rbnSelection.widthProperty().add(maxWidth));
            if (prefWidth > 0)
                this.prefWidthProperty().bind(rbnSelection.widthProperty().add(prefWidth));
        });
    }

    public boolean isSelectable() {
        return selectable.get();
    }

    public void setSelectable(boolean selectable) {
        this.selectable.set(selectable);
    }

    public boolean isSelected() {
        return this.rbtSelected.isSelected();
    }

    public void setSelected(boolean selected) {
        this.rbtSelected.setSelected(selected);
    }

    public void setSelectedListener(ChangeListener<Boolean> changeListener) {
        if (lastListener != null)
            this.rbtSelected.selectedProperty().removeListener(lastListener);
        this.rbtSelected.selectedProperty().addListener(changeListener);
    }

    public boolean isContentDisabled() {
        return contentDisabled.get();
    }

    public void setContentDisabled(boolean contentDisabled) {
        this.contentDisabled.set(contentDisabled);
    }

    public BooleanProperty contentEditableProperty() {
        return contentEditable;
    }

    public boolean isContentEditable() {
        return contentEditable.get();
    }

    public void setContentEditable(boolean contentEditable) {
        this.contentEditable.set(contentEditable);
    }

    public Object get() {
        Node node = null;
        if (this.getChildren().size() > 1)
            node = this.getChildren().get(1);
        if (node != null) {
            if (node instanceof TextField)
                return ((TextField) node).getText();
            if (node instanceof CheckBox)
                return ((CheckBox) node).isSelected();
            if (node instanceof RefBox<?>)
                return ((RefBox<?>) node).getRecord();
            if (node instanceof RefList<?>)
                return node;
        }
        return null;
    }

    public void set(Object content) {
        Node node = null;
        if (this.getChildren().size() > 1)
            node = this.getChildren().get(1);
        if (node != null) {
            if (node instanceof TextField)
                ((TextField) node).setText(content.toString());
            else if (node instanceof CheckBox)
                ((CheckBox) node).setSelected((boolean) content);
            else if (node instanceof RefBox)
                ((RefBox) node).setRecord((RecordItem) content);
            else if (node instanceof RefList<?>) {
                RefList<?> refList = (RefList<?>) node;
                refList.clear();
                refList.addAll((Collection) content);
            }
        }
    }
}
