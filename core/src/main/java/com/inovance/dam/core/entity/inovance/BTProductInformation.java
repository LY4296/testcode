package com.inovance.dam.core.entity.inovance;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents BTProductInformation and holds information about documents.
 * @author Leon
 */
@Getter
@Setter
public class BTProductInformation implements Serializable {

    private static final long serialVersionUID = 976607561041556936L;

    /**
     * Document number
     */
    @JsonAlias("docnumber")
    private String documentNumber;

    /**
     * Document name
     */
    @JsonAlias("docname")
    private String documentName;

    /**
     * Document version
     */
    @JsonAlias("docversion")
    private String documentVersion;

    /**
     * Document state
     */
    @JsonAlias("docstate")
    private String documentState;

    /**
     * Release time
     */
    @JsonAlias("releasdtime")
    private Date releaseTime;

    /**
     * Modifier
     */
    @JsonAlias("modifier")
    private String modifier;

    /**
     * Document type
     */
    @JsonAlias("doctype")
    private String documentType;

    /**
     * Document classification
     */
    @JsonAlias("docclassification")
    private String documentClassification;

    /**
     * Secret degree
     */
    @JsonAlias("secretdegree")
    private String secretDegree;

    /**
     * Document location
     */
    @JsonAlias("doclocation")
    private String documentLocation;

    /**
     * File name
     */
    @JsonAlias("filename")
    private String filename;

    /**
     * File size
     */
    @JsonAlias("filesize")
    private Long fileSize;

    /**
     * Attachment location
     */
    @JsonAlias("attachlocation")
    private String attachmentLocation;

    /**
     * Create time
     */
    @JsonAlias("createtime")
    private Date createTime;

    /**
     * Update time
     */
    @JsonAlias("updatetime")
    private Date updateTime;

    /**
     * Version label
     */
    @JsonAlias("versionlabel")
    private String versionLabel;

    @Override
    public String toString() {
        return "BTProductInformation{" +
                "documentNumber='" + documentNumber + '\'' +
                ", documentName='" + documentName + '\'' +
                ", documentVersion='" + documentVersion + '\'' +
                ", documentState='" + documentState + '\'' +
                ", releaseTime=" + releaseTime +
                ", modifier='" + modifier + '\'' +
                ", documentType='" + documentType + '\'' +
                ", documentClassification='" + documentClassification + '\'' +
                ", secretDegree='" + secretDegree + '\'' +
                ", documentLocation='" + documentLocation + '\'' +
                ", filename='" + filename + '\'' +
                ", fileSize=" + fileSize +
                ", attachmentLocation='" + attachmentLocation + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", versionLabel='" + versionLabel + '\'' +
                '}';
    }
}
