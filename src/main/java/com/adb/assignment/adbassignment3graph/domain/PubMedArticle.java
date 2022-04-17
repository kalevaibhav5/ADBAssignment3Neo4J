package com.adb.assignment.adbassignment3graph.domain;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@XmlRootElement(name = "PubmedArticle")
@XmlAccessorType(XmlAccessType.FIELD)
public class PubMedArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    public void setMedCitation(MedCitation medCitation) {
        this.medCitation = medCitation;
    }

    @XmlElement(name = "MedlineCitation")
    private MedCitation medCitation;


    public PubMedArticle() {
        super();
    }

    public PubMedArticle(MedCitation medCitation) {
        this.medCitation = medCitation;
    }
}
