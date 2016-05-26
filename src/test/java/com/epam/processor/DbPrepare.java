package com.epam.processor;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.dataset.xml.XmlDataSetWriter;
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
    public static String DB_DATA_PATH = Paths.get("src","main","resources").toString();
    public static Path table_xml = Paths.get(DB_DATA_PATH,"tables.xml");

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
            IDataSet dataSet = loader.load(DB_DATA_PATH);
            tables = dataSet.getTableNames();
        } catch (DataSetException e) {
            e.printStackTrace();
        }
        System.out.println(tables);
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
    public void csvToDbunitXml() {
        try {
            IDataSet dataSet = new CsvDataSet(new File(DB_DATA_PATH));
            dumpTables(dataSet);
            FileOutputStream fos = new FileOutputStream(table_xml.toString());
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
