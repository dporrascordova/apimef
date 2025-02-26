/**
 * MovimientoWSDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mef.std.bs.web.ws;

public class MovimientoWSDto  implements java.io.Serializable {
    private java.lang.String estado;

    private java.lang.String fechadeestado;

    private java.lang.String remitente;

    private java.lang.String unidaddestino;

    public MovimientoWSDto() {
    }

    public MovimientoWSDto(
           java.lang.String estado,
           java.lang.String fechadeestado,
           java.lang.String remitente,
           java.lang.String unidaddestino) {
           this.estado = estado;
           this.fechadeestado = fechadeestado;
           this.remitente = remitente;
           this.unidaddestino = unidaddestino;
    }


    /**
     * Gets the estado value for this MovimientoWSDto.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this MovimientoWSDto.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the fechadeestado value for this MovimientoWSDto.
     * 
     * @return fechadeestado
     */
    public java.lang.String getFechadeestado() {
        return fechadeestado;
    }


    /**
     * Sets the fechadeestado value for this MovimientoWSDto.
     * 
     * @param fechadeestado
     */
    public void setFechadeestado(java.lang.String fechadeestado) {
        this.fechadeestado = fechadeestado;
    }


    /**
     * Gets the remitente value for this MovimientoWSDto.
     * 
     * @return remitente
     */
    public java.lang.String getRemitente() {
        return remitente;
    }


    /**
     * Sets the remitente value for this MovimientoWSDto.
     * 
     * @param remitente
     */
    public void setRemitente(java.lang.String remitente) {
        this.remitente = remitente;
    }


    /**
     * Gets the unidaddestino value for this MovimientoWSDto.
     * 
     * @return unidaddestino
     */
    public java.lang.String getUnidaddestino() {
        return unidaddestino;
    }


    /**
     * Sets the unidaddestino value for this MovimientoWSDto.
     * 
     * @param unidaddestino
     */
    public void setUnidaddestino(java.lang.String unidaddestino) {
        this.unidaddestino = unidaddestino;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MovimientoWSDto)) return false;
        MovimientoWSDto other = (MovimientoWSDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.fechadeestado==null && other.getFechadeestado()==null) || 
             (this.fechadeestado!=null &&
              this.fechadeestado.equals(other.getFechadeestado()))) &&
            ((this.remitente==null && other.getRemitente()==null) || 
             (this.remitente!=null &&
              this.remitente.equals(other.getRemitente()))) &&
            ((this.unidaddestino==null && other.getUnidaddestino()==null) || 
             (this.unidaddestino!=null &&
              this.unidaddestino.equals(other.getUnidaddestino())));
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
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getFechadeestado() != null) {
            _hashCode += getFechadeestado().hashCode();
        }
        if (getRemitente() != null) {
            _hashCode += getRemitente().hashCode();
        }
        if (getUnidaddestino() != null) {
            _hashCode += getUnidaddestino().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MovimientoWSDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "movimientoWSDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechadeestado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechadeestado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remitente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "remitente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unidaddestino");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unidaddestino"));
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
