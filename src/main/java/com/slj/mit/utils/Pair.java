package com.slj.mit.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author songlijiang
 * @version 2019/8/9 16:34
 */
@Data
@AllArgsConstructor
public class  Pair <T,R>{

    public Pair(){

    }

    private T key;

    private R value;

}

