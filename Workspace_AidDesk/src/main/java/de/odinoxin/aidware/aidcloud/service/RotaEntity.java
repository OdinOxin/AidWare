
package de.odinoxin.aidware.aidcloud.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r RotaEntity complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="RotaEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rotaCategory" type="{http://rota.plugins.aidcloud.odinoxin.de/}RotaCategoryEntity" minOccurs="0"/>
 *         &lt;element name="rotaShifts" type="{http://rota.plugins.aidcloud.odinoxin.de/}RotaShiftEntity" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RotaEntity", propOrder = {
    "id",
    "title",
    "rotaCategory",
    "rotaShifts"
})
public class RotaEntity {

    protected int id;
    protected String title;
    protected RotaCategoryEntity rotaCategory;
    protected List<RotaShiftEntity> rotaShifts;

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
     * Ruft den Wert der title-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Legt den Wert der title-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Ruft den Wert der rotaCategory-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link RotaCategoryEntity }
     *     
     */
    public RotaCategoryEntity getRotaCategory() {
        return rotaCategory;
    }

    /**
     * Legt den Wert der rotaCategory-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link RotaCategoryEntity }
     *     
     */
    public void setRotaCategory(RotaCategoryEntity value) {
        this.rotaCategory = value;
    }

    /**
     * Gets the value of the rotaShifts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rotaShifts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRotaShifts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RotaShiftEntity }
     * 
     * 
     */
    public List<RotaShiftEntity> getRotaShifts() {
        if (rotaShifts == null) {
            rotaShifts = new ArrayList<RotaShiftEntity>();
        }
        return this.rotaShifts;
    }

}
