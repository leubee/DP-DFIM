package com.lbh.dpdfim.ui.utils;

import com.lbh.dpdfim.ui.service.FrequentItemSetDTO;

import java.util.ArrayList;
import java.util.List;

public class FrequentItemSetDTOGenerator {
    public static List<FrequentItemSetDTO> singleTest() {
        List<FrequentItemSetDTO> res = new ArrayList<>();

        FrequentItemSetDTO a = new FrequentItemSetDTO();
        a.setItems("a-source");
        a.setSupport(3124L);
        res.add(a);

        FrequentItemSetDTO b = new FrequentItemSetDTO();
        b.setItems("a-target");
        b.setSupport(1133L);
        res.add(b);

        FrequentItemSetDTO c = new FrequentItemSetDTO();
        c.setItems("b-source");
        c.setSupport(2001L);
        res.add(c);

        FrequentItemSetDTO d = new FrequentItemSetDTO();
        d.setItems("b-target");
        d.setSupport(999L);
        res.add(d);

        FrequentItemSetDTO e = new FrequentItemSetDTO();
        e.setItems("c-source");
        e.setSupport(1233L);
        res.add(e);

        FrequentItemSetDTO f = new FrequentItemSetDTO();
        f.setItems("c-target");
        f.setSupport(1312L);
        res.add(f);

        return res;

    }
}
