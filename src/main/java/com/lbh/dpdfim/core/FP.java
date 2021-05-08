package com.lbh.dpdfim.core;

import com.lbh.dpdfim.connector.FileOperator;
import com.lbh.dpdfim.connector.PathParser;
import com.lbh.dpdfim.ui.service.FrequentItemSetDTO;
import com.lbh.dpdfim.ui.service.FrequentModelDTO;
import lombok.Data;
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

@Data
public class FP {

    double minSupport = 0.1;
    int numPartition = 10;
    double minConfidence = 0.85;

    public FP() {}
    public FP(double minSupport, double minConfidence, int numPartition) {
        this.minConfidence = minConfidence;
        this.minSupport = minSupport;
        this.numPartition = numPartition;
    }

    public Map<String, Object> execute() throws IOException, URISyntaxException {

        Map<String, Object> result = new HashMap<>();

        SparkConf scf = new SparkConf().setAppName("FPDemo").setMaster(PathParser.REMOTE_HADOOP_CLUSTER_MASTER)
                .setJars(new String[]{"target/dp-dfim-0.0.1.jar.original"});
        JavaSparkContext sc = new JavaSparkContext(scf);

        FileOperator.copyFromLocal("/spark/asthma.csv", "/fq/asthma.csv");

        //读取本地数据集，每一行为一个项集List<>，每个项集按项分割
        JavaRDD<List<String>> trans = sc.textFile(PathParser.generateRemotePath("/fq/asthma.csv"))
                .map(s -> Arrays.stream(s.replace(","," ").trim().split(" ")).distinct().collect(Collectors.toList()));
        long counts = trans.count();
        System.out.println("记录条数：" + counts);

        FPGrowth fpGrowth = new FPGrowth().setMinSupport(this.getMinSupport()).setNumPartitions(this.getNumPartition());
        FPGrowthModel<String> model = fpGrowth.run(trans);

        FileOperator.delete("/fq/out");
        model.freqItemsets().saveAsTextFile(PathParser.generateRemotePath("/fq/out"));

        List<FrequentItemSetDTO> itemSets = new ArrayList<>();
        List<FrequentModelDTO> models = new ArrayList<>();

        for (FPGrowth.FreqItemset<String> itemset :
                model.freqItemsets().toJavaRDD().collect()) {
            System.out.println(itemset.javaItems().toString() + itemset.freq());
            FrequentItemSetDTO frequentItemSetDTO = new FrequentItemSetDTO();
            frequentItemSetDTO.setItems(itemset.javaItems().toString());
            frequentItemSetDTO.setSupport(itemset.freq() * 1.0 / counts);
            itemSets.add(frequentItemSetDTO);
        }

        for (AssociationRules.Rule<String> rule :
                model.generateAssociationRules(minConfidence).toJavaRDD().collect()) {
            System.out.println(rule.javaAntecedent() + "=>" + rule.javaConsequent() + ", " + rule.confidence());
            FrequentModelDTO frequentModelDTO = new FrequentModelDTO();
            frequentModelDTO.setSource(rule.javaAntecedent().toString());
            frequentModelDTO.setTarget(rule.javaConsequent().toString());
            frequentModelDTO.setConfidence(rule.confidence());
            frequentModelDTO.setLift(0);
            models.add(frequentModelDTO);
        }

        sc.close();

        result.put("itemSets", itemSets);
        result.put("models", models);

        return result;
    }
}
