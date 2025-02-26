/**
 * AcMsUbigwsDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mef.std.bs.web.ws;

public class AcMsUbigwsDto  implements java.io.Serializable {
    private java.lang.Integer coddist;

    private java.lang.Integer coddpto;

    private java.lang.Integer codprov;

    private java.lang.String nombre;

    public AcMsUbigwsDto() {
    }

    public AcMsUbigwsDto(
           java.lang.Integer coddist,
           java.lang.Integer coddpto,
           java.lang.Integer codprov,
           java.lang.String nombre) {
           this.coddist = coddist;
           this.coddpto = coddpto;
           this.codprov = codprov;
           this.nombre = nombre;
    }


    /**
     * Gets the coddist value for this AcMsUbigwsDto.
     * 
     * @return coddist
     */
    public java.lang.Integer getCoddist() {
        return coddist;
    }


    /**
     * Sets the coddist value for this AcMsUbigwsDto.
     * 
     * @param coddist
     */
    public void setCoddist(java.lang.Integer coddist) {
        this.coddist = coddist;
    }


    /**
     * Gets the coddpto value for this AcMsUbigwsDto.
     * 
     * @return coddpto
     */
    public java.lang.Integer getCoddpto() {
        return coddpto;
    }


    /**
     * Sets the coddpto value for this AcMsUbigwsDto.
     * 
     * @param coddpto
     */
    public void setCoddpto(java.lang.Integer coddpto) {
        this.coddpto = coddpto;
    }


    /**
     * Gets the codprov value for this AcMsUbigwsDto.
     * 
     * @return codprov
     */
    public java.lang.Integer getCodprov() {
        return codprov;
    }


    /**
     * Sets the codprov value for this AcMsUbigwsDto.
     * 
     * @param codprov
     */
    public void setCodprov(java.lang.Integer codprov) {
        this.codprov = codprov;
    }


    /**
     * Gets the nombre value for this AcMsUbigwsDto.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this AcMsUbigwsDto.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AcMsUbigwsDto)) return false;
        AcMsUbigwsDto other = (AcMsUbigwsDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.coddist==null && other.getCoddist()==null) || 
             (this.coddist!=null &&
              this.coddist.equals(other.getCoddist()))) &&
            ((this.coddpto==null && other.getCoddpto()==null) || 
             (this.coddpto!=null &&
              this.coddpto.equals(other.getCoddpto()))) &&
            ((this.codprov==null && other.getCodprov()==null) || 
             (this.codprov!=null &&
              this.codprov.equals(other.getCodprov()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre())));
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
        if (getCoddist() != null) {
            _hashCode += getCoddist().hashCode();
        }
        if (getCoddpto() != null) {
            _hashCode += getCoddpto().hashCode();
        }
        if (getCodprov() != null) {
            _hashCode += getCodprov().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AcMsUbigwsDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "acMsUbigwsDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coddist");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coddist"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coddpto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coddpto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codprov");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codprov"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
