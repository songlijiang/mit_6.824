package com.slj.mit.mapreduce.wordcount;

import com.google.common.collect.Lists;
import com.slj.mit.mapreduce.function.MapFunction;
import com.slj.mit.utils.Pair;

import java.util.List;

/**
 * @author songlijiang
 * @version 2019/8/9 17:09
 */
public class WordMapFunction implements MapFunction<String,String,String,Integer> {

    @Override
    public List<Pair<String,Integer>> map(Pair<String,String> input) {
        return Lists.newArrayList(new Pair<>(input.getKey(), 1));
    }
}
