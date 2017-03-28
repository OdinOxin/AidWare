package de.odinoxin.aiddesk.plugins.people;

import de.odinoxin.aidcloud.provider.PersonProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.Login;
import de.odinoxin.aiddesk.dialogs.MsgDialog;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.scene.control.Alert;

public class PersonEditor extends RecordEditor<Person> {

    private String currentPwdw;

    public PersonEditor(Person person) {
        super("People");
        this.attemptLoadRecord(person);
        if (person == null)
            this.onNew();
    }

    @Override
    protected Person onSave() throws ConcurrentFault_Exception {
        if (this.currentPwdw != null && this.getRecordItem().getPwd() != null)
            if (!PersonProvider.changePwd(this.getRecordItem().getId(), this.currentPwdw, this.getRecordItem().getPwd()))
                new MsgDialog(this, Alert.AlertType.ERROR, "Fehlgeschlagen!", "Passwort konnte nicht geändert werden.").showAndWait();
        return this.getProvider().save(this.getRecordItem(), this.getOriginalItem());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecordItem().getId());
    }

    @Override
    protected void setRecord(Person person) {
        if (person == null)
            this.setRecordItem(new Person());
        else {
            this.setDeletable(person.getId() != Login.getPerson().getId());
            this.setRecordItem(person);
        }
    }

    @Override
    protected Provider<Person> initProvider() {
        return new PersonProvider();
    }

    protected RecordView<Person> newView(Person record) {
        return new PersonView(record, this);
    }

    void changePwd(String currentPwd, String newPwd) {
        this.currentPwdw = currentPwd;
        this.getRecordItem().setPwd(newPwd);
    }
}
