package com.slj.mit.mapreduce.function;

import com.slj.mit.utils.Pair;

import java.util.List;

/**
 * @author songlijiang
 * @version 2019/8/9 16:34
 */
@FunctionalInterface
public  interface MapFunction<T,R,M,H>{

    List<Pair<M,H>> map(Pair<T, R> input);

}
