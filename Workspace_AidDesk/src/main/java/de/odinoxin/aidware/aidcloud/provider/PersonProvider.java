package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.people.Person;
import de.odinoxin.aidware.aiddesk.plugins.people.PersonEditor;

public class PersonProvider extends Provider<Person> {

    @Override
    public RefBoxListItem<Person> getRefBoxItem(Person item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getForename() == null ? "" : item.getForename()) + " " + (item.getName() == null ? "" : item.getName()),
                (item.getCode() == null ? "" : item.getCode()) + "\n" +
                        (item.getAddress() == null ? "" :
                                (item.getAddress().getStreet() == null ? "" : item.getAddress().getStreet()) + " " +
                                        (item.getAddress().getHsNo() == null ? "" : item.getAddress().getHsNo()) + "\n" +
                                        (item.getAddress().getZip() == null ? "" : item.getAddress().getZip()) + " " +
                                        (item.getAddress().getCountry() == null ? "" :
                                                (item.getAddress().getCountry().getName() == null ? "" : item.getAddress().getCountry().getName())))
        );
    }

    @Override
    public PersonEditor openEditor(Person entity) {
        return new PersonEditor(entity);
    }

//    public static boolean changePwd(int id, String currentPwd, String newPwd) {
//        if (PersonProvider.getSvc() != null)
//            return PersonProvider.getSvc().changePwd(id, currentPwd, newPwd);
//        return false;
//    }
}
