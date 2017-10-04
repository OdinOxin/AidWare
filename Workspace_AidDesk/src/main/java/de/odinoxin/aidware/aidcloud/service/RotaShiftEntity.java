
package de.odinoxin.aidware.aidcloud.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse f�r RotaShiftEntity complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="RotaShiftEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsBeginn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="beginnInterpretation" type="{http://shift.rota.plugins.aidcloud.odinoxin.de/}TimestampInterpretationEntity" minOccurs="0"/>
 *         &lt;element name="tsEnd" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="endInterpretation" type="{http://shift.rota.plugins.aidcloud.odinoxin.de/}TimestampInterpretationEntity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RotaShiftEntity", propOrder = {
    "id",
    "text",
    "tsBeginn",
    "beginnInterpretation",
    "tsEnd",
    "endInterpretation"
})
public class RotaShiftEntity {

    protected int id;
    protected String text;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tsBeginn;
    protected TimestampInterpretationEntity beginnInterpretation;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tsEnd;
    protected TimestampInterpretationEntity endInterpretation;

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
     * Ruft den Wert der text-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Legt den Wert der text-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Ruft den Wert der tsBeginn-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTsBeginn() {
        return tsBeginn;
    }

    /**
     * Legt den Wert der tsBeginn-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTsBeginn(XMLGregorianCalendar value) {
        this.tsBeginn = value;
    }

    /**
     * Ruft den Wert der beginnInterpretation-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TimestampInterpretationEntity }
     *     
     */
    public TimestampInterpretationEntity getBeginnInterpretation() {
        return beginnInterpretation;
    }

    /**
     * Legt den Wert der beginnInterpretation-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TimestampInterpretationEntity }
     *     
     */
    public void setBeginnInterpretation(TimestampInterpretationEntity value) {
        this.beginnInterpretation = value;
    }

    /**
     * Ruft den Wert der tsEnd-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTsEnd() {
        return tsEnd;
    }

    /**
     * Legt den Wert der tsEnd-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTsEnd(XMLGregorianCalendar value) {
        this.tsEnd = value;
    }

    /**
     * Ruft den Wert der endInterpretation-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TimestampInterpretationEntity }
     *     
     */
    public TimestampInterpretationEntity getEndInterpretation() {
        return endInterpretation;
    }

    /**
     * Legt den Wert der endInterpretation-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TimestampInterpretationEntity }
     *     
     */
    public void setEndInterpretation(TimestampInterpretationEntity value) {
        this.endInterpretation = value;
    }

}
