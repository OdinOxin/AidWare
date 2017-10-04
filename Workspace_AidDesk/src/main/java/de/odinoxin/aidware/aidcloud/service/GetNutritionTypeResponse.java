
package de.odinoxin.aidware.aidcloud.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r getNutritionTypeResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="getNutritionTypeResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://nutritiontype.plugins.aidcloud.odinoxin.de/}NutritionTypeEntity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNutritionTypeResponse", propOrder = {
    "_return"
})
public class GetNutritionTypeResponse {

    @XmlElement(name = "return")
    protected NutritionTypeEntity _return;

    /**
     * Ruft den Wert der return-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link NutritionTypeEntity }
     *     
     */
    public NutritionTypeEntity getReturn() {
        return _return;
    }

    /**
     * Legt den Wert der return-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link NutritionTypeEntity }
     *     
     */
    public void setReturn(NutritionTypeEntity value) {
        this._return = value;
    }

}
