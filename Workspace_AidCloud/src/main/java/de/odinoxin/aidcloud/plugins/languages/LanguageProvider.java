package de.odinoxin.aidcloud.plugins.languages;

import de.odinoxin.aidcloud.ConcurrentFault;
import de.odinoxin.aidcloud.DB;
import de.odinoxin.aidcloud.plugins.RecordHandler;
import org.hibernate.Session;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.List;

@WebService
public class LanguageProvider extends RecordHandler<Language> {

    public static Language[] languages = new Language[]{
            new Language(0, "Deutsch", "DEU"),
            new Language(0, "English", "USA"),
    };

    @Resource
    WebServiceContext wsCtx;

    @WebMethod
    public Language getLanguage(@WebParam(name = "id") int id) {
        return super.get(id, this.wsCtx);
    }

    @WebMethod
    public Language saveLanguage(@WebParam(name = "entity") Language entity, @WebParam(name = "original") Language original) throws ConcurrentFault {
        return this.getLanguage(super.save(entity, original, this.wsCtx));
    }

    @WebMethod
    public boolean deleteLanguage(@WebParam(name = "id") int id) {
        return super.delete(id, this.wsCtx);
    }

    @WebMethod
    public List<Language> searchLanguage(@WebParam(name = "expr") String[] expr, @WebParam(name = "max") int max) {
        return super.search(expr, max, this.wsCtx);
    }

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
                else
                    languages[i] = search(new String[]{languages[i].getName(), languages[i].getCode()}, 1).get(0);
            }
            session.getTransaction().commit();
        }
    }
}
