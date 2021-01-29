package com.MyUtils.sort;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liujiabei
 * @since 2018/11/15
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String srcPath = "C:\\Users\\admin\\Documents\\WeChat Files\\ljb021027\\Files\\商机搜索.md";
        InputStream is = new FileInputStream(srcPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = reader.readLine();

        JSONObject jsonObject = JSON.parseObject(line);
        JSONArray verticesArr = JSON.parseArray(jsonObject.get("vertices").toString());

        Map<String, JSONObject> edgesMap = new HashMap<>();
        JSONArray edgesArr = JSON.parseArray(jsonObject.get("edges").toString());
        for (int i = 0; i < edgesArr.size(); i++) {
            JSONObject edges = edgesArr.getJSONObject(i);
            edgesMap.put(edges.getString("_id"), edges);
        }

        for (int i = 0; i < verticesArr.size(); i++) {
            JSONObject vertices = verticesArr.getJSONObject(i);
            JSONArray mainPathListArr = vertices.getJSONArray("main_path_list");

            for (int i1 = 0; i1 < mainPathListArr.size(); i1++) {
                String mainPathList = mainPathListArr.getString(i1);
                JSONObject jo = edgesMap.get(mainPathList);
                mainPathListArr.set(i1, jo);
            }

        }

        System.out.println(verticesArr.toJSONString());

    }


}
