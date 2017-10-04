
package de.odinoxin.aidware.aidcloud.service;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "PersonProviderService", targetNamespace = "http://person.plugins.aidcloud.odinoxin.de/", wsdlLocation = "http://SPUTNIK:15123/AidCloud/PersonProvider?wsdl")
public class PersonProviderService
    extends Service
{

    private final static URL PERSONPROVIDERSERVICE_WSDL_LOCATION;
    private final static WebServiceException PERSONPROVIDERSERVICE_EXCEPTION;
    private final static QName PERSONPROVIDERSERVICE_QNAME = new QName("http://person.plugins.aidcloud.odinoxin.de/", "PersonProviderService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://SPUTNIK:15123/AidCloud/PersonProvider?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PERSONPROVIDERSERVICE_WSDL_LOCATION = url;
        PERSONPROVIDERSERVICE_EXCEPTION = e;
    }

    public PersonProviderService() {
        super(__getWsdlLocation(), PERSONPROVIDERSERVICE_QNAME);
    }

    public PersonProviderService(WebServiceFeature... features) {
        super(__getWsdlLocation(), PERSONPROVIDERSERVICE_QNAME, features);
    }

    public PersonProviderService(URL wsdlLocation) {
        super(wsdlLocation, PERSONPROVIDERSERVICE_QNAME);
    }

    public PersonProviderService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PERSONPROVIDERSERVICE_QNAME, features);
    }

    public PersonProviderService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PersonProviderService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns PersonProvider
     */
    @WebEndpoint(name = "PersonProviderPort")
    public PersonProvider getPersonProviderPort() {
        return super.getPort(new QName("http://person.plugins.aidcloud.odinoxin.de/", "PersonProviderPort"), PersonProvider.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PersonProvider
     */
    @WebEndpoint(name = "PersonProviderPort")
    public PersonProvider getPersonProviderPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://person.plugins.aidcloud.odinoxin.de/", "PersonProviderPort"), PersonProvider.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PERSONPROVIDERSERVICE_EXCEPTION!= null) {
            throw PERSONPROVIDERSERVICE_EXCEPTION;
        }
        return PERSONPROVIDERSERVICE_WSDL_LOCATION;
    }

}
