package com.adb.assignment.adbassignment3graph.domain;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Getter
@XmlRootElement(name = "AuthorList")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Authors implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Author")
    private List<Author> authorList;
}
