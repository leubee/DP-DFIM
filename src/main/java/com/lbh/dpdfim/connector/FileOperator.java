package com.lbh.dpdfim.connector;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FileOperator {
    public static FileSystem getFileSystem() throws IOException, URISyntaxException {
        Configuration conf = new Configuration();
        URI uri = new URI(PathParser.REMOTE_HDFS);
        return FileSystem.get(uri, conf);
    }

    public static void mkdir(String path) throws IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        fs.mkdirs(new Path(PathParser.generateClusterPath(path)));
        fs.close();
    }

    public static void copyFromLocal(String local, String dst) throws IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        fs.copyFromLocalFile(new Path(PathParser.generateLocalPath(local)), new Path(PathParser.generateClusterPath(dst)));
        System.out.println("[copy]From: " + local + " To: " + PathParser.REMOTE_HOME_DIR + dst + " ...Success!");
        fs.close();
    }

    public static void delete(String path) throws IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        fs.delete(new Path(PathParser.generateClusterPath(path)), true);   //b(boolean): delete directory recursively
        fs.close();
    }
}
