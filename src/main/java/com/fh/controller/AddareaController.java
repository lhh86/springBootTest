package com.fh.controller;

import com.fh.common.jsonDate;
import com.fh.model.AddAreaInfo;
import com.fh.service.AddareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.Pipe;
import java.util.List;

@RestController
@RequestMapping("AddareaController")
public class AddareaController {
    @Autowired
    private AddareaService addareaService;

    @RequestMapping("queryAddArea")
    public jsonDate queryAddArea(){
     List<AddAreaInfo> infoList = addareaService.queryAddArea();
     return jsonDate.getJsonSuccess(infoList);
    }



}
