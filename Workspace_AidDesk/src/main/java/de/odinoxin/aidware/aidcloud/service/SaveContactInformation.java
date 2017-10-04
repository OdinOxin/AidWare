
package de.odinoxin.aidware.aidcloud.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r saveContactInformation complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="saveContactInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entity" type="{http://information.contact.plugins.aidcloud.odinoxin.de/}ContactInformationEntity" minOccurs="0"/>
 *         &lt;element name="original" type="{http://information.contact.plugins.aidcloud.odinoxin.de/}ContactInformationEntity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveContactInformation", propOrder = {
    "entity",
    "original"
})
public class SaveContactInformation {

    protected ContactInformationEntity entity;
    protected ContactInformationEntity original;

    /**
     * Ruft den Wert der entity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ContactInformationEntity }
     *     
     */
    public ContactInformationEntity getEntity() {
        return entity;
    }

    /**
     * Legt den Wert der entity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInformationEntity }
     *     
     */
    public void setEntity(ContactInformationEntity value) {
        this.entity = value;
    }

    /**
     * Ruft den Wert der original-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ContactInformationEntity }
     *     
     */
    public ContactInformationEntity getOriginal() {
        return original;
    }

    /**
     * Legt den Wert der original-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInformationEntity }
     *     
     */
    public void setOriginal(ContactInformationEntity value) {
        this.original = value;
    }

}
