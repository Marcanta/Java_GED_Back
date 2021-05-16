package com.marcanta.ged.models;

import java.io.Serializable;
import java.net.URI;

public class Response implements Serializable {
    private String filename;
    private URI signedUrl;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public URI getSignedUrl() {
        return signedUrl;
    }

    public void setSignedUrl(URI signedUrl) {
        this.signedUrl = signedUrl;
    }
}
