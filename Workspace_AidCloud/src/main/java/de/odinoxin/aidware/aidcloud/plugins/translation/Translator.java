package de.odinoxin.aidware.aidcloud.plugins.translation;

import de.odinoxin.aidware.aidcloud.DB;
import de.odinoxin.aidware.aidcloud.plugins.auth.Secured;
import de.odinoxin.aidware.aidcloud.recordable.RecordHandler;
import de.odinoxin.aidware.aidcloud.plugins.language.LanguageProvider;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("Translation")
public class Translator extends RecordHandler<Translation> {

    private static boolean defaultsGenerated = false;

    public Translator() {
        Translator.defaultsGenerated = true;
    }

    @GET
    @Path("{lng}/{text}")
    @Produces(MediaType.TEXT_PLAIN)
    @Secured
    public String getTranslation(@PathParam("text") String text, @PathParam("lng") int lng) {
        if (text == null)
            return null;
        if (lng <= 0)
            return text;
        Session session = DB.open();
        Query q = session.createQuery("FROM Translation WHERE lng_id = :lng AND sys LIKE :text");
        q.setParameter("lng", lng);
        q.setParameter("text", text);
        List<Translation> result = q.getResultList();
        if (result != null && result.size() == 1)
            return "<" + result.get(0).getLngText() + ">";
        return text;
    }

    @Override
    public void generateDefaults() {
        if (defaultsGenerated)
            return;

        new LanguageProvider().generateDefaults();

        List<Translation> translations = new ArrayList<>();
        translations.addAll(getTranslations("ID", new String[]{"ID", "ID"}));
        translations.addAll(getTranslations("OK", new String[]{"OK", "OK"}));

        translations.addAll(getTranslations("Yes", new String[]{"Ja", "Yes"}));
        translations.addAll(getTranslations("No", new String[]{"Nein", "No"}));
        translations.addAll(getTranslations("Cancel", new String[]{"Abbrechen", "Cancel"}));
        translations.addAll(getTranslations("Save", new String[]{"Speichern", "Save"}));
        translations.addAll(getTranslations("Discard", new String[]{"Verwerfen", "Discard"}));
        translations.addAll(getTranslations("Change", new String[]{"Ändern", "Change"}));
        translations.addAll(getTranslations("New", new String[]{"Neu", "New"}));
        translations.addAll(getTranslations("Selection", new String[]{"Auswahl", "Selection"}));
        translations.addAll(getTranslations("MainMenu", new String[]{"Hauptmenü", "Main menu"}));
        translations.addAll(getTranslations("Main Menu", new String[]{"Hauptmenü", "Main menu"}));
        translations.addAll(getTranslations("MasterData", new String[]{"Stammdaten Editor", "Master data editor"}));
        translations.addAll(getTranslations("TransactionData", new String[]{"Bewegungsdaten Editor", "Transaction data editor"}));
        translations.addAll(getTranslations("Log out", new String[]{"Ausloggen", "Log out"}));
        translations.addAll(getTranslations("Log out?", new String[]{"Ausloggen?", "Log out?"}));
        translations.addAll(getTranslations("Log out and close all related windows?", new String[]{"Ausloggen und alle zugehörigen Fenster schließen?", "Log out and close all related windows?"}));
        translations.addAll(getTranslations("Exit", new String[]{"Beenden", "Exit"}));
        translations.addAll(getTranslations("Exit?", new String[]{"Beenden?", "Exit?"}));
        translations.addAll(getTranslations("Exit AidDesk?", new String[]{"AidDesk beenden und alle zugehörigen Fenster schließen?", "Exit AidDesk and close all related windows?"}));
        translations.addAll(getTranslations("Invalid input!", new String[]{"Ungültige Eingabe!", "Invalid input!"}));
        translations.addAll(getTranslations("Enter and repeat password.", new String[]{"Geben Sie das neue Passwort ein und wiederholen Sie dieses korrekt.", "Enter the new password and repeat it correctly."}));
        translations.addAll(getTranslations("Enter current password.", new String[]{"Geben Sie das aktuelle Passwort an, um ein neues Passwort zu speichern.", "Enter the current password, in order to save a new one."}));
        translations.addAll(getTranslations("Merging", new String[]{"Zusammenführen", "Merging"}));
        translations.addAll(getTranslations("Merge", new String[]{"Zusammenführen", "Merge"}));
        translations.addAll(getTranslations("Server", new String[]{"Server", "Server"}));
        translations.addAll(getTranslations("Local", new String[]{"Lokal", "Local"}));
        translations.addAll(getTranslations("Result", new String[]{"Ergebnis", "Result"}));
        translations.addAll(getTranslations("Discard changes?", new String[]{"Änderugen verwerfen?", "Discard changes?"}));
        translations.addAll(getTranslations("Discard current changes?", new String[]{"Möchten Sie die aktuellen Änderungen verwerfen?", "Do you want to discard the current changes?"}));
        translations.addAll(getTranslations("Delete", new String[]{"Löschen", "Delete"}));
        translations.addAll(getTranslations("Delete data?", new String[]{"Daten löschen?", "Delete data?"}));
        translations.addAll(getTranslations("Delete data irrevocably?", new String[]{"Wollen Sie die Daten wirklich unwiderruflich löschen?", "Are you sure you want to delete the data irrevocably?"}));
        translations.addAll(getTranslations("Deleted!", new String[]{"Gelöscht!", "Deleted!"}));
        translations.addAll(getTranslations("Successfully deleted.", new String[]{"Die Daten wurden erfolgreich gelöscht.", "Data have been deleted successfully."}));
        translations.addAll(getTranslations("More items avaiable!", new String[]{"Mehr Einträge verfügbar!", "More items avaiable!"}));
        translations.addAll(getTranslations("Load more items...", new String[]{"Weitere Einträge laden...", "Load more items..."}));
        translations.addAll(getTranslations("LanguageEditor", new String[]{"Sprachen", "Languages"}));
        translations.addAll(getTranslations("Languages", new String[]{"Sprachen", "Languages"}));
        translations.addAll(getTranslations("Language", new String[]{"Sprache", "Language"}));
        translations.addAll(getTranslations("PersonEditor", new String[]{"Personen", "People"}));
        translations.addAll(getTranslations("People", new String[]{"Personen", "People"}));
        translations.addAll(getTranslations("Person", new String[]{"Person", "Person"}));
        translations.addAll(getTranslations("Name", new String[]{"Name", "Name"}));
        translations.addAll(getTranslations("Title", new String[]{"Titel", "Title"}));
        translations.addAll(getTranslations("Forename", new String[]{"Vorname", "Forename"}));
        translations.addAll(getTranslations("Kürzel", new String[]{"Kürzel", "Code"}));
        translations.addAll(getTranslations("Password", new String[]{"Passwort", "Password"}));
        translations.addAll(getTranslations("Current password", new String[]{"Aktuelles Passwort", "Current password"}));
        translations.addAll(getTranslations("New password", new String[]{"Neues Passwort", "New password"}));
        translations.addAll(getTranslations("Repetition", new String[]{"Wiederholung", "Repetition"}));
        translations.addAll(getTranslations("PersonalSettingEditor", new String[]{"Persönliche Einstellungen", "Personal settings"}));
        translations.addAll(getTranslations("PersonalSetting", new String[]{"Persönliche Einstellungen", "Personal settings"}));
        translations.addAll(getTranslations("BgColor", new String[]{"Hintergrundfarbe", "Background color"}));
        translations.addAll(getTranslations("BgColorPlugin", new String[]{"Hintergrundfarbe (Plugin)", "Background color (plugin)"}));
        translations.addAll(getTranslations("AddressEditor", new String[]{"Adressen", "Addresses"}));
        translations.addAll(getTranslations("Addresses", new String[]{"Adressen", "Adresses"}));
        translations.addAll(getTranslations("Address", new String[]{"Adresse", "Address"}));
        translations.addAll(getTranslations("Street", new String[]{"Straße", "Street"}));
        translations.addAll(getTranslations("HsNr", new String[]{"Hausnummer", "House number"}));
        translations.addAll(getTranslations("ZIP", new String[]{"PLZ", "ZIP code"}));
        translations.addAll(getTranslations("City", new String[]{"Ort", "City"}));
        translations.addAll(getTranslations("Country", new String[]{"Land", "Country"}));
        translations.addAll(getTranslations("CountryEditor", new String[]{"Länder", "Countries"}));
        translations.addAll(getTranslations("Countries", new String[]{"Länder", "Countries"}));
        translations.addAll(getTranslations("Alpha2", new String[]{"Alpha2", "Alpha2"}));
        translations.addAll(getTranslations("Alpha3", new String[]{"Alpha3", "Alpha3"}));
        translations.addAll(getTranslations("Vorwahl", new String[]{"Vorwahl", "Area code"}));
        translations.addAll(getTranslations("ContactTypeEditor", new String[]{"Kontaktarten", "Contact types"}));
        translations.addAll(getTranslations("Contact types", new String[]{"Kontaktarten", "Contact types"}));
        translations.addAll(getTranslations("Contact type", new String[]{"Kontaktart", "Contact type"}));
        translations.addAll(getTranslations("Abbreviation", new String[]{"Abkürzung", "Abbreviation"}));
        translations.addAll(getTranslations("Format", new String[]{"Format", "Format"}));
        translations.addAll(getTranslations("ContactInformationEditor", new String[]{"Kontaktinformationen", "Contact information"}));
        translations.addAll(getTranslations("Contact information", new String[]{"Kontaktinformationen", "Contact information"}));
        translations.addAll(getTranslations("Information", new String[]{"Information", "Information"}));
        translations.addAll(getTranslations("NutritionTypeEditor", new String[]{"Ernährungsarten", "Types of nutrition"}));
        translations.addAll(getTranslations("NutritionType", new String[]{"Ernährungsart", "Type of nutrition"}));
        translations.addAll(getTranslations("RotaEditor", new String[]{"Dienstplan", "Roster"}));
        translations.addAll(getTranslations("Rota", new String[]{"Dienst", "Rota"}));
        translations.addAll(getTranslations("FirstShiftBeginn", new String[]{"Beginn der 1. Schicht", "Beginn of 1st shift"}));
        translations.addAll(getTranslations("LastShiftEnd", new String[]{"Ende der letzten Schicht", "End of last shift"}));
        translations.addAll(getTranslations("RotaShiftEditor", new String[]{"Schichten", "Shift"}));
        translations.addAll(getTranslations("RotaShifts", new String[]{"Schichten", "Shift"}));
        translations.addAll(getTranslations("RotaShift", new String[]{"Schicht", "Shift"}));
        translations.addAll(getTranslations("Beginn", new String[]{"Beginn", "Beginn"}));
        translations.addAll(getTranslations("End", new String[]{"Ende", "End"}));
        translations.addAll(getTranslations("TimestampInterpretation", new String[]{"Interpretation", "Interpretation"}));
        translations.addAll(getTranslations("RotaCategoryEditor", new String[]{"Dienstarten", "Rota categories"}));
        translations.addAll(getTranslations("RotaCategory", new String[]{"Dienstart", "Rota category"}));
        translations.addAll(getTranslations("AdditionalInformation", new String[]{"Zusatzinformationen", "Additional information"}));
        //translations.addAll(getTranslations("", new String[]{"", ""}));

        try (Session session = DB.open()) {
            session.beginTransaction();
            for (Translation translation : translations) {
                CriteriaBuilder builder = session.getEntityManagerFactory().getCriteriaBuilder();
                CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
                Root<Translation> translationRoot = query.from(Translation.class);
                query.select(translationRoot.get(Translation_.id));
                query.where(builder.equal(translationRoot.get(Translation_.sys), translation.getSys()));
                //query.where(builder.equal(translationRoot.get(Translation_.lng), translation.getLng()));
                if (session.getEntityManagerFactory().createEntityManager().createQuery(query).getResultList().size() == 0)
                    session.save(translation);
            }
            session.getTransaction().commit();
        }
    }

    private List<Translation> getTranslations(String sys, String[] lngTxts) {
        List<Translation> translations = new ArrayList<>();
        if (sys != null && !sys.isEmpty() && lngTxts != null && lngTxts.length == LanguageProvider.languages.length) {
            for (int i = 0; i < LanguageProvider.languages.length; i++) {
                if (lngTxts[i] != null && !lngTxts[i].isEmpty())
                    translations.add(new Translation(0, sys, LanguageProvider.languages[i], lngTxts[i]));
            }
        }
        return translations;
    }
}
