
package de.odinoxin.aidware.aidcloud.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r saveRotaCategory complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="saveRotaCategory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entity" type="{http://category.rota.plugins.aidcloud.odinoxin.de/}RotaCategoryEntity" minOccurs="0"/>
 *         &lt;element name="original" type="{http://category.rota.plugins.aidcloud.odinoxin.de/}RotaCategoryEntity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveRotaCategory", propOrder = {
    "entity",
    "original"
})
public class SaveRotaCategory {

    protected RotaCategoryEntity entity;
    protected RotaCategoryEntity original;

    /**
     * Ruft den Wert der entity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link RotaCategoryEntity }
     *     
     */
    public RotaCategoryEntity getEntity() {
        return entity;
    }

    /**
     * Legt den Wert der entity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link RotaCategoryEntity }
     *     
     */
    public void setEntity(RotaCategoryEntity value) {
        this.entity = value;
    }

    /**
     * Ruft den Wert der original-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link RotaCategoryEntity }
     *     
     */
    public RotaCategoryEntity getOriginal() {
        return original;
    }

    /**
     * Legt den Wert der original-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link RotaCategoryEntity }
     *     
     */
    public void setOriginal(RotaCategoryEntity value) {
        this.original = value;
    }

}
