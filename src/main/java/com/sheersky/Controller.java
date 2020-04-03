package com.sheersky;

import com.sheersky.model.ImageMetadata;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CommandLine.Command(description = "Find and remove duplicate images", name = "iac")
public class Controller  implements Callable<Integer> {
    @CommandLine.Parameters(paramLabel = "Path", description = "one or more paths to scan for images")
    Path[] directories;

    @CommandLine.Option(names = {"-c", "--clean"}, description = "Clean - Remove duplicate files", defaultValue = "False")
    boolean reallyDeleteFiles;

    @CommandLine.Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
    private boolean helpRequested;

    @Override
    public Integer call() {
        FileFinder f = new FileFinder();


        long start = System.currentTimeMillis();

        // Find the images to scan in all the given directories
        Set<Path> filesToScan = f.findFilesToProcess(directories);

        System.out.println("Unique images to scan: " + filesToScan.size());

        System.out.println();

        ImageAnalyzer imageAnalyzer = new ImageAnalyzer();
        Stream<ImageMetadata> imageData = imageAnalyzer.process(filesToScan);

        Map<String, List<ImageMetadata>> imageByHash = imageData.collect(Collectors.groupingBy(ImageMetadata::getHash));

        int duplicates = imageByHash.values().stream().filter(x -> x.size() >= 2).mapToInt(List::size).sum();

        long finish = System.currentTimeMillis();
        System.out.println("Analyzed " + filesToScan.size() + " images in " + (finish-start) + " ms");

        System.out.println("Found " + duplicates + " duplicate files");

        imageByHash.values().stream().filter(x -> x.size() >= 2).forEach(fileList -> {
            fileList.forEach(x -> System.out.println(x.getFilePath()));
            System.out.println();
        });

        if (reallyDeleteFiles) {
            System.out.println("I'd delete some files");
        } else {
            System.out.println("I wouldn't really delete anything.");
        }
        return 0;
    }

}
