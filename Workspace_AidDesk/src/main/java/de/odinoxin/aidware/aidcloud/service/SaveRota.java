
package de.odinoxin.aidware.aidcloud.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r saveRota complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="saveRota">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entity" type="{http://rota.plugins.aidcloud.odinoxin.de/}RotaEntity" minOccurs="0"/>
 *         &lt;element name="original" type="{http://rota.plugins.aidcloud.odinoxin.de/}RotaEntity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveRota", propOrder = {
    "entity",
    "original"
})
public class SaveRota {

    protected RotaEntity entity;
    protected RotaEntity original;

    /**
     * Ruft den Wert der entity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link RotaEntity }
     *     
     */
    public RotaEntity getEntity() {
        return entity;
    }

    /**
     * Legt den Wert der entity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link RotaEntity }
     *     
     */
    public void setEntity(RotaEntity value) {
        this.entity = value;
    }

    /**
     * Ruft den Wert der original-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link RotaEntity }
     *     
     */
    public RotaEntity getOriginal() {
        return original;
    }

    /**
     * Legt den Wert der original-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link RotaEntity }
     *     
     */
    public void setOriginal(RotaEntity value) {
        this.original = value;
    }

}
