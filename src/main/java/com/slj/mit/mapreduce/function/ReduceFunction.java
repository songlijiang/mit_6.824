package com.slj.mit.mapreduce.function;

import com.slj.mit.utils.Pair;

import java.util.List;

/**
 * @author songlijiang
 * @version 2019/8/9 16:35
 */
@FunctionalInterface
public interface ReduceFunction<T,R,M>{
    Pair<T,M> reduce(Pair<T, List<R>> keyValues);
}
