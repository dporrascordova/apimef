-- SISVENVI.V_VUDM_DOCUMENTO source

CREATE OR REPLACE FORCE VIEW "SISVENVI"."V_VUDM_DOCUMENTO" ("ID_DOCUMENTO", "ASUNTO", "CODIGO_ARCHIVO", "FEC_CREACION", "FEC_CREACION_STR", "FEC_MODIFICACION", "FEC_RECIBIDO", "FEC_RECIBIDO_STR", "FLG_ESTADO", "ID_ESTADO_DOCUMENTO", "DESC_ESTADO", "ID_OFICINA", "DESC_OFICINA", "ID_TIPO_DOCUMENTO", "DESC_TIPO", "ID_TIPO_USUARIO", "ID_USUARIO", "IP_CREACION", "IP_MODIFICACION", "NRO_DOCUMENTO", "NRO_FOLIOS", "USU_CREACION", "USU_MODIFICACION", "ANEXOS", "GRUPO_ESTADOS", "ANIO", "NUMERO_SID", "NOM_PERSONA", "HOJA_RUTA_ANEXO", "FECHA_ANULACION", "FEC_OBSERVACION", "FECHA_OBSERVACION", "FEC_SUBSANACION", "FECHA_SUBSANACION", "USU_ASIGNADO", "NOMB_USU_ASIGNADO", "HOJA_RUTA", "OBS_SGDD") AS 
  SELECT t.id_documento,
       t.asunto,
       t.codigo_archivo,
       t.fec_creacion,
       TO_CHAR(t.fec_creacion, 'DD/MM/YYYY hh:mi:ss a.m.') fec_creacion_str,
       t.fec_modificacion,
       fec_recibido,
       NVL(TO_CHAR(t.fec_recibido, 'DD/MM/YYYY hh:mi:ss a.m.'), '-') fec_recibido_str,
       t.flg_estado,
       t.id_estado_documento,
       E.DESC_ESTADO,
       t.ID_OFICINA,
       O.DESCRIPCION DESC_OFICINA,
       t.id_tipo_documento,
       TD.DESCRIPCION DESC_TIPO,
       t.id_tipo_usuario,
       t.id_usuario,
       NVL(t.ip_creacion, '-') AS ip_creacion,
       t.ip_modificacion,
       t.nro_documento,
       t.nro_folios,
       t.usu_creacion,
       t.usu_modificacion,
       (SELECT COUNT(*)
          FROM SISVENVI.t_Vudm_Documento_Anexo DA
         WHERE t.id_documento = DA.ID_DOCUMENTO
           AND DA.ESTADO = '1') anexos,
       gpo.grupo_estados,
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
       t.hoja_ruta_anexo,
        CASE
           WHEN t.id_estado_documento = 6 OR t.id_estado_documento = 2 THEN
            TO_CHAR(obsfecha.FECHA_ANULACION, 'DD/MM/YYYY hh:mi:ss a.m.')
        ELSE NULL
          END FECHA_ANULACION,
       obsfecha.FEC_CREACION AS FEC_OBSERVACION,
       TO_CHAR(obsfecha.FEC_CREACION, 'DD/MM/YYYY hh:mi:ss a.m.') FECHA_OBSERVACION,
       obsfecha.FEC_SUBSANACION,
       TO_CHAR(obsfecha.FEC_SUBSANACION, 'DD/MM/YYYY hh:mi:ss a.m.') FECHA_SUBSANACION,
       t.usu_asignado,
       CASE
         WHEN t.usu_asignado IS NOT NULL THEN
          PE.NOM_PERSONA || ' ' || PE.APE_PAT_PERSONA || ' ' ||
          PE.APE_MAT_PERSONA
         ELSE
          NULL
       END nomb_usu_asignado,
       t.hoja_ruta,
       CASE
		  WHEN t.obs_sgdd IS NULL THEN NULL
		  WHEN t.numero_sid IS NULL THEN NULL
		  WHEN EXISTS (
				 SELECT 1
				 FROM SISVENVI.t_vudm_documento_anexo da
				 WHERE da.id_documento = t.id_documento
				   AND da.id_anexo IS  NULL
			   ) THEN t.obs_sgdd
		  ELSE NULL
		END AS obs_sgdd
  FROM SISVENVI.t_vudm_documento t
 INNER JOIN (SELECT DISTINCT t.id_estado_documento,
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
               FROM SISVENVI.t_vudm_documento t) gpo
    ON t.id_estado_documento = gpo.id_estado_documento
 INNER JOIN SISVENVI.T_VENL_ESTADO E
    ON t.id_estado_documento = E.ID_ESTADO
 INNER JOIN SISVENVI.T_SEGM_USUARIO USU
    ON T.ID_USUARIO = USU.ID_USUARIO
 INNER JOIN SISVENVI.T_VENL_OFICINA O
    ON t.ID_OFICINA = O.IDUNIDAD
 INNER JOIN SISVENVI.T_VENL_TIPO_DOC TD
    ON t.id_tipo_documento = TD.ID_TIPO_DOC
  LEFT JOIN SISVENVI.T_SEGM_PERSONAL per
    ON usu.ID_PERSONA = per.ID_PERSONA
   AND USU.ID_TIPO_USUARIO = 1
  LEFT JOIN SISVENVI.T_SEGM_PERSONA_EXTERNA PEX
    ON USU.ID_PERSONA = PEX.ID_PERSONA_EXTERNO
   AND USU.ID_TIPO_USUARIO = 2
  LEFT JOIN SISVENVI.T_VUDM_DOCUMENTO_OBS obsfecha
    ON t.id_documento = obsfecha.id_documento
   AND obsfecha.flg_ultreg  = 1
   LEFT JOIN (SELECT DOBS.ID_DOCUMENTO, MAX(DOBS.FEC_CREACION) FEC_CREACION
               from SISVENVI.T_VUDM_DOCUMENTO_OBS DOBS
              group by DOBS.ID_DOCUMENTO) DOCOBS ON  t.id_documento = DOCOBS.id_documento
  LEFT JOIN SISVENVI.T_SEGM_USUARIO US
    ON t.USU_ASIGNADO = US.COD_USUARIO
    AND US.FLG_ESTADO = 1
  LEFT JOIN SISVENVI.T_SEGM_PERSONAL PE
    ON US.ID_PERSONA = PE.ID_PERSONA
 ORDER BY t.id_documento DESC, t.fec_creacion DESC;