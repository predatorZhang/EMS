package com.casic.accessControl.core.ext.store;

import org.springframework.core.io.AbstractResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class MultipartFileResource extends AbstractResource {
    private MultipartFile multipartFile;

    public MultipartFileResource(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getDescription() {
        return multipartFile.getOriginalFilename();
    }

    public InputStream getInputStream() throws IOException {
        return multipartFile.getInputStream();
    }

    public long contentLength() {
        return multipartFile.getSize();
    }

    public String getFilename() {
        return multipartFile.getOriginalFilename();
    }

    public boolean exists() {
        return !multipartFile.isEmpty();
    }
}
