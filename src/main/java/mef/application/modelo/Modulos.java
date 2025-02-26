package mef.application.modelo;
  

public class Modulos  {
  
	private int id_sistema_modulo;
	private int id_sistema_modulo_padre;
	private int id_tipo_modulo;
	private String id_a;
	private String id_li;
	private String desc_modulo;
	private String imagen; 
	private int orden;
	private int flg_alerta;
	private int count_alera;
	
	public int getId_sistema_modulo() {
		return id_sistema_modulo;
	}
	public void setId_sistema_modulo(int id_sistema_modulo) {
		this.id_sistema_modulo = id_sistema_modulo;
	}
	public int getId_sistema_modulo_padre() {
		return id_sistema_modulo_padre;
	}
	public void setId_sistema_modulo_padre(int id_sistema_modulo_padre) {
		this.id_sistema_modulo_padre = id_sistema_modulo_padre;
	}
	public int getId_tipo_modulo() {
		return id_tipo_modulo;
	}
	public void setId_tipo_modulo(int id_tipo_modulo) {
		this.id_tipo_modulo = id_tipo_modulo;
	}
	public String getId_a() {
		return id_a;
	}
	public void setId_a(String id_a) {
		this.id_a = id_a;
	}
	public String getId_li() {
		return id_li;
	}
	public void setId_li(String id_li) {
		this.id_li = id_li;
	}
	public String getDesc_modulo() {
		return desc_modulo;
	}
	public void setDesc_modulo(String desc_modulo) {
		this.desc_modulo = desc_modulo;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	} 

	public void setFlg_alerta(int flg_alerta) {
		this.flg_alerta = flg_alerta;
	} 
	 
	public int getFlg_alerta() {
		return flg_alerta;
	}
	
	
	public void setCount_alera(int count_alera) {
		this.count_alera = count_alera;
	} 
	 
	public int getCount_alera() {
		return count_alera;
	}
	
	

}
