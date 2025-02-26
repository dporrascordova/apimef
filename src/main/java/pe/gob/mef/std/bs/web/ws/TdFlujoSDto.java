/**
 * TdFlujoSDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mef.std.bs.web.ws;

public class TdFlujoSDto  implements java.io.Serializable {
    private java.lang.Long idinstr;

    private java.lang.Long idunidadDestino;

    private java.lang.String observacion;

    private boolean original;

    public TdFlujoSDto() {
    }

    public TdFlujoSDto(
           java.lang.Long idinstr,
           java.lang.Long idunidadDestino,
           java.lang.String observacion,
           boolean original) {
           this.idinstr = idinstr;
           this.idunidadDestino = idunidadDestino;
           this.observacion = observacion;
           this.original = original;
    }


    /**
     * Gets the idinstr value for this TdFlujoSDto.
     * 
     * @return idinstr
     */
    public java.lang.Long getIdinstr() {
        return idinstr;
    }


    /**
     * Sets the idinstr value for this TdFlujoSDto.
     * 
     * @param idinstr
     */
    public void setIdinstr(java.lang.Long idinstr) {
        this.idinstr = idinstr;
    }


    /**
     * Gets the idunidadDestino value for this TdFlujoSDto.
     * 
     * @return idunidadDestino
     */
    public java.lang.Long getIdunidadDestino() {
        return idunidadDestino;
    }


    /**
     * Sets the idunidadDestino value for this TdFlujoSDto.
     * 
     * @param idunidadDestino
     */
    public void setIdunidadDestino(java.lang.Long idunidadDestino) {
        this.idunidadDestino = idunidadDestino;
    }


    /**
     * Gets the observacion value for this TdFlujoSDto.
     * 
     * @return observacion
     */
    public java.lang.String getObservacion() {
        return observacion;
    }


    /**
     * Sets the observacion value for this TdFlujoSDto.
     * 
     * @param observacion
     */
    public void setObservacion(java.lang.String observacion) {
        this.observacion = observacion;
    }


    /**
     * Gets the original value for this TdFlujoSDto.
     * 
     * @return original
     */
    public boolean isOriginal() {
        return original;
    }


    /**
     * Sets the original value for this TdFlujoSDto.
     * 
     * @param original
     */
    public void setOriginal(boolean original) {
        this.original = original;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TdFlujoSDto)) return false;
        TdFlujoSDto other = (TdFlujoSDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idinstr==null && other.getIdinstr()==null) || 
             (this.idinstr!=null &&
              this.idinstr.equals(other.getIdinstr()))) &&
            ((this.idunidadDestino==null && other.getIdunidadDestino()==null) || 
             (this.idunidadDestino!=null &&
              this.idunidadDestino.equals(other.getIdunidadDestino()))) &&
            ((this.observacion==null && other.getObservacion()==null) || 
             (this.observacion!=null &&
              this.observacion.equals(other.getObservacion()))) &&
            this.original == other.isOriginal();
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
        if (getIdinstr() != null) {
            _hashCode += getIdinstr().hashCode();
        }
        if (getIdunidadDestino() != null) {
            _hashCode += getIdunidadDestino().hashCode();
        }
        if (getObservacion() != null) {
            _hashCode += getObservacion().hashCode();
        }
        _hashCode += (isOriginal() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TdFlujoSDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "tdFlujoSDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idinstr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idinstr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idunidadDestino");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idunidadDestino"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("observacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "observacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("original");
        elemField.setXmlName(new javax.xml.namespace.QName("", "original"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
