package de.odinoxin.aidcloud.provider;

import de.odinoxin.aidcloud.service.Translator;
import de.odinoxin.aidcloud.service.TranslatorService;
import de.odinoxin.aiddesk.Login;
import de.odinoxin.aiddesk.plugins.languages.Language;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

/**
 * Translation provider.
 */
public abstract class TranslatorProvider {
    private static Translator svc;

    private static Translator getSvc() {
        if (svc == null) {
            if (Login.getServerUrl() == null)
                return null;
            try {
                svc = new TranslatorService(new URL(Login.getServerUrl() + "/Translator?wsdl")).getTranslatorPort();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        return svc;
    }

    private static Map<Language, Map<String, String>> cache = new Hashtable<>();

    /**
     * Returns translation for the given text, by users selected language.
     *
     * @param text The text to translate.
     * @return The translated text.
     */
    public static String getTranslation(String text) {
        if (Login.getPerson() == null || Login.getPerson().getLanguage() == null)
            return text;
        Language lng = Login.getPerson().getLanguage();
        if (cache.containsKey(lng)) {
            if (cache.get(lng).containsKey(text))
                return cache.get(lng).get(text);
        }
        if (TranslatorProvider.getSvc() != null) {
            String translation = TranslatorProvider.getSvc().getTranslation(text, Login.getPerson().getLanguage().toEntity());
            if (!cache.containsKey(lng))
                cache.put(lng, new Hashtable<>());
            cache.get(lng).put(text, translation);
            return translation;
        }
        return text;
    }
}
