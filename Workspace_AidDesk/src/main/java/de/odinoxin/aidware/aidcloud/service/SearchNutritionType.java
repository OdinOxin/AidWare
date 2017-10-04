
package de.odinoxin.aidware.aidcloud.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r searchNutritionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="searchNutritionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="expr" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="max" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="exceptIds" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchNutritionType", propOrder = {
    "expr",
    "max",
    "exceptIds"
})
public class SearchNutritionType {

    @XmlElement(nillable = true)
    protected List<String> expr;
    protected int max;
    @XmlElement(nillable = true)
    protected List<Integer> exceptIds;

    /**
     * Gets the value of the expr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the expr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExpr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getExpr() {
        if (expr == null) {
            expr = new ArrayList<String>();
        }
        return this.expr;
    }

    /**
     * Ruft den Wert der max-Eigenschaft ab.
     * 
     */
    public int getMax() {
        return max;
    }

    /**
     * Legt den Wert der max-Eigenschaft fest.
     * 
     */
    public void setMax(int value) {
        this.max = value;
    }

    /**
     * Gets the value of the exceptIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exceptIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExceptIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getExceptIds() {
        if (exceptIds == null) {
            exceptIds = new ArrayList<Integer>();
        }
        return this.exceptIds;
    }

}
