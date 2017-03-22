package de.odinoxin.aidcloud.provider;

import de.odinoxin.aidcloud.service.Translator;
import de.odinoxin.aidcloud.service.TranslatorService;
import de.odinoxin.aiddesk.Login;

import java.net.MalformedURLException;
import java.net.URL;

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

    /**
     * Returns translation for the given text, by users selected language.
     * @param text The text to translate.
     * @return The translated text.
     */
    public static String getTranslation(String text) {
        if (TranslatorProvider.getSvc() != null)
            return TranslatorProvider.getSvc().getTranslation(text, Login.getPerson() != null ? Login.getPerson().getLanguage() != null ? Login.getPerson().getLanguage().getCode() : null : null);
        return text;
    }
}
