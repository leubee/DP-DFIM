package com.lbh.dpdfim.ui.controller;

import com.lbh.dpdfim.core.FP;
import com.lbh.dpdfim.ui.utils.FrequentItemSetDTOGenerator;
import com.lbh.dpdfim.ui.utils.FrequentModelDTOGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class DataResource {

    @GetMapping("/test")
    public Map<String, Object> test_findAllFIList() {
        Map<String, Object> res = new HashMap<>();
        res.put("itemSetList", FrequentItemSetDTOGenerator.singleTest());
        res.put("modelList", FrequentModelDTOGenerator.singleTest());
        return res;
    }

    @GetMapping("/testfp")
    public Map<String, Object> testfp_findAllFIList() throws IOException, URISyntaxException {
        FP fp = new FP();
        return fp.execute();
    }


}
