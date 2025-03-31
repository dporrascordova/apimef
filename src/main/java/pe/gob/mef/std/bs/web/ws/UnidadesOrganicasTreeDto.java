/**
 * UnidadesOrganicasTreeDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.mef.std.bs.web.ws;

public class UnidadesOrganicasTreeDto implements java.io.Serializable {
    private java.lang.String acronimo;

    private java.lang.String codigo;

    private boolean conjefe;

    private java.lang.String descripcion;

    private java.lang.String descripcionCompleta;

    private java.lang.Integer flagofgeneral;

    private java.lang.Long idunidad;

    private java.lang.String jefe;

    public UnidadesOrganicasTreeDto() {
    }

    public UnidadesOrganicasTreeDto(
            java.lang.String acronimo,
            java.lang.String codigo,
            boolean conjefe,
            java.lang.String descripcion,
            java.lang.String descripcionCompleta,
            java.lang.Integer flagofgeneral,
            java.lang.Long idunidad,
            java.lang.String jefe) {
        this.acronimo = acronimo;
        this.codigo = codigo;
        this.conjefe = conjefe;
        this.descripcion = descripcion;
        this.descripcionCompleta = descripcionCompleta;
        this.flagofgeneral = flagofgeneral;
        this.idunidad = idunidad;
        this.jefe = jefe;
    }

    /**
     * Gets the acronimo value for this UnidadesOrganicasTreeDto.
     * 
     * @return acronimo
     */
    public java.lang.String getAcronimo() {
        return acronimo;
    }

    /**
     * Sets the acronimo value for this UnidadesOrganicasTreeDto.
     * 
     * @param acronimo
     */
    public void setAcronimo(java.lang.String acronimo) {
        this.acronimo = acronimo;
    }

    /**
     * Gets the codigo value for this UnidadesOrganicasTreeDto.
     * 
     * @return codigo
     */
    public java.lang.String getCodigo() {
        return codigo;
    }

    /**
     * Sets the codigo value for this UnidadesOrganicasTreeDto.
     * 
     * @param codigo
     */
    public void setCodigo(java.lang.String codigo) {
        this.codigo = codigo;
    }

    /**
     * Gets the conjefe value for this UnidadesOrganicasTreeDto.
     * 
     * @return conjefe
     */
    public boolean isConjefe() {
        return conjefe;
    }

    /**
     * Sets the conjefe value for this UnidadesOrganicasTreeDto.
     * 
     * @param conjefe
     */
    public void setConjefe(boolean conjefe) {
        this.conjefe = conjefe;
    }

    /**
     * Gets the descripcion value for this UnidadesOrganicasTreeDto.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the descripcion value for this UnidadesOrganicasTreeDto.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets the descripcionCompleta value for this UnidadesOrganicasTreeDto.
     * 
     * @return descripcionCompleta
     */
    public java.lang.String getDescripcionCompleta() {
        return descripcionCompleta;
    }

    /**
     * Sets the descripcionCompleta value for this UnidadesOrganicasTreeDto.
     * 
     * @param descripcionCompleta
     */
    public void setDescripcionCompleta(java.lang.String descripcionCompleta) {
        this.descripcionCompleta = descripcionCompleta;
    }

    /**
     * Gets the flagofgeneral value for this UnidadesOrganicasTreeDto.
     * 
     * @return flagofgeneral
     */
    public java.lang.Integer getFlagofgeneral() {
        return flagofgeneral;
    }

    /**
     * Sets the flagofgeneral value for this UnidadesOrganicasTreeDto.
     * 
     * @param flagofgeneral
     */
    public void setFlagofgeneral(java.lang.Integer flagofgeneral) {
        this.flagofgeneral = flagofgeneral;
    }

    /**
     * Gets the idunidad value for this UnidadesOrganicasTreeDto.
     * 
     * @return idunidad
     */
    public java.lang.Long getIdunidad() {
        return idunidad;
    }

    /**
     * Sets the idunidad value for this UnidadesOrganicasTreeDto.
     * 
     * @param idunidad
     */
    public void setIdunidad(java.lang.Long idunidad) {
        this.idunidad = idunidad;
    }

    /**
     * Gets the jefe value for this UnidadesOrganicasTreeDto.
     * 
     * @return jefe
     */
    public java.lang.String getJefe() {
        return jefe;
    }

    /**
     * Sets the jefe value for this UnidadesOrganicasTreeDto.
     * 
     * @param jefe
     */
    public void setJefe(java.lang.String jefe) {
        this.jefe = jefe;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UnidadesOrganicasTreeDto))
            return false;
        UnidadesOrganicasTreeDto other = (UnidadesOrganicasTreeDto) obj;
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
                ((this.acronimo == null && other.getAcronimo() == null) ||
                        (this.acronimo != null &&
                                this.acronimo.equals(other.getAcronimo())))
                &&
                ((this.codigo == null && other.getCodigo() == null) ||
                        (this.codigo != null &&
                                this.codigo.equals(other.getCodigo())))
                &&
                this.conjefe == other.isConjefe() &&
                ((this.descripcion == null && other.getDescripcion() == null) ||
                        (this.descripcion != null &&
                                this.descripcion.equals(other.getDescripcion())))
                &&
                ((this.descripcionCompleta == null && other.getDescripcionCompleta() == null) ||
                        (this.descripcionCompleta != null &&
                                this.descripcionCompleta.equals(other.getDescripcionCompleta())))
                &&
                ((this.flagofgeneral == null && other.getFlagofgeneral() == null) ||
                        (this.flagofgeneral != null &&
                                this.flagofgeneral.equals(other.getFlagofgeneral())))
                &&
                ((this.idunidad == null && other.getIdunidad() == null) ||
                        (this.idunidad != null &&
                                this.idunidad.equals(other.getIdunidad())))
                &&
                ((this.jefe == null && other.getJefe() == null) ||
                        (this.jefe != null &&
                                this.jefe.equals(other.getJefe())));
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
        if (getAcronimo() != null) {
            _hashCode += getAcronimo().hashCode();
        }
        if (getCodigo() != null) {
            _hashCode += getCodigo().hashCode();
        }
        _hashCode += (isConjefe() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getDescripcionCompleta() != null) {
            _hashCode += getDescripcionCompleta().hashCode();
        }
        if (getFlagofgeneral() != null) {
            _hashCode += getFlagofgeneral().hashCode();
        }
        if (getIdunidad() != null) {
            _hashCode += getIdunidad().hashCode();
        }
        if (getJefe() != null) {
            _hashCode += getJefe().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
            UnidadesOrganicasTreeDto.class, true);

    static {
        typeDesc.setXmlType(
                new javax.xml.namespace.QName("http://ws.web.bs.std.mef.gob.pe/", "unidadesOrganicasTreeDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acronimo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acronimo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conjefe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "conjefe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionCompleta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcionCompleta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagofgeneral");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagofgeneral"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("jefe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jefe"));
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
