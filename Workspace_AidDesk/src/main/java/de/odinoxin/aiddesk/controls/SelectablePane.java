package de.odinoxin.aiddesk.controls;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class SelectablePane extends HBox {

    private static final Background TRANSPARENT = new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null));

    private BooleanProperty selectable = new SimpleBooleanProperty(true);
    private final RadioButton rbtSelected;
    private ChangeListener<Boolean> lastListener = null;

    public SelectablePane() {
        FXMLLoader fxmlLoader = new FXMLLoader(SelectablePane.class.getResource("/controls/selectablepane.fxml"));
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
            if (!newValue) {
                this.setBackground(TRANSPARENT);
                this.setEffect(null);
            }
        });
        setSelectable(false); // Toggle once to default
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
}
