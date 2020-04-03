package com.sheersky;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FileFinder {

    public Set<Path> findFilesToProcess(Path[] directories) {
        if (directories == null) { throw new RuntimeException("Enter at least one path to scan"); }

        Set<Path> files = new HashSet<>();

        for (Path p : directories) {
            Set<Path> filesInDir = findFilesToProcess(p);
            files.addAll(filesInDir);
            System.out.println(p + ": " + filesInDir.size() + " images");
        }
        return files;
    }

    public Set<Path> findFilesToProcess(Path basePath) {
        try {
            return Files.list(basePath)
                    .filter(this::hasImageExtension).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasImageExtension(Path path) {
        final String[] extensions = {"png", "gif", "jpg", "jpeg"};
        String loweredPath = path.toString().toLowerCase();
        for (String extension : extensions) {
            if (loweredPath.endsWith(extension)) return true;
        }
        return false;
    }

}
