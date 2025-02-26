/**
 * TaInformacionCompDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mef.std.bs.web.ws;

public class TaInformacionCompDTO  implements java.io.Serializable {
    private java.lang.Long idinfocomp;

    private java.lang.Long idunidad;

    private java.lang.String idunidadTxt;

    private java.lang.String informacion;

    public TaInformacionCompDTO() {
    }

    public TaInformacionCompDTO(
           java.lang.Long idinfocomp,
           java.lang.Long idunidad,
           java.lang.String idunidadTxt,
           java.lang.String informacion) {
           this.idinfocomp = idinfocomp;
           this.idunidad = idunidad;
           this.idunidadTxt = idunidadTxt;
           this.informacion = informacion;
    }


    /**
     * Gets the idinfocomp value for this TaInformacionCompDTO.
     * 
     * @return idinfocomp
     */
    public java.lang.Long getIdinfocomp() {
        return idinfocomp;
    }


    /**
     * Sets the idinfocomp value for this TaInformacionCompDTO.
     * 
     * @param idinfocomp
     */
    public void setIdinfocomp(java.lang.Long idinfocomp) {
        this.idinfocomp = idinfocomp;
    }


    /**
     * Gets the idunidad value for this TaInformacionCompDTO.
     * 
     * @return idunidad
     */
    public java.lang.Long getIdunidad() {
        return idunidad;
    }


    /**
     * Sets the idunidad value for this TaInformacionCompDTO.
     * 
     * @param idunidad
     */
    public void setIdunidad(java.lang.Long idunidad) {
        this.idunidad = idunidad;
    }


    /**
     * Gets the idunidadTxt value for this TaInformacionCompDTO.
     * 
     * @return idunidadTxt
     */
    public java.lang.String getIdunidadTxt() {
        return idunidadTxt;
    }


    /**
     * Sets the idunidadTxt value for this TaInformacionCompDTO.
     * 
     * @param idunidadTxt
     */
    public void setIdunidadTxt(java.lang.String idunidadTxt) {
        this.idunidadTxt = idunidadTxt;
    }


    /**
     * Gets the informacion value for this TaInformacionCompDTO.
     * 
     * @return informacion
     */
    public java.lang.String getInformacion() {
        return informacion;
    }


    /**
     * Sets the informacion value for this TaInformacionCompDTO.
     * 
     * @param informacion
     */
    public void setInformacion(java.lang.String informacion) {
        this.informacion = informacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TaInformacionCompDTO)) return false;
        TaInformacionCompDTO other = (TaInformacionCompDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idinfocomp==null && other.getIdinfocomp()==null) || 
             (this.idinfocomp!=null &&
              this.idinfocomp.equals(other.getIdinfocomp()))) &&
            ((this.idunidad==null && other.getIdunidad()==null) || 
             (this.idunidad!=null &&
              this.idunidad.equals(other.getIdunidad()))) &&
            ((this.idunidadTxt==null && other.getIdunidadTxt()==null) || 
             (this.idunidadTxt!=null &&
              this.idunidadTxt.equals(other.getIdunidadTxt()))) &&
            ((this.informacion==null && other.getInformacion()==null) || 
             (this.informacion!=null &&
              this.informacion.equals(other.getInformacion())));
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
        if (getIdinfocomp() != null) {
            _hashCode += getIdinfocomp().hashCode();
        }
        if (getIdunidad() != null) {
            _hashCode += getIdunidad().hashCode();
        }
        if (getIdunidadTxt() != null) {
            _hashCode += getIdunidadTxt().hashCode();
        }
        if (getInformacion() != null) {
            _hashCode += getInformacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TaInformacionCompDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "taInformacionCompDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idinfocomp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idinfocomp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idunidad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idunidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idunidadTxt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idunidadTxt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "informacion"));
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
