package com.example.backend.Service.Record;

import com.alibaba.fastjson.JSONObject;

public interface GetRecordListService{
    JSONObject getList(Integer page);
}
