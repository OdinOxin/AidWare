package de.odinoxin.aidware.aiddesk.controls.reflist;

import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import de.odinoxin.aidware.aiddesk.utils.Command;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link ListCell} for {@link RefList}.
 *
 * @param <T> The type of the record.
 */
class RefListCell<T extends RecordItem> extends HBox {

    private List<T> source;
    private int index;
    @FXML
    private RefBox<T> refBox;

    private Command removeCmd;

    private BooleanProperty distinct;

    /**
     * Initializes the {@link RefListCell} and its behavior.
     *
     * @param provider The provider to use.
     * @param source   The source to display an item from.
     * @param index    The index of the item to display.
     */
    RefListCell(ObjectProperty<Provider<T>> provider, BooleanProperty showDetails, BooleanProperty distinct, List<T> source, int index) {
        this.source = source;
        this.index = index;
        this.distinct = distinct;

        FXMLLoader fxmlLoader = new FXMLLoader(RefListCell.class.getResource("/controls/reflistcell.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.refBox.providerProperty().bind(provider);
        this.refBox.setShowEditButton(true);
        this.refBox.showDetails().bind(showDetails);
        SVGPath svgRemove = new SVGPath();
        svgRemove.setContent("M 0 0 h 1 l 2 2 l 2 -2 h 1 v 1 l -2 2 l 2 2 v 1 h -1 l -2 -2 l -2 2 h -1 v -1 l 2 -2 l -2 -2 z");
        svgRemove.setScaleX(0.5);
        svgRemove.setScaleY(0.5);
        this.removeCmd = new Command(() -> this.source.remove(this.index), new SimpleBooleanProperty(true), new SimpleObjectProperty<>(svgRemove));

        this.update(this.index);

        this.refBox.recordProperty().addListener((observable, oldValue, newValue) -> {
            if (this.index < this.source.size()) {
                if (newValue != null)
                    this.source.set(this.index, newValue);
            } else if (newValue != null)
                this.source.add(newValue);
        });

        distinct.addListener((observable, oldValue, newValue) -> this.update(this.index));
    }

    /**
     * Updates the {@link RefListCell}.
     *
     * @param index The (maybe new) index to reference.
     */
    void update(int index) {
        this.index = index;
        boolean pseudo = this.index == this.source.size();
        if (pseudo) {
            this.refBox.removeCommand(removeCmd);
            this.refBox.setRecord(null);
        } else {
            this.refBox.setRecord(this.source.get(this.index));
            this.refBox.addCommand(removeCmd);
        }
        List<Integer> exceptedIds = new ArrayList<>();
        if (this.distinct.get()) {
            for (T record : this.source)
                exceptedIds.add(record.getId());
        }
        this.refBox.setExceptedIds(exceptedIds);
        this.refBox.setShowSearchButton(pseudo);
        this.refBox.setShowNewButton(pseudo);
    }
}
