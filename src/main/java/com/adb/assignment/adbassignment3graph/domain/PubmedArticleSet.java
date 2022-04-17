package com.adb.assignment.adbassignment3graph.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "PubmedArticleSet")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PubmedArticleSet implements Serializable {

    private static final long serialVersionUID = 1L;


    public List<PubMedArticle> getPubmedArticle() {
        return pubmedArticles;
    }

    public void setPubmedArticle(List<PubMedArticle> pubmedArticles) {
        this.pubmedArticles = pubmedArticles;
    }

    @XmlElement(name = "PubmedArticle")
    public List<PubMedArticle> pubmedArticles;


    public PubmedArticleSet() {
        super();
    }

    public PubmedArticleSet(List<PubMedArticle> pubmedArticles) {
        this.pubmedArticles = pubmedArticles;
    }


}
