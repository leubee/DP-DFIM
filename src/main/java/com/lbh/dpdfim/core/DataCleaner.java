package com.lbh.dpdfim.core;

import com.lbh.dpdfim.config.GlobalConfig;
import com.lbh.dpdfim.connector.PathParser;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.codehaus.janino.Java;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataCleaner {

    public static JavaRDD<List<String>> clean(JavaSparkContext context, String remotePath) {
        String suffix = remotePath.substring(remotePath.indexOf(".") + 1);
        if (suffix.equals("csv")) return csvCleaner(context.textFile(PathParser.generateRemotePath(remotePath)));
        else throw new IllegalArgumentException("Not Supported file type: " + suffix);
    }

    public static JavaRDD<List<String>> csvCleaner(JavaRDD<String> data) {
        return data.map(s -> Arrays.stream(s.replace(","," ")
                .trim()
                .split(" "))
                .distinct()
                .collect(Collectors.toList()));
    }

}
