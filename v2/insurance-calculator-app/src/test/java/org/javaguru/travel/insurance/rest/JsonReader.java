package org.javaguru.travel.insurance.rest;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class JsonReader {
    public String readString(String path) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + path);
        return new String(Files.readAllBytes(file.toPath()));
    }
}
