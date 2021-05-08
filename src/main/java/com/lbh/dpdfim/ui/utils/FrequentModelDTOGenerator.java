package com.lbh.dpdfim.ui.utils;

import com.lbh.dpdfim.ui.service.FrequentModelDTO;

import java.util.ArrayList;
import java.util.List;

public class FrequentModelDTOGenerator {

    public static List<FrequentModelDTO> singleTest() {
        FrequentModelDTO a = new FrequentModelDTO();
        a.setSource("a-source");
        a.setTarget("a-target");
        a.setConfidence(0.13123434);

        FrequentModelDTO b = new FrequentModelDTO();
        b.setSource("b-target");
        b.setTarget("b-source");
        b.setConfidence(0.432733123);

        FrequentModelDTO c = new FrequentModelDTO();
        c.setSource("c-source");
        c.setTarget("c-target");
        c.setConfidence(0.13428448);

        List<FrequentModelDTO> res = new ArrayList<>();
        res.add(a);
        res.add(b);
        res.add(c);

        return res;
    }
}
