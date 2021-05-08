package com.lbh.dpdfim.ui.utils;

import com.lbh.dpdfim.ui.service.FrequentItemSetDTO;

import java.util.ArrayList;
import java.util.List;

public class FrequentItemSetDTOGenerator {
    public static List<FrequentItemSetDTO> singleTest() {
        List<FrequentItemSetDTO> res = new ArrayList<>();

        FrequentItemSetDTO a = new FrequentItemSetDTO();
        a.setItems("a-source");
        a.setSupport(0.8813103);
        res.add(a);

        FrequentItemSetDTO b = new FrequentItemSetDTO();
        b.setItems("a-target");
        b.setSupport(0.8213411);
        res.add(b);

        FrequentItemSetDTO c = new FrequentItemSetDTO();
        c.setItems("b-source");
        c.setSupport(0.9100382);
        res.add(c);

        FrequentItemSetDTO d = new FrequentItemSetDTO();
        d.setItems("b-target");
        d.setSupport(0.8910132);
        res.add(d);

        FrequentItemSetDTO e = new FrequentItemSetDTO();
        e.setItems("c-source");
        e.setSupport(0.9100382);
        res.add(e);

        FrequentItemSetDTO f = new FrequentItemSetDTO();
        f.setItems("c-target");
        f.setSupport(0.7913382);
        res.add(f);

        return res;

    }
}
