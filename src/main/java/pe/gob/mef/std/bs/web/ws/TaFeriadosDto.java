/**
 * TaFeriadosDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mef.std.bs.web.ws;

public class TaFeriadosDto  implements java.io.Serializable {
    private java.lang.String feDesc;

    private java.util.Calendar feFecha;

    public TaFeriadosDto() {
    }

    public TaFeriadosDto(
           java.lang.String feDesc,
           java.util.Calendar feFecha) {
           this.feDesc = feDesc;
           this.feFecha = feFecha;
    }


    /**
     * Gets the feDesc value for this TaFeriadosDto.
     * 
     * @return feDesc
     */
    public java.lang.String getFeDesc() {
        return feDesc;
    }


    /**
     * Sets the feDesc value for this TaFeriadosDto.
     * 
     * @param feDesc
     */
    public void setFeDesc(java.lang.String feDesc) {
        this.feDesc = feDesc;
    }


    /**
     * Gets the feFecha value for this TaFeriadosDto.
     * 
     * @return feFecha
     */
    public java.util.Calendar getFeFecha() {
        return feFecha;
    }


    /**
     * Sets the feFecha value for this TaFeriadosDto.
     * 
     * @param feFecha
     */
    public void setFeFecha(java.util.Calendar feFecha) {
        this.feFecha = feFecha;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TaFeriadosDto)) return false;
        TaFeriadosDto other = (TaFeriadosDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.feDesc==null && other.getFeDesc()==null) || 
             (this.feDesc!=null &&
              this.feDesc.equals(other.getFeDesc()))) &&
            ((this.feFecha==null && other.getFeFecha()==null) || 
             (this.feFecha!=null &&
              this.feFecha.equals(other.getFeFecha())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getFeDesc() != null) {
            _hashCode += getFeDesc().hashCode();
        }
        if (getFeFecha() != null) {
            _hashCode += getFeFecha().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TaFeriadosDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "taFeriadosDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "feDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feFecha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "feFecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
