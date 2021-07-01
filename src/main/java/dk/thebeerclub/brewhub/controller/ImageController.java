package dk.thebeerclub.brewhub.controller;

import dk.thebeerclub.brewhub.model.Brew;
import dk.thebeerclub.brewhub.model.Image;
import dk.thebeerclub.brewhub.service.BrewService;
import dk.thebeerclub.brewhub.service.ImageService;
import io.minio.errors.MinioException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;
    private final BrewService brewService;

    public ImageController(ImageService imageService, BrewService brewService) {
        this.imageService = imageService;
        this.brewService = brewService;
    }

    @PostMapping("/upload/brew/{brewId}")
    public Image upload(@RequestParam("file") MultipartFile file, @PathVariable String brewId) {
        return handleUpload(file, brewId, null);
    }

    @PostMapping("/upload/brew/{brewId}/step/{stepId}")
    public Image upload(@RequestParam("file") MultipartFile file, @PathVariable String brewId, @PathVariable String stepId) {
        return handleUpload(file, brewId, stepId);
    }

    private Image handleUpload(MultipartFile file, String brewId, String stepId) {
        try {

            Optional<Brew> optional = brewService.findById(Long.valueOf(brewId));
            if (optional.isEmpty()) {
                throw new IllegalStateException("No brew found with id: " + brewId);
            }

            String imageUrl = imageService.upload(file, brewId);
            Image image = new Image();
            image.setBrew(optional.get());
            image.setImageUrl(imageUrl);
            image.setStepId(stepId == null ? null : Long.valueOf(stepId));
            return imageService.save(image);

        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("The file cannot be read", e);
        }
    }

}
