package com.RobertoPalMol.TribeMe_Backend.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    private final Path storageFolder;

    public ImageService(@Value("${app.images.storage-folder:/TribeMe_Backend/TribeMe/tribus/imagenes}") String folder) throws IOException {
        this.storageFolder = Paths.get(folder).toAbsolutePath().normalize();

        if (!Files.exists(this.storageFolder)) {
            Files.createDirectories(this.storageFolder);
        }
    }

    public String storeImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("El archivo está vacío");
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // Crear nombre único para evitar colisiones (UUID)
        String uniqueFilename = "upload_" + UUID.randomUUID() + fileExtension;

        // Ruta destino física para guardar la imagen
        Path destinationFile = this.storageFolder.resolve(uniqueFilename).normalize();

        // Guardar el archivo
        file.transferTo(destinationFile);

        // Devolver URL pública (ajústala si tu endpoint usa otro path)
        return "/imagenes/" + uniqueFilename;
    }

    public Path getStorageFolder() {
        return storageFolder;
    }

}
