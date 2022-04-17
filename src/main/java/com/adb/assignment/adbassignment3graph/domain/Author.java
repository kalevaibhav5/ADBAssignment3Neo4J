package com.adb.assignment.adbassignment3graph.domain;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@XmlRootElement(name = "Author")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "LastName")
    private String lastName;

    @XmlElement(name = "ForeName")
    private String foreName;

    @XmlElement(name="CollectiveName")
    private String collectiveName;

    @XmlElement(name="Initials")
    private String initials;
}
