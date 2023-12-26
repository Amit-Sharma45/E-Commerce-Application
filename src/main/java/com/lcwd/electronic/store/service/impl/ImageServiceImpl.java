package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.exceptions.BadApiRequestException;
import com.lcwd.electronic.store.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName : {}", originalFilename);
        String fileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = fileName + extension;
        String fullPathWithFileName = path + fileNameWithExtension;

        logger.info("full image path: {}", fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

//            file save
            logger.info("file extension is : {}", extension);
            File folder = new File(path);
            if (!folder.exists()) {
//                create the folder
                folder.mkdirs();
            }
//            upload
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;
        } else {
            throw new BadApiRequestException("file with this " + extension + " not allowed !!");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
