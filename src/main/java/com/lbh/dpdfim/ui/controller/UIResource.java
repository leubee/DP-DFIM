package com.lbh.dpdfim.ui.controller;

import com.lbh.dpdfim.config.GlobalConfig;
import com.lbh.dpdfim.connector.HDFSOperator;
import com.lbh.dpdfim.connector.PathParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ui")
@Slf4j
public class UIResource {
    @RequestMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/fpChart")
    public String fpChart(Model model) throws IOException, URISyntaxException {
        List<String> fileList = HDFSOperator.fileList(PathParser.generateRemoteDataDir(GlobalConfig.PERSISTENCE_STATUS));
        log.info("[Data]File List:" + fileList.toString());
        model.addAttribute("fileList", fileList);
        return "echart-min";
    }

    @RequestMapping("/fileUpload")
    public String fileUpload() {
        return "uploader";
    }
}
