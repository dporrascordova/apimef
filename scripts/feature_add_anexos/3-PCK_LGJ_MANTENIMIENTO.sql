CREATE OR REPLACE PACKAGE BODY SISVENVI.PCK_LGJ_MANTENIMIENTO AS
  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (28/07/2020)
  -- Descripcion:  Listar documentos
  -- ****************************************************************************************

  PROCEDURE P_LISTAR_DOCUMENTOSNO(IN_ID_ESTADO_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_ESTADO_DOCUMENTO%TYPE,
                                  IN_ID_DOCUMENTO        IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                                  IN_ID_OFICINA          IN T_VUDM_DOCUMENTO.ID_OFICINA%TYPE,
                                  IN_ID_TIPO_DOCUMENTO   IN T_VUDM_DOCUMENTO.ID_TIPO_DOCUMENTO%TYPE,
                                  IN_NRO_DOCUMENTO       IN T_VUDM_DOCUMENTO.NRO_DOCUMENTO%TYPE,
                                  IN_ASUNTO              IN T_VUDM_DOCUMENTO.ASUNTO%TYPE,
                                  IN_FEC_INICIO          IN VARCHAR2,
                                  IN_FEC_FIN             IN VARCHAR2,
                                  IN_TAB_DOC             IN VARCHAR2,
                                  IN_ID_USUARIO          IN T_VUDM_DOCUMENTO.ID_USUARIO%TYPE,
                                  OUT_CURSOR             IN OUT SYS_REFCURSOR) AS
  BEGIN
    OPEN OUT_CURSOR FOR
      WITH lista_documnetos AS
       (SELECT t.id_documento,
               t.asunto,
               t.codigo_archivo,
               TO_CHAR(t.fec_creacion, 'DD/MM/YYYY hh:mi:ss a.m.') fec_creacion,
               TO_CHAR(NVL(t.fec_creacion, ''), 'DD/MM/YYYY hh:mi:ss a.m.') fec_creacion2,
               -- NVL(t.fec_modificacion ,(TO_CHAR(t.fec_creacion, 'DD/MM/YYYY hh:mi:ss a.m.'))),
               NVL(TO_CHAR(NVL(t.fec_recibido, '-'),
                           'DD/MM/YYYY hh:mi:ss a.m.'),
                   '-') fec_recibido,
               t.flg_estado,
               t.id_estado_documento,
               t.id_oficina,
               t.id_tipo_documento,
               t.id_tipo_usuario,
               NVL(t.id_usuario, 0),
               NVL(t.ip_creacion, 'x'),
               NVL(t.ip_modificacion, 'x'),
               t.nro_documento,
               t.nro_folios,
               t.usu_creacion,
               NVL(t.usu_modificacion, 'x'),
               (SELECT COUNT(*)
                  FROM SISVENVI.t_Vudm_Documento_Anexo DA
                 WHERE t.id_documento = DA.ID_DOCUMENTO
                   AND DA.ESTADO = '1') anexos,
               CASE
                 WHEN t.id_estado_documento = 1 OR t.id_estado_documento = 4 THEN
                  'TAB_PENDIENTE'
                 WHEN t.id_estado_documento = 2 THEN
                  'TAB_OBSERVADO'
                 WHEN t.id_estado_documento = 3 THEN
                  'TAB_RECIBIDO'
               END grupo_estados,
               t.anio,
               NVL(t.numero_sid, 'x')
          FROM t_vudm_documento t
         WHERE t.id_usuario = IN_ID_USUARIO)
      SELECT *
        FROM lista_documnetos
       WHERE (NVL(IN_ID_ESTADO_DOCUMENTO, 0) = 0 OR
             id_estado_documento = IN_ID_ESTADO_DOCUMENTO)
         AND (NVL(IN_ID_DOCUMENTO, 0) = 0 OR id_documento = IN_ID_DOCUMENTO)
         AND (NVL(IN_ID_OFICINA, 0) = 0 OR id_oficina = IN_ID_OFICINA)
         AND (IN_ID_TIPO_DOCUMENTO IS NULL OR
             id_tipo_documento = IN_ID_TIPO_DOCUMENTO)
         AND (IN_NRO_DOCUMENTO IS NULL OR nro_documento = IN_NRO_DOCUMENTO)
         AND (IN_ASUNTO IS NULL OR
             UPPER(asunto) LIKE '%' || UPPER(IN_ASUNTO) || '%')
         AND (IN_FEC_INICIO IS NULL OR
             TO_CHAR(fec_recibido, 'DD/MM/YYYY') >= IN_FEC_INICIO)
         AND (IN_FEC_FIN IS NULL OR
             TO_CHAR(fec_recibido, 'DD/MM/YYYY') <= IN_FEC_FIN)
         AND (IN_TAB_DOC IS NULL OR grupo_estados = IN_TAB_DOC)
       ORDER BY id_documento DESC, fec_creacion DESC;
    --CLOSE OUT_CURSOR; 
  END;

  PROCEDURE P_LISTAR_DOCUMENTOS(IN_ID_ESTADO_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_ESTADO_DOCUMENTO%TYPE,
                                IN_ID_DOCUMENTO        IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                                IN_ID_OFICINA          IN T_VUDM_DOCUMENTO.ID_OFICINA%TYPE,
                                IN_ID_TIPO_DOCUMENTO   IN T_VUDM_DOCUMENTO.ID_TIPO_DOCUMENTO%TYPE,
                                IN_NRO_DOCUMENTO       IN T_VUDM_DOCUMENTO.NRO_DOCUMENTO%TYPE,
                                IN_ASUNTO              IN T_VUDM_DOCUMENTO.ASUNTO%TYPE,
                                IN_FEC_INICIO          IN VARCHAR2,
                                IN_FEC_FIN             IN VARCHAR2,
                                IN_TAB_DOC             IN VARCHAR2,
                                IN_ID_USUARIO          IN T_VUDM_DOCUMENTO.ID_USUARIO%TYPE,
                                OUT_CURSOR             IN OUT SYS_REFCURSOR) AS
  BEGIN
    OPEN OUT_CURSOR FOR
      WITH documento_gpo_estados AS
       (SELECT DISTINCT t.id_estado_documento,
                        CASE
                          WHEN t.id_estado_documento = 1 OR
                               t.id_estado_documento = 4 OR
                               t.id_estado_documento = 7 OR
                               t.id_estado_documento = 8 THEN
                           'TAB_PENDIENTE'
                          WHEN t.id_estado_documento = 2 THEN
                           'TAB_OBSERVADO'
                          WHEN t.id_estado_documento = 3 THEN
                           'TAB_RECIBIDO'
                          WHEN t.id_estado_documento = 5 THEN
                           'TAB_FINALIZADO'
                          WHEN t.id_estado_documento = 6 THEN
                           'TAB_ANULADO'
                        END grupo_estados
          FROM t_vudm_documento t),
      documentos_lista AS
       (SELECT t.*, docgpo.grupo_estados
          FROM t_vudm_documento t
         INNER JOIN documento_gpo_estados docgpo
            ON t.id_estado_documento = docgpo.id_estado_documento
         WHERE t.id_usuario = NVL(IN_ID_USUARIO, t.id_usuario)
           AND (NVL(IN_ID_ESTADO_DOCUMENTO, 0) = 0 OR
               t.id_estado_documento = IN_ID_ESTADO_DOCUMENTO)
           AND (NVL(IN_ID_DOCUMENTO, 0) = 0 OR
               id_documento = IN_ID_DOCUMENTO)
           AND (NVL(IN_ID_OFICINA, 0) = 0 OR id_oficina = IN_ID_OFICINA)
           AND (IN_ID_TIPO_DOCUMENTO IS NULL OR
               t.id_tipo_documento = IN_ID_TIPO_DOCUMENTO)
           AND (IN_NRO_DOCUMENTO IS NULL OR
               UPPER(nro_documento) LIKE
               '%' || UPPER(IN_NRO_DOCUMENTO) || '%')
           AND (IN_ASUNTO IS NULL OR
               UPPER(asunto) LIKE '%' || UPPER(IN_ASUNTO) || '%')
           AND (IN_FEC_INICIO IS NULL OR
               TO_CHAR(t.fec_creacion, 'YYYYMMDD') >=
               TO_CHAR(TO_DATE(IN_FEC_INICIO, 'DD/MM/YYYY'), 'YYYYMMDD'))
           AND (IN_FEC_FIN IS NULL OR
               TO_CHAR(t.fec_creacion, 'YYYYMMDD') <=
               TO_CHAR(TO_DATE(IN_FEC_FIN, 'DD/MM/YYYY'), 'YYYYMMDD'))
           AND (IN_TAB_DOC IS NULL OR docgpo.grupo_estados = IN_TAB_DOC))
      SELECT t.id_documento,
             t.asunto,
             t.codigo_archivo,
             TO_CHAR(t.fec_creacion, 'DD/MM/YYYY hh:mi:ss a.m.') fec_creacion,
             t.fec_modificacion,
             NVL(TO_CHAR(t.fec_recibido, 'DD/MM/YYYY hh:mi:ss a.m.'), '-') fec_recibido,
             t.flg_estado,
             t.id_estado_documento,
             E.DESC_ESTADO,
             t.ID_OFICINA,
             O.DESCRIPCION DESC_OFICINA,
             t.id_tipo_documento,
             TD.DESCRIPCION DESC_TIPO,
             t.id_tipo_usuario,
             t.id_usuario,
             NVL(t.ip_creacion, '-'),
             t.ip_modificacion,
             t.nro_documento,
             t.nro_folios,
             t.usu_creacion,
             t.usu_modificacion,
             (SELECT COUNT(*)
                FROM t_Vudm_Documento_Anexo DA
               WHERE t.id_documento = DA.ID_DOCUMENTO
                 AND DA.ESTADO = '1') anexos,
             t.grupo_estados,
             t.anio,
             t.numero_sid,
             CASE
               WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                UPPER(per.NOM_PERSONA) || ' ' || UPPER(per.APE_PAT_PERSONA) || ' ' ||
                UPPER(per.APE_MAT_PERSONA)
               ELSE
                CASE
                  WHEN PEX.ID_TIPO_PERSONA = 1 THEN
                   UPPER(PEX.NOMBRES) || ' ' || UPPER(PEX.APELLIDO_PATERNO) || ' ' ||
                   UPPER(PEX.APELLIDO_MATERNO)
                  ELSE
                   UPPER(PEX.RAZON_SOCIAL)
                END
             END NOM_PERSONA,
             t.hoja_ruta_anexo
        FROM documentos_lista t
       INNER JOIN documento_gpo_estados docgpo
          ON t.id_estado_documento = docgpo.id_estado_documento
       INNER JOIN T_VENL_ESTADO E
          ON t.id_estado_documento = E.ID_ESTADO
       INNER JOIN T_SEGM_USUARIO USU
          ON T.ID_USUARIO = USU.ID_USUARIO
       INNER JOIN T_VENL_OFICINA O
          ON t.ID_OFICINA = O.IDUNIDAD
       INNER JOIN T_VENL_TIPO_DOC TD
          ON t.id_tipo_documento = TD.ID_TIPO_DOC
        LEFT JOIN T_SEGM_PERSONAL per
          ON usu.ID_PERSONA = per.ID_PERSONA
         AND USU.ID_TIPO_USUARIO = 1
        LEFT JOIN T_SEGM_PERSONA_EXTERNA PEX
          ON USU.ID_PERSONA = PEX.ID_PERSONA_EXTERNO
         AND USU.ID_TIPO_USUARIO = 2
       ORDER BY t.id_documento DESC, t.fec_creacion DESC;
    -- CLOSE OUT_CURSOR;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (28/07/2020)
  -- Descripcion:  Obtiene la secuencia de la numeración del anexo
  -- ****************************************************************************************
  FUNCTION F_SECUENCIA_ANEXO(IN_ID_DOCUMENTO IN T_VUDM_DOCUMENTO_ANEXO.ID_DOCUMENTO%TYPE)
    RETURN NUMBER AS
    v_orden NUMBER(5);
  BEGIN
    SELECT NVL(MAX(orden), 0) + 1
      INTO v_orden
      FROM t_vudm_documento_anexo
     WHERE id_documento = IN_ID_DOCUMENTO;
  
    RETURN v_orden;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (28/07/2020)
  -- Descripcion:  Insertar solicitud
  -- ****************************************************************************************
  PROCEDURE P_INSERT_DOCUMENTO(IN_ASUNTO              IN T_VUDM_DOCUMENTO.ASUNTO%TYPE,
                               IN_CODIGO_ARCHIVO      IN T_VUDM_DOCUMENTO.CODIGO_ARCHIVO%TYPE,
                               IN_FLG_ESTADO          IN T_VUDM_DOCUMENTO.FLG_ESTADO%TYPE,
                               IN_ID_ESTADO_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_ESTADO_DOCUMENTO%TYPE,
                               IN_ID_OFICINA          IN T_VUDM_DOCUMENTO.ID_OFICINA%TYPE,
                               IN_ID_TIPO_DOCUMENTO   IN T_VUDM_DOCUMENTO.ID_TIPO_DOCUMENTO%TYPE,
                               IN_NRO_DOCUMENTO       IN T_VUDM_DOCUMENTO.NRO_DOCUMENTO%TYPE,
                               IN_NRO_FOLIOS          IN T_VUDM_DOCUMENTO.NRO_FOLIOS%TYPE,
                               IN_HOJA_RUTA_ANEXO     IN T_VUDM_DOCUMENTO.HOJA_RUTA_ANEXO%TYPE,
                               IN_USU_CREACION        IN T_VUDM_DOCUMENTO.USU_CREACION%TYPE,
                               IN_ID_USUARIO          IN T_VUDM_DOCUMENTO.ID_USUARIO%TYPE,
                               IN_IP_CREACION         IN T_VUDM_DOCUMENTO.IP_CREACION%TYPE,
                               OUT_ESTADO             OUT NUMBER,
                               OUT_RETVAL             OUT NUMBER,
                               OUT_MENSAJE            OUT VARCHAR2) IS
    P_ID_DOCUMENTO INT := 0;
  BEGIN
    OUT_RETVAL  := 0;
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    SELECT sec_id_documento.NEXTVAL INTO P_ID_DOCUMENTO FROM DUAL;
  
    INSERT INTO t_vudm_documento
      (id_documento,
       asunto,
       codigo_archivo,
       fec_creacion,
       flg_estado,
       id_estado_documento,
       id_oficina,
       id_tipo_documento,
       nro_documento,
       nro_folios,
       HOJA_RUTA_ANEXO,
       usu_creacion,
       id_usuario,
       IP_CREACION)
    VALUES
      (P_ID_DOCUMENTO,
       IN_ASUNTO,
       IN_CODIGO_ARCHIVO,
       SYSDATE,
       IN_FLG_ESTADO,
       IN_ID_ESTADO_DOCUMENTO,
       IN_ID_OFICINA,
       IN_ID_TIPO_DOCUMENTO,
       IN_NRO_DOCUMENTO,
       IN_NRO_FOLIOS,
       IN_HOJA_RUTA_ANEXO,
       IN_USU_CREACION,
       IN_ID_USUARIO,
       IN_IP_CREACION);
  
    /* PCK_LGJ_MANTENIMIENTO.USP_CASILLA_INSERTAR (
       PI_ID_USUARIO     => IN_ID_USUARIO,
       PI_ID_DOCUMENTO   => P_ID_DOCUMENTO,
       PI_OBSERVACION    => 'Solicitud enviada correctamente',
       PI_USU_CREACION   => IN_USU_CREACION,
       PI_IP_CREACION    => '10.02.2.2'
    );*/
  
    /* INSERT INTO SISVENVI.T_VEND_CASILLA
      (ID_CASILLA,
       ID_USUARIO,
       ID_DOCUMENTO,
       OBSERVACION,
       USU_CREACION,
       ID_ESTADO,
       FEC_NOTIFICACION )
    values
      (SEQ_ID_CASILLA.nextval,
      IN_ID_USUARIO,
      P_ID_DOCUMENTO,
      'Solicitud enviada correctamente',
      IN_USU_CREACION,
      0,
       SYSDATE );*/
  
    IF SQL%ROWCOUNT > 0 THEN
      OUT_ESTADO := 100;
      SELECT sec_id_documento.CURRVAL INTO OUT_RETVAL FROM DUAL;
      COMMIT;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (28/07/2020)
  -- Descripcion:  Insertar anexos del documento
  -- ****************************************************************************************
  PROCEDURE P_INSERT_DOC_ANEXO(IN_ID_DOCUMENTO      IN T_VUDM_DOCUMENTO_ANEXO.ID_DOCUMENTO%TYPE,
                               IN_CODIGO_ARCHIVO    IN T_VUDM_DOCUMENTO_ANEXO.CODIGO_ARCHIVO%TYPE,
                               IN_NOMBRE_ARCHIVO    IN T_VUDM_DOCUMENTO_ANEXO.NOMBRE_ARCHIVO%TYPE,
                               IN_EXTENSION_ARCHIVO IN T_VUDM_DOCUMENTO_ANEXO.EXTENSION_ARCHIVO%TYPE,
                               IN_TAMANIO_ARCHIVO   IN T_VUDM_DOCUMENTO_ANEXO.TAMANIO_ARCHIVO%TYPE,
                               IN_MIMETYPE_ARCHIVO  IN T_VUDM_DOCUMENTO_ANEXO.MIMETYPE_ARCHIVO%TYPE,
                               IN_USU_CREACION      IN T_VUDM_DOCUMENTO_ANEXO.USU_CREACION%TYPE,
                               IN_FLG_LINK          IN T_VUDM_DOCUMENTO_ANEXO.FLG_LINK%TYPE,
                               IN_FLG_CREA_MPI      IN T_VUDM_DOCUMENTO_ANEXO.FLG_CREA_MPI%TYPE,
                               OUT_ESTADO           OUT NUMBER,
                               OUT_MENSAJE          OUT VARCHAR2) IS
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    INSERT INTO t_vudm_documento_anexo
      (id_documento,
       codigo_archivo,
       nombre_archivo,
       extension_archivo,
       tamanio_archivo,
       MIMETYPE_ARCHIVO,
       orden,
       usu_creacion,
       flg_link,
       flg_crea_mpi,
       estado)
    VALUES
      (IN_ID_DOCUMENTO,
       IN_CODIGO_ARCHIVO,
       IN_NOMBRE_ARCHIVO,
       IN_EXTENSION_ARCHIVO,
       IN_TAMANIO_ARCHIVO,
       IN_MIMETYPE_ARCHIVO,
       F_SECUENCIA_ANEXO(IN_ID_DOCUMENTO),
       IN_USU_CREACION,
       RTRIM(IN_FLG_LINK),
       NVL(TRIM(IN_FLG_CREA_MPI), 0),
       1);
  
    IF SQL%ROWCOUNT > 0 THEN
      OUT_ESTADO := 100;
      COMMIT;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (29/07/2020)
  -- Descripcion:  Obtener documento por id
  -- ****************************************************************************************
  PROCEDURE P_OBTENER_DOCUMENTO(IN_ID_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                                OUT_CURSOR      IN OUT SYS_REFCURSOR) AS
  BEGIN
    OPEN OUT_CURSOR FOR
      SELECT t.id_documento,
             REPLACE(translate(CASE WHEN length(t.asunto) < 12 THEN 'Asunto del documento: '|| t.asunto ELSE t.asunto END, chr(10) || chr(13) || chr(09), ' '),' ',' ')asunto,
             t.codigo_archivo,
             TO_CHAR(t.fec_creacion, 'DD/MM/YYYY hh:mi:ss a.m.') fec_creacion,
             TO_CHAR(t.fec_modificacion, 'DD/MM/YYYY hh:mi:ss a.m.') fec_modificacion,
             TO_CHAR(t.fec_recibido, 'DD/MM/YYYY hh:mi:ss a.m.') fec_recibido,
             t.flg_estado,
             t.id_estado_documento,
             t.id_oficina,
             t.id_tipo_documento,
             t.id_tipo_usuario,
             t.id_usuario,
             t.ip_creacion,
             t.ip_modificacion,
             t.nro_documento,
             t.nro_folios,
             t.usu_creacion,
             t.usu_modificacion,
             t.anio,
             t.numero_sid,
             NVL(T.HOJA_RUTA_ANEXO, '') HOJA_RUTA_ANEXO,
             CASE
               WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                UPPER(per.NOM_PERSONA) || ' ' || UPPER(per.APE_PAT_PERSONA) || ' ' ||
                UPPER(per.APE_MAT_PERSONA)
               ELSE
                CASE
                  WHEN PEX.ID_TIPO_PERSONA = 1 THEN
                   UPPER(PEX.NOMBRES) || ' ' || UPPER(PEX.APELLIDO_PATERNO) || ' ' ||
                   UPPER(PEX.APELLIDO_MATERNO)
                  ELSE
                   UPPER(PEX.RAZON_SOCIAL)
                END
             END NOM_PERSONA,
             CASE
               WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                UPPER(per.NOM_PERSONA) || ' ' || UPPER(per.APE_PAT_PERSONA) || ' ' ||
                UPPER(per.APE_MAT_PERSONA)
               ELSE
                CASE
                 WHEN PEX.ID_TIPO_PERSONA = 1
                 THEN
                    TO_CHAR (CASE WHEN length(PEX.DNI) < 8 THEN LPAD(PEX.DNI, 8, '0') ELSE PEX.DNI END)
                 ELSE
                    TO_CHAR (CASE WHEN length(PEX.RUC) < 11 THEN LPAD(PEX.RUC, 11, '0') ELSE PEX.RUC END)
                  --WHEN PEX.ID_TIPO_PERSONA = 1 THEN
                   --UPPER(PEX.DNI)
                  --ELSE
                   --UPPER(PEX.RUC)
                END
             END NRODOCUMENTO_PERSONA,
             PEX.ID_TIPO_PERSONA,
             CASE
               WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                PER.ID_PERSONA
               ELSE
                PEX.ID_PERSONA_EXTERNO
             END ID_PERSONA,
             TD.DESCRIPCION DESC_TIPO_DOC,
             O.DESCRIPCION,
             t.usu_asignado,
             t.hoja_ruta
        FROM t_vudm_documento t
       INNER JOIN T_VENL_OFICINA O
          ON O.IDUNIDAD = T.ID_OFICINA
       INNER JOIN T_VENL_TIPO_DOC TD
          ON TD.ID_TIPO_DOC = T.ID_TIPO_DOCUMENTO
       INNER JOIN T_SEGM_USUARIO U
          ON U.ID_USUARIO = T.ID_USUARIO
        LEFT JOIN T_SEGM_PERSONAL PER
          ON per.ID_PERSONA = U.ID_PERSONA
         AND U.ID_TIPO_USUARIO = 1
        LEFT JOIN T_SEGM_PERSONA_EXTERNA PEX
          ON PEX.ID_PERSONA_EXTERNO = U.ID_PERSONA
         AND U.ID_TIPO_USUARIO = 2
       WHERE id_documento = IN_ID_DOCUMENTO;
    --CLOSE OUT_CURSOR;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (29/07/2020)
  -- Descripcion:  Listar anexos del documento por el id
  -- ****************************************************************************************
  PROCEDURE P_LISTAR_DOCUMENTO_ANEXOS(IN_ID_DOCUMENTO IN T_VUDM_DOCUMENTO_ANEXO.ID_DOCUMENTO%TYPE,
                                      OUT_CURSOR      IN OUT SYS_REFCURSOR) AS
  BEGIN
    OPEN OUT_CURSOR FOR
      SELECT t.id_documento,
             t.codigo_archivo,
             t.nombre_archivo,
             t.extension_archivo,
             t.tamanio_archivo,
             T.MIMETYPE_ARCHIVO,
             t.orden,
             TO_CHAR(t.fec_creacion, 'DD/MM/YYYY hh:mi:ss a.m.') fec_creacion,
             t.fec_modificacion,
             t.usu_creacion,
             t.usu_modificacion,
             RTRIM(t.flg_link) flg_link,
             NVL(t.flg_crea_mpi, 0),
             estado,
             t.ESTADO_ANEXO,
             t.ID_ANEXO
        FROM t_vudm_documento_anexo t
       WHERE id_documento = IN_ID_DOCUMENTO
        -- AND estado = 1
       ORDER BY orden;
    --CLOSE OUT_CURSOR;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (29/07/2020)
  -- Descripcion:  Recepcionar documento
  -- ****************************************************************************************
  PROCEDURE P_RECEPCIONAR_DOCUMENTO(IN_ID_DOCUMENTO      IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                                    IN_ANIO              IN T_VUDM_DOCUMENTO.ANIO%TYPE,
                                    IN_NUMERO_SID        IN T_VUDM_DOCUMENTO.NUMERO_SID%TYPE,
                                    IN_ASUNTO            IN T_VUDM_DOCUMENTO.ASUNTO%TYPE,
                                    IN_NRO_FOLIOS        IN T_VUDM_DOCUMENTO.NRO_FOLIOS%TYPE,
                                    IN_ID_OFICINA        IN T_VUDM_DOCUMENTO.ID_OFICINA%TYPE,
                                    IN_ID_TIPO_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_TIPO_DOCUMENTO%TYPE,
                                    IN_HOJA_RUTA_ANEXO   IN T_VUDM_DOCUMENTO.HOJA_RUTA_ANEXO %TYPE,
                                    IN_NRO_DOCUMENTO     IN T_VUDM_DOCUMENTO.NRO_DOCUMENTO %TYPE,
                                    IN_USU_MODIFICACION  IN T_VUDM_DOCUMENTO.USU_MODIFICACION%TYPE,
                                    IN_IP_MODIFICACION   IN T_VUDM_DOCUMENTO.IP_MODIFICACION%TYPE,
                                    IN_ID_PRIORIDAD      IN T_VUDM_DOCUMENTO.ID_PRIORIDAD %TYPE,
                                    IN_CONGRESISTA       IN T_VUDM_DOCUMENTO.CONGRESISTA %TYPE,
                                    
                                    OUT_ESTADO  OUT NUMBER,
                                    OUT_MENSAJE OUT VARCHAR2) AS
    P_ID_USUARIO INT := 0;
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    SELECT ID_USUARIO
      INTO P_ID_USUARIO
      FROM t_vudm_documento
     WHERE ID_DOCUMENTO = IN_ID_DOCUMENTO;
  
    UPDATE t_vudm_documento d
       SET d.id_estado_documento = 3,
           d.fec_recibido        = SYSDATE,
           d.fec_modificacion    = SYSDATE,
           d.anio                = IN_ANIO,
           d.numero_sid          = IN_NUMERO_SID,
           d.usu_modificacion    = IN_USU_MODIFICACION,
           ASUNTO                = IN_ASUNTO,
           NRO_FOLIOS            = IN_NRO_FOLIOS,
           IP_MODIFICACION       = IN_IP_MODIFICACION,
           d.id_oficina          = IN_ID_OFICINA,
           D.ID_TIPO_DOCUMENTO   = IN_ID_TIPO_DOCUMENTO,
           D.HOJA_RUTA_ANEXO     = IN_HOJA_RUTA_ANEXO,
           D.NRO_DOCUMENTO       = IN_NRO_DOCUMENTO,
           D.CONGRESISTA         = IN_CONGRESISTA,
           D.ID_PRIORIDAD        = IN_ID_PRIORIDAD
    
     WHERE id_documento = IN_ID_DOCUMENTO;
  
    /* PCK_LGJ_MANTENIMIENTO.USP_CASILLA_INSERTAR (
       PI_ID_USUARIO     => P_ID_USUARIO,
       PI_ID_DOCUMENTO   => IN_ID_DOCUMENTO,
       PI_OBSERVACION    => 'Su solicitud ha validada y recepcionada correctamente',
       PI_USU_CREACION   => IN_USU_MODIFICACION,
       PI_IP_CREACION    => '10.02.2.2'
    );*/
  
    IF SQL%ROWCOUNT > 0 THEN
      OUT_ESTADO := 100;
      COMMIT;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (28/07/2020)
  -- Descripcion:  Insertar observación del documento
  -- ****************************************************************************************
  PROCEDURE P_INSERT_DOC_OBSERVACION(IN_ID_DOCUMENTO    IN T_VUDM_DOCUMENTO_OBS.ID_DOCUMENTO%TYPE,
                                     IN_OBSERVACION     IN T_VUDM_DOCUMENTO_OBS.OBSERVACION%TYPE,
                                     IN_FECHA_ANULACION IN T_VUDM_DOCUMENTO_OBS.OBSERVACION%TYPE,
                                     IN_USU_CREACION    IN T_VUDM_DOCUMENTO_OBS.USU_CREACION%TYPE,
                                     OUT_ESTADO         OUT NUMBER,
                                     OUT_MENSAJE        OUT VARCHAR2) IS
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    UPDATE t_vudm_documento_obs
       SET flg_estado = 0, flg_ultreg = 0
     WHERE id_documento = IN_ID_DOCUMENTO;
  
    INSERT INTO t_vudm_documento_obs
      (id_documento,
       observacion,
       FECHA_ANULACION,
       flg_ultreg,
       usu_creacion)
    VALUES
      (IN_ID_DOCUMENTO,
       IN_OBSERVACION,
       TO_DATE(IN_FECHA_ANULACION --|| ' ' || TO_CHAR(SYSDATE, 'HH24:MI:SS'),
               ,'DD/MM/YYYY HH24:MI:SS'),
       1,
       IN_USU_CREACION);
  
    UPDATE t_vudm_documento d
       SET d.id_estado_documento = 2,
           d.fec_modificacion    = SYSDATE,
           d.usu_modificacion    = IN_USU_CREACION,
           d.usu_asignado        = NULL
     WHERE id_documento = IN_ID_DOCUMENTO;
  
    COMMIT;
    OUT_ESTADO := 100;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (28/07/2020)
  -- Descripcion:  Actualizar solicitud
  -- ****************************************************************************************
  PROCEDURE P_UPDATE_DOCUMENTO(IN_ID_DOCUMENTO        IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                               IN_ASUNTO              IN T_VUDM_DOCUMENTO.ASUNTO%TYPE,
                               IN_CODIGO_ARCHIVO      IN T_VUDM_DOCUMENTO.CODIGO_ARCHIVO%TYPE,
                               IN_FLG_ESTADO          IN T_VUDM_DOCUMENTO.FLG_ESTADO%TYPE,
                               IN_ID_ESTADO_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_ESTADO_DOCUMENTO%TYPE,
                               IN_ID_OFICINA          IN T_VUDM_DOCUMENTO.ID_OFICINA%TYPE,
                               IN_ID_TIPO_DOCUMENTO   IN T_VUDM_DOCUMENTO.ID_TIPO_DOCUMENTO%TYPE,
                               IN_NRO_DOCUMENTO       IN T_VUDM_DOCUMENTO.NRO_DOCUMENTO%TYPE,
                               IN_NRO_FOLIOS          IN T_VUDM_DOCUMENTO.NRO_FOLIOS%TYPE,
                               IN_HOJA_RUTA_ANEXO     IN T_VUDM_DOCUMENTO.HOJA_RUTA_ANEXO%TYPE,
                               IN_USU_MODIFICACION    IN T_VUDM_DOCUMENTO.USU_MODIFICACION%TYPE,
                               OUT_ESTADO             OUT NUMBER,
                               OUT_MENSAJE            OUT VARCHAR2) IS
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    UPDATE t_vudm_documento
       SET asunto              = IN_ASUNTO,
           codigo_archivo      = IN_CODIGO_ARCHIVO,
           fec_modificacion    = SYSDATE,
           flg_estado          = IN_FLG_ESTADO,
           id_estado_documento = IN_ID_ESTADO_DOCUMENTO,
           id_oficina          = IN_ID_OFICINA,
           id_tipo_documento   = IN_ID_TIPO_DOCUMENTO,
           nro_documento       = IN_NRO_DOCUMENTO,
           nro_folios          = IN_NRO_FOLIOS,
           HOJA_RUTA_ANEXO     = IN_HOJA_RUTA_ANEXO,
           usu_modificacion    = IN_USU_MODIFICACION
     WHERE id_documento = IN_ID_DOCUMENTO;
  
    IF IN_ID_ESTADO_DOCUMENTO = 4 THEN
      UPDATE T_VUDM_DOCUMENTO_OBS
         SET FEC_SUBSANACION = SYSDATE, FLG_ESTADO = '0'
       WHERE ID_DOCUMENTO = IN_ID_DOCUMENTO
         AND FLG_ESTADO = '1';
    END IF;
  
    IF SQL%ROWCOUNT > 0 THEN
      OUT_ESTADO := 100;
      COMMIT;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (29/07/2020)
  -- Descripcion:  Actualiza el estado de los anexos a inactivo
  -- ****************************************************************************************
  PROCEDURE P_ANULAR_ANEXOS(IN_ID_DOCUMENTO     IN T_VUDM_DOCUMENTO_ANEXO.ID_DOCUMENTO%TYPE,
                            IN_CODIGO_ARCHIVO   IN VARCHAR2,
                            IN_USU_MODIFICACION IN T_VUDM_DOCUMENTO_ANEXO.USU_MODIFICACION%TYPE,
                            OUT_ESTADO          OUT NUMBER,
                            OUT_MENSAJE         OUT VARCHAR2) IS
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    IF IN_CODIGO_ARCHIVO IS NULL THEN
      UPDATE t_vudm_documento_anexo t
         SET estado           = 0,
             fec_modificacion = SYSDATE,
             usu_modificacion = IN_USU_MODIFICACION
       WHERE id_documento = IN_ID_DOCUMENTO;
    ELSE
      UPDATE t_vudm_documento_anexo t
         SET estado           = 0,
             fec_modificacion = SYSDATE,
             usu_modificacion = IN_USU_MODIFICACION
       WHERE id_documento = IN_ID_DOCUMENTO
         AND codigo_archivo NOT IN
             (SELECT * FROM TABLE(pack_lgj_utils.f_split(IN_CODIGO_ARCHIVO)));
    END IF;
  
    UPDATE t_vudm_documento_anexo t
       SET estado           = 0,
           fec_modificacion = SYSDATE,
           usu_modificacion = IN_USU_MODIFICACION
     WHERE id_documento = IN_ID_DOCUMENTO
       AND codigo_archivo NOT IN
           (SELECT * FROM TABLE(pack_lgj_utils.f_split(IN_CODIGO_ARCHIVO)));
  
    IF SQL%ROWCOUNT > 0 THEN
      OUT_ESTADO := 100;
      COMMIT;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (29/07/2020)
  -- Descripcion:  Listar observaciones del documento por el id
  -- ****************************************************************************************
  PROCEDURE P_LISTAR_DOCUMENTO_OBS(IN_ID_DOCUMENTO IN T_VUDM_DOCUMENTO_ANEXO.ID_DOCUMENTO%TYPE,
                                   OUT_CURSOR      IN OUT SYS_REFCURSOR) AS
  BEGIN
    OPEN OUT_CURSOR FOR
      SELECT c.id_documento,
             c.observacion,
             TO_CHAR(c.fec_creacion, 'DD/MM/YYYY hh:mi:ss a.m.') fec_creacion,
             TO_CHAR(c.fec_modificacion, 'DD/MM/YYYY hh:mi:ss a.m.') fec_modificacion,
             c.usu_creacion,
             c.usu_modificacion
        FROM t_vudm_documento_obs c
       WHERE c.id_documento = IN_ID_DOCUMENTO
       ORDER BY c.fec_creacion;
    --CLOSE OUT_CURSOR;
  END;

  PROCEDURE P_LISTAR_DOCUMENTOS_PAGINADO(IN_ID_ESTADO_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_ESTADO_DOCUMENTO%TYPE,
                                         IN_ID_DOCUMENTO        IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                                         IN_ID_OFICINA          IN T_VUDM_DOCUMENTO.ID_OFICINA%TYPE,
                                         IN_ID_TIPO_DOCUMENTO   IN T_VUDM_DOCUMENTO.ID_TIPO_DOCUMENTO%TYPE,
                                         IN_NRO_DOCUMENTO       IN T_VUDM_DOCUMENTO.NRO_DOCUMENTO%TYPE,
                                         IN_ASUNTO              IN T_VUDM_DOCUMENTO.ASUNTO%TYPE,
                                         IN_RAZONSOCIAL         IN T_VUDM_DOCUMENTO.ASUNTO%TYPE,
                                         IN_DESTINO             IN T_VUDM_DOCUMENTO.ASUNTO%TYPE,
                                         IN_FEC_INICIO          IN VARCHAR2,
                                         IN_FEC_FIN             IN VARCHAR2,
                                         IN_TAB_DOC             IN VARCHAR2,
                                         IN_ID_USUARIO          IN T_VUDM_DOCUMENTO.ID_USUARIO%TYPE,
                                         IN_ORDEN               IN VARCHAR2,
                                         IN_NUMPAG              IN INTEGER,
                                         IN_NUMREG              IN INTEGER,
                                         OUT_CURSOR             IN OUT SYS_REFCURSOR) AS
    lvcSQL  VARCHAR2(4000);
    v_start NUMBER;
    v_end   NUMBER;
  BEGIN
    lvcSQL := Q'[SELECT T.*,COUNT(1) OVER() AS TOTALREG,ROW_NUMBER() OVER(ORDER BY {ORDEN}) NROREG FROM V_VUDM_DOCUMENTO T WHERE 1=1]';
  
    IF IN_ORDEN IS NOT NULL THEN
      lvcSQL := REPLACE(lvcSQL, '{ORDEN}', IN_ORDEN);
    ELSIF IN_TAB_DOC = 'TAB_PENDIENTE' THEN
      lvcSQL := REPLACE(lvcSQL,
                        '{ORDEN}',
                        'DECODE(T.FEC_SUBSANACION,NULL,T.FEC_CREACION,T.FEC_SUBSANACION) DESC,T.ID_DOCUMENTO DESC');
    ELSIF IN_TAB_DOC = 'TAB_OBSERVADO' THEN
      lvcSQL := REPLACE(lvcSQL,
                        '{ORDEN}',
                        'DECODE(T.FEC_OBSERVACION,NULL,T.FEC_CREACION,T.FEC_OBSERVACION) DESC,T.ID_DOCUMENTO DESC');
    ELSE
      lvcSQL := REPLACE(lvcSQL,
                        '{ORDEN}',
                        'T.ID_DOCUMENTO DESC, T.FEC_CREACION DESC');
    END IF;
  
    IF IN_ID_USUARIO IS NOT NULL THEN
      lvcSQL := lvcSQL || Q'[ AND T.ID_USUARIO = :IN_ID_USUARIO ]';
    ELSE
      lvcSQL := lvcSQL || Q'[ AND ((1=1) OR :IN_ID_USUARIO  IS NULL) ]';
    END IF;
  
    IF NVL(IN_ID_ESTADO_DOCUMENTO, 0) <> 0 THEN
      lvcSQL := lvcSQL ||
                Q'[ AND T.ID_ESTADO_DOCUMENTO = :IN_ID_ESTADO_DOCUMENTO ]';
    ELSE
      lvcSQL := lvcSQL ||
                Q'[ AND ((1=1) OR :IN_ID_ESTADO_DOCUMENTO IS NULL) ]';
    END IF;
  
    IF NVL(IN_ID_DOCUMENTO, 0) <> 0 THEN
      lvcSQL := lvcSQL || Q'[ AND ID_DOCUMENTO = :IN_ID_DOCUMENTO ]';
    ELSE
      lvcSQL := lvcSQL || Q'[ AND ((1=1) OR :IN_ID_DOCUMENTO IS NULL) ]';
    END IF;
  
    IF NVL(IN_ID_OFICINA, 0) <> 0 THEN
      lvcSQL := lvcSQL || Q'[ AND ID_OFICINA = :IN_ID_OFICINA ]';
    ELSE
      lvcSQL := lvcSQL || Q'[ AND ((1=1) OR :IN_ID_OFICINA IS NULL) ]';
    END IF;
  
    IF IN_ID_TIPO_DOCUMENTO IS NOT NULL THEN
      lvcSQL := lvcSQL ||
                Q'[ AND T.ID_TIPO_DOCUMENTO = :IN_ID_TIPO_DOCUMENTO ]';
    ELSE
      lvcSQL := lvcSQL ||
                Q'[ AND ((1=1) OR :IN_ID_TIPO_DOCUMENTO IS NULL) ]';
    END IF;
  
    IF IN_NRO_DOCUMENTO IS NOT NULL THEN
      lvcSQL := lvcSQL ||
                Q'[ AND UPPER(NRO_DOCUMENTO) LIKE UPPER(:IN_NRO_DOCUMENTO) ]';
    ELSE
      lvcSQL := lvcSQL || Q'[ AND ((1=1) OR :IN_NRO_DOCUMENTO IS NULL) ]';
    END IF;
  
    IF IN_ASUNTO IS NOT NULL THEN
      lvcSQL := lvcSQL || Q'[ AND UPPER(ASUNTO) LIKE UPPER(:IN_ASUNTO) ]';
    ELSE
      lvcSQL := lvcSQL || Q'[ AND ((1=1) OR :IN_ASUNTO IS NULL) ]';
    END IF;
  
    IF IN_RAZONSOCIAL IS NOT NULL THEN
      lvcSQL := lvcSQL ||
                Q'[ AND UPPER(NOM_PERSONA) LIKE UPPER(:IN_RAZONSOCIAL) ]';
    ELSE
      lvcSQL := lvcSQL || Q'[ AND ((1=1) OR :IN_RAZONSOCIAL IS NULL) ]';
    END IF;
  
    IF IN_DESTINO IS NOT NULL THEN
      lvcSQL := lvcSQL ||
                Q'[ AND UPPER(DESC_OFICINA) LIKE UPPER(:IN_DESTINO) ]';
    ELSE
      lvcSQL := lvcSQL || Q'[ AND ((1=1) OR :IN_DESTINO IS NULL) ]';
    END IF;
  
    IF IN_FEC_INICIO IS NOT NULL THEN
      lvcSQL := lvcSQL ||
                Q'[ AND TO_NUMBER(TO_CHAR(T.FEC_CREACION, 'YYYYMMDD')) >= TO_NUMBER(TO_CHAR(TO_DATE(:IN_FEC_INICIO,'DD/MM/YYYY'), 'YYYYMMDD')) ]';
    ELSE
      lvcSQL := lvcSQL || Q'[ AND ((1=1) OR :IN_FEC_INICIO IS NULL) ]';
    END IF;
  
    IF IN_FEC_FIN IS NOT NULL THEN
      lvcSQL := lvcSQL ||
                Q'[ AND TO_NUMBER(TO_CHAR(T.FEC_CREACION, 'YYYYMMDD')) <= TO_NUMBER(TO_CHAR(TO_DATE(:IN_FEC_FIN,'DD/MM/YYYY'), 'YYYYMMDD')) ]';
    ELSE
      lvcSQL := lvcSQL || Q'[ AND ((1=1) OR :IN_FEC_FIN IS NULL) ]';
    END IF;
  
    IF IN_TAB_DOC IS NOT NULL THEN
      lvcSQL := lvcSQL || Q'[ AND T.GRUPO_ESTADOS = :IN_TAB_DOC ]';
    ELSE
      lvcSQL := lvcSQL || Q'[ AND ((1=1) OR :IN_TAB_DOC IS NULL) ]';
    END IF;
  
    -- Calculo de p¿ginas
    v_start := (((IN_NUMPAG - 1) * IN_NUMREG) + 1);
    v_end   := (IN_NUMPAG * IN_NUMREG);
  
    lvcSQL := Q'[SELECT {CAMPOS} FROM (]' || lvcSQL ||
              Q'[) X WHERE X.NROREG BETWEEN :IN_PAGE_START AND :IN_PAGE_END ]';
  
    lvcSQL := REPLACE(lvcSQL,
                      '{CAMPOS}',
                      Q'[X.ID_DOCUMENTO,
                        X.ASUNTO,
                        X.CODIGO_ARCHIVO,
                        X.FEC_CREACION_STR AS FEC_CREACION,
                        X.FEC_MODIFICACION,
                        X.FEC_RECIBIDO_STR AS FEC_RECIBIDO,
                        X.FLG_ESTADO,
                        X.ID_ESTADO_DOCUMENTO,
                        X.DESC_ESTADO,
                        X.ID_OFICINA,
                        X.DESC_OFICINA,
                        X.ID_TIPO_DOCUMENTO,
                        X.DESC_TIPO,
                        X.ID_TIPO_USUARIO,
                        X.ID_USUARIO,
                        X.IP_CREACION,
                        X.IP_MODIFICACION,
                        X.NRO_DOCUMENTO,
                        X.NRO_FOLIOS,
                        X.USU_CREACION,
                        X.USU_MODIFICACION,
                        X.ANEXOS,
                        X.GRUPO_ESTADOS,
                        X.ANIO,
                        X.NUMERO_SID,
                        X.NOM_PERSONA,
                        X.TOTALREG,
                        X.HOJA_RUTA_ANEXO,
                        X.FECHA_ANULACION,                    
                        X.NROREG, 
                        X.FECHA_OBSERVACION,
                        X.FECHA_SUBSANACION,
                        X.USU_ASIGNADO,
                        X.NOMB_USU_ASIGNADO,
						X.HOJA_RUTA,
                        X.OBS_SGDD]');
    OPEN OUT_CURSOR FOR lvcSQL
      USING IN_ID_USUARIO, IN_ID_ESTADO_DOCUMENTO, IN_ID_DOCUMENTO, IN_ID_OFICINA, IN_ID_TIPO_DOCUMENTO, '%' || IN_NRO_DOCUMENTO || '%', '%' || IN_ASUNTO || '%', '%' || IN_RAZONSOCIAL || '%', '%' || IN_DESTINO || '%', IN_FEC_INICIO, IN_FEC_FIN, IN_TAB_DOC, v_start, v_end;
  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20007,
                              'Error buscando paginado..' || SQLERRM);
  END;

  PROCEDURE P_ESTADO_DOCUMENTO(IN_NUMERO_SID       IN T_VUDM_DOCUMENTO.NUMERO_SID%TYPE,
                               IN_ANIO             IN T_VUDM_DOCUMENTO.ANIO%TYPE,
                               IN_USU_MODIFICACION IN T_VUDM_DOCUMENTO.USU_MODIFICACION%TYPE,
                               PO_CUENTA           OUT INT,
                               PO_MENSAJE          OUT VARCHAR2) IS
  BEGIN
    PO_CUENTA  := 0;
    PO_MENSAJE := 'No se encontro la solicitud';
  
    SELECT COUNT(*)
      INTO PO_CUENTA
      FROM t_vudm_documento
     WHERE NUMERO_SID = IN_NUMERO_SID
       AND ANIO = IN_ANIO;
  
    IF PO_CUENTA != 0 THEN
      PO_MENSAJE := '';
      SELECT ID_DOCUMENTO
        INTO PO_CUENTA
        FROM t_vudm_documento
       WHERE NUMERO_SID = IN_NUMERO_SID
         AND ANIO = IN_ANIO
         AND ROWNUM = 1
       ORDER BY ID_DOCUMENTO ASC;
      UPDATE t_vudm_documento
         SET ID_ESTADO_DOCUMENTO = 5,
             USU_MODIFICACION    = IN_USU_MODIFICACION,
             FEC_MODIFICACION    = SYSDATE
       WHERE NUMERO_SID = IN_NUMERO_SID
         AND ANIO = IN_ANIO;
    END IF;
    /* OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
    
    
    
    IF SQL%ROWCOUNT > 0 THEN
      OUT_ESTADO := 100;
      COMMIT;
    END IF;*/
    /*EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;*/
  END;

  PROCEDURE P_HOJARUTA_DOCUMENTO(IN_NUMERO_SID IN T_VUDM_DOCUMENTO.NUMERO_SID%TYPE,
                                 IN_ANIO       IN T_VUDM_DOCUMENTO.ANIO%TYPE,
                                 PO_CUENTA     OUT INT,
                                 PO_MENSAJE    OUT VARCHAR2) IS
  BEGIN
    PO_CUENTA  := 0;
    PO_MENSAJE := 'No se encontro la solicitud';
  
    SELECT COUNT(*)
      INTO PO_CUENTA
      FROM t_vudm_documento
     WHERE NUMERO_SID = IN_NUMERO_SID
       AND ANIO = IN_ANIO;
  
    IF PO_CUENTA != 0 THEN
      PO_MENSAJE := '';
      SELECT ID_DOCUMENTO
        INTO PO_CUENTA
        FROM t_vudm_documento
       WHERE NUMERO_SID = IN_NUMERO_SID
         AND ANIO = IN_ANIO
         AND ROWNUM = 1
       ORDER BY ID_DOCUMENTO ASC;
    END IF;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (28/07/2020)
  -- Descripcion:  Insertar observación del documento
  -- ****************************************************************************************
  PROCEDURE P_UPDATE_ESTADO_DOC(IN_ID_DOCUMENTO IN T_VUDM_DOCUMENTO_OBS.ID_DOCUMENTO%TYPE,
                                IN_FLG_ESTADO   IN T_VUDM_DOCUMENTO_OBS.ID_DOCUMENTO%TYPE,
                                OUT_ESTADO      OUT NUMBER,
                                OUT_MENSAJE     OUT VARCHAR2) IS
  
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    UPDATE t_vudm_documento d
       SET d.Id_Estado_Documento = IN_FLG_ESTADO
     WHERE id_documento = IN_ID_DOCUMENTO;
  
    COMMIT;
    OUT_ESTADO := 100;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;

  PROCEDURE P_ASIGNAR_DOCUMENTO(IN_ID_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                                IN_USU_ASIGNADO IN T_VUDM_DOCUMENTO.USU_ASIGNADO%TYPE,
                                OUT_ESTADO      OUT NUMBER,
                                OUT_MENSAJE     OUT VARCHAR2) IS
  
    v_asignar_usu T_VUDM_DOCUMENTO.USU_ASIGNADO%TYPE;
    v_nomb_usu    VARCHAR2(300);
  BEGIN
    OUT_ESTADO := 1;
    SELECT do.usu_asignado,
           pe.nom_persona || ' ' || pe.ape_pat_persona || ' ' ||
           pe.ape_mat_persona
      INTO v_asignar_usu, v_nomb_usu
      FROM T_VUDM_DOCUMENTO do
      LEFT JOIN T_SEGM_USUARIO us
        ON do.usu_asignado = us.cod_usuario
      LEFT JOIN t_segm_personal PE
        ON us.id_persona = pe.id_persona
     WHERE ID_DOCUMENTO = IN_ID_DOCUMENTO;
  
    IF v_asignar_usu IS NULL THEN
      UPDATE t_vudm_documento
         SET usu_asignado = IN_USU_ASIGNADO
       WHERE id_documento = IN_ID_DOCUMENTO;
    
      IF SQL%ROWCOUNT = 0 THEN
        OUT_ESTADO  := 0;
        OUT_MENSAJE := 'No se pudo realizar la autoasignación';
      ELSE
        COMMIT;
      END IF;
    
    ELSE
      OUT_ESTADO := 0;
    
      IF v_asignar_usu <> IN_USU_ASIGNADO THEN
        OUT_MENSAJE := 'La solicitud N° ' || IN_ID_DOCUMENTO ||
                       ' esta autoasignada a ' || v_nomb_usu;
      END IF;
    END IF;
  END;

  PROCEDURE P_RECEP_Y_OBSERVAR(IN_ID_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                               IN_USU_ASIGNADO IN T_VUDM_DOCUMENTO.USU_ASIGNADO%TYPE,
                               OUT_ESTADO      OUT NUMBER,
                               OUT_MENSAJE     OUT VARCHAR2) IS
  
    v_existe      NUMBER(3) := 0;
    v_asignar_usu T_VUDM_DOCUMENTO.USU_ASIGNADO%TYPE;
    v_nomb_usu    VARCHAR2(300);
  BEGIN
    OUT_ESTADO := 1;
  
    SELECT COUNT(1)
      INTO v_existe
      FROM T_VUDM_DOCUMENTO
     WHERE ID_DOCUMENTO = IN_ID_DOCUMENTO
       AND usu_asignado = IN_USU_ASIGNADO;
  
    IF v_existe = 0 THEN
      SELECT do.usu_asignado,
             pe.nom_persona || ' ' || pe.ape_pat_persona || ' ' ||
             pe.ape_mat_persona
        INTO v_asignar_usu, v_nomb_usu
        FROM T_VUDM_DOCUMENTO do
        LEFT JOIN T_SEGM_USUARIO us
          ON do.usu_asignado = us.cod_usuario
        LEFT JOIN t_segm_personal PE
          ON us.id_persona = pe.id_persona
       WHERE ID_DOCUMENTO = IN_ID_DOCUMENTO;
    
      OUT_ESTADO  := 0;
      OUT_MENSAJE := 'La acción no se pudo realizar, debido que la solicitud N° ' ||
                     IN_ID_DOCUMENTO || ' esta autoasignada a ' ||
                     v_nomb_usu;
    END IF;
  END;

  PROCEDURE P_ANULAR_ANEXO(IN_ID_DOCUMENTO     IN T_VUDM_DOCUMENTO_ANEXO.ID_DOCUMENTO%TYPE,
                           IN_CODIGO_ARCHIVO   IN T_VUDM_DOCUMENTO_ANEXO.CODIGO_ARCHIVO%TYPE,
                           IN_USU_MODIFICACION IN T_VUDM_DOCUMENTO_ANEXO.USU_MODIFICACION%TYPE,
                           OUT_ESTADO          OUT NUMBER,
                           OUT_MENSAJE         OUT VARCHAR2) IS
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    UPDATE t_vudm_documento_anexo t
       SET estado           = 0,
           fec_modificacion = SYSDATE,
           usu_modificacion = IN_USU_MODIFICACION
     WHERE id_documento = IN_ID_DOCUMENTO
       AND codigo_archivo = IN_CODIGO_ARCHIVO;
  
    IF SQL%ROWCOUNT > 0 THEN
      OUT_ESTADO := 100;
      COMMIT;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;

  PROCEDURE P_VAL_HOJA_RUTA_ANEXO(IN_ID_DOCUMENTO    IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                                  IN_HOJA_RUTA_ANEXO IN T_VUDM_DOCUMENTO.HOJA_RUTA_ANEXO%TYPE,
                                  OUT_ESTADO         OUT NUMBER,
                                  OUT_MENSAJE        OUT VARCHAR2) IS
  
    v_existe_hoja_ruta  NUMBER := 0;
    v_id_tipo_documento T_VUDM_DOCUMENTO.ID_TIPO_DOCUMENTO%TYPE;
    v_nro_documento     T_VUDM_DOCUMENTO.NRO_DOCUMENTO%TYPE;
  BEGIN
    OUT_ESTADO  := 100;
    OUT_MENSAJE := NULL;
  
    IF IN_HOJA_RUTA_ANEXO IS NOT NULL THEN
    
      SELECT id_tipo_documento, nro_documento
        INTO v_id_tipo_documento, v_nro_documento
        FROM t_vudm_documento
       WHERE id_documento = IN_ID_DOCUMENTO;
    
      SELECT COUNT(1)
        INTO v_existe_hoja_ruta
        FROM t_vudm_documento t
       WHERE t.hoja_ruta_anexo = IN_HOJA_RUTA_ANEXO
         AND id_tipo_documento = v_id_tipo_documento
            
         AND nro_documento = v_nro_documento
         AND t.id_documento <> IN_ID_DOCUMENTO;
    
      IF v_existe_hoja_ruta > 0 THEN
        OUT_MENSAJE := 'Se ha encontrado documentos similares al que se ha ingresado, desea continuar con el envío';
        OUT_ESTADO  := -100;
      END IF;
    END IF;
  END;

  PROCEDURE P_PERSONA_USUARIO_LISTAR(PI_ID_TIPO_USUARIO   T_SEGM_USUARIO.ID_TIPO_USUARIO%TYPE,
                                     PI_FLG_ESTADO        T_SEGM_USUARIO.FLG_ESTADO%TYPE,
                                     PI_NOM_PERSONA       T_SEGM_PERSONAL.NOM_PERSONA%TYPE,
                                     PI_NRO_DOCUMENTO     T_SEGM_PERSONAL.NUM_DOC_PERSONA%TYPE,
                                     PI_CELULAR           T_SEGM_PERSONAL.CELULAR%TYPE,
                                     PI_TELEFONO          T_SEGM_PERSONAL.TELEFONO%TYPE,
                                     PI_CORREO            T_SEGM_PERSONAL.CORREO%TYPE,
                                     PI_ID_TIPO_DOCUMENTO T_SEGM_PERSONA_EXTERNA.ID_TIPO_DOCUMENTO%TYPE,
                                     PI_FECHA_INICIO      IN VARCHAR2,
                                     PI_FECHA_FIN         IN VARCHAR2,
                                     PO_CURSOR            OUT TYPES.CURSOR_TYPE) IS
  BEGIN
  
    OPEN po_cursor FOR
      WITH MILISTA AS
       (SELECT usu.ID_USUARIO,
               usu.COD_USUARIO,
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  NVL(PER.ID_PERSONA, 0)
                 ELSE
                  NVL(PEX.ID_PERSONA_EXTERNO, 0)
               END ID_PERSONA,
               --NVL (ofi.ID_OFICINA, 0) ID_OFICINA,
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  'INTERNO'
                 ELSE
                  'EXTERNO'
               END TIPO_USUARIO,
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  'Interno'
                 ELSE
                  CASE
                    WHEN PEX.ID_TIPO_PERSONA = 1 THEN
                     'Natural'
                    ELSE
                     'Juridica'
                  END
               END TIPO_PERSONA,
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  ofi.DESCRIPCION
                 ELSE
                  'Sin oficina'
               END DESC_OFICINA,
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  per.NOM_PERSONA || ' ' || per.APE_PAT_PERSONA || ' ' ||
                  per.APE_MAT_PERSONA
                 ELSE
                  CASE
                    WHEN PEX.ID_TIPO_PERSONA = 1 THEN
                     PEX.NOMBRES || ' ' || PEX.APELLIDO_PATERNO || ' ' ||
                     PEX.APELLIDO_MATERNO
                    ELSE
                     PEX.RAZON_SOCIAL
                  END
               END NOM_PERSONA,
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  PER.NUM_DOC_PERSONA
                 ELSE
                  CASE
                    WHEN PEX.ID_TIPO_PERSONA = 1 THEN
                     TO_CHAR(PEX.DNI)
                    ELSE
                     TO_CHAR(PEX.RUC)
                  END
               END NRO_DOCUMENTO,
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  PER.CELULAR
                 ELSE
                  PEX.CELULAR
               END CELULAR,
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  PER.TELEFONO
                 ELSE
                  NVL(PEX.TELEFONO, '-')
               END TELEFONO,
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  PER.CORREO
                 ELSE
                  PEX.CORREO
               END CORREO,
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  3
                 ELSE
                  PEX.ID_TIPO_PERSONA
               END TIPO_USUPER,
               NVL(TO_CHAR(USU.FECHA_ACTIVACION, 'DD/MM/YYYY'), '-'),
               NVL(TO_CHAR(USU.FECHA_DESACTIVACION, 'DD/MM/YYYY'), '-'),
               usu.FLG_ESTADO,
               NVL(usu.USU_CREACION, 'Externo') USU_CREACION,
               TO_CHAR(usu.FEC_CREACION, 'DD/MM/YYYY hh:mi:ss a.m.') FEC_CREACION,
               usu.IP_CREACION,
               NVL(usu.USU_MODIFICACION, '-') USU_MODIFICACION,
               TO_CHAR(usu.FEC_MODIFICACION, 'DD/MM/YYYY hh:mi:ss a.m.') FEC_MODIFICACION,
               usu.IP_MODIFICACION,
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  PER.TIPO_D_DOC_PERSONA
                 ELSE
                  PEX.ID_TIPO_DOCUMENTO
               END ID_TIPO_DOCUMENTO,
               usu.FEC_CREACION DATE_FEC_CREACION,
               
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  '-'
                 ELSE
                  RE.NOMBRES || ' ' || RE.APELLIDO_PATERNO || ' ' ||
                  RE.APELLIDO_MATERNO
               END REPRESENTANTE,
               
               CASE
                 WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                  '-'
                 ELSE
                  DE.NOMBRES || ' ' || DE.APELLIDO_PATERNO || ' ' ||
                  DE.APELLIDO_MATERNO
               END DELEGADO,
               DEP.DESC_DEPARTAMENTO,
               PROV.DESC_PROVINCIA,
               DIST.DESC_DISTRITO
        
          FROM T_SEGM_USUARIO usu
          LEFT JOIN T_SEGJ_PERFIL_USUARIO usuper
            ON usuper.ID_USUARIO = usu.ID_USUARIO
          LEFT JOIN T_SEGM_PERSONAL per
            ON per.ID_PERSONA = usu.ID_PERSONA
           AND USU.ID_TIPO_USUARIO = 1
          LEFT JOIN T_SEGJ_OFICINA_PERSONA op
            ON op.ID_PERSONA = per.ID_PERSONA
          LEFT JOIN T_VENL_OFICINA ofi
            ON ofi.IDUNIDAD = op.ID_OFICINA
          LEFT JOIN T_SEGM_PERSONA_EXTERNA PEX
            ON PEX.ID_PERSONA_EXTERNO = USU.ID_PERSONA
           AND USU.ID_TIPO_USUARIO = 2
        
          LEFT JOIN T_VEND_DEPARTAMENTO DEP
            ON DEP.ID_DEPARTAMENTO = PEX.ID_DEPARTAMENTO
          LEFT JOIN T_VEND_PROVINCIA PROV
            ON PROV.ID_DEPARTAMENTO = PEX.ID_DEPARTAMENTO
           AND PROV.ID_PROVINCIA = PEX.ID_PROVINCIA
          LEFT JOIN T_VEND_DISTRITO DIST
            ON DIST.ID_DEPARTAMENTO = PEX.ID_DEPARTAMENTO
           AND DIST.ID_PROVINCIA = PEX.ID_PROVINCIA
           AND DIST.ID_DISTRITO = PEX.ID_DISTRITO
        
          LEFT JOIN T_SEGD_EXTERNO_REPRESENTANTE RE
            ON RE.ID_USUARIO_EXTERNO = USU.ID_PERSONA
           AND USU.ID_TIPO_USUARIO = 2
          LEFT JOIN T_SEGD_EXTERNO_DELEGADO DE
            ON DE.ID_USUARIO_EXTERNO = USU.ID_PERSONA
           AND USU.ID_TIPO_USUARIO = 2
         WHERE (USU.ID_TIPO_USUARIO =
               NVL(PI_ID_TIPO_USUARIO, USU.ID_TIPO_USUARIO) OR
               PI_ID_TIPO_USUARIO = 0)
           AND USU.FLG_ESTADO = NVL(PI_FLG_ESTADO, USU.FLG_ESTADO))
      
      SELECT *
        FROM (SELECT T.*,
                     COUNT(1) OVER() AS TOTALREG,
                     ROW_NUMBER() OVER(ORDER BY FEC_CREACION DESC) NROREG
                FROM MILISTA T
               WHERE UPPER(NOM_PERSONA) LIKE
                     '%' || NVL(UPPER(PI_NOM_PERSONA), UPPER(NOM_PERSONA)) || '%'
                 AND NRO_DOCUMENTO LIKE
                     '%' || NVL(PI_NRO_DOCUMENTO, NRO_DOCUMENTO) || '%'
                 AND CELULAR LIKE '%' || NVL(PI_CELULAR, CELULAR) || '%'
                 AND NVL(TELEFONO, ' ') LIKE
                     '%' || NVL(PI_TELEFONO, NVL(TELEFONO, ' ')) || '%'
                 AND CORREO LIKE '%' || NVL(PI_CORREO, CORREO) || '%'
                    
                 AND ID_TIPO_DOCUMENTO = (CASE
                                            WHEN PI_ID_TIPO_DOCUMENTO = 4 THEN
                                             ID_TIPO_DOCUMENTO -- 4 todos los documentos 
                                            WHEN PI_ID_TIPO_DOCUMENTO != 4 THEN
                                             PI_ID_TIPO_DOCUMENTO
                                            WHEN PI_ID_TIPO_DOCUMENTO is null THEN
                                             ID_TIPO_DOCUMENTO
                                            ELSE
                                             null
                                          END) -- tipo 3 todos los tipos de documentos 
                    
                 AND trunc(DATE_FEC_CREACION) BETWEEN
                     NVL(trunc(TO_DATE(PI_FECHA_INICIO, 'DD/MM/YYYY')),
                         trunc(DATE_FEC_CREACION)) AND
                     NVL(trunc(TO_DATE(PI_FECHA_FIN, 'DD/MM/YYYY')),
                         trunc(DATE_FEC_CREACION))
              
              ) X;
  
  END;

  PROCEDURE P_DESASIGNAR_DOCUMENTO(IN_ID_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                                   OUT_ESTADO      OUT NUMBER,
                                   OUT_MENSAJE     OUT VARCHAR2) IS
  
    v_total NUMBER := 0;
  BEGIN
    OUT_ESTADO := 1;
  
    SELECT COUNT(1)
      INTO v_total
      FROM t_vudm_documento
     WHERE id_estado_documento IN (1, 4)
       AND id_documento = IN_ID_DOCUMENTO;
  
    IF v_total > 0 THEN
      UPDATE t_vudm_documento
         SET usu_asignado = NULL
       WHERE id_documento = IN_ID_DOCUMENTO;
    
      IF SQL%ROWCOUNT = 0 THEN
        OUT_ESTADO  := 0;
        OUT_MENSAJE := 'No se pudo realizar la desasignación.';
      ELSE
        COMMIT;
      END IF;
    
    ELSE
      OUT_ESTADO  := 0;
      OUT_MENSAJE := 'No se pudo realizar la desasignación, por favor verifique que la solicitud este en estado Pendiente.';
    END IF;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (24/10/2022)
  -- Descripcion:  Añadir hoja de ruta al documento
  -- ****************************************************************************************
  PROCEDURE P_DOCUMENTO_HOJARUTA(IN_ID_DOCUMENTO     IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                                 IN_ANIO             IN T_VUDM_DOCUMENTO.ANIO%TYPE,
                                 IN_NUMERO_SID       IN T_VUDM_DOCUMENTO.NUMERO_SID%TYPE,
                                 IN_USU_MODIFICACION IN T_VUDM_DOCUMENTO.USU_MODIFICACION%TYPE,
                                 OUT_ESTADO          OUT NUMBER,
                                 OUT_MENSAJE         OUT VARCHAR2) IS
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    UPDATE t_vudm_documento
       SET HOJA_RUTA           = IN_NUMERO_SID || '-' || IN_ANIO,
           anio                = IN_ANIO,
           numero_sid          = IN_NUMERO_SID,
           id_estado_documento = 7, --EN PROCESO
           usu_modificacion    = IN_USU_MODIFICACION
     WHERE id_documento = IN_ID_DOCUMENTO;
  
    IF SQL%ROWCOUNT > 0 THEN
      OUT_ESTADO := 100;
      COMMIT;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (29/10/2022)
  -- Descripcion:  Actualizar el estado del documento a NO PRESENTADO
  -- ****************************************************************************************
  PROCEDURE P_DOCUMENTO_NOPRESENTAR(IN_ID_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                                    OUT_ESTADO      OUT NUMBER,
                                    OUT_MENSAJE     OUT VARCHAR2) IS
    v_total           NUMBER := 0;
    v_fecha_anulacion T_VUDM_DOCUMENTO_OBS.FECHA_ANULACION%TYPE;
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    SELECT COUNT(1)
      INTO v_total
      FROM t_vudm_documento
     WHERE id_documento = IN_ID_DOCUMENTO
       AND ID_ESTADO_DOCUMENTO = 2;
  
    IF v_total = 0 THEN
      OUT_MENSAJE := 'La solicitud no está en estado observado';
    ELSE
      SELECT c.fecha_anulacion
        INTO v_fecha_anulacion
        FROM t_vudm_documento_obs c
       WHERE c.id_documento = IN_ID_DOCUMENTO
         AND c.flg_estado = 1;
    
      IF SYSDATE >= v_fecha_anulacion THEN
      
        UPDATE t_vudm_documento
           SET ID_ESTADO_DOCUMENTO = 6, FEC_MODIFICACION = SYSDATE
         WHERE id_documento = IN_ID_DOCUMENTO
           AND ID_ESTADO_DOCUMENTO = 2;
      
        IF SQL%ROWCOUNT > 0 THEN
          OUT_ESTADO := 100;
          COMMIT;
        END IF;
      ELSE
        OUT_MENSAJE := 'La solicitud no puede cambiar a estado NO PRESENTADO, porque aún está en plazo de SUBSANACIÓN';
      END IF;
    END IF;
  
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (10/11/2022)
  -- Descripcion:  Actualizar estado del documento a RECIBIDO
  -- ****************************************************************************************
  PROCEDURE P_DOCUMENTO_RECEPCIONAR(IN_ID_DOCUMENTO     IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                                    IN_FEC_RECIBIDO     IN T_VUDM_DOCUMENTO.FEC_RECIBIDO%TYPE,
                                    IN_USU_MODIFICACION IN T_VUDM_DOCUMENTO.USU_MODIFICACION%TYPE,
                                    OUT_ESTADO          OUT NUMBER,
                                    OUT_MENSAJE         OUT VARCHAR2) IS
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    UPDATE t_vudm_documento
       SET fec_recibido        = SYSDATE,
           fec_recibido_sgdd   = IN_FEC_RECIBIDO,
           id_estado_documento = 3, --RECIBIDO
           usu_modificacion    = IN_USU_MODIFICACION
     WHERE id_documento = IN_ID_DOCUMENTO;
  
    IF SQL%ROWCOUNT > 0 THEN
      OUT_ESTADO := 100;
      COMMIT;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;

  -- **************************************************************************************
  -- Creado por:   Anderson Ruiz  (24/11/2022)
  -- Descripcion:  Listar documentos por estado.
  -- ****************************************************************************************
  PROCEDURE P_LISTAR_DOCUMENTO_POR_ESTADO(IN_ID_ESTADO_DOCUMENTO IN T_VUDM_DOCUMENTO.ID_ESTADO_DOCUMENTO%TYPE,
                                          OUT_CURSOR             IN OUT SYS_REFCURSOR) AS
  BEGIN
    OPEN OUT_CURSOR FOR
      SELECT t.id_documento,
             REPLACE(translate(CASE WHEN length(t.asunto) < 12 THEN 'Asunto del documento: '|| t.asunto ELSE t.asunto END, chr(10) || chr(13) || chr(09), ' '),' ',' ') asunto,
            
             --REPLACE(translate(t.asunto, chr(10) || chr(13) || chr(09), ' '),' ',' ')asunto,
             --t.asunto,
             t.codigo_archivo,
             TO_CHAR(t.fec_creacion, 'DD/MM/YYYY hh:mi:ss a.m.') fec_creacion,
             TO_CHAR(t.fec_modificacion, 'DD/MM/YYYY hh:mi:ss a.m.') fec_modificacion,
             TO_CHAR(t.fec_recibido, 'DD/MM/YYYY hh:mi:ss a.m.') fec_recibido,
             t.flg_estado,
             t.id_estado_documento,
             t.id_oficina,
             t.id_tipo_documento,
             t.id_tipo_usuario,
             t.id_usuario,
             t.ip_creacion,
             t.ip_modificacion,
             t.nro_documento,
             t.nro_folios,
             t.usu_creacion,
             t.usu_modificacion,
             t.anio,
             t.numero_sid,
             NVL(T.HOJA_RUTA_ANEXO, '') HOJA_RUTA_ANEXO,
             CASE
               WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                UPPER(per.NOM_PERSONA) || ' ' || UPPER(per.APE_PAT_PERSONA) || ' ' ||
                UPPER(per.APE_MAT_PERSONA)
               ELSE
                CASE
                  WHEN PEX.ID_TIPO_PERSONA = 1 THEN
                   UPPER(PEX.NOMBRES) || ' ' || UPPER(PEX.APELLIDO_PATERNO) || ' ' ||
                   UPPER(PEX.APELLIDO_MATERNO)
                  ELSE
                   UPPER(PEX.RAZON_SOCIAL)
                END
             END NOM_PERSONA,
             CASE
               WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                UPPER(per.NOM_PERSONA) || ' ' || UPPER(per.APE_PAT_PERSONA) || ' ' ||
                UPPER(per.APE_MAT_PERSONA)
               ELSE
                CASE
                  WHEN PEX.ID_TIPO_PERSONA = 1 THEN
                   UPPER(PEX.DNI)
                  ELSE
                   UPPER(PEX.RUC)
                END
             END NRODOCUMENTO_PERSONA,
             PEX.ID_TIPO_PERSONA,
             CASE
               WHEN PEX.ID_PERSONA_EXTERNO IS NULL THEN
                PER.ID_PERSONA
               ELSE
                PEX.ID_PERSONA_EXTERNO
             END ID_PERSONA,
             TD.DESCRIPCION DESC_TIPO_DOC,
             O.DESCRIPCION,
             t.usu_asignado,
             t.hoja_ruta
        FROM t_vudm_documento t
       INNER JOIN T_VENL_OFICINA O
          ON O.IDUNIDAD = T.ID_OFICINA
       INNER JOIN T_VENL_TIPO_DOC TD
          ON TD.ID_TIPO_DOC = T.ID_TIPO_DOCUMENTO
       INNER JOIN T_SEGM_USUARIO U
          ON U.ID_USUARIO = T.ID_USUARIO
        LEFT JOIN T_SEGM_PERSONAL PER
          ON per.ID_PERSONA = U.ID_PERSONA
         AND U.ID_TIPO_USUARIO = 1
        LEFT JOIN T_SEGM_PERSONA_EXTERNA PEX
          ON PEX.ID_PERSONA_EXTERNO = U.ID_PERSONA
         AND U.ID_TIPO_USUARIO = 2
       WHERE t.id_estado_documento = IN_ID_ESTADO_DOCUMENTO AND t.flg_errorservicio = 1;
  END;
  
  PROCEDURE P_UPDATE_FLGERROR(IN_ID_DOCUMENTO        IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                               OUT_ESTADO             OUT NUMBER,
                               OUT_MENSAJE            OUT VARCHAR2) IS
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    UPDATE t_vudm_documento
       SET FLG_ERRORSERVICIO = 1 
     WHERE id_documento = IN_ID_DOCUMENTO;
   
  
    IF SQL%ROWCOUNT > 0 THEN
      OUT_ESTADO := 100;
      COMMIT;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;
  
  PROCEDURE P_UPDATE_FLGCORRIGIO(IN_ID_DOCUMENTO        IN T_VUDM_DOCUMENTO.ID_DOCUMENTO%TYPE,
                               OUT_ESTADO             OUT NUMBER,
                               OUT_MENSAJE            OUT VARCHAR2) IS
  BEGIN
    OUT_ESTADO  := -100;
    OUT_MENSAJE := NULL;
  
    UPDATE t_vudm_documento
       SET FLG_ERRORSERVICIO = 0 
     WHERE id_documento = IN_ID_DOCUMENTO;
   
  
    IF SQL%ROWCOUNT > 0 THEN
      OUT_ESTADO := 100;
      COMMIT;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      OUT_ESTADO  := 200;
      OUT_MENSAJE := SQLCODE || ' ' || SQLERRM;
  END;
END PCK_LGJ_MANTENIMIENTO;