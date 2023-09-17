package com.inovance.dam.core.entity.inovance;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the relationship between sales catalogs and products in Inovance.
 * @author Leon
 */
@Getter
@Setter
public class PbiSalesCat {

    /**
     * Sales Catalog Level 0 Number
     */
    @JsonAlias("SALESCAT_NO_L0")
    private String salesCatNoL0;

    /**
     * Sales Catalog Level 0 Name
     */
    @JsonAlias("SALESCAT_NAME_L0")
    private String salesCatNameL0;

    /**
     * Sales Catalog Level 1 Number
     */
    @JsonAlias("SALESCAT_NO_L1")
    private String salesCatNoL1;

    /**
     * Sales Catalog Level 1 Name
     */
    @JsonAlias("SALESCAT_NAME_L1")
    private String salesCatNameL1;

    /**
     * Sales Catalog Level 2 Number
     */
    @JsonAlias("SALESCAT_NO_L2")
    private String salesCatNoL2;

    /**
     * Sales Catalog Level 2 Name
     */
    @JsonAlias("SALESCAT_NAME_L2")
    private String salesCatNameL2;

    /**
     * Sales Catalog Level 3 Number
     */
    @JsonAlias("SALESCAT_NO_L3")
    private String salesCatNoL3;

    /**
     * Sales Catalog Level 3 Name
     */
    @JsonAlias("SALESCAT_NAME_L3")
    private String salesCatNameL3;

    /**
     * Sales Catalog Level 4 Number
     */
    @JsonAlias("SALESCAT_NO_L4")
    private String salesCatNoL4;

    /**
     * Sales Catalog Level 4 Name
     */
    @JsonAlias("SALESCAT_NAME_L4")
    private String salesCatNameL4;

    /**
     * Offering Number
     */
    @JsonAlias("OFFERING_NO")
    private String offeringNo;

    /**
     * Offering Name
     */
    @JsonAlias("OFFERING_NAME")
    private String offeringName;

    /**
     * Offering Type
     */
    @JsonAlias("OFFERING_TYPE")
    private String offeringType;

    /**
     * Product Number
     */
    @JsonAlias("PRODUCT_NO")
    private String productNo;

    /**
     * Product Model
     */
    @JsonAlias("PRODUCT_MODEL")
    private String productModel;

    @Override
    public String toString() {
        return "PbiSalesCat{" +
                "salesCatNoL0='" + salesCatNoL0 + '\'' +
                ", salesCatNameL0='" + salesCatNameL0 + '\'' +
                ", salesCatNoL1='" + salesCatNoL1 + '\'' +
                ", salesCatNameL1='" + salesCatNameL1 + '\'' +
                ", salesCatNoL2='" + salesCatNoL2 + '\'' +
                ", salesCatNameL2='" + salesCatNameL2 + '\'' +
                ", salesCatNoL3='" + salesCatNoL3 + '\'' +
                ", salesCatNameL3='" + salesCatNameL3 + '\'' +
                ", salesCatNoL4='" + salesCatNoL4 + '\'' +
                ", salesCatNameL4='" + salesCatNameL4 + '\'' +
                ", offeringNo='" + offeringNo + '\'' +
                ", offeringName='" + offeringName + '\'' +
                ", offeringType='" + offeringType + '\'' +
                ", productNo='" + productNo + '\'' +
                ", productModel='" + productModel + '\'' +
                '}';
    }
}
