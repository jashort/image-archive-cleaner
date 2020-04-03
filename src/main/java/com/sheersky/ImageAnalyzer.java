package com.sheersky;

import com.sheersky.model.ImageMetadata;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.stream.Stream;

public class ImageAnalyzer {

    public ImageAnalyzer() {
    }

    public Stream<ImageMetadata> process(Collection<Path> paths) {
        return paths.parallelStream()
                .map(this::getInfo);
    }

    private ImageMetadata getInfo(Path path) {

        try (InputStream is = Files.newInputStream(path)) {
            MessageDigest md = MessageDigest.getInstance("MD5");

            DigestInputStream dis = new DigestInputStream(is, md);
            BufferedImage picture = ImageIO.read(dis);
            Image resized = resize(picture);

            return new ImageMetadata(path,
                    picture.getWidth(),
                    picture.getHeight(),
                    new BigInteger(1, dis.getMessageDigest().digest()).toString(16), // convert to md5 string
                    resized);
        } catch (IOException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Image resize(Image image) {
        BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, 100, 100, null);
        return bi;
    }


}
