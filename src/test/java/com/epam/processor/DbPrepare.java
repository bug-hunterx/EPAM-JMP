package com.epam.processor;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.dataset.xml.XmlDataSetWriter;
import org.dbunit.util.fileloader.CsvDataFileLoader;
import org.dbunit.util.fileloader.DataFileLoader;
import org.junit.Test;

import java.io.*;

/**
 * Created by bill on 16-5-25.
 */
public class DbPrepare {
    public static String filepath = "/home/bill/IdeaProjects/EPAM-JMP/CSV/";

    public void dumpTables(IDataSet dataSet) {
        try {
            ITableIterator tableIterator = dataSet.iterator();
            while (tableIterator.next()) {
                ITable table = tableIterator.getTable();
                System.out.println(table);
                int count = table.getRowCount();
                System.out.println("Row Count="+count);
            }
        } catch (DataSetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void csvToXml() {
        DataFileLoader loader = new CsvDataFileLoader();
        String[] tables = new String[0];
        try {
            IDataSet dataSet = loader.load(filepath);
            tables = dataSet.getTableNames();
        } catch (DataSetException e) {
            e.printStackTrace();
        }
        System.out.println(tables);
    }

    @Test
    public void loadXml() {
        try {
            IDataSet dataSet2 = new XmlDataSet(new FileInputStream("tables.xml"));
            dumpTables(dataSet2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataSetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void csvUtil() {
        try {
            IDataSet dataSet = new CsvDataSet(new File(filepath));
            dumpTables(dataSet);
            FileOutputStream fos = new FileOutputStream("tables.xml");
            XmlDataSetWriter xmlWriter = new XmlDataSetWriter(fos, null);
            xmlWriter.write(dataSet);
        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
