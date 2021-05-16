package com.marcanta.ged.controllers;

import com.marcanta.ged.dtos.ImageGetDto;
import com.marcanta.ged.models.Image;
import com.marcanta.ged.models.Response;
import com.marcanta.ged.repositories.ImageRepository;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Value("${file.image-dir}")
    String IMAGE_FILE_DIRECTORY;

    @GetMapping
    public List<ImageGetDto> getAllImages() {
        return this.imageRepository.findAll().stream().filter(image -> !image.isArchived()).map(ImageGetDto::fromImage).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Object> imageUpload(@RequestParam String name, @RequestParam String category, @RequestParam String description, @RequestParam(value = "file")MultipartFile sourceImageFile, @RequestParam int persons, @RequestParam String objects, @RequestParam boolean published) throws IOException {
        String path = IMAGE_FILE_DIRECTORY + sourceImageFile.getOriginalFilename();
        File destImageFile = new File(path);
        destImageFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(destImageFile);
        fos.write(sourceImageFile.getBytes());
        fos.close();

        Image image = new Image(name, description, path, published, category, objects, persons );
        return new ResponseEntity<Object>(this.imageRepository.save(image), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable(name="id")Long id) {
        Optional<Image> optImage = this.imageRepository.findById(id).filter(image -> !image.isArchived());
        if (optImage.isPresent()) {
            ImageGetDto image = ImageGetDto.fromImage(optImage.get());
            return new ResponseEntity<>(image, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeImage(@PathVariable(name="id")Long id) {
        Optional<Image> optImage = this.imageRepository.findById(id).filter(image -> !image.isArchived());
        if (optImage.isPresent()) {
            Image image = optImage.get();
            // remove image from disk
            if (!new File(image.getPath()).delete()) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            image.setArchived(true);
            return new ResponseEntity<>(this.imageRepository.save(image), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<Object> displayImage(@PathVariable(name = "id")Long id) throws IOException {
        Optional<Image> optImage = this.imageRepository.findById(id).filter(image -> !image.isArchived());
        if (optImage.isPresent()) {
            Image image = optImage.get();
            // send Image as bytes
            File imageFile = new File(image.getPath());

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(Files.probeContentType(imageFile.toPath())))
                    .body(new InputStreamResource(new FileInputStream(imageFile)));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
