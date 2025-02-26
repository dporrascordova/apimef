package mef.application.modelo;

import java.io.Serializable;
import java.util.List;

public class TipoDocumento implements Serializable {  
	private int id_tipodocumento;
	private String desc_tipodocumento;
	private String flg_estado;
	public List<TipoDocumento> lista_tipodoc;
	public int getId_tipodocumento() {
		return id_tipodocumento;
	}
	public void setId_tipodocumento(int id_tipodocumento) {
		this.id_tipodocumento = id_tipodocumento;
	}
	public String getDesc_tipodocumento() {
		return desc_tipodocumento;
	}
	public void setDesc_tipodocumento(String desc_tipodocumento) {
		this.desc_tipodocumento = desc_tipodocumento;
	}
	public String getFlg_estado() {
		return flg_estado;
	}
	public void setFlg_estado(String flg_estado) {
		this.flg_estado = flg_estado;
	}
}
