package com.adb.assignment.adbassignment3graph.domain;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@XmlRootElement(name = "Article")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "ArticleTitle")
    private String articleTitle;

    @XmlElement(name = "AuthorList")
    private Authors authorList;

    @XmlElement(name = "Journal")
    private Journal journal;

    @XmlElement(name = "Abstract")
    private Abstract anAbstract;


}