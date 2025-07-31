package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.listener.ReadListener;
import lombok.Data;

@Data
public class DemoDataListener implements ReadListener<DemoData> {
    private List<DemoData> dataList = new ArrayList<>();

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        dataList.add(data);
        System.out.println("解析到一条数据" + JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("所有数据解析完成！");
    }
}
