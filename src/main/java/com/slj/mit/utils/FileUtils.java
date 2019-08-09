package com.slj.mit.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author songlijiang
 * @version 2019/8/9 16:41
 */
@Slf4j
public class FileUtils {


    public static List<String> readLines(String fileName){
        File file = new File(fileName);
        try {
           return org.apache.commons.io.FileUtils.readLines(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("FileUtils readLines error ",e);
            return Lists.newArrayList();
        }
    }


    public static void writeLines(String fileName,List<String> contents) throws IOException {
        File file = new File(fileName);
        org.apache.commons.io.FileUtils.writeLines(file,contents);
    }
}
