/**
 * IdValorDtoComCongre.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mef.std.bs.web.ws;

public class IdValorDtoComCongre  implements java.io.Serializable {
    private pe.gob.mef.std.bs.web.ws.IdValorDto[] congresistas;

    private java.lang.Long id;

    private java.lang.String valor;

    public IdValorDtoComCongre() {
    }

    public IdValorDtoComCongre(
           pe.gob.mef.std.bs.web.ws.IdValorDto[] congresistas,
           java.lang.Long id,
           java.lang.String valor) {
           this.congresistas = congresistas;
           this.id = id;
           this.valor = valor;
    }


    /**
     * Gets the congresistas value for this IdValorDtoComCongre.
     * 
     * @return congresistas
     */
    public pe.gob.mef.std.bs.web.ws.IdValorDto[] getCongresistas() {
        return congresistas;
    }


    /**
     * Sets the congresistas value for this IdValorDtoComCongre.
     * 
     * @param congresistas
     */
    public void setCongresistas(pe.gob.mef.std.bs.web.ws.IdValorDto[] congresistas) {
        this.congresistas = congresistas;
    }

    public pe.gob.mef.std.bs.web.ws.IdValorDto getCongresistas(int i) {
        return this.congresistas[i];
    }

    public void setCongresistas(int i, pe.gob.mef.std.bs.web.ws.IdValorDto _value) {
        this.congresistas[i] = _value;
    }


    /**
     * Gets the id value for this IdValorDtoComCongre.
     * 
     * @return id
     */
    public java.lang.Long getId() {
        return id;
    }


    /**
     * Sets the id value for this IdValorDtoComCongre.
     * 
     * @param id
     */
    public void setId(java.lang.Long id) {
        this.id = id;
    }


    /**
     * Gets the valor value for this IdValorDtoComCongre.
     * 
     * @return valor
     */
    public java.lang.String getValor() {
        return valor;
    }


    /**
     * Sets the valor value for this IdValorDtoComCongre.
     * 
     * @param valor
     */
    public void setValor(java.lang.String valor) {
        this.valor = valor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IdValorDtoComCongre)) return false;
        IdValorDtoComCongre other = (IdValorDtoComCongre) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.congresistas==null && other.getCongresistas()==null) || 
             (this.congresistas!=null &&
              java.util.Arrays.equals(this.congresistas, other.getCongresistas()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.valor==null && other.getValor()==null) || 
             (this.valor!=null &&
              this.valor.equals(other.getValor())));
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
        if (getCongresistas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCongresistas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCongresistas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getValor() != null) {
            _hashCode += getValor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IdValorDtoComCongre.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDtoComCongre"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("congresistas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "congresistas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "idValorDto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valor"));
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
