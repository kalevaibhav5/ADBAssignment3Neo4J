package com.adb.assignment.adbassignment3graph.domain;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@XmlRootElement(name = "PubDate")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PubDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Year")
    private Integer year;

    @XmlElement(name = "Month")
    private String month;

    @XmlElement(name = "Day")
    private Integer day;

}
