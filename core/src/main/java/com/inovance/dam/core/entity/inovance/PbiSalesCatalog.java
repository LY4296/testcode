package com.inovance.dam.core.entity.inovance;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents the sales catalog tree structure in Inovance.
 * @author Leon
 */
@Getter
@Setter
public class PbiSalesCatalog {

    /**
     * Root ID of the sales catalog.
     */
    @JsonAlias("ROOTID")
    private String rootId;

    /**
     * Root Name of the sales catalog.
     */
    @JsonAlias("ROOTNAME")
    private String rootName;

    /**
     * Level Name of the sales catalog.
     */
    @JsonAlias("LEVELNAME")
    private String levelName;

    /**
     * Catalog ID of the sales catalog.
     */
    @JsonAlias("CATAID")
    private String cataId;

    /**
     * Catalog Name of the sales catalog.
     */
    @JsonAlias("CATANAME")
    private String cataName;

    /**
     * Catalog Level of the sales catalog.
     */
    @JsonAlias("CATALEVEL")
    private String cataLevel;

    /**
     * Catalog Type of the sales catalog.
     */
    @JsonAlias("CATATYPE")
    private String cataType;

    /**
     * Catalog Status of the sales catalog.
     */
    @JsonAlias("CATASTATUS")
    private String cataStatus;

    /**
     * English Description of the sales catalog.
     */
    @JsonAlias("ENGLISHDESC")
    private String englishDesc;

    /**
     * Show/Hide Indicator of the sales catalog.
     */
    @JsonAlias("SHOWORNOT")
    private String showOrNot;

    /**
     * Updated By (User) of the sales catalog.
     */
    @JsonAlias("UPDATEDBY")
    private String updatedBy;

    /**
     * Creator (User) of the sales catalog.
     */
    @JsonAlias("CREATOR")
    private String creator;

    /**
     * Creation Time of the sales catalog.
     */
    @JsonAlias("CREATETIME")
    private String createTime;

    /**
     * Update Time of the sales catalog.
     */
    @JsonAlias("UPDATETIME")
    private String updateTime;

    /**
     * Parent ID of the sales catalog.
     */
    @JsonAlias("PARENTID")
    private String parentId;

    /**
     * Parent Name of the sales catalog.
     */
    @JsonAlias("PARENTNAME")
    private String parentName;

    /**
     * Catalog Path of the sales catalog.
     */
    @JsonAlias("CATAPATH")
    private String cataPath;

    /**
     * List of children sales catalogs.
     */
    private List<PbiSalesCatalog> children;

    @Override
    public String toString() {
        return "PbiSalesCatalog{" +
                "rootId='" + rootId + '\'' +
                ", rootName='" + rootName + '\'' +
                ", levelName='" + levelName + '\'' +
                ", cataId='" + cataId + '\'' +
                ", cataName='" + cataName + '\'' +
                ", cataLevel='" + cataLevel + '\'' +
                ", cataType='" + cataType + '\'' +
                ", cataStatus='" + cataStatus + '\'' +
                ", englishDesc='" + englishDesc + '\'' +
                ", showOrNot='" + showOrNot + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", cataPath='" + cataPath + '\'' +
                '}';
    }
}
