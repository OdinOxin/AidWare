
package de.odinoxin.aidware.aidcloud.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r PersonEntity complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="PersonEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="forename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="language" type="{http://people.plugins.aidcloud.odinoxin.de/}LanguageEntity" minOccurs="0"/>
 *         &lt;element name="address" type="{http://people.plugins.aidcloud.odinoxin.de/}AddressEntity" minOccurs="0"/>
 *         &lt;element name="contactInformation" type="{http://people.plugins.aidcloud.odinoxin.de/}ContactInformationEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nutritionType" type="{http://people.plugins.aidcloud.odinoxin.de/}NutritionTypeEntity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonEntity", propOrder = {
    "id",
    "name",
    "forename",
    "code",
    "pwd",
    "language",
    "address",
    "contactInformation",
    "nutritionType"
})
public class PersonEntity {

    protected int id;
    protected String name;
    protected String forename;
    protected String code;
    protected String pwd;
    protected LanguageEntity language;
    protected AddressEntity address;
    protected List<ContactInformationEntity> contactInformation;
    protected NutritionTypeEntity nutritionType;

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
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Ruft den Wert der forename-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForename() {
        return forename;
    }

    /**
     * Legt den Wert der forename-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForename(String value) {
        this.forename = value;
    }

    /**
     * Ruft den Wert der code-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Legt den Wert der code-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Ruft den Wert der pwd-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Legt den Wert der pwd-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPwd(String value) {
        this.pwd = value;
    }

    /**
     * Ruft den Wert der language-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LanguageEntity }
     *     
     */
    public LanguageEntity getLanguage() {
        return language;
    }

    /**
     * Legt den Wert der language-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LanguageEntity }
     *     
     */
    public void setLanguage(LanguageEntity value) {
        this.language = value;
    }

    /**
     * Ruft den Wert der address-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AddressEntity }
     *     
     */
    public AddressEntity getAddress() {
        return address;
    }

    /**
     * Legt den Wert der address-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressEntity }
     *     
     */
    public void setAddress(AddressEntity value) {
        this.address = value;
    }

    /**
     * Gets the value of the contactInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactInformationEntity }
     * 
     * 
     */
    public List<ContactInformationEntity> getContactInformation() {
        if (contactInformation == null) {
            contactInformation = new ArrayList<ContactInformationEntity>();
        }
        return this.contactInformation;
    }

    /**
     * Ruft den Wert der nutritionType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link NutritionTypeEntity }
     *     
     */
    public NutritionTypeEntity getNutritionType() {
        return nutritionType;
    }

    /**
     * Legt den Wert der nutritionType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link NutritionTypeEntity }
     *     
     */
    public void setNutritionType(NutritionTypeEntity value) {
        this.nutritionType = value;
    }

}
