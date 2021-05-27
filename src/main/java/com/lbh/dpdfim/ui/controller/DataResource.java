package com.lbh.dpdfim.ui.controller;

import com.lbh.dpdfim.connector.FileTransporter;
import com.lbh.dpdfim.core.FP;
import com.lbh.dpdfim.ui.service.UserControlParamDTO;
import com.lbh.dpdfim.ui.utils.FrequentItemSetDTOGenerator;
import com.lbh.dpdfim.ui.utils.FrequentModelDTOGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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

    @GetMapping("/fpMainUser")
    public Map<String, Object> getAllFIListWithUserParam(UserControlParamDTO userParam) throws IOException, URISyntaxException {
        FP fp = new FP();
        if (userParam != null) {
            if (userParam.getMinConfidence() != null) fp.setMinConfidence(userParam.getMinConfidence());
            if (userParam.getMinSupport() != null) fp.setMinSupport(userParam.getMinSupport());
            if (userParam.getNumPartitions() != null) fp.setNumPartition(userParam.getNumPartitions());
        }
        fp.setChosenFile(userParam.getUserFile());
        log.info("Parallel FP Module successfully created. minConfidence: " + fp.getMinConfidence() + ";minSupport: " + fp.getMinSupport() + ";Num of partition: " + fp.getNumPartition());
        return fp.execute();
    }

    @GetMapping("/fpMain")
    public Map<String, Object> getAllFIList() throws IOException, URISyntaxException {
        FP fp = new FP();
        log.info("Parallel FP Module successfully created. minConfidence: " + fp.getMinConfidence() + ";minSupport: " + fp.getMinSupport() + ";Num of partition: " + fp.getNumPartition());
        return fp.execute();
    }

    @PostMapping("/upload")
    public void saveFileToHDFS(@RequestParam("uploadFile") MultipartFile file) throws IOException, URISyntaxException {
        File f = FileTransporter.MultipartFileToFile(file);
        FileTransporter.fileToHDFS(f);
        log.info("success save: " + f.getName());
    }

}
