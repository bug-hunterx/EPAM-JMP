package com.epam.processor;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.util.TableFormatter;
import org.dbunit.util.fileloader.CsvDataFileLoader;
import org.dbunit.util.fileloader.DataFileLoader;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by bill on 16-5-25.
 */
public class DbPrepare {
    public static String CSV_FILE_PATH = Paths.get("src","main","resources").toString();
    public static String DB_DATA_PATH = Paths.get("src","test","resources").toString();
    public static Path table_xml = Paths.get(DB_DATA_PATH,"tables.xml");
    public static Path sampleData_xml = Paths.get(DB_DATA_PATH,"sampleData.xml");

    public void dumpTables(IDataSet dataSet) {
        try {
            ITableIterator tableIterator = dataSet.iterator();
            while (tableIterator.next()) {
                ITable table = tableIterator.getTable();
                System.out.println(new TableFormatter().format(table));
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
            IDataSet dataSet = loader.load(CSV_FILE_PATH);
            tables = dataSet.getTableNames();
        } catch (DataSetException e) {
            e.printStackTrace();
        }
        System.out.println(tables);
    }

    @Test
    public void flatXmlDataSetTest() {
//        IDataSet dataSet = new FlatXmlDataSetLoader(new FileInputStream(table_xml.toString()));
    }
    @Test
    public void csvToFlatXml() throws Exception {
        IDataSet dataSet = new CsvDataSet(new File(CSV_FILE_PATH));
//        dumpTables(dataSet);
        System.out.println("Writing to " + sampleData_xml.toString());
        FileOutputStream fos = new FileOutputStream(sampleData_xml.toString());
        FlatXmlWriter flatXmlWriter = new FlatXmlWriter(fos);
        flatXmlWriter.write(dataSet);
    }

    @Test
    public void loadXml() {
        try {
            System.out.println("Loading " + table_xml.toString());
            IDataSet dataSet2 = new XmlDataSet(new FileInputStream(table_xml.toString()));
            dumpTables(dataSet2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataSetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void csvToDbunitXml() throws Exception {
        IDataSet dataSet = new CsvDataSet(new File(CSV_FILE_PATH));
        dumpTables(dataSet);
        FileOutputStream fos = new FileOutputStream(sampleData_xml.toString());
        FlatXmlWriter flatXmlWriter = new FlatXmlWriter(fos);
//        XmlDataSetWriter xmlWriter = new XmlDataSetWriter(fos, null);
//        xmlWriter.write(dataSet);
        flatXmlWriter.write(dataSet);
    }
}
