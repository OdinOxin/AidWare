
package de.odinoxin.aidware.aidcloud.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r saveAddress complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="saveAddress">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entity" type="{http://addresses.plugins.aidcloud.odinoxin.de/}AddressEntity" minOccurs="0"/>
 *         &lt;element name="original" type="{http://addresses.plugins.aidcloud.odinoxin.de/}AddressEntity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveAddress", propOrder = {
    "entity",
    "original"
})
public class SaveAddress {

    protected AddressEntity entity;
    protected AddressEntity original;

    /**
     * Ruft den Wert der entity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AddressEntity }
     *     
     */
    public AddressEntity getEntity() {
        return entity;
    }

    /**
     * Legt den Wert der entity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressEntity }
     *     
     */
    public void setEntity(AddressEntity value) {
        this.entity = value;
    }

    /**
     * Ruft den Wert der original-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AddressEntity }
     *     
     */
    public AddressEntity getOriginal() {
        return original;
    }

    /**
     * Legt den Wert der original-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressEntity }
     *     
     */
    public void setOriginal(AddressEntity value) {
        this.original = value;
    }

}
