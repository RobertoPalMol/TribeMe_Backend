package com.RobertoPalMol.TribeMe_Backend.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    private final Path storageFolder = Paths.get("/app/TribeMe/tribus/imagenes/");

    public ImageService() throws IOException {
        // Crear carpeta si no existe
        if (!Files.exists(storageFolder)) {
            Files.createDirectories(storageFolder);
        }
    }

    public String storeImage(MultipartFile file) throws IOException {
        // Validar que el archivo no esté vacío
        if (file.isEmpty()) {
            throw new IOException("El archivo está vacío");
        }

        // Obtener nombre original (puedes crear uno único si quieres evitar colisiones)
        String originalFilename = file.getOriginalFilename();

        // Ruta destino
        Path destinationFile = this.storageFolder.resolve(Paths.get(originalFilename))
                .normalize().toAbsolutePath();

        // Guardar el archivo
        file.transferTo(destinationFile);

        // Retornar la URL o ruta para acceder a la imagen (depende de tu servidor)
        // Por ejemplo: si sirves los archivos estáticos desde /uploads/images
        return "TribeMe/tribus/imagenes/" + originalFilename;
    }
}
