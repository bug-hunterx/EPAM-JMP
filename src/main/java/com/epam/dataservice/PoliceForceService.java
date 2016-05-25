package com.epam.dataservice;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PoliceForceService{

    private static final String POLICE_FORCE_CSV = "src/main/resources/police_force.csv";
    private final String PHONE_PREFIX = "13163862";
    private Map<String,Integer> forceMap = new HashMap<>();
    private AtomicInteger executionCount = new AtomicInteger(0);
    private final int HALT_AT_EXECUTION = 10;
    private final long HALT_FOR = 1000 * 2;

    public PoliceForceService(){
        init();
    }

    public  void  init(){
        Iterable<CSVRecord> records = null;
        try {
            Reader reader = new FileReader(POLICE_FORCE_CSV);
            records = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
            for (CSVRecord record : records) {
                Integer key = Integer.valueOf(record.get(0));
                String value = record.get(1);
                forceMap.put(value, key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getContactNo(String policeForceName){
        if(executionCount.get() == HALT_AT_EXECUTION){
            try {
                Thread.sleep(HALT_FOR);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Integer idx = forceMap.get(policeForceName);
        executionCount.incrementAndGet();
        return idx == null ? PHONE_PREFIX : PHONE_PREFIX + idx.toString() ;
    }

    public static void main(String[] args) {
        PoliceForceService ps = new PoliceForceService();
        for (String forceName: ps.forceMap.keySet() ) {
            System.out.println("fetching contact no for " + ps.forceMap.get(forceName));
            ps.getContactNo(forceName);
        }
    }
}
