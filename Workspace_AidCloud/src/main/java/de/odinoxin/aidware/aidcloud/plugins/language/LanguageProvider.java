package de.odinoxin.aidware.aidcloud.plugins.language;

import de.odinoxin.aidware.aidcloud.DB;
import de.odinoxin.aidware.aidcloud.plugins.RecordHandler;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("Language")
public class LanguageProvider extends RecordHandler<Language> {

    public static Language[] languages = new Language[]{
            new Language(0, "Deutsch", "DEU"),
            new Language(0, "English", "USA"),
    };

    @Override
    protected Expression<Integer> getIdExpression(Root<Language> root) {
        return root.get(Language_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<Language> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        expressions.add(root.get(Language_.name));
        expressions.add(root.get(Language_.code));
        return expressions;
    }

    @Override
    public void generateDefaults() {
        try (Session session = DB.open()) {
            session.beginTransaction();
            for (int i = 0; i < languages.length; i++) {
                CriteriaBuilder builder = session.getEntityManagerFactory().getCriteriaBuilder();
                CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
                Root<Language> translationRoot = query.from(Language.class);
                query.select(translationRoot.get(Language_.id));
                query.where(builder.equal(translationRoot.get(Language_.name), languages[i].getName()));
                query.where(builder.equal(translationRoot.get(Language_.code), languages[i].getCode()));
                if (session.getEntityManagerFactory().createEntityManager().createQuery(query).getResultList().size() == 0)
                    session.save(languages[i]);
                else {
                    List<String> search = new ArrayList<>();
                    search.add(languages[i].getName());
                    search.add(languages[i].getCode());
                    languages[i] = search(search, 1, null).get(0);
                }
            }
            session.getTransaction().commit();
        }
    }
}
