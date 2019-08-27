package com.ninjamodding.ninjaconnectclient;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties = new Properties();
    private static boolean setup = false;

    public static String getApiKey() {
        setup();
        return properties.getProperty("api-key");
    }

    private static void setup() {
        if (!setup) {
            try {
                properties.load(new FileInputStream("config.txt"));
                setup = true;
            } catch (IOException e) {
                URL resource = ConfigManager.class.getClass().getClassLoader().getResource("config.txt");
                try {
                    File defaultConfig = new File(resource.toURI());
                    File file = new File("config.txt");
                    copyFile(defaultConfig, file);
                    setup();
                    return;
                } catch (URISyntaxException | IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    private static void copyFile(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }
}
