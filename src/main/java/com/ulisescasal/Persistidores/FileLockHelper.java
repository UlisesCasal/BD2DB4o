package com.ulisescasal.Persistidores;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.OverlappingFileLockException;

public class FileLockHelper {

    public static boolean isFileLocked(File file) {
        try (RandomAccessFile raFile = new RandomAccessFile(file, "rw");
             FileChannel channel = raFile.getChannel()) {
            channel.tryLock();
            return false;
        } catch (OverlappingFileLockException e) {
            // El archivo está actualmente bloqueado por otro proceso.
            return true;
        } catch (Exception e) {
            // Manejar otras posibles excepciones como IOExceptions
            e.printStackTrace();
            return true;
        }
    }

}