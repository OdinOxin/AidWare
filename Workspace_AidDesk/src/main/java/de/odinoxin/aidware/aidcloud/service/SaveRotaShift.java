
package de.odinoxin.aidware.aidcloud.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r saveRotaShift complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="saveRotaShift">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entity" type="{http://shift.rota.plugins.aidcloud.odinoxin.de/}RotaShiftEntity" minOccurs="0"/>
 *         &lt;element name="original" type="{http://shift.rota.plugins.aidcloud.odinoxin.de/}RotaShiftEntity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveRotaShift", propOrder = {
    "entity",
    "original"
})
public class SaveRotaShift {

    protected RotaShiftEntity entity;
    protected RotaShiftEntity original;

    /**
     * Ruft den Wert der entity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link RotaShiftEntity }
     *     
     */
    public RotaShiftEntity getEntity() {
        return entity;
    }

    /**
     * Legt den Wert der entity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link RotaShiftEntity }
     *     
     */
    public void setEntity(RotaShiftEntity value) {
        this.entity = value;
    }

    /**
     * Ruft den Wert der original-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link RotaShiftEntity }
     *     
     */
    public RotaShiftEntity getOriginal() {
        return original;
    }

    /**
     * Legt den Wert der original-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link RotaShiftEntity }
     *     
     */
    public void setOriginal(RotaShiftEntity value) {
        this.original = value;
    }

}
