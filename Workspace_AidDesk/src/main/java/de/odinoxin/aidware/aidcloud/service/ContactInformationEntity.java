
package de.odinoxin.aidware.aidcloud.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r ContactInformationEntity complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ContactInformationEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="contactType" type="{http://information.contact.plugins.aidcloud.odinoxin.de/}ContactTypeEntity" minOccurs="0"/>
 *         &lt;element name="information" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactInformationEntity", propOrder = {
    "id",
    "contactType",
    "information"
})
public class ContactInformationEntity {

    protected int id;
    protected ContactTypeEntity contactType;
    protected String information;

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Ruft den Wert der contactType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ContactTypeEntity }
     *     
     */
    public ContactTypeEntity getContactType() {
        return contactType;
    }

    /**
     * Legt den Wert der contactType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactTypeEntity }
     *     
     */
    public void setContactType(ContactTypeEntity value) {
        this.contactType = value;
    }

    /**
     * Ruft den Wert der information-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInformation() {
        return information;
    }

    /**
     * Legt den Wert der information-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInformation(String value) {
        this.information = value;
    }

}
