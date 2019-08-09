package com.slj.mit.mapreduce.enums;

/**
 * @author songlijiang
 * @version 2019/8/9 16:37
 */
public enum Phase{

    MAP ("map"),
    REDUCE("reduce");

    Phase(String name){
        this.name=name;
    }

    private String name;
}
