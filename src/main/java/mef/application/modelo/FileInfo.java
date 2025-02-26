package mef.application.modelo;

import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

public class FileInfo {
	
	@Transient
	private MultipartFile file;
	
	private String name;
	private String url;
	
	public FileInfo(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	

}
