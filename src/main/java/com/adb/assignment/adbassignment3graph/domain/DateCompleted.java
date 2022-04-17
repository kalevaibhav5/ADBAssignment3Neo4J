package com.adb.assignment.adbassignment3graph.domain;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@XmlRootElement(name = "DateCompleted")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DateCompleted implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Year")
    private Integer year;

    @XmlElement(name = "Month")
    private Integer month;

    @XmlElement(name = "Day")
    private Integer day;

}

