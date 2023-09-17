package com.inovance.dam.core.util;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.tagging.InvalidTagFormatException;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.inovance.dam.core.config.PLMConfiguration;
import com.inovance.dam.core.entity.inovance.BTProductInformation;
import com.inovance.dam.core.entity.inovance.InovanceAsset;
import com.inovance.dam.core.entity.inovance.PLMProductInformation;
import com.inovance.dam.core.entity.inovance.PbiCatalog;
import com.inovance.dam.core.entity.inovance.PbiSalesCat;
import com.inovance.dam.core.entity.inovance.PbiSalesCatalog;
import com.inovance.dam.core.entity.inovance.ProjectNormal;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ResourceUtil {

    @Reference
    private PLMConfiguration plmConfiguration;

    /**
     * 拼装资产
     * @param informationList
     * @param plmProductInformationList
     * @param projectNormal
     * @param pbiSalesCatalogMap
     * @param pbiCatalogMap
     */
    public static Map<BTProductInformation, List<InovanceAsset>> buildAsset(List<BTProductInformation> informationList,
                                                                            List<PLMProductInformation> plmProductInformationList,
                                                                            ProjectNormal projectNormal,
                                                                            Map<String, PbiSalesCatalog> pbiSalesCatalogMap,
                                                                            Map<String, PbiCatalog> pbiCatalogMap) {

        // 将视图数据以docNumber docVersion转为map
        Map<String, List<PLMProductInformation>> plmProductInformationMap = plmProductInformationList.stream().collect(Collectors.groupingBy(k -> k.getDocnumber() + k.getDocversion()));

        Map<BTProductInformation, List<InovanceAsset>> result = new HashMap<>();
        for (BTProductInformation btProductInformation : informationList) {
            List<InovanceAsset> assets = new ArrayList<>();
            //基于资产出发，先获取到视图的关系数据
            List<PLMProductInformation> plmList = plmProductInformationMap.get(btProductInformation.getDocumentNumber() + btProductInformation.getDocumentVersion());
            for (PLMProductInformation plmProductInformation : plmList) {
                //根据视图获取产品
                PbiCatalog pbiCatalog = pbiCatalogMap.get(plmProductInformation.getOffering());

                //根据产品获取销售目录树
                PbiSalesCatalog pbiSalesCatalog = pbiCatalog == null ? null : pbiSalesCatalogMap.get(pbiCatalog.getParentId());

                InovanceAsset asset = ConverToInovanceAsset(btProductInformation, projectNormal, pbiCatalog, pbiSalesCatalog);

                assets.add(asset);
            }

            result.put(btProductInformation, assets);
        }
        return result;
    }

    /**
     * 创建offering标签
     * @param resourceResolver
     * @param offeringList
     * @param path
     * @throws Exception
     */
    public static void createOfferingTag(ResourceResolver resourceResolver,
                                         List<PbiCatalog> offeringList,
                                         String path) throws Exception {
        // 获取TagManager对象
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

        for (PbiCatalog catalog : offeringList) {
            Tag tag = tagManager.resolve(path + catalog.getCataId());
            if (tag == null) {
                tagManager.createTag(path + catalog.getCataId(), catalog.getCataName(), catalog.getCataPath());
            } else {
                ModifiableValueMap valueMap = tag.adaptTo(Resource.class).adaptTo(ModifiableValueMap.class);
                valueMap.put("jcr:title", catalog.getCataName());
                valueMap.put("jcr:description", catalog.getCataPath());
            }
        }
    }

    /**
     * 创建销售目录树标签
     * @param resourceResolver
     * @param catalogAneName
     * @param path
     */
    public static void createSalesCatalogTag(ResourceResolver resourceResolver,
                                             LinkedHashMap<String, String> catalogAneName,
                                             String path) throws Exception {
        // 获取TagManager对象
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

        for (Map.Entry<String, String> map : catalogAneName.entrySet()) {
            Tag tag = tagManager.resolve(path + map.getValue());
            String[] keys = map.getKey().split(":");
            if(tag == null) {
                tagManager.createTag(path + map.getValue(), keys[1], keys[2]);
            } else {
                ModifiableValueMap valueMap = tag.adaptTo(Resource.class).adaptTo(ModifiableValueMap.class);
                valueMap.put("jcr:title", keys[1]);
                valueMap.put("jcr:description", keys[2]);
            }
        }
    }

    /**
     * 创建项目标签
     * @param resourceResolver
     * @param projectNormals
     * @param path
     * @throws Exception
     */
    public static void createProjectTag(ResourceResolver resourceResolver,
                                        List<ProjectNormal> projectNormals,
                                        String path) throws Exception {
        // 获取TagManager对象
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

        for (ProjectNormal project : projectNormals) {
            Tag tag = tagManager.resolve(path + project.getProjectNumber());
            if (tag == null) {
                tagManager.createTag(path + project.getProjectNumber(), project.getProjectName(), project.getCompany());
            } else {
                ModifiableValueMap valueMap = tag.adaptTo(Resource.class).adaptTo(ModifiableValueMap.class);
                valueMap.put("jcr:title", project.getProjectName());
                valueMap.put("jcr:description", project.getCompany());
            }
        }
    }

    /**
     * 创建资产到DAM
     * @param resourceResolver
     * @param inovanceAssetMap
     * @param parentPath
     */
    public static void doResourceOperation(ResourceResolver resourceResolver,
                                           Map<BTProductInformation, List<InovanceAsset>> inovanceAssetMap,
                                           String parentPath,
                                           Map<String, String> offeringMap) throws Exception {
        // 获取AssetManager对象
        AssetManager assetManager = resourceResolver.adaptTo(AssetManager.class);

        File file;
        InputStream inputStream = null;
        if (assetManager != null && inovanceAssetMap != null && !inovanceAssetMap.isEmpty()) {
            for (Map.Entry<BTProductInformation, List<InovanceAsset>> assetMap : inovanceAssetMap.entrySet()) {
                BTProductInformation information = assetMap.getKey();
                List<InovanceAsset> inovanceAssetList = assetMap.getValue();

                // 路径 如 /ProdDoc/CY/19120037-CY/A00/test1.pptx
                String filePath = information.getDocumentLocation();
                file = new File("D:/test.pptx");
                if (file.exists()) {
                    // 获取要上传的文件的输入流
                    inputStream = new FileInputStream(file);
                    // 处理mineType
                    String[] fileArr = filePath.split("\\.");
                    // 上传资产并获取上传后的Resource对象
                    String importPath = parentPath + filePath;
                    Asset asset = assetManager.createAsset(importPath, inputStream, fileArr[fileArr.length - 1], true);
                    // 获取资源
                    Resource resource = asset.adaptTo(Resource.class);

                    Resource metaDataResource =  resource.getChild("jcr:content/metadata");

                    // 获取节点
                    Node node = metaDataResource.adaptTo(Node.class);

                    // 添加节点
                    if (metaDataResource.getChild("document") == null) {
                        node.addNode("document");
                    }
                    if (metaDataResource.getChild("marketing-base-info") == null) {
                        node.addNode("marketing-base-info");
                    }
                    if (metaDataResource.getChild("approval") == null) {
                        node.addNode("approval");
                    }
                    if (metaDataResource.getChild("offering") == null) {
                        node.addNode("offering");
                    }
                    if (metaDataResource.getChild("project") == null) {
                        node.addNode("project");
                    }

                    // 获取
                    Resource documentResource = metaDataResource.getChild("document");
                    ModifiableValueMap documentMetadata = documentResource.adaptTo(ModifiableValueMap.class);

                    Resource marketingResource = metaDataResource.getChild("marketing-base-info");
                    ModifiableValueMap marketingMetadata = marketingResource.adaptTo(ModifiableValueMap.class);

                    Resource approvalResource = metaDataResource.getChild("approval");
                    ModifiableValueMap approvalMetadata = approvalResource.adaptTo(ModifiableValueMap.class);

                    Resource offeringResource = metaDataResource.getChild("offering");
                    ModifiableValueMap offeringMetadata = offeringResource.adaptTo(ModifiableValueMap.class);

                    Resource projectResource = metaDataResource.getChild("project");
                    ModifiableValueMap projectMetadata = projectResource.adaptTo(ModifiableValueMap.class);

                    // 资料属性标签
                    buildProfile(documentMetadata, marketingMetadata, information, importPath);

                    // offering
                    buildOffering(offeringMetadata, inovanceAssetList, offeringMap);

                    // 项目
                    buildProject(projectMetadata, inovanceAssetList);

                    // 发布下游系统
                    buildPublishApplication(marketingMetadata);
                }
            }
            // 提交修改
            resourceResolver.commit();
        }
        if (inputStream != null) inputStream.close();
    }

    /**
     * 构建资产实体
     * @param btProductInformation
     * @param projectNormal
     * @param pbiCatalog
     * @param pbiSalesCatalog
     * @return
     */
    private static InovanceAsset ConverToInovanceAsset(BTProductInformation btProductInformation,
                                                       ProjectNormal projectNormal,
                                                       PbiCatalog pbiCatalog,
                                                       PbiSalesCatalog pbiSalesCatalog) {
        InovanceAsset asset = new InovanceAsset();
        asset.setMaterialVersion(btProductInformation.getDocumentVersion());
        asset.setStorageLocation(btProductInformation.getDocumentLocation());
        asset.setMaterialStatus(btProductInformation.getDocumentState());
        asset.setDocumentNumber(btProductInformation.getDocumentNumber());
        asset.setDocumentName(btProductInformation.getDocumentName());
        asset.setSecurityLevel(btProductInformation.getSecretDegree());
        asset.setFileFormat(btProductInformation.getDocumentClassification());
        asset.setCreateTime(btProductInformation.getCreateTime());
        asset.setOwner(btProductInformation.getModifier());
        asset.setCode(btProductInformation.getDocumentName());
        asset.setOfferingId(pbiCatalog.getCataId());
        asset.setOfferingChineseName(pbiCatalog.getCataName());
        asset.setOfferingEnglishName(pbiCatalog.getCataName());
//        asset.setOfferingLifecycle(pbiCatalog.getccc);
        asset.setProductCatalog(pbiSalesCatalog.getCataId());
        asset.setProjectId(projectNormal.getProjectNumber());
        asset.setProjectName(projectNormal.getProjectName());
        asset.setProjectType(projectNormal.getProjectType());
        return asset;
    }

    private static void buildProfile(ModifiableValueMap documentMetadata,
                                     ModifiableValueMap marketingMetadata,
                                     BTProductInformation information, String importPath) {
        // document
        documentMetadata.put("materialVersion", information.getDocumentVersion());
        documentMetadata.put("storageLocation", importPath);
        documentMetadata.put("materialStatus", information.getDocumentState());
        // marketing
        marketingMetadata.put("documentNumber", information.getDocumentNumber());
        marketingMetadata.put("documentName", information.getDocumentName());
        marketingMetadata.put("securityLevel", information.getSecretDegree());
        marketingMetadata.put("fileFormat", information.getDocumentClassification());
        marketingMetadata.put("documentSource", information.getDocumentLocation());
        marketingMetadata.put("createTime", information.getCreateTime());
        marketingMetadata.put("owner", information.getModifier());
        marketingMetadata.put("code", information.getDocumentVersion());
    }
    private static void buildOffering(ModifiableValueMap offeringMetadata,
                                      List<InovanceAsset> inovanceAssetList,
                                      Map<String, String> offeringMap) {

        // offeing
        if (!inovanceAssetList.isEmpty()) {
            offeringMetadata.put("offeringId", inovanceAssetList.stream().map(ele -> "inovance:offering:offering_id:" + ele.getOfferingId()).collect(Collectors.toList()));
            offeringMetadata.put("offeringChineseName", inovanceAssetList.stream().map(InovanceAsset::getOfferingChineseName).collect(Collectors.toList()));
            offeringMetadata.put("offeringEnglishName", inovanceAssetList.stream().map(InovanceAsset::getOfferingEnglishName).collect(Collectors.toList()));
            offeringMetadata.put("offeringLifecycle", "");
            offeringMetadata.put("salesCatalog", inovanceAssetList.stream()
                    .filter(inovanceAsset -> StringUtils.isNotBlank(offeringMap.get(inovanceAsset.getOfferingId())))
                    .map(inovanceAsset -> "inovance:salescatalog" + offeringMap.get(inovanceAsset.getOfferingId()).replaceAll("/", ":"))
                    .collect(Collectors.toList()));
        }
    }

    private static void buildProject(ModifiableValueMap projectMetadata,
                                     List<InovanceAsset> inovanceAssetList) {
        if (!inovanceAssetList.isEmpty()) {
            projectMetadata.put("projectId", inovanceAssetList.stream().map(InovanceAsset::getProjectId).collect(Collectors.toList()));
            projectMetadata.put("projectName", inovanceAssetList.stream().map(InovanceAsset::getProjectName).collect(Collectors.toList()));
        }
    }

    private static void buildPublishApplication(ModifiableValueMap marketingMetadata) {
        marketingMetadata.put("publishApplication", "掌上汇川/官网");
    }


}
