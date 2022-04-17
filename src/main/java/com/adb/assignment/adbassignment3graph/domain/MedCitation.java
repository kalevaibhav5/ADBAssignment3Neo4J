package com.adb.assignment.adbassignment3graph.domain;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@XmlRootElement(name = "MedlineCitation")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedCitation implements Serializable {

    private static final long serialVersionUID = 1L;

    public MedCitation() {
    }

    public MedCitation(Integer pmid) {
        this.pmid = pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    @XmlElement(name = "PMID")
    private Integer pmid;

    @XmlElement(name = "Article")
    private Article article;

    @XmlElement(name = "DateCompleted")
    private DateCompleted dateCompleted;

    @XmlElement(name = "KeywordList")
    private KeywordList keywordList;

    @XmlElement(name = "MeshHeadingList")
    private MeshHeadingList meshHeadingList;

}