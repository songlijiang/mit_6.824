package com.slj.mit.mapreduce.wordcount;

import com.slj.mit.mapreduce.function.Merge;
import com.slj.mit.utils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.List;

/**
 * @author songlijiang
 * @version 2019/8/9 16:59
 */
@Data
@AllArgsConstructor
public class MergeAll implements Merge {

    private String jobName;

    private int rCount;

    @Override
    public void merge() {

        for (int i = 0; i <rCount ; i++) {
            try {
                List<String> list = FileUtils.readLines(WordReduceSchedule.getReduceFileName(jobName,i));
                FileUtils.writeLines(getMergeFileName(jobName),list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getMergeFileName(String jobName){
        return "mrmerge-"+jobName;
    }



}

