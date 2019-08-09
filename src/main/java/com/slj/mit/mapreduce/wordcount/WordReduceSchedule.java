package com.slj.mit.mapreduce.wordcount;

import com.slj.mit.mapreduce.function.ReduceFunction;
import com.slj.mit.mapreduce.function.Schedule;
import com.slj.mit.utils.FileUtils;
import com.slj.mit.utils.JsonUtils;
import com.slj.mit.utils.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songlijiang
 * @version 2019/8/9 16:53
 */
@Data
@AllArgsConstructor
public class WordReduceSchedule implements Schedule {

    private String jobName;

    private int rCount;

    private int mCount;

    private ReduceFunction<String, Integer, Integer> reduceFunction;


    @Override
    public void schedule() {

        for (int i = 0; i < rCount; i++) {

            for (int j = 0; j < mCount; j++) {
                try {
                    //read data
                    List<String> readLines = FileUtils.readLines(WordMapSchedule.getMapFileName(jobName, j, i));

                    //group by key
                    List<Pair<String, List<Integer>>> pairList = readLines.stream().map(e ->
                            JsonUtils.<Pair<String, Integer>>fromJson(e, new TypeReference<Pair<String, Integer>>() {
                            })
                    ).collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())))
                            .entrySet().stream().map(e -> new Pair<>(e.getKey(), e.getValue())).collect(Collectors.toList());

                    //do reduce
                    List<Pair<String, Integer>> result = pairList.stream().map(e -> reduceFunction.reduce(e)).collect(Collectors.toList());

                    //do save
                    List<String> collect = result.stream().map(JsonUtils::toJson).collect(Collectors.toList());
                    FileUtils.writeLines(getReduceFileName(jobName, i), collect);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    public static String getReduceFileName(String jobName, int rIndex) {

        return "reduce-" + jobName + rIndex;
    }

}