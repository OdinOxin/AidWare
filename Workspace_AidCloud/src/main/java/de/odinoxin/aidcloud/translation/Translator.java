package de.odinoxin.aidcloud.translation;

import de.odinoxin.aidcloud.DB;
import de.odinoxin.aidcloud.plugins.RecordHandler;
import org.hibernate.Session;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@WebService
public class Translator extends RecordHandler<Translation> {

    private static final Translation[] TRANSLATIONS = new Translation[]{
            new Translation("ID"),
            new Translation("OK"),
            new Translation("Yes", "Ja", null),
            new Translation("No", "Nein", null),
            new Translation("Cancel", "Abbrechen", null),
            new Translation("Save", "Speichern", null),
            new Translation("Discard", "Verwerfen", null),
            new Translation("Change", "Ändern", null),
            new Translation("New", "Neu", null),
            new Translation("Selection", "Auswahl", null),

            new Translation("MainMenu", "Hauptmenü", "Main menu"),
            new Translation("Main Menu", "Hauptmenü", "Main menu"),
            new Translation("MasterData", "Stammdaten Editor", "Master data editor"),
            new Translation("TransactionData", "Bewegungsdaten Editor", "Transaction data editor"),
            new Translation("Log out", "Ausloggen", null),
            new Translation("Log out?", "Ausloggen?", null),
            new Translation("Log out and close all related windows?", "Ausloggen und alle zugehörigen Fenster schließen?", null),
            new Translation("Exit", "Beenden", null),
            new Translation("Exit?", "Beenden?", null),
            new Translation("Exit AidDesk?", "AidDesk beenden und alle zugehörigen Fenster schließen?", "Exit AidDesk and close all related windows?"),

            new Translation("Invalid input!", "Ungültige Eingabe!", null),
            new Translation("Enter and repeat password.", "Geben Sie das neue Passwort ein und wiederholen Sie dieses korrekt.", "Enter the new password and repeat it correctly."),
            new Translation("Enter current password.", "Geben Sie das aktuelle Passwort an, um ein neues Passwort zu speichern.", "Enter the current password, in order to save a new one."),

            new Translation("Merging", "Zusammenführen", null),
            new Translation("Merge", "Zusammenführen", null),
            new Translation("Server", null, null),
            new Translation("Local", "Lokal", null),
            new Translation("Result", "Ergebnis", null),
            new Translation("Discard changes?", "Änderugen verwerfen?", null),
            new Translation("Discard current changes?", "Möchten Sie die aktuellen Änderungen verwerfen?", "Do you want to discard the current changes?"),
            new Translation("Delete", "Löschen", null),
            new Translation("Delete data?", "Daten löschen?", null),
            new Translation("Delete data irrevocably?", "Wollen Sie die Daten wirklich unwiderruflich löschen?", "Are you sure you want to delete the data irrevocably?"),
            new Translation("Deleted!", "Gelöscht!", null),
            new Translation("Successfully deleted.", "Die Daten wurden erfolgreich gelöscht.", "Data have been deleted successfully."),

            new Translation("More items avaiable!", "Mehr Einträge verfügbar!", null),
            new Translation("Load more items...", "Weitere Einträge laden...", null),

            new Translation("LanguageEditor", "Sprachen", "Languages"),
            new Translation("Languages", "Sprachen", null),
            new Translation("Language", "Sprache", null),

            new Translation("PersonEditor", "Personen", "People"),
            new Translation("People", "Personen", null),
            new Translation("Person", "Person", null),
            new Translation("Name"),
            new Translation("Title", "Titel", null),
            new Translation("Forename", "Vorname", null),
            new Translation("Kürzel", null, "Code"),
            new Translation("Password", "Passwort", null),
            new Translation("Current password", "Aktuelles Passwort", null),
            new Translation("New password", "Neues Passwort", null),
            new Translation("Repetition", "Wiederholung", null),

            new Translation("AddressEditor", "Adressen", "Addresses"),
            new Translation("Addresses", "Adressen", null),
            new Translation("Address", "Adresse", null),
            new Translation("Street", "Straße", null),
            new Translation("HsNr", "Hausnummer", "House number"),
            new Translation("ZIP", "PLZ", null),
            new Translation("City", "Ort", null),
            new Translation("Country", "Land", null),

            new Translation("CountryEditor", "Länder", "Countries"),
            new Translation("Countries", "Länder", null),
            new Translation("Alpha2"),
            new Translation("Alpha3"),
            new Translation("Vorwahl", null, "Area code"),

            new Translation("ContactTypeEditor", "Kontaktarten", "Contact types"),
            new Translation("Contact types", "Kontaktarten", null),
            new Translation("Contact type", "Kontaktart", null),
            new Translation("Abbreviation", "Abkürzung", null),
            new Translation("Format"),

            new Translation("ContactInformationEditor", "Kontaktinformationen", "Contact information"),
            new Translation("Contact information", "Kontaktinformationen", null),
            new Translation("Information"),

            new Translation("NutritionTypeEditor", "Ernährungsarten", "Types of nutrition"),
            new Translation("NutritionType", "Ernährungsart", "Type of nutrition"),

            new Translation("RotaEditor", "Dienstplan", "Roster"),
            new Translation("Rota", "Dienst", "Rota"),

            new Translation("RotaShiftEditor", "Schichten", "Shift"),
            new Translation("RotaShift", "Schicht", "Shift"),
            new Translation("Beginn"),
            new Translation("End", "Ende", null),
            new Translation("TimestampInterpretation", "Interpretation", "Interpretation"),

            new Translation("RotaCategoryEditor", "Dienstarten", "Rota categories"),
            new Translation("RotaCategory", "Dienstart", "Rota category"),
    };
    private static Translator instance;

    public Translator() {
        if (Translator.instance == null)
            this.generateDefaults();
    }

    public static Translator get() {
        if (instance == null)
            instance = new Translator();
        return instance;
    }

    @WebMethod
    public String getTranslation(@WebParam(name = "text") String text, @WebParam(name = "language") String language) {

        if (text == null)
            return null;
        if (language == null || language.isEmpty())
            language = "SYS";
        Session session = DB.open();
        Query q = session.createQuery("FROM Translation WHERE SYS LIKE :text");
        q.setParameter("text", text);
        List<Translation> result = q.getResultList();
        if (result != null && result.size() == 1) {
            switch (language) {
                case "DEU":
                    return "<" + result.get(0).getDeu() + ">";
                case "USA":
                    return "<" + result.get(0).getUsa() + ">";
            }
        }
        return text;
    }

    @Override
    public void generateDefaults() {
        if (this.countRecords() != TRANSLATIONS.length) {
            try (Session session = DB.open()) {
                session.beginTransaction();
                for (Translation translation : TRANSLATIONS) {
                    CriteriaBuilder builder = session.getEntityManagerFactory().getCriteriaBuilder();
                    CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
                    Root<Translation> translationRoot = query.from(Translation.class);
                    query.select(translationRoot.get(Translation_.id));
                    query.where(builder.equal(translationRoot.get(Translation_.sys), translation.getSys()));
                    if (session.getEntityManagerFactory().createEntityManager().createQuery(query).getResultList().size() > 0)
                        session.delete(new Translation(session.getEntityManagerFactory().createEntityManager().createQuery(query).getResultList().get(0)));
                    session.save(translation);
                }
                session.getTransaction().commit();
            }
        }
    }
}
