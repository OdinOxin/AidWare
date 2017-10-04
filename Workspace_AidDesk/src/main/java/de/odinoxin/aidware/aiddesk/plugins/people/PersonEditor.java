package de.odinoxin.aidware.aiddesk.plugins.people;

import de.odinoxin.aidware.aidcloud.provider.PersonProvider;
import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aidware.aiddesk.Login;
import de.odinoxin.aidware.aiddesk.dialogs.MsgDialog;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;
import javafx.scene.control.Alert;

public class PersonEditor extends RecordEditor<Person> {

    private String currentPwdw;

    public PersonEditor() {
        this(null);
    }

    public PersonEditor(Person record) {
        super("People", record);
    }

    @Override
    protected Person onSave() throws ConcurrentFault_Exception {
        if (this.currentPwdw != null && this.getRecord().getPwd() != null)
            if (!PersonProvider.changePwd(this.getRecord().getId(), this.currentPwdw, this.getRecord().getPwd()))
                new MsgDialog(this, Alert.AlertType.ERROR, "Fehlgeschlagen!", "Passwort konnte nicht geändert werden.").showAndWait();
        return super.onSave();
    }

    @Override
    protected void setRecord(Person person) {
        if (person == null)
            super.setRecord(new Person());
        else {
            this.setDeletable(person.getId() != Login.getPerson().getId());
            super.setRecord(person);
        }
    }

    @Override
    protected Provider<Person> initProvider() {
        return new PersonProvider();
    }

    @Override
    public RecordView<Person> newView(Person record) {
        return new PersonView(record, this);
    }

    void changePwd(String currentPwd, String newPwd) {
        this.currentPwdw = currentPwd;
        this.getRecord().setPwd(newPwd);
    }
}
