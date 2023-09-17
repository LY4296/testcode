package com.inovance.dam.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.inovance.dam.core.entity.inovance.InovanceResult;
import com.inovance.dam.core.entity.inovance.PbiCatalog;
import com.inovance.dam.core.entity.inovance.PbiSalesCatalog;
import com.inovance.dam.core.entity.inovance.ProjectNormal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InovanceApi {

    private static final String APP_KEY = "appKey";

    private static final String TYPE = "application/json";

    private static final String THINGWORX = "true";


    /**
     * 查询项目
     */
    public static List<ProjectNormal> getProject(Map<String, Object> map, String url) throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("whereClause", map);

        String res = HttpUtils.post(url, body, createHeader(), 30000);

        InovanceResult<List<ProjectNormal>> result = JsonUtil.jsonToBean(res, new TypeReference<InovanceResult<List<ProjectNormal>>>(){});

        return result.getRows();
    }

    /**
     * 查询销售目录树
     */
    public static List<PbiSalesCatalog> getPbiSalesCatalog(Map<String, Object> map, String url) throws Exception {
        String res = HttpUtils.post(url, map, createHeader(), 30000);

        InovanceResult<List<PbiSalesCatalog>> result = JsonUtil.jsonToBean(res, new TypeReference<InovanceResult<List<PbiSalesCatalog>>>(){});

        return result.getRows();
    }

    /**
     * 查询产品
     */
    public static List<PbiCatalog> getPbiCatalog(Map<String, Object> map, String url) throws Exception {
        String res = HttpUtils.post(url, map, createHeader(), 30000);

        InovanceResult<List<PbiCatalog>> result = JsonUtil.jsonToBean(res, new TypeReference<InovanceResult<List<PbiCatalog>>>(){});

        return result.getRows();
    }

    /**
     * 构建header
     *
     * @return
     */
    private static Map<String, String> createHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("appKey", APP_KEY);
        headers.put("Content-Type", TYPE);
        headers.put("Accept", TYPE);
        headers.put("x-thingworx-session", THINGWORX);
        return headers;
    }

}
