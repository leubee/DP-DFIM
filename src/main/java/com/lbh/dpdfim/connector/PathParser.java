package com.lbh.dpdfim.connector;

/**
 *
 */
public class PathParser {

    public static final String REMOTE_HADOOP_CLUSTER_MASTER = "spark://192.168.8.100:7077";

    public static final String REMOTE_HDFS = "hdfs://192.168.8.100:9000";

    public static final String LOCAL_RESOURCES_ROOT = "E:/Projects/NJUCM/DP-DFIM/src/main/resources";

    public static final String LOCAL_JAR_PATH = "E:/Projects/NJUCM/DP-DFIM/target";

    public static final String REMOTE_HOME_DIR = "/usr/spark";

    public static final String REMOTE_REPOSITORY = "/data";

    public static final String TMP_DIR = "/tmp";

    public static String generateFSPath(String path) {
        return PathParser.REMOTE_HOME_DIR + path;
    }

    public static String generateRemotePath(String path) {
        return PathParser.REMOTE_HDFS + PathParser.REMOTE_HOME_DIR + path;
    }

    public static String generateLocalPath(String path) {
        return PathParser.LOCAL_RESOURCES_ROOT + path;
    }

    public static String generateRemoteDataDir(boolean persistence) {
        return  REMOTE_REPOSITORY + (persistence ? "" : TMP_DIR);
    }

}
