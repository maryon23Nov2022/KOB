package com.example.backend.controller.RankList;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.Service.RankList.GetRankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetRankListController{
    @Autowired
    private GetRankListService getRankListService;

    @GetMapping("/ranklist/getlist/")
    public JSONObject getList(@RequestParam Map<String, String> data){
        Integer page = Integer.parseInt(data.get("page"));
        return getRankListService.getList(page);
    }
}
