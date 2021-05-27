package com.lbh.dpdfim.connector;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HDFSOperator {
    public static FileSystem getFileSystem() throws IOException, URISyntaxException {
        Configuration conf = new Configuration();
        URI uri = new URI(PathParser.REMOTE_HDFS);
        return FileSystem.get(uri, conf);
    }

    public static void mkdir(String path) throws IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        fs.mkdirs(new Path(PathParser.generateFSPath(path)));
        fs.close();
    }

    public static void copyFromLocal(String local, String dst) throws IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        fs.copyFromLocalFile(true, new Path(PathParser.generateLocalPath(local)), new Path(PathParser.generateFSPath(dst)));
        log.info("[copy]From: " + local + " To: " + PathParser.REMOTE_HOME_DIR + dst + " ...Success!");
        fs.close();
    }

    public static void delete(String path) throws IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        fs.delete(new Path(PathParser.generateFSPath(path)), true);   //b(boolean): delete directory recursively
        fs.close();
    }

    public static List<String> fileList(String path) throws IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        FileStatus[] status = fs.listStatus(new Path(PathParser.generateFSPath(path)));
        List<String> list = new ArrayList<>();
        for (FileStatus file : status) {
            if (file.isFile()) {
                list.add(file.getPath().getName());
            }
        }
        return list;
    }
}
