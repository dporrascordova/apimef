package mef.application.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import mef.application.service.FilesStorageService;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

	@Value("${file.upload}")
	private String path;

	// private Path root = Paths.get("uploads");

	@Override
	public void init() {
		try {
			Path root = Paths.get(path);
			Files.createDirectory(root);
		} catch (IOException e) {
			throw new RuntimeException("No se pudo inicializar la carpeta para cargar " + path);
		}
	}

	@Override
	public void save(MultipartFile file) {
		try {
			Path root = Paths.get(path);
			System.out.println(file.getOriginalFilename());
			Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("No se pudo almacenar el archivo. Error: " + e.getMessage());
		}
	}

	@Override
	public void save(MultipartFile file, String fileName) {
		try {
			Path root = Paths.get(path);
			Files.copy(file.getInputStream(), root.resolve(fileName));
		} catch (Exception e) {
			throw new RuntimeException("No se pudo almacenar el archivo. Error: " + e.getMessage());
		}
	}

	@Override
	public Resource load(String filename) {
		try {
			Path root = Paths.get(path);
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("No se pudo leer el archivo");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		Path root = Paths.get(path);
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			Path root = Paths.get(path);
			return Files.walk(root, 1).filter(path -> !path.equals(root)).map(root::relativize);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("No se pudieron cargar los archivos");
		}
	}

	@Override
	public void save(String uploadFolder, MultipartFile file, String fileName) {
		try {
			Path root = Paths.get(uploadFolder);
			Files.copy(file.getInputStream(), root.resolve(fileName.toLowerCase()));
		} catch (Exception e) {
			throw new RuntimeException("No se pudo almacenar el archivo. Error: " + e.getMessage());
		}
	}

	@Override
	public void replace(String uploadFolder, MultipartFile file, String fileName) {
		try {
			Path root = Paths.get(uploadFolder);
			Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException("No se pudo almacenar el archivo. Error: " + e.getMessage());
		}
	}

}
