package de.odinoxin.aiddesk.plugins;

import de.odinoxin.aidcloud.provider.PersonProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.provider.TrackedChangeProvider;
import de.odinoxin.aidcloud.provider.TranslatorProvider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aiddesk.controls.translateable.Button;
import de.odinoxin.aiddesk.dialogs.DecisionDialog;
import de.odinoxin.aiddesk.dialogs.MergeDialog;
import de.odinoxin.aiddesk.dialogs.MsgDialog;
import de.odinoxin.aiddesk.plugins.people.Person;
import de.odinoxin.aiddesk.utils.Command;
import javafx.beans.property.*;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.SVGPath;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

/**
 * Base class for RecordEditors.
 *
 * @param <T> The type of the RecordItem to edit
 */
public abstract class RecordEditor<T extends RecordItem<?>> extends Plugin {

    private TextField txfId;
    private RefBox<T> refBoxKey;
    private RecordView<T> view = newView(null);
    private TextField txfTrackedChangeDate;
    private RefBox<Person> refBoxTrackedChangeUser;
    private Button btnSave;
    private Button btnDiscard;
    private Button btnDelete;

    private Provider<T> provider;
    private static TrackedChangeProvider trackedChangeProvider = new TrackedChangeProvider();
    private static PersonProvider personProvider = new PersonProvider();
    private static DateFormat timestampFormatter = new SimpleDateFormat("dd. MMM. yyyy, HH:mm:ss");

    /**
     * The current record.
     */
    private ObjectProperty<T> record = new SimpleObjectProperty<>();
    private BooleanProperty storeable = new SimpleBooleanProperty(true);
    private BooleanProperty deletable = new SimpleBooleanProperty(true);
    private ReadOnlyBooleanWrapper changedWrapper = new ReadOnlyBooleanWrapper();
    /**
     * The original, unmodified RecordItem from the service.
     * For resetting use-cases.
     */
    private T original;

    /**
     * Initializes the RecordEditor.
     *
     * @param title The title of the editor.
     */
    public RecordEditor(String title) {
        super("/plugins/recordeditor.fxml", title);

        try {
            this.provider = this.initProvider();
            this.view = newView(null);
            this.view.setViewMode(RecordView.ViewMode.EDITING);
            this.view.setOnKeyPressed(ev -> checkHotKeys(ev));
            this.refBoxKey = (RefBox<T>) this.root.lookup("#refBoxKey");
            this.refBoxKey.setProvider(this.provider);
            this.refBoxKey.setOnNewAction(ev -> refBoxNewAction());
            this.refBoxKey.recordProperty().addListener((observable, oldValue, newValue) -> {
                if ((newValue == null && this.getRecord() != null && this.getRecord().getId() != 0)
                        || (newValue != null && newValue != this.getRecord()))
                    this.attemptLoadRecord(newValue == null || this.provider == null ? null : this.provider.get(newValue.getId()));
            });
            SVGPath svgRefresh = new SVGPath();
            svgRefresh.setContent("M125.938,208.901l-39.102-39.408c-25.01,32.209-40.502,69.32-45.251,107.941h56.06" +
                    "C101.54,253.299,111.234,229.71,125.938,208.901z M97.646,332.881h-56.06c4.748,38.621,19.978,75.775,44.988,107.941l39.08-39.365" +
                    "C111.234,380.626,101.54,357.301,97.646,332.881z M125.654,480.515c32.209,24.967,69.67,39.955,108.181,44.638V468.83" +
                    "c-24.135-4.157-47.439-13.61-68.248-28.555L125.654,480.515z M289.304,85.162V0l-126.19,126.212l126.212,123.476V141.222" +
                    "c78.839,13.304,138.706,81.552,138.706,163.935s-59.889,150.653-138.706,164.001v55.994" +
                    "c109.626-13.61,194.241-106.759,194.241-219.995C483.545,191.987,398.93,98.751,289.304,85.162z");
            this.refBoxKey.addCommand(new Command(
                    () -> this.attemptLoadRecord(this.getServerRecord()),
                    new SimpleBooleanProperty(true),
                    new SimpleObjectProperty<>(svgRefresh)
            ));
            super.setOnCloseRequest(ev -> closeRequest(ev));
            this.txfId = (TextField) this.root.lookup("#txfId");
            ((ScrollPane) this.root.lookup("#boxDetails")).setContent(view);

            this.txfTrackedChangeDate = (TextField) this.root.lookup("#txfTrackedChangeDate");
            this.refBoxTrackedChangeUser = (RefBox<Person>) this.root.lookup("#refBoxTrackedChangeUser");
            this.refBoxTrackedChangeUser.setProvider(new PersonProvider());

            this.btnSave = (Button) this.root.lookup("#btnSave");
            this.btnSave.setOnAction(ev -> saveAction());
            setButtonEnter(this.btnSave);
            this.btnDiscard = (Button) this.root.lookup("#btnDiscard");
            this.btnDiscard.setOnAction(ev -> this.discard());
            setButtonEnter(this.btnDiscard);
            this.recordItem().addListener((observable, oldValue, newValue) -> bindButtons(newValue));
            this.btnDelete = (Button) this.root.lookup("#btnDelete");
            this.btnDelete.setOnAction(ev -> deleteAction());
            setButtonEnter(this.btnDelete);
            this.btnDiscard.setOnKeyPressed(ev -> checkHotKeys(ev));
            this.btnDelete.setOnKeyPressed(ev -> checkHotKeys(ev));
            this.btnSave.setOnKeyPressed(ev -> checkHotKeys(ev));
            this.refBoxKey.setOnKeyPressed(ev -> checkHotKeys(ev));
            this.txfId.setOnKeyPressed(ev -> checkHotKeys(ev));

            this.show();
            this.sizeToScene();
            this.centerOnScreen();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initializes the RecordEditor and attempts to load the given Record.
     *
     * @param title  the title of the editor.
     * @param record the record to load.
     */
    public RecordEditor(String title, T record) {
        this(title);
        this.attemptLoadRecord(record);
    }

    /**
     * Returns the current record.
     *
     * @return The current record.
     */
    public ObjectProperty<T> recordItem() {
        return record;
    }

    /**
     * Indicates, whether the current record has any changes.
     *
     * @return ReadOnlyBooleanProperty to listen to the first change.
     */
    public ReadOnlyBooleanProperty isChanged() {
        return this.changedWrapper.getReadOnlyProperty();
    }

    /**
     * Attempts to load the given record.
     *
     * @param record The record to load.
     */
    public void attemptLoadRecord(T record) {
        if (this.getRecord() != null && this.getRecord().isChanged()) {
            if (discardDialog())
                setRecord(record);
            else
                this.refBoxKey.setRecord(this.getRecord());
        } else
            setRecord(record);
    }

    /**
     * Called, when a new record was created.
     */
    protected void onNew() {
        this.view.requestFocus();
    }

    /**
     * Called, when the current record should be saved.
     *
     * @return The saved record.
     */
    protected abstract T onSave() throws ConcurrentFault_Exception;

    /**
     * Called, when the current record should be deleted.
     *
     * @return Success indicator
     */
    protected abstract boolean onDelete();

    /**
     * Sets, whether saving is enabled.
     *
     * @param storeable True, if saving is enabled; False otherwise.
     */
    protected void setStoreable(boolean storeable) {
        this.storeable.set(storeable);
    }

    /**
     * Sets, whether the deleting is enabled.
     *
     * @param deletable True, if deleting is enabled; False otherwise.
     */
    protected void setDeletable(boolean deletable) {
        this.deletable.set(deletable);
    }

    /**
     * Sets the current item, without checking for current changes to save.
     *
     * @param record The record to set.
     */
    protected void setRecord(T record) {
        this.record.set(record);
        this.refBoxKey.setRecord(record);
        if (this.changedWrapper.isBound())
            this.changedWrapper.unbind();
        this.changedWrapper.bind(record.changedProperty());
        if (this.getRecord() != null) {
            view.bind(this.getRecord());
            TrackedChange change = trackedChangeProvider.getLastChangeEntry(record.getClass().getSimpleName(), record.getId());
            if (change != null) {
                this.txfTrackedChangeDate.setText(timestampFormatter.format(change.getTimestamp()));
                this.refBoxTrackedChangeUser.setRecord(personProvider.get(change.getUserId()));
            } else {
                this.txfTrackedChangeDate.setText(null);
                this.refBoxTrackedChangeUser.setRecord(null);
            }
            this.getRecord().setChanged(false);
        }
    }

    /**
     * Returns the current record.
     *
     * @return The current record.
     */
    public T getRecord() {
        return this.record.get();
    }

    /**
     * Returns the original record.
     *
     * @return The original record.
     */
    public T getOriginalRecord() {
        return this.original;
    }

    /**
     * Returns the current record from the server.
     *
     * @return The current record from the server.
     */
    public T getServerRecord() {
        return this.provider.get(this.getRecord().getId());
    }

    /**
     * Returns a clone of the original record.
     *
     * @return A clone of the original record.
     */
    public T getClonedRecord() {
        if (this.getOriginalRecord() == null)
            return null;
        return (T) this.getOriginalRecord().clone();
    }

    /**
     * Replaces the value, which is interpreted as original record.
     *
     * @param record The new original.
     */
    public void setOriginalRecord(T record) {
        this.original = record;
    }

    /**
     * Initializes the provider.
     *
     * @return The initialized provider.
     */
    protected abstract Provider<T> initProvider();

    /**
     * Returns the provider.
     *
     * @return The provider.
     */
    protected Provider<T> getProvider() {
        return this.provider;
    }

    /**
     * Creates a new view and binds the given record to it.
     *
     * @param record The record to bind.
     * @return The created view.
     */
    public abstract RecordView<T> newView(T record);

    /**
     * Adds all hotkeys as click action.
     *
     * @param ev The Event to add the keys.
     */
    private void checkHotKeys(KeyEvent ev) {
        if (ev.isControlDown()) {
            switch (ev.getCode()) {
                case S:
                    saveAction();
                    ev.consume();
                    break;
                case N:
                    refBoxNewAction();
                    ev.consume();
                    break;
            }
        }
        if (ev.getCode() == KeyCode.F5) {
            this.attemptLoadRecord(this.getServerRecord());
            ev.consume();
        }
    }

    /**
     * An action that leaves the RecordEditor empty
     */
    private void refBoxNewAction() {
        this.attemptLoadRecord(null);
        this.refBoxKey.setRecord(this.getRecord());
        this.onNew();
    }

    /**
     * An action that starts to save the record
     */
    private void saveAction() {
        if (this.getRecord().isChanged()) {
            try {
                T newObj = this.onSave();
                if (newObj != null) {
                    this.getRecord().setChanged(false);
                    this.attemptLoadRecord(newObj);
                    this.refBoxKey.setRecord(newObj);
                }
            } catch (ConcurrentFault_Exception ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                MergeDialog mergeDialog = new MergeDialog<T>(this);
                mergeDialog.show();
//                ex.printStackTrace();
            }
        }
    }

    /**
     * An action that delete the record
     */
    private void deleteAction() {
        if (this.getRecord() != null && this.getRecord().getId() != 0) {
            DecisionDialog dialog = new DecisionDialog(this, "Delete data?", "Delete data irrevocably?");
            Optional<ButtonType> dialogRes = dialog.showAndWait();
            if (dialogRes.isPresent() && ButtonType.OK.equals(dialogRes.get())) {
                boolean succeeded = this.onDelete();
                if (succeeded) {
                    this.attemptLoadRecord(null);
                    this.refBoxKey.setRecord(null);
                    this.onNew();
                    new MsgDialog(this, Alert.AlertType.INFORMATION, "Deleted!", "Successfully deleted.").show();
                }
            }
        }
    }

    /**
     * An action that ask the user if the edited record should be saved or not
     *
     * @param ev The Event to cancel the original closeRequest
     */
    private void closeRequest(Event ev) {
        if (this.getRecord() != null && this.getRecord().isChanged() && !discardDialog())
            ev.consume();
    }

    /**
     * Binds the buttons to the new record and stores the record as original item.
     *
     * @param record The record to bind and to use as original item.
     */
    private void bindButtons(T record) {
        if (record == null) {
            this.original = null;
            this.txfId.setText("");
            this.btnSave.setDisable(true);
            this.btnDiscard.setDisable(true);
            this.btnDelete.setDisable(true);
        } else {
            this.original = (T) record.clone();
            this.txfId.setText(record.getId() == 0 ? TranslatorProvider.getTranslation("New") : String.valueOf(record.getId()));
            if (this.btnSave.disableProperty().isBound())
                this.btnSave.disableProperty().unbind();
            this.btnSave.disableProperty().bind(this.storeable.not().or(record.changedProperty().not()));
            if (this.btnDiscard.disableProperty().isBound())
                this.btnDiscard.disableProperty().unbind();
            this.btnDiscard.disableProperty().bind(record.changedProperty().not());
            if (this.btnDelete.disableProperty().isBound())
                this.btnDelete.disableProperty().unbind();
            this.btnDelete.disableProperty().bind(this.deletable.not().or(record.idProperty().isEqualTo(0)));
        }
    }

    /**
     * This method opens a dialog where the user is asked if the changes should be discardet
     *
     * @return true if the user pressed OK and false if not
     */
    private boolean discardDialog() {
        DecisionDialog dialog = new DecisionDialog(this, "Discard changes?", "Discard current changes?");
        Optional<ButtonType> dialogRes = dialog.showAndWait();
        return ButtonType.OK.equals(dialogRes.get());
    }

    /**
     * Discard all current changes, by restoring the original item.
     */
    private void discard() {
        this.attemptLoadRecord(this.original);
    }
}
