package com.project.JewelHub.util;


import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @GetMapping("/itemImage/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable("imageName") String imageName) throws IOException {
        // Retrieve image file from your backend storage
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        // Join paths
        Path newPath = currentPath.resolve("src/main/resources/static/itemImage/"+imageName);

        String newPathString = newPath.toString();

        File file = new File(newPathString); // Replace with the path to your image
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        // Return the image with appropriate headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set appropriate content type
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/video/{videoName}")
    public ResponseEntity<Resource> getVideo(@PathVariable("videoName") String videoName) {
        try {
            // Retrieve video file from your backend storage
            Path currentPath = Paths.get(System.getProperty("user.dir"));
            // Join paths
            Path newPath = currentPath.resolve("src/main/resources/static/itemVideo/" + videoName);

            String newPathString = newPath.toString();

            File file = new File(newPathString);
            Path path = Paths.get(file.getAbsolutePath());

            if (!Files.exists(path) || Files.isDirectory(path)) {
                return ResponseEntity.notFound().build();
            }

            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            // Return the video with appropriate headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("video/mp4")); // Set content type to video/mp4

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace(); // Or log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}