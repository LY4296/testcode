package com.inovance.dam.core.entity.inovance;

import java.io.Serializable;

public class PLMProductInformation implements Serializable {

    private static final long serialVersionUID = 976607561041556936L;

    private String partnumber;

    private String productseriescode;

    private String chinesedesc;

    private String englishdesc;

    private String offering;

    private String projectnum;

    private String specmode;

    private String docnumber;

    private String docversion;

    private String doctype;

    private String docname;

    private String releasdtime;

    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    public String getProductseriescode() {
        return productseriescode;
    }

    public void setProductseriescode(String productseriescode) {
        this.productseriescode = productseriescode;
    }

    public String getChinesedesc() {
        return chinesedesc;
    }

    public void setChinesedesc(String chinesedesc) {
        this.chinesedesc = chinesedesc;
    }

    public String getEnglishdesc() {
        return englishdesc;
    }

    public void setEnglishdesc(String englishdesc) {
        this.englishdesc = englishdesc;
    }

    public String getOffering() {
        return offering;
    }

    public void setOffering(String offering) {
        this.offering = offering;
    }

    public String getProjectnum() {
        return projectnum;
    }

    public void setProjectnum(String projectnum) {
        this.projectnum = projectnum;
    }

    public String getSpecmode() {
        return specmode;
    }

    public void setSpecmode(String specmode) {
        this.specmode = specmode;
    }

    public String getDocnumber() {
        return docnumber;
    }

    public void setDocnumber(String docnumber) {
        this.docnumber = docnumber;
    }

    public String getDocversion() {
        return docversion;
    }

    public void setDocversion(String docversion) {
        this.docversion = docversion;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getReleasdtime() {
        return releasdtime;
    }

    public void setReleasdtime(String releasdtime) {
        this.releasdtime = releasdtime;
    }

    @Override
    public String toString() {
        return "PLMProductInformation{" +
                "partnumber='" + partnumber + '\'' +
                ", productseriescode='" + productseriescode + '\'' +
                ", chinesedesc='" + chinesedesc + '\'' +
                ", englishdesc='" + englishdesc + '\'' +
                ", offering='" + offering + '\'' +
                ", projectnum='" + projectnum + '\'' +
                ", specmode='" + specmode + '\'' +
                ", docnumber='" + docnumber + '\'' +
                ", docversion='" + docversion + '\'' +
                ", doctype='" + doctype + '\'' +
                ", docname='" + docname + '\'' +
                ", releasdtime='" + releasdtime + '\'' +
                '}';
    }
}
