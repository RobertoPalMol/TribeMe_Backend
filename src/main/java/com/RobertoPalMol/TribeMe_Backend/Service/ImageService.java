package com.RobertoPalMol.TribeMe_Backend.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    private final Path storageFolder = Paths.get("/TribeMe_Backend/TribeMe/tribus/imagenes/");

    public ImageService() throws IOException {
        // Crear carpeta si no existe
        if (!Files.exists(storageFolder)) {
            Files.createDirectories(storageFolder);
        }
    }

    public String storeImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("El archivo está vacío");
        }

        String originalFilename = file.getOriginalFilename();

        // Ruta física en el servidor / contenedor donde se guardan las imágenes
        Path destinationFile = this.storageFolder.resolve(Paths.get(originalFilename))
                .normalize().toAbsolutePath();

        // Guardar el archivo físicamente
        file.transferTo(destinationFile);

        // Devolver la URL pública que puede usar el cliente para acceder a la imagen
        return "/imagenes/" + originalFilename;
    }

}
