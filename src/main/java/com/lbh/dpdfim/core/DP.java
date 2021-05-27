package com.lbh.dpdfim.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.distribution.LaplaceDistribution;

import java.util.List;

@Data
@Slf4j
public class DP {

    Double mu;
    Double beta;

    public static void test(String[] args) {
        int count = 766;
        LaplaceDistribution ld = new LaplaceDistribution(0, 1/Math.log(3));
        Integer[] a = {623, 565, 429, 420, 397, 395};
        for (int i=10; i-->0;) {
            for (Integer n: a) {
                System.out.print(n + ld.sample() + " ");
            }
            System.out.println("");
        }
    }

    public List<Double> addNoise() {
        return null;
    }

}
