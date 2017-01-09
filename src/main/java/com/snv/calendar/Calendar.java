package com.snv.calendar;

import lombok.Data;

import java.io.Serializable;

@Data
public class Calendar implements Serializable {

    /**
     * Serial Version for Serialization
     */
    private static final long serialVersionUID = -1654259065593124107L;

    /**
     * technical identifier
     */
    private Long id;

    /**
     * title of event
     */
    private String title;

    /**
     * event start date or dateTime
     */
    private String start;

    /**
     * event end date or dateTime
     */
    private String end;

    /**
     * event color
     */
    private String color;
}
