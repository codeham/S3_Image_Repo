package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage")
public class BucketController {

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient){
        this.amazonClient = amazonClient;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file){
        return this.amazonClient.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl){
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }

    @PostMapping("/testPost")
    public String testPost(@RequestParam String text){
        return "Test Post Success ! " + text;
    }

    @GetMapping("/getTest")
    public String getTest(){
        return "HELLO WORLD !";
    }
}
