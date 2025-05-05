/**
 * HrDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mef.std.bs.web.ws;

public class HrDto implements java.io.Serializable {
    private java.util.Calendar fechaCompleto;

    private java.lang.Long idanexo;

    private java.lang.Long iddoc;

    private java.lang.Integer numeroAnio;

    private java.lang.String numeroDoc;

    private java.lang.String numeroSid;

    public HrDto() {
    }

    public HrDto(
            java.util.Calendar fechaCompleto,
            java.lang.Long idanexo,
            java.lang.Long iddoc,
            java.lang.Integer numeroAnio,
            java.lang.String numeroDoc,
            java.lang.String numeroSid) {
        this.fechaCompleto = fechaCompleto;
        this.idanexo = idanexo;
        this.iddoc = iddoc;
        this.numeroAnio = numeroAnio;
        this.numeroDoc = numeroDoc;
        this.numeroSid = numeroSid;
    }

    /**
     * Gets the fechaCompleto value for this HrDto.
     * 
     * @return fechaCompleto
     */
    public java.util.Calendar getFechaCompleto() {
        return fechaCompleto;
    }

    /**
     * Sets the fechaCompleto value for this HrDto.
     * 
     * @param fechaCompleto
     */
    public void setFechaCompleto(java.util.Calendar fechaCompleto) {
        this.fechaCompleto = fechaCompleto;
    }

    /**
     * Gets the idanexo value for this HrDto.
     * 
     * @return idanexo
     */
    public java.lang.Long getIdanexo() {
        return idanexo;
    }

    /**
     * Sets the idanexo value for this HrDto.
     * 
     * @param idanexo
     */
    public void setIdanexo(java.lang.Long idanexo) {
        this.idanexo = idanexo;
    }

    /**
     * Gets the iddoc value for this HrDto.
     * 
     * @return iddoc
     */
    public java.lang.Long getIddoc() {
        return iddoc;
    }

    /**
     * Sets the iddoc value for this HrDto.
     * 
     * @param iddoc
     */
    public void setIddoc(java.lang.Long iddoc) {
        this.iddoc = iddoc;
    }

    /**
     * Gets the numeroAnio value for this HrDto.
     * 
     * @return numeroAnio
     */
    public java.lang.Integer getNumeroAnio() {
        return numeroAnio;
    }

    /**
     * Sets the numeroAnio value for this HrDto.
     * 
     * @param numeroAnio
     */
    public void setNumeroAnio(java.lang.Integer numeroAnio) {
        this.numeroAnio = numeroAnio;
    }

    /**
     * Gets the numeroDoc value for this HrDto.
     * 
     * @return numeroDoc
     */
    public java.lang.String getNumeroDoc() {
        return numeroDoc;
    }

    /**
     * Sets the numeroDoc value for this HrDto.
     * 
     * @param numeroDoc
     */
    public void setNumeroDoc(java.lang.String numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    /**
     * Gets the numeroSid value for this HrDto.
     * 
     * @return numeroSid
     */
    public java.lang.String getNumeroSid() {
        return numeroSid;
    }

    /**
     * Sets the numeroSid value for this HrDto.
     * 
     * @param numeroSid
     */
    public void setNumeroSid(java.lang.String numeroSid) {
        this.numeroSid = numeroSid;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HrDto))
            return false;
        HrDto other = (HrDto) obj;
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.fechaCompleto == null && other.getFechaCompleto() == null) ||
                        (this.fechaCompleto != null &&
                                this.fechaCompleto.equals(other.getFechaCompleto())))
                &&
                ((this.iddoc == null && other.getIddoc() == null) ||
                        (this.iddoc != null &&
                                this.iddoc.equals(other.getIddoc())))
                &&
                ((this.numeroAnio == null && other.getNumeroAnio() == null) ||
                        (this.numeroAnio != null &&
                                this.numeroAnio.equals(other.getNumeroAnio())))
                &&
                ((this.numeroDoc == null && other.getNumeroDoc() == null) ||
                        (this.numeroDoc != null &&
                                this.numeroDoc.equals(other.getNumeroDoc())))
                &&
                ((this.numeroSid == null && other.getNumeroSid() == null) ||
                        (this.numeroSid != null &&
                                this.numeroSid.equals(other.getNumeroSid())));
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
        if (getFechaCompleto() != null) {
            _hashCode += getFechaCompleto().hashCode();
        }
        if (getIddoc() != null) {
            _hashCode += getIddoc().hashCode();
        }
        if (getNumeroAnio() != null) {
            _hashCode += getNumeroAnio().hashCode();
        }
        if (getNumeroDoc() != null) {
            _hashCode += getNumeroDoc().hashCode();
        }
        if (getNumeroSid() != null) {
            _hashCode += getNumeroSid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(HrDto.class,
            true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "hrDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaCompleto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaCompleto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("iddoc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "iddoc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroAnio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroAnio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDoc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroDoc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroSid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroSid"));
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
        return new org.apache.axis.encoding.ser.BeanSerializer(
                _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanDeserializer(
                _javaType, _xmlType, typeDesc);
    }

}
