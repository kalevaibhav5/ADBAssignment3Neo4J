package com.adb.assignment.adbassignment3graph.controller;

import com.adb.assignment.adbassignment3graph.service.ReadPubMedFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AdbController {

    @Autowired
    private ReadPubMedFileService readPubMedFileService;

    @RequestMapping(path="loaddata", method= RequestMethod.GET)
    public void loadData() throws Exception{
        readPubMedFileService.loadFile();
    }
}
