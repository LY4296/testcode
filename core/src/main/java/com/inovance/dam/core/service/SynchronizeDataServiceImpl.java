package com.inovance.dam.core.service;

import com.inovance.dam.core.config.PLMConfiguration;
import com.inovance.dam.core.entity.inovance.BTProductInformation;
import com.inovance.dam.core.entity.WhereClause;
import com.inovance.dam.core.entity.inovance.InovanceAsset;
import com.inovance.dam.core.entity.inovance.PLMProductInformation;
import com.inovance.dam.core.entity.inovance.PbiCatalog;
import com.inovance.dam.core.entity.inovance.PbiSalesCat;
import com.inovance.dam.core.entity.inovance.PbiSalesCatalog;
import com.inovance.dam.core.entity.inovance.ProjectNormal;
import com.inovance.dam.core.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.day.commons.datasource.poolservice.DataSourcePool;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        service = SynchronizeDataServiceImpl.class
)
public class SynchronizeDataServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(SynchronizeDataServiceImpl.class);

    @Reference
    private DataSourcePool dataSourcePool;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private PLMConfiguration plmConfiguration;

    @Activate
    public void activate() throws Exception {
        // 创建身份验证信息
        Map<String, Object> authenticationInfo = new HashMap<>();
        authenticationInfo.put(ResourceResolverFactory.USER, "admin");
        authenticationInfo.put(ResourceResolverFactory.PASSWORD, "admin");
        // 创建ResourceResolver对象
        ResourceResolver resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(authenticationInfo);

        //DataSource dataSource = (DataSource) dataSourcePool.getDataSource("portal_db");

        try {
            /**
             * 查询所有项目
             * TODO
             * List<ProjectNormal> projectNormalList= InovanceApi.getProject(null, plmConfiguration.getIpmsProjectUrl());
             */
            List<ProjectNormal> projectNormalList= MockUtil.mockProject();
            // 手动过滤已结项的项目
            List<ProjectNormal> projectNormals = projectNormalList.stream().filter(projectNormal -> "已结项".equals(projectNormal.getProjectStatus())).collect(Collectors.toList());

            /**
             * 销售目录树
             *List<PbiSalesCatalog> pbiSalesCatalogs = InovanceApi.getPbiSalesCatalog(buildClause(buildCatalogExpressions(), "="), plmConfiguration.getPbiSalesCatalogUrl());
             *TODO
             */
            List<PbiSalesCatalog> pbiSalesCatalogs = MockUtil.mockSalesCatalog();
            //
            //

            /**
             * 产品基本信息
             * TODO
             * List<PbiCatalog> pbiCatalogs = InovanceApi.getPbiCatalog(buildClause(buildCatalogExpressions(), "="), plmConfiguration.getPbicatalogUrl());
             */
            List<PbiCatalog> pbiCatalogs = MockUtil.mockOffering();
            // 销售目录和整机
            //        List<PbiSalesCat> pbiSalesCats =  InovanceApi.getData(buildClause(buildProjectExpressions(), "="), plmConfiguration.getPbisalecatUrl(), new PbiSalesCat());

            // 保存的层级结构的map，名字为key, path为value, 用于建立标签
            LinkedHashMap<String, String> catalogAneName = new LinkedHashMap<>();

            // offering所在的销售目录
            Map<String, String> offeringMap = new HashMap<>();

            // 从产品信息中过滤出所有的offering
            List<PbiCatalog> offerings = pbiCatalogs.stream().filter(pbiCatalog -> pbiCatalog.getCataId().startsWith("OF")).collect(Collectors.toList());
            // 处理offering可能挂载在任意层级
            Map<String, List<PbiSalesCatalog>> offeringToSalesMap = offerings.stream().map(v -> JsonUtil.clone(v, PbiSalesCatalog.class)).collect(Collectors.groupingBy(PbiSalesCatalog::getParentId));
            // 销售目录树形结构处理
            buildSalesCatalogTree(pbiSalesCatalogs, offeringToSalesMap, catalogAneName, offeringMap);

            // 创建offering标签
            ResourceUtil.createOfferingTag(resourceResolver, pbiCatalogs, "/inovance/offering/offering_id/");

            // 创建销售目录树标签
            ResourceUtil.createSalesCatalogTag(resourceResolver, catalogAneName, "/inovance/salescatalog");

            ResourceUtil.createProjectTag(resourceResolver, projectNormals, "/inovance/project/document_number/");

            // 产品信息 以cataId（即offeringid）转为map
            Map<String, PbiCatalog> pbiCatalogMap = pbiCatalogs.stream().collect(Collectors.toMap(PbiCatalog::getCataId, v -> v));

            // 销售目录树 以cataId转为map
            Map<String, PbiSalesCatalog> pbiSalesCatalogMap = pbiSalesCatalogs.stream().collect(Collectors.toMap(PbiSalesCatalog::getCataId, v -> v));

            /**
             * 测试查询所有数据
             * List<BTProductInformation> btProductInformationList = JDBCUtil.queryByCondition(dataSource, BTProductInformation.class, "select * from BTPRODUCTINFORMATION ", new ArrayList<>());
             * TODO
             */
            List<BTProductInformation> btProductInformationList = MockUtil.mockAsset();
            // 遍历项目
            for (ProjectNormal projectNormal : projectNormals) {
                long startTime = System.currentTimeMillis();
                log.info("任务 {} 编号 {} 开始处理-------> ", projectNormal.getProjectName(), projectNormal.getProjectNumber());


                /**
                 * 查询视图
                 * List<PLMProductInformation> plmProductInformationList = JDBCUtil.queryByCondition(dataSource, PLMProductInformation.class, "select * from BV_PLM_PRODUCTINFORMATION where PROJECTNUMBER = ? ", buildPLMProductInformationParam(projectNormal));
                 * TODO
                 */
                List<PLMProductInformation> plmProductInformationList = MockUtil.mockView();
                if (plmProductInformationList.isEmpty()) {
                    continue;
                }
//                Set<String> docNumbers = plmProductInformationList.stream().map(PLMProductInformation::getDocNumber).collect(Collectors.toSet());
//                // 查询表
//                List<BTProductInformation> btProductInformationList = JDBCUtil.queryByCondition(dataSource, BTProductInformation.class, "select * from BTPRODUCTINFORMATION where DOCNUMBER = ? ", buildBTProductInformationParam(docNumbers));

                //构建资产信息
                Map<BTProductInformation, List<InovanceAsset>> inovanceAssetMap =  ResourceUtil.buildAsset(btProductInformationList,
                        plmProductInformationList, projectNormal, pbiSalesCatalogMap, pbiCatalogMap);

                //创建
                ResourceUtil.doResourceOperation(resourceResolver, inovanceAssetMap, plmConfiguration.getInovanceFolderPath(), offeringMap);

                log.info("任务 {} 编号 {} 结束处理-------> 耗时: {} s", projectNormal.getProjectName(), projectNormal.getProjectNumber(), (System.currentTimeMillis() - startTime) / 1000);
            }
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
        } finally {
            // 关闭ResourceResolver对象
            if (resourceResolver != null) {
                resourceResolver.close();
            }
        }
    }

    private void buildSalesCatalogTree(List<PbiSalesCatalog> pbiSalesCatalogs,
                                       Map<String, List<PbiSalesCatalog>> offeringToSalesMap,
                                       LinkedHashMap<String, String> catalogAneName,
                                       Map<String, String> offeringMap) {
        // 过滤出顶级
        List<PbiSalesCatalog> collect = pbiSalesCatalogs.stream().filter(item -> "L0".equals(item.getCataLevel())).collect(Collectors.toList());

        // 通过父级id进行分组 获取我们的"List<PbiSalesCatalog> children"子节点
        Map<String, List<PbiSalesCatalog>> map = pbiSalesCatalogs.stream().collect(Collectors.groupingBy(PbiSalesCatalog::getParentId));

        getChildrenTree(collect, map, offeringToSalesMap, "", catalogAneName, offeringMap);
    }

    public static void getChildrenTree(List<PbiSalesCatalog> collect,
                                       Map<String, List<PbiSalesCatalog>> map,
                                       Map<String, List<PbiSalesCatalog>> offeringToMap,
                                       String path,
                                       LinkedHashMap<String, String> catalogAneName,
                                       Map<String, String> offeringMap) {
        for (PbiSalesCatalog catalog : collect) {
            // 根据上级路径拼接
            String childPath = path + "/" + catalog.getCataId();
            List<PbiSalesCatalog> childList = map.get(catalog.getCataId());
            // 当前层级的offering
            List<PbiSalesCatalog> offeringList = offeringToMap.get(catalog.getCataId());
            if (offeringList != null && !offeringList.isEmpty()) {
                offeringList.forEach(offering -> offeringMap.put(offering.getCataId(), childPath));
                if (childList == null) {
                    childList = offeringList;
                } else {
                    childList.addAll(offeringList);
                }
            }

            // 再寻找子节点
            if (childList != null && !childList.isEmpty()) {
                getChildrenTree(childList, map, offeringToMap, childPath, catalogAneName, offeringMap);
            }
            // 放入层级结构数组中
            catalogAneName.put(catalog.getCataId() + ":" + catalog.getCataName() + ":" + catalog.getCataPath(), childPath);
            catalog.setChildren(childList);
        }
    }

    private List<Object> buildPLMProductInformationParam(ProjectNormal projectNormal) {
        List<Object> params = new ArrayList<>();
        params.add(projectNormal.getProjectNumber());
        return params;
    }

    private List<Object> buildBTProductInformationParam(Set<String> docNumbers) {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        docNumbers.forEach(docNumber -> {
            sb.append("'").append(docNumber).append("',");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        params.add(sb.toString());
        return params;
    }

    private List<WhereClause.Expression> buildProjectExpressions() {
        List<WhereClause.Expression> expressions = new ArrayList<>();
        WhereClause.Expression expression = new WhereClause.Expression();
        expression.setAttribute("to_char(UPDATETIMEM, 'yyyy-mm-dd')");
        expression.setOperator(">=");
        expression.setValue("2022-05-01");
        expressions.add(expression);
        return expressions;
    }

    private List<WhereClause.Expression> buildCatalogExpressions() {
        List<WhereClause.Expression> expressions = new ArrayList<>();
        WhereClause.Expression expression = new WhereClause.Expression();
        expression.setAttribute("ROOTID");
        expression.setOperator("=");
        expression.setValue("SALESCATALOG_ROOT");
        expressions.add(expression);
        return expressions;
    }

    private Map<String, Object> buildClause(List<WhereClause.Expression> expressions, String operator) {
        Map<String, Object> map = new HashMap<>();
        WhereClause whereClause = new WhereClause();
        whereClause.setExpressions(expressions);
        whereClause.setOperator(operator);
        map.put("whereClause", whereClause);
        return map;
    }

}
