package com.adb.assignment.adbassignment3graph.domain;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@XmlRootElement(name = "Abstract")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Abstract implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "AbstractText")
    private String AbstractText;
}