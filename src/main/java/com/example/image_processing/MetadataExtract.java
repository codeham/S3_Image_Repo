package com.example.image_processing;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata.ImageMetadataItem;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetadataExtract {
    public List<String> metadataExample(final File file) throws IOException, ImageReadException {
        final ImageMetadata metadata = Imaging.getMetadata(file);
        final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
        final List<ImageMetadataItem> items = jpegMetadata.getItems();
        List<String> metaDataList = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {
            final ImageMetadataItem item = items.get(i);
            metaDataList.add(item.toString());
            System.out.println(item.toString());
        }
        return metaDataList;
    }
}
