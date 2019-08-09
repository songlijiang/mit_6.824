package com.slj.mit.mapreduce.wordcount;

import com.google.common.collect.Lists;
import com.slj.mit.mapreduce.function.Finish;
import com.slj.mit.mapreduce.function.Merge;
import com.slj.mit.mapreduce.function.Schedule;
import lombok.Data;

import java.util.List;

/**
 * @author songlijiang
 * @version 2019/8/9 17:03
 */
@Data
public class Master {

    private String jobName;

    private List<String> inputFiles;

    private int rCount;

    Schedule mapSchedule;

    Schedule reduceSchedule;

    Merge merge;
    

    public static void main(String[] args) {
        Master master = new Master();
        String jobName="wordCount";
        int rCount=1;
        int mCount=1;
        List<String> files = Lists.newArrayList("wordcount0");
        WordMapSchedule wordMapSchedule =new WordMapSchedule(jobName, rCount, files,new WordMapFunction());
        Schedule reduceSchedule = new WordReduceSchedule(jobName, rCount, mCount, new WordReduceFunction());
        Merge merge = new MergeAll(jobName,rCount);
        Finish finish = () -> System.out.println("end job "+jobName);
        master.run(jobName,files,rCount,wordMapSchedule,reduceSchedule,merge,finish);
    }




    public void run(String jobName, List<String> inputFiles, int rCount, Schedule mapSchedule, Schedule reduceSchedule, Merge merge, Finish finish){

        System.out.println("start job "+jobName);
        this.jobName = jobName;
        this.inputFiles=inputFiles;
        this.rCount=rCount;
        this.mapSchedule=mapSchedule;
        this.reduceSchedule=reduceSchedule;
        this.merge =merge;

        mapSchedule.schedule();
        reduceSchedule.schedule();
        merge.merge();
        finish.finish();

    }

}
