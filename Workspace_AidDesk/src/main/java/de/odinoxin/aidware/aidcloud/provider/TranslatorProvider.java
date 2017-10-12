package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.auth.Login;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.Hashtable;
import java.util.Map;

/**
 * Translation provider.
 */
public abstract class TranslatorProvider {

    private static Map<Integer, Map<String, String>> cache = new Hashtable<>();

    /**
     * Returns translation for the given text, by users selected language.
     *
     * @param text The text to translate.
     * @return The translated text.
     */
    public static String getTranslation(String text) {
        if (Login.getPerson() == null || Login.getPerson().getLanguage() == null)
            return text;
        int lngId = Login.getPerson().getLanguage().getId();
        if (cache.containsKey(lngId)) {
            if (cache.get(lngId).containsKey(text))
                return cache.get(lngId).get(text);
        }
        String translation = Provider.webCall(webTarget -> Provider.newInvocationBuilder(webTarget, MediaType.TEXT_PLAIN).get(String.class),
                ClientBuilder.newClient().target(Login.getServerUrl()).path("Translation").path(String.valueOf(lngId)).path(text));
        if (!cache.containsKey(lngId))
            cache.put(lngId, new Hashtable<>());
        cache.get(lngId).put(text, translation);
        return translation;
    }
}
