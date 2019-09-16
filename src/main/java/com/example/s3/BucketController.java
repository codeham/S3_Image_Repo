package com.example.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/storage")
public class BucketController {

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient){
        this.amazonClient = amazonClient;
    }

    @RequestMapping("/")
    public String index(){
        return "index.html";
    }

    @PostMapping("/uploadFile")
    public Map<String, String> uploadFile(@RequestPart(value = "file") MultipartFile file){
        return this.amazonClient.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl){
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }

    @GetMapping("/getTest")
    public String getTest(){
        return "HELLO WORLD, CRISTIAN !";
    }
}
