package com.marcanta.ged.controllers;

import com.marcanta.ged.models.Image;
import com.marcanta.ged.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Value("${file.image-dir}")
    String IMAGE_FILE_DIRECTORY;

    @GetMapping
    public List<Image> getAllImages() {
        return this.imageRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> imageUpload(@RequestParam("file")MultipartFile sourceImageFile) throws IOException {
        String path = IMAGE_FILE_DIRECTORY + sourceImageFile.getOriginalFilename();
        File destImageFile = new File(path);
        destImageFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(destImageFile);
        fos.write(sourceImageFile.getBytes());
        fos.close();

        Image image = new Image(sourceImageFile.getOriginalFilename(), "une image de toto", path, true, "person", "", 1 );
        return new ResponseEntity<Object>(this.imageRepository.save(image), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeImage(@PathVariable(name="id")Long id) {
        Optional<Image> optImage = this.imageRepository.findById(id);
        if (optImage.isPresent()) {
            Image image = optImage.get();
            // remove image from disk
            if (!new File(image.getPath()).delete()) return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            image.setArchived(true);
            return new ResponseEntity<>(this.imageRepository.save(image), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
