package com.marcanta.ged.controllers;

import com.marcanta.ged.dtos.ImageGetDto;
import com.marcanta.ged.models.Image;
import com.marcanta.ged.models.Response;
import com.marcanta.ged.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/images/analyze")
public class AnalyzeController {
    RestTemplate restTemplate;

    public AnalyzeController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }


    @PostMapping
    public ResponseEntity<Object> analyzeImage(@RequestParam String name, @RequestParam(value = "file")MultipartFile file) throws IOException {
        String url = "https://uf8m8gb2k0.execute-api.eu-central-1.amazonaws.com/epsi/generate-signed-url";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", "meRxq0l2cE5SQfNNrfnKUalbs0VIRkIA5KFI8hSF");
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create a map for post parameters
        Map<String, Object> map = new HashMap<>();
        map.put("filename", name);

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // send POST request
        ResponseEntity<Response> response = this.restTemplate.postForEntity(url, entity, Response.class);

        // check response status code
        if (response.getStatusCode() == HttpStatus.OK) {
            URI putUrl = response.getBody().getSignedUrl();
            String nameTemp = response.getBody().getFilename();
            putFile(putUrl, file);
            return getFile(nameTemp);
        } else {
            return null;
        }
    }

    public void putFile(URI url, MultipartFile file) throws IOException {

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes());

        // send POST request
        ResponseEntity<Response> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, Response.class);
    }

    public ResponseEntity<Object> getFile(String name) throws IOException {

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", "meRxq0l2cE5SQfNNrfnKUalbs0VIRkIA5KFI8hSF");

        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String url = "https://uf8m8gb2k0.execute-api.eu-central-1.amazonaws.com/epsi/detect-labels/" + name;

        // send POST request
        ResponseEntity<Object> response = this.restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class);
        return response;
    }
}







