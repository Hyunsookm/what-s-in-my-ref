package kr.ac.dankook.ace.whatsinmyref.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;


@Configuration
@ConfigurationProperties(prefix = "file.upload")
@Data
public class FileStorageProperties {
    private String location;
}