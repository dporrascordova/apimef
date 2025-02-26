package mef.application.infrastructure;

import pe.gob.mef.std.bs.web.ws.AcMsUbigwsDto;

public class UbigeoHelpers {

	/**
	 * Obtener el departamento por el Id.
	 * @param ubigeos
	 * @param iddepartamento
	 * @return
	 */
	public static String getDepartamento(AcMsUbigwsDto[] ubigeos, String iddepartamento) {
		String departamento = "";
		for (AcMsUbigwsDto ubigeo : ubigeos) {
			if (iddepartamento.equals(ubigeo.getCoddpto() + "") && ubigeo.getCodprov().equals(0)
					&& ubigeo.getCoddist().equals(0)) {
				departamento = ubigeo.getNombre();
				break;
			}
		}

		return departamento;
	}

	/**
	 * Obtener la provincia por los Ids.
	 * @param ubigeos
	 * @param iddepartamento
	 * @param idprovincia
	 * @return
	 */
	public static String getProvincia(AcMsUbigwsDto[] ubigeos, String iddepartamento, String idprovincia) {
		String provincia = "";
		for (AcMsUbigwsDto ubigeo : ubigeos) {
			if (iddepartamento.equals(ubigeo.getCoddpto() + "") && idprovincia.equals(ubigeo.getCodprov() + "")
					&& ubigeo.getCoddist().equals(0)) {
				provincia = ubigeo.getNombre();
				break;
			}
		}

		return provincia;
	}

	/**
	 * Obtener el distrito por los Ids.
	 * @param ubigeos
	 * @param iddepartamento
	 * @param idprovincia
	 * @param iddistrito
	 * @return
	 */
	public static String getDistrito(AcMsUbigwsDto[] ubigeos, String iddepartamento, String idprovincia,
			String iddistrito) {
		String distrito = "";
		for (AcMsUbigwsDto ubigeo : ubigeos) {
			if (iddepartamento.equals(ubigeo.getCoddpto() + "") && idprovincia.equals(ubigeo.getCodprov()+"")
					&& iddistrito.equals(ubigeo.getCoddist()+"")) {
				distrito = ubigeo.getNombre();
				break;
			}
		}

		return distrito;
	}
}
