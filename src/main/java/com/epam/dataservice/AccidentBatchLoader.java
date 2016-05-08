package com.epam.dataservice;

import com.epam.data.RoadAccident;
import com.epam.dataservice.RoadAccidentParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;


public class AccidentBatchLoader implements Runnable {
        private Integer batchSize;
        private BlockingQueue<CopyOnWriteArrayList<RoadAccident>> dataQueue;
        private String dataFileName;
        private RoadAccidentParser roadAccidentParser;

        public AccidentBatchLoader(int batchSize, BlockingQueue<CopyOnWriteArrayList<RoadAccident>> dataQueue, String dataFileName){
            this.batchSize = batchSize;
            this.dataQueue = dataQueue;
            this.dataFileName = dataFileName;
            this.roadAccidentParser=new RoadAccidentParser();
        }

        @Override
        public void run() {
            int dataCount = 0;
            try{
                System.out.println("Reader  Thread ["+Thread.currentThread().getId()+"] Started  ");
                System.out.println("inside run " +  Thread.currentThread().getName() + "  Batch Size = " + batchSize);
                Iterator<CSVRecord> recordIterator = getRecordIterator();
                int batchCount = 0;
                CopyOnWriteArrayList<RoadAccident> roadAccidentBatch = null;

                boolean isDataLoadFinished = false;
                while(!isDataLoadFinished){
                    roadAccidentBatch = getNextBatch(recordIterator);
                    dataCount = dataCount + roadAccidentBatch.size();
                    if(roadAccidentBatch.isEmpty()){
                        isDataLoadFinished = true;
                    }else{
                        ++batchCount;
                        System.out.println(" Completed reading " + dataCount + " in " + batchCount + " batches for " + dataFileName);
                    }
                    System.out.println(" Reader Thread trying to put to dataQueue  Thread ["+Thread.currentThread().getId()+"]  ");
                    dataQueue.put(roadAccidentBatch);

                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        private Iterator<CSVRecord> getRecordIterator() throws Exception{
            Reader reader = new FileReader(dataFileName);
            return new CSVParser(reader, CSVFormat.EXCEL.withHeader()).iterator();
        }

        private CopyOnWriteArrayList<RoadAccident> getNextBatch(Iterator<CSVRecord> recordIterator){
            CopyOnWriteArrayList<RoadAccident> roadAccidentBatch = new CopyOnWriteArrayList<RoadAccident>();
            int recordCount = 0;
            RoadAccident roadAccidentItem = null;
            while(recordCount < batchSize && recordIterator.hasNext() ){
                roadAccidentItem = roadAccidentParser.parseRecord(recordIterator.next());
                if(roadAccidentItem != null){
                    roadAccidentBatch.add(roadAccidentItem);
                    recordCount++;
                }
            }
            return  roadAccidentBatch;
        }
    }
