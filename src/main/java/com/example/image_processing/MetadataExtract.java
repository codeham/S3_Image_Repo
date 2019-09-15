package com.example.image_processing;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata.ImageMetadataItem;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetadataExtract {
    public Map<String, String> metadataExample(final File file) throws IOException, ImageReadException {
        Map<String, String> itemMap = new HashMap<>();
        final ImageMetadata metadata = Imaging.getMetadata(file);
        final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
        final List<ImageMetadataItem> items = jpegMetadata.getItems();
        List<String> metaDataList = new ArrayList<String>();

        for (int i = 0; i < items.size(); i++) {
            final ImageMetadataItem item = items.get(i);
            String[] keyValue = splitItemIntoKeyValue(item);
            itemMap.put(keyValue[0], keyValue[1]);

//            metaDataList.add(item.toString());
//            System.out.println(item.toString());
        }
        return itemMap;
    }

    public String[] splitItemIntoKeyValue(ImageMetadataItem item){
        String[] keyValue = new String[2];
        String itemText = item.toString();
        String itemKey = itemText.substring(0, itemText.indexOf(":"));
        String itemValue = itemText.substring(itemText.indexOf(":") + 1, itemText.length());

        keyValue[0] = itemKey;
        keyValue[1] = formatValue(itemValue);
        System.out.println(itemKey);
        System.out.println(itemValue);
        return keyValue;
    }

    public String formatValue(String value){
        // remove quotes ' ' and empty space
        return value.replace("'", "").replace(" ", "");
    }



}
