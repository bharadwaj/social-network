package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.storage.StorageFileNotFoundException;
import com.my.network.socialnetwork.storage.StorageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;



@Controller
@RequestMapping(value = "/files")
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    SubscribedUserRepository subscribedUserRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private static String UPLOADED_FOLDER = ".//images//";

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes,  @RequestHeader(value= "Authorization") String authTokenHeader) {

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        //Validate follow user & user's existence.
        Boolean userIsValid = subscribedUserRepository.findById(userId).isPresent();
        String fileNameToSave = "";

        if(!userIsValid)
            return new ResponseEntity<>("Invalid User to Follow", HttpStatus.BAD_REQUEST);
        //storageService.store(file);
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return new ResponseEntity<>("File is Empty", HttpStatus.BAD_REQUEST);
        }

        try {

            int positionOfDot = file.getOriginalFilename().indexOf('.');

            // Get the file and save it somewhere
            fileNameToSave = userId+"_"+generateUniqueHandle(file.getOriginalFilename())+"_"+ file.getOriginalFilename().substring(positionOfDot);
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + fileNameToSave);
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }


        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return new ResponseEntity<>(fileNameToSave, HttpStatus.CREATED);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    String generateUniqueHandle(String str){

        String finalHandle = StringUtils.left(str, 10);
        finalHandle = finalHandle.replace('.', '_');
        try {
            finalHandle =  URLEncoder.encode(finalHandle.toLowerCase(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        finalHandle = finalHandle + "_"+ RandomStringUtils.randomAlphanumeric(8);
        return finalHandle;
    }

}