package com.slj.mit.mapreduce.wordcount;

import com.slj.mit.mapreduce.function.MapFunction;
import com.slj.mit.mapreduce.function.Schedule;
import com.slj.mit.utils.FileUtils;
import com.slj.mit.utils.JsonUtils;
import com.slj.mit.utils.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author songlijiang
 * @version 2019/8/9 16:39
 */
@Data
@AllArgsConstructor
public class WordMapSchedule implements Schedule {

        private String jobName;

        private int rCount;

        private List<String> inputFiles;

        private MapFunction<String, String, String, Integer> mapFunction;

        @Override
        public void schedule() {

        for (int i = 0; i < inputFiles.size(); i++) {
            String inputFileName = inputFiles.get(i);

            //readData
            List<Pair<String, String>> inputs = FileUtils.readLines(inputFileName).stream()
                    .map(e-> new Pair<>(e, inputFileName)).collect(Collectors.toList());
            //doMap
            List<Pair<String, Integer>> temp = inputs.stream().map(e -> mapFunction.map(e))
                    .flatMap(Collection::stream).collect(Collectors.toList());
            //hash
            Map<Integer, List<Pair<String, Integer>>> mapResult = temp.stream()
                    .collect(Collectors.groupingBy(e -> e.getKey().hashCode() % rCount));

            final int finalI = i;
            //write
            mapResult.forEach((key, value) -> {
                String fileName = getMapFileName(jobName, finalI, key);
                List<String> collect = value.stream().map(JsonUtils::toJson).collect(Collectors.toList());
                try {
                    FileUtils.writeLines(fileName, collect);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static String getMapFileName(String jobName, int mIndex, int rIndex) {
        return "map-" + jobName + "-" + mIndex + "-" + rIndex;
    }

}
