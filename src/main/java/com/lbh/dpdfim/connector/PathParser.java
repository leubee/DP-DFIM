package com.lbh.dpdfim.connector;

/**
 *
 */
public class PathParser {

    public static final String REMOTE_HADOOP_CLUSTER_MASTER = "spark://192.168.8.100:7077";

    public static final String REMOTE_HDFS = "hdfs://192.168.8.100:9000";

    public static final String LOCAL_RESOURCES_ROOT = "E:/Projects/NJUCM/DP-DFIM/src/main/resources";

    public static final String LOCAL_JAR_PATH = "E:/Projects/NJUCM/DP-DFIM/target";

    public static final String REMOTE_HOME_DIR = "/usr/spark/tmp";

    public static String generateClusterPath(String path) {
        return PathParser.REMOTE_HOME_DIR + path;
    }

    public static String generateRemotePath(String path) {
        return PathParser.REMOTE_HDFS + PathParser.REMOTE_HOME_DIR + path;
    }

    public static String generateLocalPath(String path) {
        return PathParser.LOCAL_RESOURCES_ROOT + path;
    }


}
