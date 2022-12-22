package com.example.backend.Service.RankList;

import com.alibaba.fastjson.JSONObject;

public interface GetRankListService{
    JSONObject getList(Integer page);
}
