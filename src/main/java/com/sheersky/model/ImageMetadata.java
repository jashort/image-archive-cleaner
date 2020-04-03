package com.sheersky.model;

import java.awt.*;
import java.nio.file.Path;

public class ImageMetadata {
    private final Path filePath;
    private final Integer width;
    private final Integer height;
    private final String hash;
    private final Image thumbnail;

    public ImageMetadata(Path filePath, Integer width, Integer height, String hash, Image thumbnail) {
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.hash = hash;
        this.thumbnail = thumbnail;
    }

    public Path getFilePath() {
        return filePath;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getHash() {
        return hash;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    @Override
    public String toString() {
        return "ImageMetadata{" +
                "filePath=" + filePath.getFileName() +
                ", width=" + width +
                ", height=" + height +
                ", hash='" + hash + '\'' +
                '}';
    }
}
