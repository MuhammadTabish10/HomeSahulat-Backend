package com.HomeSahulat.service;

public interface BucketService {
    String save(byte[] pdf, String folderName, String fileName, String folderType);
    byte[] downloadFile(String folderName, String fileName, String folderType);
    void deleteFile(String fileName);
    void deleteFilesStartingWith(String folderKey, String prefix);
    void deleteFolder(String folderName);
}
