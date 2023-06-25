package org.example.unitls;

import java.io.File;

public class ExtensionChecker {
    public static boolean isDocxFile(File file) {
        String extension = file.getName();
        return extension.toLowerCase().endsWith(".docx");
    }

    public static boolean isZipFile(File file) {
        String fileName = file.getName();
        return fileName.toLowerCase().endsWith(".zip");
    }

    public static boolean isJarFile(File file) {
        String fileName = file.getName();
        return fileName.toLowerCase().endsWith(".jar");
    }
    public static boolean isMDFile(File file) {
        String fileName = file.getName();
        return fileName.toLowerCase().endsWith(".md");
    }
}
