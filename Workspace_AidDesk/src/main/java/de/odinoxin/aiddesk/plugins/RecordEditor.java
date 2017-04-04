package de.odinoxin.aiddesk.plugins;

import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.provider.TranslatorProvider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aiddesk.controls.translateable.Button;
import de.odinoxin.aiddesk.dialogs.DecisionDialog;
import de.odinoxin.aiddesk.dialogs.MsgDialog;
import de.odinoxin.aiddesk.dialogs.MergeDialog;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Optional;

/**
 * Base class for RecordEditors.
 *
 * @param <T> The type of the RecordItem to edit
 */
public abstract class RecordEditor<T extends RecordItem<?>> extends Plugin {

    private TextField txfId;
    private RefBox<T> refBoxKey;
    private Button btnRefresh;
    private RecordView<T> view = newView(null);
    private Button btnSave;
    private Button btnDiscard;
    private Button btnDelete;

    private Provider<T> provider;
    /**
     * The current record.
     */
    private ObjectProperty<T> recordItem = new SimpleObjectProperty<>();
    private BooleanProperty storeable = new SimpleBooleanProperty(true);
    private BooleanProperty deletable = new SimpleBooleanProperty(true);
    private ReadOnlyBooleanWrapper changedWrapper = new ReadOnlyBooleanWrapper();
    /**
     * The original, unmodified RecordItem from the service.
     * For resetting use-cases.
     */
    private T original;

    public RecordEditor(String title) {
        super("/plugins/recordeditor.fxml", title);

        try {
            this.provider = this.initProvider();
            this.view = newView(null);
            this.view.setOnKeyPressed(ev -> HotkeyEvent(ev));
            this.refBoxKey = (RefBox<T>) this.root.lookup("#refBoxKey");
            this.refBoxKey.setProvider(this.provider);
            this.refBoxKey.setOnNewAction(ev -> refBoxNewAction());
            this.refBoxKey.recordProperty().addListener((observable, oldValue, newValue) -> this.attemptLoadRecord(newValue == null || this.provider == null ? null : this.provider.get(newValue.getId())));
            super.setOnCloseRequest(ev -> closeRequest(ev));
            this.btnRefresh = (Button) this.root.lookup("#btnRefresh");
            this.btnRefresh.setOnAction(ev -> this.attemptLoadRecord(this.provider.get(this.getRecordItem().getId())));
            this.txfId = (TextField) this.root.lookup("#txfId");
            ((ScrollPane) this.root.lookup("#boxDetails")).setContent(view);

            this.btnSave = (Button) this.root.lookup("#btnSave");
            this.btnSave.setOnAction(ev -> saveAction());
            setButtonEnter(this.btnSave);
            this.btnDiscard = (Button) this.root.lookup("#btnDiscard");
            this.btnDiscard.setOnAction(ev -> this.discard());
            setButtonEnter(this.btnDiscard);
            this.recordItem().addListener((observable, oldValue, newValue) -> recordItemListener(observable, oldValue, newValue));

            this.btnDelete = (Button) this.root.lookup("#btnDelete");
            this.btnDelete.setOnAction(ev -> deleteAction());
            setButtonEnter(this.btnDelete);
            this.btnDiscard.setOnKeyPressed(ev -> HotkeyEvent(ev));
            this.btnDelete.setOnKeyPressed(ev -> HotkeyEvent(ev));
            this.btnSave.setOnKeyPressed(ev -> HotkeyEvent(ev));
            this.btnRefresh.setOnKeyPressed(ev -> HotkeyEvent(ev));
            this.refBoxKey.setOnKeyPressed(ev -> HotkeyEvent(ev));
            this.txfId.setOnKeyPressed(ev -> HotkeyEvent(ev));

            this.show();
            this.sizeToScene();
            this.centerOnScreen();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns the view.
     *
     * @return The view.
     */
    protected RecordView<T> getView() {
        return this.view;
    }

    /**
     * Discard all current changes, by restoring the original item.
     */
    private void discard() {
        this.attemptLoadRecord(this.original);
    }

    /**
     * Returns the current record.
     *
     * @return The current record.
     */
    public ObjectProperty<T> recordItem() {
        return recordItem;
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
        if (this.getRecordItem() != null && this.getRecordItem().isChanged()) {
            boolean discard = discardDialog();
            if (discard)
                discardRecord(record);
            else
                this.refBoxKey.setRecord(this.getRecordItem());
        } else
            discardRecord(record);
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
     * Sets, whether saving is enabled.
     *
     * @param storeable True, if saving is enabled; False otherwise.
     */
    protected void setStoreable(boolean storeable) {
        this.storeable.set(storeable);
    }

    /**
     * Called, when the current record should be deleted.
     *
     * @return Success indicator
     */
    protected abstract boolean onDelete();

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
    protected abstract void setRecord(T record);

    /**
     * @return The current record item.
     */
    public T getRecordItem() {
        return this.recordItem.get();
    }

    public T getOriginalRecordItem() {
        return this.original;
    }

    /**
     * Returns the current record from the server.
     *
     * @return The current record from the server.
     */
    public T getServerRecordItem() {
        return this.provider.get(this.getRecordItem().getId());
    }

    /**
     * Returns a clone of the record item.
     *
     * @return A clone of the record item.
     */
    public T getClonedRecordItem() {
        if (this.getOriginalRecordItem() == null)
            return null;
        return (T) this.getOriginalRecordItem().clone();
    }

    /**
     * Sets the current record item.
     * Should only called by implementations of setRecord()!
     *
     * @param record The record to set.
     */
    protected void setRecordItem(T record) {
        this.recordItem.set(record);
        if (this.changedWrapper.isBound())
            this.changedWrapper.unbind();
        this.changedWrapper.bind(record.changedProperty());
    }

    /**
     * Replaces the value, which is interpreted as original record.
     *
     * @param record The new original.
     */
    public void setOriginalRecordItem(T record) {
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

    public abstract RecordView<T> newView(T record);

    /**
     * Adds all hotkeys as click action.
     *
     * @param ev The Event to add the keys.
     */
    private void HotkeyEvent(KeyEvent ev) {
        if(ev.isControlDown() && ev.getCode() == KeyCode.S)
        {
            saveAction();
            ev.consume();
        }
        if(ev.isControlDown() && ev.getCode() == KeyCode.N)
        {
            refBoxNewAction();
            ev.consume();
        }
    }

    /**
     * An action that leaves the RecordEditor empty
     *
     */
    private void refBoxNewAction()
    {
        this.attemptLoadRecord(null);
        this.refBoxKey.setRecord(this.getRecordItem());
        this.onNew();
    }

    /**
     * An action that starts to save the record
     *
     */
    private void saveAction()
    {
        if(this.getRecordItem().isChanged())
        {
            try {
                T newObj = this.onSave();
                if (newObj != null) {
                    this.getRecordItem().setChanged(false);
                    this.attemptLoadRecord(newObj);
                    this.refBoxKey.setRecord(newObj);
                }
            } catch (ConcurrentFault_Exception ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                MergeDialog mergeDialog = new MergeDialog<T>(this); //getOriginalItem(), newView(), newView(getRecordItem()), newView(resultRecord));
                mergeDialog.show();
//                    ex.printStackTrace();
            }
        }
    }

    /**
     * An action that delete the record
     *
     */
    private void deleteAction()
    {
        if (this.getRecordItem() != null && this.getRecordItem().getId() != 0) {
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
    private void closeRequest(Event ev)
    {
        if (this.getRecordItem() != null && this.getRecordItem().isChanged()) {
            boolean discard = discardDialog();
            if (discard) {
                discardRecord(getRecordItem());
            } else {
                ev.consume();
            }
        }
    }

     /**
     * I don't know what this Method do //TODO: OdinOxin
     *
     * @param observable ?.
     * @param oldValue ?.
     * @param newValue ?.
     */
    private void recordItemListener(ObservableValue observable, T oldValue, T newValue)
    {
        if (newValue == null) {
            this.original = null;
            this.txfId.setText("");
            this.btnSave.setDisable(true);
            this.btnDiscard.setDisable(true);
            this.btnDelete.setDisable(true);
        } else {
            this.original = (T) newValue.clone();
            this.txfId.setText(newValue.getId() == 0 ? TranslatorProvider.getTranslation("New") : String.valueOf(newValue.getId()));
            if (this.btnSave.disableProperty().isBound())
                this.btnSave.disableProperty().unbind();
            this.btnSave.disableProperty().bind(this.storeable.not().or(newValue.changedProperty().not()));
            if (this.btnDiscard.disableProperty().isBound())
                this.btnDiscard.disableProperty().unbind();
            this.btnDiscard.disableProperty().bind(newValue.changedProperty().not());
            if (this.btnDelete.disableProperty().isBound())
                this.btnDelete.disableProperty().unbind();
            this.btnDelete.disableProperty().bind(this.deletable.not().or(newValue.idProperty().isEqualTo(0)));
        }
    }
    /**
     * This method opens a dialog where the user is asked if the changes should be discardet
     *
     * @return true if the user pressed OK and false if not
     */
    private boolean discardDialog(){
        DecisionDialog dialog = new DecisionDialog(this, "Discard changes?", "Discard current changes?");
        Optional<ButtonType> dialogRes = dialog.showAndWait();
        if (ButtonType.OK.equals(dialogRes.get()))
            return true;
        return false;
    }

    /**
     * This method discard the record
     *
     * @param record  is the record wich should closed
     */
    private void discardRecord(T record){
        this.setRecord(record);
        if (this.getRecordItem() != null) {
            view.bind(this.getRecordItem());
            this.getRecordItem().setChanged(false);
        }
    }
}
