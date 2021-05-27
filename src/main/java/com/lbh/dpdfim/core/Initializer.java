package com.lbh.dpdfim.core;

import com.lbh.dpdfim.connector.HDFSOperator;
import com.lbh.dpdfim.connector.PathParser;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URISyntaxException;

public class Initializer {
    public static void FSInit() throws IOException, URISyntaxException {
        FileSystem fs = HDFSOperator.getFileSystem();
        try {
            fs.getFileStatus(new Path(PathParser.REMOTE_HOME_DIR));
        } catch (Exception e) {
            fs.mkdirs(new Path(PathParser.REMOTE_HOME_DIR));
        }

        try {
            fs.getFileStatus(new Path(PathParser.generateFSPath(PathParser.generateRemoteDataDir(false))));
        } catch (Exception e) {
            fs.mkdirs(new Path(PathParser.generateFSPath(PathParser.generateRemoteDataDir(false))));
        }
    }
}
