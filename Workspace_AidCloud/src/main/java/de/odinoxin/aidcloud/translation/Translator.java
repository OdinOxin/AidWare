package de.odinoxin.aidcloud.translation;

import de.odinoxin.aidcloud.DB;
import de.odinoxin.aidcloud.plugins.RecordHandler;
import de.odinoxin.aidcloud.plugins.languages.Language;
import de.odinoxin.aidcloud.plugins.languages.LanguageProvider;
import org.hibernate.Session;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@WebService
public class Translator extends RecordHandler<Translation> {

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
    public String getTranslation(@WebParam(name = "text") String text, @WebParam(name = "lng") Language lng) {
        if (text == null)
            return null;
        if (lng == null)
            return text;
        Session session = DB.open();
        Query q = session.createQuery("FROM Translation WHERE lng = :lng AND sys LIKE :text");
        q.setParameter("lng", lng);
        q.setParameter("text", text);
        List<Translation> result = q.getResultList();
        if (result != null && result.size() == 1)
            return "<" + result.get(0).getLngText() + ">";
        return text;
    }

    @Override
    public void generateDefaults() {
        new LanguageProvider().generateDefaults();

        List<Translation> translations = new ArrayList<>();
        translations.addAll(getTranslations("ID", "ID", "ID"));
        translations.addAll(getTranslations("OK", "OK", "OK"));

        translations.addAll(getTranslations("Yes", "Ja", "Yes"));
        translations.addAll(getTranslations("No", "Nein", "No"));
        translations.addAll(getTranslations("Cancel", "Abbrechen", "Cancel"));
        translations.addAll(getTranslations("Save", "Speichern", "Save"));
        translations.addAll(getTranslations("Discard", "Verwerfen", "Discard"));
        translations.addAll(getTranslations("Change", "Ändern", "Change"));
        translations.addAll(getTranslations("New", "Neu", "New"));
        translations.addAll(getTranslations("Selection", "Auswahl", "Selection"));
        translations.addAll(getTranslations("MainMenu", "Hauptmenü", "Main menu"));
        translations.addAll(getTranslations("Main Menu", "Hauptmenü", "Main menu"));
        translations.addAll(getTranslations("MasterData", "Stammdaten Editor", "Master data editor"));
        translations.addAll(getTranslations("TransactionData", "Bewegungsdaten Editor", "Transaction data editor"));
        translations.addAll(getTranslations("Log out", "Ausloggen", "Log out"));
        translations.addAll(getTranslations("Log out?", "Ausloggen?", "Log out?"));
        translations.addAll(getTranslations("Log out and close all related windows?", "Ausloggen und alle zugehörigen Fenster schließen?", "Log out and close all related windows?"));
        translations.addAll(getTranslations("Exit", "Beenden", "Exit"));
        translations.addAll(getTranslations("Exit?", "Beenden?", "Exit?"));
        translations.addAll(getTranslations("Exit AidDesk?", "AidDesk beenden und alle zugehörigen Fenster schließen?", "Exit AidDesk and close all related windows?"));
        translations.addAll(getTranslations("Invalid input!", "Ungültige Eingabe!", "Invalid input!"));
        translations.addAll(getTranslations("Enter and repeat password.", "Geben Sie das neue Passwort ein und wiederholen Sie dieses korrekt.", "Enter the new password and repeat it correctly."));
        translations.addAll(getTranslations("Enter current password.", "Geben Sie das aktuelle Passwort an, um ein neues Passwort zu speichern.", "Enter the current password, in order to save a new one."));
        translations.addAll(getTranslations("Merging", "Zusammenführen", "Merging"));
        translations.addAll(getTranslations("Merge", "Zusammenführen", "Merge"));
        translations.addAll(getTranslations("Server", "Server", "Server"));
        translations.addAll(getTranslations("Local", "Lokal", "Local"));
        translations.addAll(getTranslations("Result", "Ergebnis", "Result"));
        translations.addAll(getTranslations("Discard changes?", "Änderugen verwerfen?", "Discard changes?"));
        translations.addAll(getTranslations("Discard current changes?", "Möchten Sie die aktuellen Änderungen verwerfen?", "Do you want to discard the current changes?"));
        translations.addAll(getTranslations("Delete", "Löschen", "Delete"));
        translations.addAll(getTranslations("Delete data?", "Daten löschen?", "Delete data?"));
        translations.addAll(getTranslations("Delete data irrevocably?", "Wollen Sie die Daten wirklich unwiderruflich löschen?", "Are you sure you want to delete the data irrevocably?"));
        translations.addAll(getTranslations("Deleted!", "Gelöscht!", "Deleted!"));
        translations.addAll(getTranslations("Successfully deleted.", "Die Daten wurden erfolgreich gelöscht.", "Data have been deleted successfully."));
        translations.addAll(getTranslations("More items avaiable!", "Mehr Einträge verfügbar!", "More items avaiable!"));
        translations.addAll(getTranslations("Load more items...", "Weitere Einträge laden...", "Load more items..."));
        translations.addAll(getTranslations("LanguageEditor", "Sprachen", "Languages"));
        translations.addAll(getTranslations("Languages", "Sprachen", "Languages"));
        translations.addAll(getTranslations("Language", "Sprache", "Language"));
        translations.addAll(getTranslations("PersonEditor", "Personen", "People"));
        translations.addAll(getTranslations("People", "Personen", "People"));
        translations.addAll(getTranslations("Person", "Person", "Person"));
        translations.addAll(getTranslations("Name", "Name", "Name"));
        translations.addAll(getTranslations("Title", "Titel", "Title"));
        translations.addAll(getTranslations("Forename", "Vorname", "Forename"));
        translations.addAll(getTranslations("Kürzel", "Kürzel", "Code"));
        translations.addAll(getTranslations("Password", "Passwort", "Password"));
        translations.addAll(getTranslations("Current password", "Aktuelles Passwort", "Current password"));
        translations.addAll(getTranslations("New password", "Neues Passwort", "New password"));
        translations.addAll(getTranslations("Repetition", "Wiederholung", "Repetition"));
        translations.addAll(getTranslations("AddressEditor", "Adressen", "Addresses"));
        translations.addAll(getTranslations("Addresses", "Adressen", "Adresses"));
        translations.addAll(getTranslations("Address", "Adresse", "Address"));
        translations.addAll(getTranslations("Street", "Straße", "Street"));
        translations.addAll(getTranslations("HsNr", "Hausnummer", "House number"));
        translations.addAll(getTranslations("ZIP", "PLZ", "ZIP code"));
        translations.addAll(getTranslations("City", "Ort", "City"));
        translations.addAll(getTranslations("Country", "Land", "Country"));
        translations.addAll(getTranslations("CountryEditor", "Länder", "Countries"));
        translations.addAll(getTranslations("Countries", "Länder", "Countries"));
        translations.addAll(getTranslations("Alpha2", "Alpha2", "Alpha2"));
        translations.addAll(getTranslations("Alpha3", "Alpha3", "Alpha3"));
        translations.addAll(getTranslations("Vorwahl", null, "Area code"));
        translations.addAll(getTranslations("ContactTypeEditor", "Kontaktarten", "Contact types"));
        translations.addAll(getTranslations("Contact types", "Kontaktarten", "Contact types"));
        translations.addAll(getTranslations("Contact type", "Kontaktart", "Contact type"));
        translations.addAll(getTranslations("Abbreviation", "Abkürzung", "Abbreviation"));
        translations.addAll(getTranslations("Format", "Format", "Format"));
        translations.addAll(getTranslations("ContactInformationEditor", "Kontaktinformationen", "Contact information"));
        translations.addAll(getTranslations("Contact information", "Kontaktinformationen", "Contact information"));
        translations.addAll(getTranslations("Information", "Information", "Information"));
        translations.addAll(getTranslations("NutritionTypeEditor", "Ernährungsarten", "Types of nutrition"));
        translations.addAll(getTranslations("NutritionType", "Ernährungsart", "Type of nutrition"));
        translations.addAll(getTranslations("RotaEditor", "Dienstplan", "Roster"));
        translations.addAll(getTranslations("Rota", "Dienst", "Rota"));
        translations.addAll(getTranslations("RotaShiftEditor", "Schichten", "Shift"));
        translations.addAll(getTranslations("RotaShift", "Schicht", "Shift"));
        translations.addAll(getTranslations("Beginn", "Beginn", "Beginn"));
        translations.addAll(getTranslations("End", "Ende", "End"));
        translations.addAll(getTranslations("TimestampInterpretation", "Interpretation", "Interpretation"));
        translations.addAll(getTranslations("RotaCategoryEditor", "Dienstarten", "Rota categories"));
        translations.addAll(getTranslations("RotaCategory", "Dienstart", "Rota category"));
        //translations.addAll(getTranslations("", "", ""));

        if (this.countRecords() != translations.size()) {
            try (Session session = DB.open()) {
                session.beginTransaction();
                for (Translation translation : translations) {
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

    private List<Translation> getTranslations(String sys, String ger, String usa) {
        List<Translation> translations = new ArrayList<>();
        if (sys != null && !sys.isEmpty()) {
            if (ger != null && !ger.isEmpty())
                translations.add(new Translation(0, sys, LanguageProvider.german, ger));
            if (usa != null && !usa.isEmpty())
                translations.add(new Translation(0, sys, LanguageProvider.english, usa));
        }
        return translations;
    }
}
