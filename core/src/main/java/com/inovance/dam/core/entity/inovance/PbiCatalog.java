package com.inovance.dam.core.entity.inovance;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents  sales catalog tree in Inovance.
 * @author Leon
 */
@Getter
@Setter
public class PbiCatalog {

    /**
     * Root ID
     */
    @JsonAlias("ROOTID")
    private String rootId;

    /**
     * Root Name
     */
    @JsonAlias("ROOTNAME")
    private String rootName;

    /**
     * Catalog ID
     */
    @JsonAlias("CATAID")
    private String cataId;

    /**
     * Catalog Name
     */
    @JsonAlias("CATANAME")
    private String cataName;

    // TODO: Add English name

    /**
     * Catalog Level
     */
    @JsonAlias("CATALEVEL")
    private String cataLevel;

    /**
     * Parent ID
     */
    @JsonAlias("PARENTID")
    private String parentId;

    /**
     * Parent Name
     */
    @JsonAlias("PARENTNAME")
    private String parentName;

    /**
     * Catalog Path
     */
    @JsonAlias("CATAPATH")
    private String cataPath;

    @Override
    public String toString() {
        return "PbiCatalog{" +
                "rootId='" + rootId + '\'' +
                ", rootName='" + rootName + '\'' +
                ", cataId='" + cataId + '\'' +
                ", cataName='" + cataName + '\'' +
                ", cataLevel='" + cataLevel + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", cataPath='" + cataPath + '\'' +
                '}';
    }
}
