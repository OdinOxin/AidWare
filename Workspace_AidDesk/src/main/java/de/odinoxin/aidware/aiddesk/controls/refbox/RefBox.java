package de.odinoxin.aidware.aiddesk.controls.refbox;

import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import de.odinoxin.aidware.aiddesk.utils.Command;
import de.odinoxin.aidware.aiddesk.utils.TextUtils;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * RefBox control.
 *
 * @param <T> The type to deal with.
 */
public class RefBox<T extends RecordItem> extends VBox {

    private static final int MAX_SEARCH_RESULT = 10;

    @FXML
    private TextField txfText;
    @FXML
    private Separator sepButtons;
    @FXML
    private HBox hbxButtons;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnSearch;
    @FXML
    private Separator sepCommands;
    @FXML
    private HBox hbxCommands;
    @FXML
    private TextArea txfDetails;
    private RefBoxList<T> refBoxList;

    /**
     * The current record.
     */
    private ObjectProperty<T> record = new SimpleObjectProperty<>(this, "record", null);
    /**
     * Whether the record can be changed.
     */
    private BooleanProperty changeable = new SimpleBooleanProperty(this, "changeable", true);
    /**
     * Whether the new button is displayed.
     */
    private BooleanProperty showNewButton = new SimpleBooleanProperty(this, "showNewButton", false);
    /**
     * Whether the edit button is displayed.
     */
    private BooleanProperty showEditButton = new SimpleBooleanProperty(this, "showEditButton", false);
    /**
     * Whether the search button is displayed.
     */
    private BooleanProperty showSearchButton = new SimpleBooleanProperty(this, "showSearchButton", true);
    /**
     * Whether the detail area is displayed.
     */
    private BooleanProperty showDetails = new SimpleBooleanProperty(this, "showDetails", false);
    /**
     * Whether the ID is displayed.
     */
    private BooleanProperty showID = new SimpleBooleanProperty(this, "showID", true);
    /**
     * Whether the content should be translated.
     */
    private BooleanProperty translate = new SimpleBooleanProperty(this, "translate", false);
    /**
     * How many rows in the details area are expected.
     * 2 by default.
     */
    private IntegerProperty detailsRows = new SimpleIntegerProperty(this, "detailsRows", 2);
    /**
     * The current state.
     */
    private ObjectProperty<State> state = new SimpleObjectProperty<>();
    /**
     * Whether this RefBox has custom commands.
     */
    private BooleanProperty hasCmds = new SimpleBooleanProperty(this, "hasCmds", false);
    /**
     * List of IDs to ignore while searching.
     */
    private ListProperty<Integer> exceptedIds = new SimpleListProperty<>(this, "exceptedIds", FXCollections.observableArrayList());

    private boolean ignoreTextChange;
    private boolean keepText;

    /**
     * The related provider.
     */
    private ObjectProperty<Provider<T>> provider = new SimpleObjectProperty<>(this, "provider", null);

    /**
     * Initializes the {@link RefBox} and its behavior.
     */
    public RefBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controls/refbox.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.txfText.setOnKeyPressed(ev ->
        {
            if (ev.getCode() == KeyCode.DOWN)
                this.search();
        });
        this.txfText.editableProperty().bind(changeableProperty());
        this.txfText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            if (this.ignoreTextChange || !isChangeable())
                return;
            this.keepText = true;
            this.setRecord(null);
            this.keepText = false;
            this.search();
        });
        this.record.addListener((observable, oldValue, newValue) -> this.update());
        this.state.addListener((observable, oldValue, newValue) ->
        {
            switch (newValue) {
                case LOGGED_IN:
                    this.txfText.setStyle("-fx-text-fill: black");
                    break;
                case NO_RESULTS:
                    this.txfText.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
                    break;
                case SEARCHING:
                    this.txfText.setStyle("-fx-text-fill: orange; -fx-font-weight: bold");
                    break;
            }
        });

        this.txfText.textProperty().addListener((observable, oldValue, newValue) -> this.showSepButtons());
        this.txfText.widthProperty().addListener((observable, oldValue, newValue) -> this.showSepButtons());

        this.hbxButtons.widthProperty().addListener((observable, oldValue, newValue) -> {
            this.txfText.setPadding(new Insets(5, (double) newValue, 5, 5));
            this.setMinWidth((double) newValue + 50);
            this.showSepButtons();
            Platform.runLater(this::requestLayout);
        });
        this.showNewButton.addListener((observable, oldValue, newValue) -> this.update());
        this.initBtn(this.btnNew, ev -> {
            if (this.getProvider() != null) {
                RecordEditor<T> editor = this.getProvider().openEditor(null);
                if (editor != null)
                    editor.recordItem().addListener((observable, oldValue, newValue) -> this.setRecord(newValue));
            }
        });
        this.showEditButton.addListener((observable, oldValue, newValue) -> this.update());
        this.initBtn(this.btnEdit, ev -> {
            if (this.getProvider() != null) {
                RecordEditor<T> editor = this.getProvider().openEditor(this.getRecord());
                if (editor != null)
                    editor.recordItem().addListener((observable, oldValue, newValue) -> this.setRecord(newValue));
            }
        });
        this.showSearchButton.addListener((observable, oldValue, newValue) -> this.update());
        this.initBtn(this.btnSearch, ev -> this.search());

        this.sepCommands.visibleProperty().bind(this.hasCmds.and(showEditButton().or(showNewButton().and(changeableProperty())).or(showSearchButton().and(changeableProperty()))));
        this.sepCommands.managedProperty().bind(this.hasCmds.and(showEditButton().or(showNewButton().and(changeableProperty())).or(showSearchButton().and(changeableProperty()))));

        this.showDetails.addListener((observable, oldValue, newValue) ->
        {
            this.txfDetails.setVisible(newValue);
            this.txfDetails.setManaged(newValue);
        });
        this.txfDetails.setVisible(this.isShowDetails());
        this.txfDetails.setManaged(this.isShowDetails());
        this.detailsRows.addListener((observable, oldValue, newValue) -> this.txfDetails.setPrefHeight((int) newValue * 20 + 15));
        this.txfDetails.setPrefHeight(this.getDetailsRows() * 20 + 15);

        this.provider.addListener((observable, oldValue, newValue) -> this.update());

        this.setRecord(null);
        this.update();
    }

    /**
     * Returns the referenced record.
     *
     * @return The referenced record.
     */
    public T getRecord() {
        return record.get();
    }

    /**
     * Sets the record to reference.
     *
     * @param record The record to reference.
     */
    public void setRecord(T record) {
        this.record.set(record);
        if (this.refBoxList != null)
            this.refBoxList.hide();
    }

    public String getText() {
        return this.txfText.getText();
    }

    public void setText(String text) {
        if (!this.keepText)
            this.txfText.setText(text);
    }

    public boolean isShowNewButton() {
        return showNewButton.get();
    }

    public void setShowNewButton(boolean showNewButton) {
        this.showNewButton.set(showNewButton);
    }

    public boolean isShowEditButton() {
        return showEditButton.get();
    }

    public void setShowEditButton(boolean showEditButton) {
        this.showEditButton.set(showEditButton);
    }

    public boolean isShowSearchButton() {
        return showSearchButton.get();
    }

    public void setShowSearchButton(boolean showSearchButton) {
        this.showSearchButton.set(showSearchButton);
    }

    public boolean isShowDetails() {
        return showDetails.get();
    }

    public void setShowDetails(boolean showDetails) {
        this.showDetails.set(showDetails);
    }

    public boolean isShowID() {
        return showID.get();
    }

    public void setShowID(boolean showID) {
        this.showID.set(showID);
    }

    public boolean isTranslate() {
        return translate.get();
    }

    public void setTranslate(boolean translate) {
        this.translate.set(translate);
    }

    public int getDetailsRows() {
        return detailsRows.get();
    }

    public void setDetailsRows(int detailsRows) {
        this.detailsRows.set(detailsRows);
    }

    public Provider<T> getProvider() {
        return provider.get();
    }

    public void setProvider(Provider provider) {
        this.provider.set(provider);
    }

    public ObjectProperty<Provider<T>> providerProperty() {
        return provider;
    }

    public StringProperty textProperty() {
        return this.txfText.textProperty();
    }

    public ObjectProperty<T> recordProperty() {
        return record;
    }

    public BooleanProperty changeableProperty() {
        return changeable;
    }

    public boolean isChangeable() {
        return changeable.get();
    }

    public void setChangeable(boolean changeable) {
        this.changeable.set(changeable);
    }

    public BooleanProperty showNewButton() {
        return showNewButton;
    }

    public BooleanProperty showEditButton() {
        return showEditButton;
    }

    public BooleanProperty showSearchButton() {
        return showSearchButton;
    }

    public BooleanProperty showDetails() {
        return this.showDetails;
    }

    public BooleanProperty showID() {
        return this.showID;
    }

    public IntegerProperty detailsRowsProperty() {
        return detailsRows;
    }

    public void setOnNewAction(EventHandler<ActionEvent> eventHandler) {
        this.btnNew.setOnAction(eventHandler);
    }

    public void setOnEditAction(EventHandler<ActionEvent> eventHandler) {
        this.btnEdit.setOnAction(eventHandler);
    }

    public final void setOnAction(EventHandler<ActionEvent> value) {
        this.txfText.onActionProperty().set(value);
    }

    public ObservableList<Integer> getExceptedIds() {
        return exceptedIds.get();
    }

    public void setExceptedIds(List<Integer> exceptedIds) {
        if (this.exceptedIds.get() != null) {
            this.exceptedIds.get().clear();
            if (exceptedIds != null)
                this.exceptedIds.get().addAll(exceptedIds);
        } else if (exceptedIds != null)
            this.exceptedIds.set(FXCollections.observableArrayList(exceptedIds));
    }

    public ListProperty<Integer> exceptedIdsProperty() {
        return exceptedIds;
    }

    public void addCommand(Command cmd) {
        if (cmd == null)
            return;

        for (Node n : this.hbxCommands.getChildren()) {
            if (n instanceof Button && n.getId().equals(cmd.getId().toString())) {
                return; // Already known
            }
        }

        Button btn = new de.odinoxin.aidware.aiddesk.controls.translateable.Button("");
        if (cmd.getNode() != null)
            btn.setGraphic(cmd.getNode());
        else {
            SVGPath svgBug = new SVGPath();
            svgBug.setContent("M20 8h-2.81c-.45-.78-1.07-1.45-1.82-1.96L17 4.41 15.59 3l-2.17 2.17C12.96 5.06 12.49 5 12 5c-.49 0-.96.06-1.41.17L8.41 3 7 4.41l1.62 1.63C7.88 6.55 7.26 7.22 6.81 8H4v2h2.09c-.05.33-.09.66-.09 1v1H4v2h2v1c0 .34.04.67.09 1H4v2h2.81c1.04 1.79 2.97 3 5.19 3s4.15-1.21 5.19-3H20v-2h-2.09c.05-.33.09-.66.09-1v-1h2v-2h-2v-1c0-.34-.04-.67-.09-1H20V8zm-6 8h-4v-2h4v2zm0-4h-4v-2h4v2z");
            btn.setGraphic(svgBug);
        }
        btn.setId(cmd.getId().toString());
        this.initBtn(btn, ev -> cmd.execute());
        btn.disableProperty().bind(cmd.canExecuteProperty().not());
        this.hbxCommands.getChildren().add(btn);
        this.hasCmds.set(true);
    }

    public void removeCommand(Command cmd) {
        if (cmd == null)
            return;

        Button btn = null;
        for (Node n : this.hbxCommands.getChildren()) {
            if (n instanceof Button && n.getId().equals(cmd.getId().toString())) {
                btn = (Button) n;
                break;
            }
        }
        if (btn != null) {
            this.hbxCommands.getChildren().remove(btn);
            this.hasCmds.set(!this.hbxCommands.getChildren().isEmpty());
        }
    }

    /**
     * Updates the state and style, based on the current record.
     */
    private void update() {
        this.ignoreTextChange = true;
        RefBoxListItem<T> item;
        boolean showNewBtn = false;
        boolean showEditBtn = false;
        boolean showSearchBtn = false;
        if (this.getProvider() != null) {
            if (this.getRecord() != null) {
                item = this.getProvider().getRefBoxItem(this.getRecord());
                this.setText(item.getText());
                this.txfDetails.setText(item.getSubText());
                this.state.set(State.LOGGED_IN);
            } else {
                this.state.set(State.SEARCHING);
                if (!this.keepText)
                    this.txfText.setText("");
                this.txfDetails.setText("");
            }
            showNewBtn = this.isShowNewButton() && isChangeable();
            showEditBtn = this.getRecord() != null && this.isShowEditButton();
            showSearchBtn = this.isShowSearchButton() && isChangeable();
        } else if (!this.isDisabled()) {
            item = new RefBoxListItem<T>(null, "Provider not set!", "", new String[]{"Provider", "not", "set!"});
            this.setText(item.getText());
            this.txfDetails.setText(item.getSubText());
            this.state.set(State.NO_RESULTS);
        }
        this.btnNew.setVisible(showNewBtn);
        this.btnNew.setManaged(showNewBtn);
        this.btnEdit.setVisible(showEditBtn);
        this.btnEdit.setManaged(showEditBtn);
        this.btnSearch.setVisible(showSearchBtn);
        this.btnSearch.setManaged(showSearchBtn);
        this.showSepButtons();
        this.ignoreTextChange = false;
    }

    private void search() {
        this.search(MAX_SEARCH_RESULT);
    }

    /**
     * Searches for matching records and displays the suggestions list with the found records ordered by match percentage.
     *
     * @param max Number of maximum results to search for.
     */
    private void search(int max) {
        this.txfText.requestFocus();
        if (this.refBoxList != null)
            this.refBoxList.hide();
        if (!this.isChangeable())
            return;
        this.refBoxList = new RefBoxList<>(this.localToScreen(0, this.txfText.getHeight()));
        this.refBoxList.setPrefWidth(this.getWidth());
        this.refBoxList.getSuggestionsList().setCellFactory(param -> new RefBoxListItemCell(max, showID.get()));
        String[] highlight = this.txfText.getText() == null || this.txfText.getText().isEmpty() ? null : this.txfText.getText().split(" ");
        if (this.getProvider() != null) {
            List<RefBoxListItem<T>> result = this.getProvider().search(highlight == null ? null : Arrays.asList(highlight), max, exceptedIds.get());
            if (result != null) {
                for (RefBoxListItem<T> item : result)
                    item.setHighlight(highlight);
                this.refBoxList.getSuggestionsList().getItems().addAll(result);
                if (result.size() > 0) {
                    if (this.state.get() == State.NO_RESULTS)
                        this.state.set(State.SEARCHING);
                    this.refBoxList.getSuggestionsList().setOnKeyPressed(ev ->
                    {
                        switch (ev.getCode()) {
                            case TAB:
                                this.btnSearch.requestFocus();
                            case ENTER:
                                RefBoxListItem<T> item = this.refBoxList.getSuggestionsList().getSelectionModel().getSelectedItem();
                                if (item != null) {
                                    if (item.getRecord() == null)
                                        this.search(max + MAX_SEARCH_RESULT);
                                    else
                                        this.setRecord(item.getRecord());
                                }
                                break;
                            case A:
                                if (ev.isControlDown())
                                    this.txfText.selectAll();
                                break;
                            case ESCAPE:
                                this.refBoxList.hide();
                                break;
                        }
                    });
                    this.refBoxList.getSuggestionsList().setOnMouseClicked(ev ->
                    {
                        if (ev.getButton() == MouseButton.PRIMARY && ev.getClickCount() == 2) {
                            RefBoxListItem<T> item = this.refBoxList.getSuggestionsList().getSelectionModel().getSelectedItem();
                            if (item != null) {
                                if (item.getRecord() == null)
                                    this.search(max + MAX_SEARCH_RESULT);
                                else
                                    this.setRecord(item.getRecord());
                            }
                        }
                    });
                    for (RefBoxListItem item : this.refBoxList.getSuggestionsList().getItems()) {
                        item.matchProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) ->
                        {
                            Collections.sort(RefBox.this.refBoxList.getSuggestionsList().getItems(), (RefBoxListItem item1, RefBoxListItem item2) ->
                            {
                                if (item1.getMatch() < item2.getMatch())
                                    return 1;
                                else if (item1.getMatch() == item2.getMatch())
                                    return 0;
                                return -1;
                            });
                            RefBox.this.refBoxList.getSuggestionsList().getSelectionModel().selectFirst();
                        });
                    }
                    if (this.getScene() != null) {
                        this.refBoxList.show(this.getScene().getWindow());
                        this.refBoxList.getSuggestionsList().getSelectionModel().selectFirst();
                    }
                } else
                    this.state.set(State.NO_RESULTS);
            }
        } else {
            RefBoxListItem<T> item = new RefBoxListItem<>(null, "Provider not set!", "", new String[]{"Provider", "not", "set!"});
            this.setText(item.getText());
            this.txfDetails.setText(item.getSubText());
            this.state.set(State.NO_RESULTS);
        }
    }

    /**
     * Returns a {@link ChangeListener}, which highlights the given button with a blue shadow, when the observed value becomes true and undoes these changes, when the observed value becomes false.
     *
     * @param btn The button to highlight.
     * @return A new {@link ChangeListener} as described above.
     */
    private ChangeListener<Boolean> getBtnHighlighter(Button btn) {
        return (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            Shape shape = null;
            if (btn.getGraphic() instanceof Shape)
                shape = (Shape) btn.getGraphic();
            if (newValue) {
                Glow glow = new Glow();
                glow.setLevel(1d / 3d);
                if (shape != null)
                    shape.setFill(Color.web("#039ED3"));
                btn.setTextFill(Color.web("#039ED3"));
                btn.setEffect(glow);
            } else {
                btn.setTextFill(Color.BLACK);
                if (shape != null)
                    shape.setFill(Color.BLACK);
                btn.setEffect(null);
            }
        };
    }

    private void initBtn(Button btn, EventHandler<ActionEvent> onAction) {
        btn.setStyle("-fx-background-color: transparent");
        btn.setPickOnBounds(false);
        btn.minHeightProperty().bind(this.txfText.heightProperty().subtract(2));
        btn.maxHeightProperty().bind(this.txfText.heightProperty().subtract(2));
        btn.minWidthProperty().bind(this.txfText.heightProperty());
        btn.maxWidthProperty().bind(this.txfText.heightProperty());
        if (btn.getGraphic() instanceof SVGPath) {
            SVGPath original = (SVGPath) btn.getGraphic();
            SVGPath svgPath = new SVGPath();
            svgPath.setContent(original.getContent());
            btn.setGraphic(svgPath);
            double divisor = Math.max(original.boundsInLocalProperty().get().getWidth(), original.boundsInLocalProperty().get().getHeight());
            double factor = 0.70;
            double customScaleX = original.getScaleX();
            double customScaleY = original.getScaleY();
            svgPath.scaleXProperty().bind(btn.widthProperty().divide(divisor).multiply(factor).multiply(customScaleX));
            svgPath.scaleYProperty().bind(btn.heightProperty().divide(divisor).multiply(factor).multiply(customScaleY));
        }
        btn.setOnKeyPressed(ev ->
        {
            switch (ev.getCode()) {
                case ENTER:
                    btn.fire();
                    ev.consume();
                    break;
                case DOWN:
                    this.search();
                    ev.consume();
                    break;
            }
        });
        btn.setOnAction(onAction);
        btn.focusedProperty().addListener(this.getBtnHighlighter(btn));
        btn.hoverProperty().addListener(this.getBtnHighlighter(btn));
    }

    private void showSepButtons() {
        boolean show = this.txfText.getWidth() - (this.txfText.getPadding().getRight() + this.txfText.getPadding().getLeft()) <= TextUtils.computeTextWidth(this.txfText.getFont(), this.txfText.getText(), 0d);
        this.sepButtons.setVisible(show);
    }

    /**
     * Requests the focus on the {@link TextField}, when te {@link RefBox} requests focus.
     */
    @Override
    public void requestFocus() {
        super.requestFocus();
        this.txfText.requestFocus();
    }

    /**
     * States, which the RefBox could assume.
     */
    private enum State {
        NO_RESULTS,
        SEARCHING,
        LOGGED_IN,
    }
}
