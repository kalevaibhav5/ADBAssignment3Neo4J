package com.adb.assignment.adbassignment3graph.domain;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@XmlRootElement(name = "Journal")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Journal implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Title")
    private String publisher;

    @XmlElement(name = "JournalIssue")
    private JournalIssue journalIssue;

}