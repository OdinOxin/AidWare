
package de.odinoxin.aidware.aidcloud.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r saveNutritionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="saveNutritionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entity" type="{http://nutritiontype.plugins.aidcloud.odinoxin.de/}NutritionTypeEntity" minOccurs="0"/>
 *         &lt;element name="original" type="{http://nutritiontype.plugins.aidcloud.odinoxin.de/}NutritionTypeEntity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveNutritionType", propOrder = {
    "entity",
    "original"
})
public class SaveNutritionType {

    protected NutritionTypeEntity entity;
    protected NutritionTypeEntity original;

    /**
     * Ruft den Wert der entity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link NutritionTypeEntity }
     *     
     */
    public NutritionTypeEntity getEntity() {
        return entity;
    }

    /**
     * Legt den Wert der entity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link NutritionTypeEntity }
     *     
     */
    public void setEntity(NutritionTypeEntity value) {
        this.entity = value;
    }

    /**
     * Ruft den Wert der original-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link NutritionTypeEntity }
     *     
     */
    public NutritionTypeEntity getOriginal() {
        return original;
    }

    /**
     * Legt den Wert der original-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link NutritionTypeEntity }
     *     
     */
    public void setOriginal(NutritionTypeEntity value) {
        this.original = value;
    }

}
