package com.example.image_processing;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;

import java.io.File;
import java.io.IOException;

public class MetadataExtract {
    public void metadataExample(final File file) throws IOException, ImageReadException {
        final ImageMetadata metadata = Imaging.getMetadata(file);
        System.out.println(metadata);
    }
}
