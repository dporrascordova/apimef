package mef.application.controlador;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mef.application.component.EmailComponent;
import mef.application.dto.Resource;
import mef.application.modelo.Estado;
import mef.application.service.EstadoService;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EstadoControlador {

	@Autowired
	EstadoService estadoService;

	private EmailComponent emailComponent;

	public EstadoControlador(EmailComponent emailComponent) {
		this.emailComponent = emailComponent;
	}

	@GetMapping("/estado")
	public ResponseEntity<List> listEstado() {
		List<Estado> listEstado = new ArrayList<>();
		listEstado = estadoService.listEstados();
		JSONArray jsonArray = new JSONArray();
		for (Estado estado : listEstado) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("codigo", estado.getId_estado());
			jsonObject.put("descripcion", estado.getDesc_estado());
			jsonArray.put(jsonObject);
		}
		 /*try {
			Map<String, Object> params = new HashMap<>();  
			params.put("usuario", "Richard");
			params.put("codigoclave", "DSADSAG-REGREDW-DWQDWq");
			params.put("imageResourceName", "logo.png");
			Resource resource = new Resource();
			resource.setImageBytes(Files.readAllBytes(Paths.get("C:\\opt\\logo.jpg")));
			resource.setContentType("image/png");

			Map<String, Resource> resources = new HashMap<>();
			resources.put("logo.png", resource);

			emailComponent.sendHTML("vongolaenoshiro@gmail.com", "Test", "email/casilla", params, resources);
		} catch (Exception e) {
			e.printStackTrace();
		} 
*/
		return new ResponseEntity<>(jsonArray.toList(), HttpStatus.OK);
	}

}
