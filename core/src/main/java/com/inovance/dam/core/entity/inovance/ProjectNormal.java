package com.inovance.dam.core.entity.inovance;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents project information from PLM system.
 * @author Leon
 */
@Getter
@Setter
public class ProjectNormal {

    /**
     * Project number.
     */
    @JsonAlias("PROJECTNUMBER")
    private String projectNumber;

    /**
     * Project name.
     */
    @JsonAlias("PROJECTNAME")
    private String projectName;

    /**
     * Company associated with the project.
     */
    @JsonAlias("COMPANY")
    private String company;

    /**
     * Department associated with the project.
     */
    @JsonAlias("DEPARTMENT")
    private String department;

    /**
     * Project type.
     */
    @JsonAlias("PROJECTTYPE")
    private String projectType;

    /**
     * Project manager.
     */
    @JsonAlias("PROJECTMANAGER")
    private String projectManager;

    /**
     * Product line.
     */
    @JsonAlias("PRODUCTLINE")
    private String productLine;

    /**
     * Industry line.
     */
    @JsonAlias("INDUSTRYLINE")
    private String industryLine;

    /**
     * Project phase.
     */
    @JsonAlias("PROJECTPHASE")
    private String projectPhase;

    /**
     * Project status.
     */
    @JsonAlias("PROJECTSTATUS")
    private String projectStatus;

    /**
     * System name.
     */
    @JsonAlias("SYSTEMNAME")
    private String systemName;

    /**
     * Approval time.
     */
    @JsonAlias("APPROVALTIME")
    private String approvalTime;

    /**
     * Create time.
     */
    @JsonAlias("CREATETIME")
    private String createTime;

    /**
     * Update time.
     */
    @JsonAlias("UPDATETIME")
    private String updateTime;

    /**
     * Indicates whether it is an expense.
     */
    @JsonAlias("ISEXPENSE")
    private String isExpense;

    @Override
    public String toString() {
        return "ProjectNormal{" +
                "projectNumber='" + projectNumber + '\'' +
                ", projectName='" + projectName + '\'' +
                ", company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectManager='" + projectManager + '\'' +
                ", productLine='" + productLine + '\'' +
                ", industryLine='" + industryLine + '\'' +
                ", projectPhase='" + projectPhase + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                ", systemName='" + systemName + '\'' +
                ", approvalTime='" + approvalTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", isExpense='" + isExpense + '\'' +
                '}';
    }
}
