package de.odinoxin.aiddesk.plugins.people;

import de.odinoxin.aiddesk.dialogs.merging.MergeDialog;

public class PersonMerger extends MergeDialog<Person> {

    public PersonMerger() {
        super(new PersonView(), new PersonView(), new PersonView());
    }
}
