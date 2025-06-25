package kr.ac.dankook.ace.whatsinmyref.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import kr.ac.dankook.ace.whatsinmyref.dto.FileStorageProperties;

@Service
public class FileService {
    // Save the uploaded file to this folder
    //private static final String UPLOAD_PATH = "C:" + File.separator + "Users" + File.separator + "park" + File.separator + "Uploads" + File.separator;
    private final Path location;
    
    public FileService(FileStorageProperties properties) {
        this.location = Paths.get(properties.getLocation()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.location);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public String saveFile(MultipartFile file,int recipeno,String extension) throws IOException {
        if (!file.isEmpty()) {
            Path additionalPath=Paths.get(Integer.toString(recipeno));
            Path filePath=this.location.resolve(additionalPath).normalize();
            Files.createDirectories(filePath);
            additionalPath=Paths.get("mainImage"+extension);
            filePath=filePath.resolve(additionalPath).normalize();

            byte[] bytes = file.getBytes();
            // upload file
            Files.write(filePath, bytes);
            // return filePath.toString();
            return "/files/"+Integer.toString(recipeno)+"/mainImage"+extension;
        }
        return null;
    }

    public String saveManualFile(MultipartFile file,int recipeno,int number,String extension) throws IOException {
        if (!file.isEmpty()) {
            Path additionalPath=Paths.get(Integer.toString(recipeno)+"/manual"+ (number<10 ?"0":"")+Integer.toString(number)+extension);
            Path filePath= this.location.resolve(additionalPath).normalize();
            byte[] bytes = file.getBytes();

            // upload file
            Files.write(filePath, bytes);
            // return filePath.toString();
            return "/files/"+Integer.toString(recipeno)+"/manual"+ (number<10 ?"0":"")+Integer.toString(number)+extension;
        }
        return null;
    }

    public void loadFileToResponse(String filename, HttpServletResponse response) throws IOException {
        // The file to be downloaded.
        Path file = this.location.resolve(filename).normalize();

        // Get the media type of the file
        String contentType = Files.probeContentType(file);
        if (contentType == null) {
            // Use the default media type
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        
        response.setContentType(contentType);
        // File Size
        response.setContentLengthLong(Files.size(file));
        // Building the Content-Disposition header with the ContentDisposition utility class can avoid the problem of garbled downloaded file names.
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename(file.getFileName().toString(), StandardCharsets.UTF_8)
                .build()
                .toString());
        // download file
        Files.copy(file, response.getOutputStream());
        // Response data to the client
        response.getOutputStream().flush();
    }

    public void loadZipFileToResponse(List<MultipartFile> multipartFiles, HttpServletResponse response) throws IOException {
        // Convert MultipartFile objects to Path objects resolved from the base location
        List<Path> files = multipartFiles.stream()
        .map(file -> {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            return this.location.resolve(filename).normalize();
        })
        .collect(Collectors.toList());

        response.setContentType("application/zip"); // zip archive format
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename("download.zip", StandardCharsets.UTF_8)
                .build()
                .toString());
        
        // Archiving multiple files and responding to the client
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            for (Path file : files) {
                if (Files.exists(file)) {
                    try (InputStream inputStream = Files.newInputStream(file)) {
                        zipOutputStream.putNextEntry(new ZipEntry(file.getFileName().toString()));
                        StreamUtils.copy(inputStream, zipOutputStream);
                        zipOutputStream.flush();
                    }
                }
            }
        }
    }

    public ResponseEntity<Resource> view(String filename) {
        Resource resource = new FileSystemResource(this.location + File.separator + filename);
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }        
}
