//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.15 at 02:44:00 AM FET 
//


package by.epam.cards;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Theme.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Theme">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CITYSPACE"/>
 *     &lt;enumeration value="NATURE"/>
 *     &lt;enumeration value="PEOPLE"/>
 *     &lt;enumeration value="RELIGION"/>
 *     &lt;enumeration value="SPORTS"/>
 *     &lt;enumeration value="ARCHITECTURE"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Theme")
@XmlEnum
public enum Theme {

    CITYSPACE,
    NATURE,
    PEOPLE,
    RELIGION,
    SPORTS,
    ARCHITECTURE,
    OTHER;

    public String value() {
        return name();
    }

    public static Theme fromValue(String v) {
        return valueOf(v);
    }

}
