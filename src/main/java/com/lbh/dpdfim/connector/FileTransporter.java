package com.lbh.dpdfim.connector;

import com.lbh.dpdfim.config.GlobalConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;

@Slf4j
public class FileTransporter {

    public static void inputStreamToFile(InputStream ins, File file) throws IOException {
        OutputStream outs = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            outs.write(buffer, 0, bytesRead);
        }
        outs.close();
        ins.close();
    }

    public static File MultipartFileToFile(MultipartFile src) throws IOException {
        File file = null;
        if (src.equals("") || src.getSize() == 0) {
            src = null;
        } else {
            InputStream ins = src.getInputStream();
            file = new File(PathParser.generateLocalPath("/" + src.getOriginalFilename()));
            inputStreamToFile(ins, file);
        }
        return file;
    }

    public static void fileToHDFS(File file) throws IOException, URISyntaxException {
        FileSystem fs = HDFSOperator.getFileSystem();
        fs.copyFromLocalFile(true, new Path(file.getAbsolutePath()), new Path(PathParser.generateRemoteDataDir(GlobalConfig.PERSISTENCE_STATUS) + "/" + file.getName()));
        log.info("[Copy]" + file.getName() + ": to HDFS DataDir ...Success!");
    }

}
