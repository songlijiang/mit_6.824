package com.slj.mit.mapreduce.wordcount;

import com.slj.mit.mapreduce.function.ReduceFunction;
import com.slj.mit.utils.Pair;

import java.util.List;

/**
 * @author songlijiang
 * @version 2019/8/9 17:13
 */
public class WordReduceFunction implements ReduceFunction<String,Integer,Integer> {
    @Override
    public Pair<String, Integer> reduce(Pair<String, List<Integer>> keyValues) {
        return new Pair<>(keyValues.getKey(),keyValues.getValue().stream().mapToInt(e->e).sum());
    }
}
