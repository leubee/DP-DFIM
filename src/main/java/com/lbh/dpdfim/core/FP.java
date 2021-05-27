package com.lbh.dpdfim.core;

import com.lbh.dpdfim.config.GlobalConfig;
import com.lbh.dpdfim.connector.HDFSOperator;
import com.lbh.dpdfim.connector.PathParser;
import com.lbh.dpdfim.ui.service.FrequentItemSetDTO;
import com.lbh.dpdfim.ui.service.FrequentModelDTO;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.fpm.AssociationRules;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowthModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Parallel FP-Growth Algorithm on Spark for FIM
 * @author Banghao Li
 */
@Data
@Slf4j
public class FP {

    double minSupport = 0.1;
    int numPartition = 10;
    double minConfidence = 0.85;

    String chosenFile = "asthma.csv";
    String dataPath = PathParser.REMOTE_REPOSITORY;

    public FP() {}
    public FP(double minSupport, double minConfidence, int numPartition) throws IOException, URISyntaxException {
        Initializer.FSInit();
        this.minConfidence = minConfidence;
        this.minSupport = minSupport;
        this.numPartition = numPartition;
    }

    public Map<String, Object> execute() throws IOException, URISyntaxException {

        Map<String, Object> result = new HashMap<>();

        SparkConf conf = new SparkConf().setAppName("FPDemo").setMaster(PathParser.REMOTE_HADOOP_CLUSTER_MASTER)
                .setJars(new String[]{"target/dp-dfim-0.0.1.jar.original"});
        JavaSparkContext context = new JavaSparkContext(conf);

//        HDFSOperator.copyFromLocal("/spark/asthma.csv", "/data/asthma.csv");

        //读取数据集
        JavaRDD<List<String>> trans = context.textFile(PathParser.generateRemotePath(inFilePath()))
                .map(s -> Arrays.stream(s.replace(","," ").trim().split(" ")).distinct().collect(Collectors.toList()));
        long counts = trans.count();
        log.info("记录条数：" + counts);

        FPGrowth fpGrowth = new FPGrowth().setMinSupport(this.getMinSupport()).setNumPartitions(this.getNumPartition());
        FPGrowthModel<String> model = fpGrowth.run(trans);

        HDFSOperator.delete(outFilePath());
        model.freqItemsets().saveAsTextFile(PathParser.generateRemotePath(outFilePath()));

        List<FrequentItemSetDTO> itemSets = new ArrayList<>();
        List<FrequentModelDTO> models = new ArrayList<>();

        for (FPGrowth.FreqItemset<String> itemset :
                model.freqItemsets().toJavaRDD().collect()) {
            log.debug(itemset.javaItems().toString() + itemset.freq());
            FrequentItemSetDTO frequentItemSetDTO = new FrequentItemSetDTO();
            frequentItemSetDTO.setItems(itemset.javaItems().toString());
            frequentItemSetDTO.setSupport(itemset.freq() * 1.0 / counts);
            itemSets.add(frequentItemSetDTO);
        }

        for (AssociationRules.Rule<String> rule :
                model.generateAssociationRules(minConfidence).toJavaRDD().collect()) {
            log.debug(rule.javaAntecedent() + "=>" + rule.javaConsequent() + ", " + rule.confidence());
            FrequentModelDTO frequentModelDTO = new FrequentModelDTO();
            frequentModelDTO.setSource(rule.javaAntecedent().toString());
            frequentModelDTO.setTarget(rule.javaConsequent().toString());
            frequentModelDTO.setConfidence(rule.confidence());
            frequentModelDTO.setLift(0);
            models.add(frequentModelDTO);
        }

        context.close();

        result.put("itemSets", itemSets);
        result.put("models", models);

        return result;
    }

    String inFilePath() {
        return PathParser.generateRemoteDataDir(GlobalConfig.PERSISTENCE_STATUS) + "/" + chosenFile;
    }

    String outFilePath() {
        return PathParser.generateRemoteDataDir(GlobalConfig.PERSISTENCE_STATUS)+ "/out/" + chosenFile.substring(0, chosenFile.indexOf('.'));
    }

}
