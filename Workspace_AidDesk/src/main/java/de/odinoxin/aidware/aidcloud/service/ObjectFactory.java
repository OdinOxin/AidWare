
package de.odinoxin.aidware.aidcloud.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.odinoxin.aidcloud.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConcurrentFault_QNAME = new QName("http://category.rota.plugins.aidcloud.odinoxin.de/", "ConcurrentFault");
    private final static QName _GetRotaCategory_QNAME = new QName("http://category.rota.plugins.aidcloud.odinoxin.de/", "getRotaCategory");
    private final static QName _DeleteRotaCategoryResponse_QNAME = new QName("http://category.rota.plugins.aidcloud.odinoxin.de/", "deleteRotaCategoryResponse");
    private final static QName _SaveRotaCategoryResponse_QNAME = new QName("http://category.rota.plugins.aidcloud.odinoxin.de/", "saveRotaCategoryResponse");
    private final static QName _SearchRotaCategory_QNAME = new QName("http://category.rota.plugins.aidcloud.odinoxin.de/", "searchRotaCategory");
    private final static QName _SearchRotaCategoryResponse_QNAME = new QName("http://category.rota.plugins.aidcloud.odinoxin.de/", "searchRotaCategoryResponse");
    private final static QName _GetRotaCategoryResponse_QNAME = new QName("http://category.rota.plugins.aidcloud.odinoxin.de/", "getRotaCategoryResponse");
    private final static QName _DeleteRotaCategory_QNAME = new QName("http://category.rota.plugins.aidcloud.odinoxin.de/", "deleteRotaCategory");
    private final static QName _SaveRotaCategory_QNAME = new QName("http://category.rota.plugins.aidcloud.odinoxin.de/", "saveRotaCategory");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.odinoxin.aidcloud.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConcurrentFault }
     * 
     */
    public ConcurrentFault createConcurrentFault() {
        return new ConcurrentFault();
    }

    /**
     * Create an instance of {@link GetRotaCategory }
     * 
     */
    public GetRotaCategory createGetRotaCategory() {
        return new GetRotaCategory();
    }

    /**
     * Create an instance of {@link GetRotaCategoryResponse }
     * 
     */
    public GetRotaCategoryResponse createGetRotaCategoryResponse() {
        return new GetRotaCategoryResponse();
    }

    /**
     * Create an instance of {@link SearchRotaCategoryResponse }
     * 
     */
    public SearchRotaCategoryResponse createSearchRotaCategoryResponse() {
        return new SearchRotaCategoryResponse();
    }

    /**
     * Create an instance of {@link SaveRotaCategoryResponse }
     * 
     */
    public SaveRotaCategoryResponse createSaveRotaCategoryResponse() {
        return new SaveRotaCategoryResponse();
    }

    /**
     * Create an instance of {@link SearchRotaCategory }
     * 
     */
    public SearchRotaCategory createSearchRotaCategory() {
        return new SearchRotaCategory();
    }

    /**
     * Create an instance of {@link DeleteRotaCategoryResponse }
     * 
     */
    public DeleteRotaCategoryResponse createDeleteRotaCategoryResponse() {
        return new DeleteRotaCategoryResponse();
    }

    /**
     * Create an instance of {@link DeleteRotaCategory }
     * 
     */
    public DeleteRotaCategory createDeleteRotaCategory() {
        return new DeleteRotaCategory();
    }

    /**
     * Create an instance of {@link SaveRotaCategory }
     * 
     */
    public SaveRotaCategory createSaveRotaCategory() {
        return new SaveRotaCategory();
    }

    /**
     * Create an instance of {@link RotaCategoryEntity }
     * 
     */
    public RotaCategoryEntity createRotaCategoryEntity() {
        return new RotaCategoryEntity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConcurrentFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://category.rota.plugins.aidcloud.odinoxin.de/", name = "ConcurrentFault")
    public JAXBElement<ConcurrentFault> createConcurrentFault(ConcurrentFault value) {
        return new JAXBElement<ConcurrentFault>(_ConcurrentFault_QNAME, ConcurrentFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRotaCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://category.rota.plugins.aidcloud.odinoxin.de/", name = "getRotaCategory")
    public JAXBElement<GetRotaCategory> createGetRotaCategory(GetRotaCategory value) {
        return new JAXBElement<GetRotaCategory>(_GetRotaCategory_QNAME, GetRotaCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRotaCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://category.rota.plugins.aidcloud.odinoxin.de/", name = "deleteRotaCategoryResponse")
    public JAXBElement<DeleteRotaCategoryResponse> createDeleteRotaCategoryResponse(DeleteRotaCategoryResponse value) {
        return new JAXBElement<DeleteRotaCategoryResponse>(_DeleteRotaCategoryResponse_QNAME, DeleteRotaCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveRotaCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://category.rota.plugins.aidcloud.odinoxin.de/", name = "saveRotaCategoryResponse")
    public JAXBElement<SaveRotaCategoryResponse> createSaveRotaCategoryResponse(SaveRotaCategoryResponse value) {
        return new JAXBElement<SaveRotaCategoryResponse>(_SaveRotaCategoryResponse_QNAME, SaveRotaCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchRotaCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://category.rota.plugins.aidcloud.odinoxin.de/", name = "searchRotaCategory")
    public JAXBElement<SearchRotaCategory> createSearchRotaCategory(SearchRotaCategory value) {
        return new JAXBElement<SearchRotaCategory>(_SearchRotaCategory_QNAME, SearchRotaCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchRotaCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://category.rota.plugins.aidcloud.odinoxin.de/", name = "searchRotaCategoryResponse")
    public JAXBElement<SearchRotaCategoryResponse> createSearchRotaCategoryResponse(SearchRotaCategoryResponse value) {
        return new JAXBElement<SearchRotaCategoryResponse>(_SearchRotaCategoryResponse_QNAME, SearchRotaCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRotaCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://category.rota.plugins.aidcloud.odinoxin.de/", name = "getRotaCategoryResponse")
    public JAXBElement<GetRotaCategoryResponse> createGetRotaCategoryResponse(GetRotaCategoryResponse value) {
        return new JAXBElement<GetRotaCategoryResponse>(_GetRotaCategoryResponse_QNAME, GetRotaCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRotaCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://category.rota.plugins.aidcloud.odinoxin.de/", name = "deleteRotaCategory")
    public JAXBElement<DeleteRotaCategory> createDeleteRotaCategory(DeleteRotaCategory value) {
        return new JAXBElement<DeleteRotaCategory>(_DeleteRotaCategory_QNAME, DeleteRotaCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveRotaCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://category.rota.plugins.aidcloud.odinoxin.de/", name = "saveRotaCategory")
    public JAXBElement<SaveRotaCategory> createSaveRotaCategory(SaveRotaCategory value) {
        return new JAXBElement<SaveRotaCategory>(_SaveRotaCategory_QNAME, SaveRotaCategory.class, null, value);
    }

}
