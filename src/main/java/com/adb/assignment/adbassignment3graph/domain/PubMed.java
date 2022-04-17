package com.adb.assignment.adbassignment3graph.domain;

import lombok.Builder;

import java.sql.Date;
import java.util.List;

@Builder
public class PubMed {
    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getFirst_author() {
        return first_author;
    }

    public void setFirst_author(String first_author) {
        this.first_author = first_author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    private Integer pmid;
    private String article_title;

    public String getAnabstract() {
        return anabstract;
    }

    public void setAnabstract(String anabstract) {
        this.anabstract = anabstract;
    }

    private String anabstract;
    private String first_author;
    private String publisher;
    private Date publishedDate;
    private String uploader;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    private Integer authorId;

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    private Journal journal;

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    private List<String> keywords;


    public MeshHeadingList getMeshHeadingList() {
        return meshHeadingList;
    }

    public void setMeshHeadingList(MeshHeadingList meshHeadingList) {
        this.meshHeadingList = meshHeadingList;
    }

    private MeshHeadingList meshHeadingList;

    public Authors getAuthors() {
        return authors;
    }

    public void setAuthors(Authors authors) {
        this.authors = authors;
    }

    private Authors authors;

}
