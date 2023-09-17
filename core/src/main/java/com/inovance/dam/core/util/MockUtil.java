package com.inovance.dam.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inovance.dam.core.entity.inovance.BTProductInformation;
import com.inovance.dam.core.entity.inovance.PLMProductInformation;
import com.inovance.dam.core.entity.inovance.PbiCatalog;
import com.inovance.dam.core.entity.inovance.PbiSalesCatalog;
import com.inovance.dam.core.entity.inovance.ProjectNormal;
import com.inovance.dam.core.util.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockUtil {

    /**
     * mock资产 表数据
     */
    public static List<BTProductInformation> mockAsset() {
        String data = "[\n" +
                "    {\n" +
                "      \"docnumber\": \"19120037-CY\",\n" +
                "      \"docname\": \"默纳克电梯群控方案-英文\",\n" +
                "      \"docversion\": \"A00\",\n" +
                "      \"docstate\": \"已发布\",\n" +
                "      \"releasdtime\": \"2020-09-12\",\n" +
                "      \"modifier\": \"w110859\",\n" +
                "      \"doctype\": \"CY\",\n" +
                "      \"docclassification\": \"彩页\",\n" +
                "      \"secretdegree\": \"内部\",\n" +
                "      \"doclocation\": \"/ProdDoc/CY/19120037-CY/A00/test1.pptx\",\n" +
                "      \"filename\": \"19120037-CY_A00（19120037《默纳克电梯群控方案》-英文）20201212.pdf\",\n" +
                "      \"filesize\": \"1892823\",\n" +
                "      \"attachlocation\": \"\",\n" +
                "      \"createtime\": \"2020-09-12\",\n" +
                "      \"updatetime\": \"2020-09-12\",\n" +
                "      \"versionlabel\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"docnumber\": \"19120036-CY\",\n" +
                "      \"docname\": \"默纳克电梯群控方案\",\n" +
                "      \"docversion\": \"A00\",\n" +
                "      \"docstate\": \"已发布\",\n" +
                "      \"releasdtime\": \"2020-09-12\",\n" +
                "      \"modifier\": \"w110859\",\n" +
                "      \"doctype\": \"CY\",\n" +
                "      \"docclassification\": \"彩页\",\n" +
                "      \"secretdegree\": \"外部\",\n" +
                "      \"doclocation\": \"/ProdDoc/CY/19120036-CY/A00/test2.pptx\",\n" +
                "      \"filename\": \"19120036-CY_A00（19120036《默纳克电梯群控方案》）20201212.pdf\",\n" +
                "      \"filesize\": \"1993562\",\n" +
                "      \"attachlocation\": \"\",\n" +
                "      \"createtime\": \"2020-09-12\",\n" +
                "      \"updatetime\": \"2020-09-12\",\n" +
                "      \"versionlabel\": \"\"\n" +
                "    }\n" +
                "  ]";
        return JsonUtil.jsonToList(data, BTProductInformation.class);
    }

    /**
     * mock视图数据
     */
    public static List<PLMProductInformation> mockView() {
        String data = "[\n" +
                "    {\n" +
                "      \"partnumber\": \"1\",\n" +
                "      \"productseriescode\": \"code1\",\n" +
                "      \"chinesedesc\": \"中文1\",\n" +
                "      \"englishdesc\": \"english 1\",\n" +
                "      \"specmode\": \"MOE1\",\n" +
                "      \"docnumber\": \"19120037-CY\",\n" +
                "      \"docversion\": \"A00\",\n" +
                "      \"doctype\": \"CY\",\n" +
                "      \"docname\": \"默纳克电梯群控方案-英文\",\n" +
                "      \"releasdtime\": \"2020-09-12 00:00:00:000\",\n" +
                "      \"offering\": \"OF000001\",\n" +
                "      \"projectnum\": \"P00001\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"partnumber\": \"2\",\n" +
                "      \"productseriescode\": \"code2\",\n" +
                "      \"chinesedesc\": \"中文2\",\n" +
                "      \"englishdesc\": \"english 2\",\n" +
                "      \"specmode\": \"MOE2\",\n" +
                "      \"docnumber\": \"19120036-CY\",\n" +
                "      \"docversion\": \"A00\",\n" +
                "      \"doctype\": \"CY\",\n" +
                "      \"docname\": \"默纳克电梯群控方案\",\n" +
                "      \"releasdtime\": \"2020-09-12 00:00:00:000\",\n" +
                "      \"offering\": \"OF000002\",\n" +
                "      \"projectnum\": \"P00001\"\n" +
                "    }\n" +
                "  ]";
        return JsonUtil.jsonToList(data, PLMProductInformation.class);
    }

    /**
     * mock offering的数据
     */
    public static List<PbiCatalog> mockOffering() {
        PbiCatalog catalog1 = new PbiCatalog();
        catalog1.setRootId("SALESCATALOG_ROOT");
        catalog1.setRootName("销售目录树");
        catalog1.setCataId("OF000001");
        catalog1.setCataName("电梯OF00001");
        catalog1.setCataLevel("OFFERING");
        catalog1.setParentId("T00001");
        catalog1.setParentName("电梯二级");
        catalog1.setCataPath("/电梯一级/电梯二级");

        PbiCatalog catalog2 = new PbiCatalog();
        catalog2.setRootId("SALESCATALOG_ROOT");
        catalog2.setRootName("销售目录树");
        catalog2.setCataId("OF000002");
        catalog2.setCataName("电梯OF00002");
        catalog2.setCataLevel("OFFERING");
        catalog2.setParentId("T00001");
        catalog2.setParentName("电梯二级");
        catalog2.setCataPath("/电梯一级/电梯二级");

        List<PbiCatalog> catalogs = new ArrayList<>();
        catalogs.add(catalog1);
        catalogs.add(catalog2);
        return catalogs;
    }

    /**
     * mock销售目录树
     */
    public static List<PbiSalesCatalog> mockSalesCatalog() {
        PbiSalesCatalog salesCatalog1 = new PbiSalesCatalog();
        salesCatalog1.setRootId("SALESCATALOG_ROOT");
        salesCatalog1.setRootName("销售目录树");
        salesCatalog1.setLevelName("一级");
        salesCatalog1.setCataId("R00001");
        salesCatalog1.setCataName("电梯一级");
        salesCatalog1.setCataLevel("L0");
        salesCatalog1.setCataType("DIR");
        salesCatalog1.setCataStatus("status");
        salesCatalog1.setEnglishDesc("one level");
        salesCatalog1.setShowOrNot("show");
        salesCatalog1.setUpdatedBy("inovance");
        salesCatalog1.setCreator("inovance");
        salesCatalog1.setCreateTime("2020-09-12 00:00:00");
        salesCatalog1.setUpdateTime("2020-09-12 00:00:00");
        salesCatalog1.setParentId("ROOT");
        salesCatalog1.setParentName("销售目录树");
        salesCatalog1.setCataPath("/");

        PbiSalesCatalog salesCatalog2 = new PbiSalesCatalog();
        salesCatalog2.setRootId("SALESCATALOG_ROOT");
        salesCatalog2.setRootName("销售目录树");
        salesCatalog2.setLevelName("二级");
        salesCatalog2.setCataId("T00001");
        salesCatalog2.setCataName("电梯二级");
        salesCatalog2.setCataLevel("L1");
        salesCatalog2.setCataType("DIR");
        salesCatalog2.setCataStatus("status");
        salesCatalog2.setEnglishDesc("two level");
        salesCatalog2.setShowOrNot("show");
        salesCatalog2.setUpdatedBy("inovance");
        salesCatalog2.setCreator("inovance");
        salesCatalog2.setCreateTime("2020-09-12 00:00:00");
        salesCatalog2.setUpdateTime("2020-09-12 00:00:00");
        salesCatalog2.setParentId("R00001");
        salesCatalog2.setParentName("电梯一级");
        salesCatalog2.setCataPath("/电梯一级");

        List<PbiSalesCatalog> salesCatalogs = new ArrayList<>();
        salesCatalogs.add(salesCatalog1);
        salesCatalogs.add(salesCatalog2);
        return salesCatalogs;
    }

    /**
     * mock项目
     */
    public static List<ProjectNormal> mockProject() {
        ProjectNormal projectNormal = new ProjectNormal();
        projectNormal.setProjectNumber("P00001");
        projectNormal.setProjectName("projectName");
        projectNormal.setCompany("汇川");
        projectNormal.setDepartment("IT-001");
        projectNormal.setProjectType("TEST");
        projectNormal.setProjectManager("inovance");
        projectNormal.setProductLine("line");
        projectNormal.setProjectPhase("phase");
        projectNormal.setProjectStatus("已结项");
        projectNormal.setSystemName("systemName");
        projectNormal.setApprovalTime("2023-09-01");
        projectNormal.setCreateTime("2023-09-01");
        projectNormal.setUpdateTime("2023-09-01");
        projectNormal.setIsExpense("isExpense");

        List<ProjectNormal> projectNormals = new ArrayList<>();
        projectNormals.add(projectNormal);
        return projectNormals;
    }

}
