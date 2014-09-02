package org.wso2.toolbox.nlp.test;

import org.apache.log4j.Logger;
import org.wso2.toolbox.nlp.operation.FindNameEntityTypeViaDictionaryExecutor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Chanuka on 9/1/14 AD.
 */
public class FindNameEntityTypeViaDictionaryExecutorTest {

    private static Logger logger = Logger.getLogger(DictionaryTest.class);
    private static long start;
    private static long end;
    private static List<String> results;
    private static StringBuilder textBuffer = null;
    private static FindNameEntityTypeViaDictionaryExecutor executor = null;

    public static void main(String[] args) throws Exception {
        setUp();
        testFindNameEntityTypeViaDictionaryExecutor("PERSON","src/main/resources/test/dictionaryTest.xml");
        testFindNameEntityTypeViaDictionaryExecutor("LOCATION","src/main/resources/test/dictionaryTest.xml");
        testFindNameEntityTypeViaDictionaryExecutor("ORGANIZATION","src/main/resources/test/dictionaryTest.xml");
    }

    public static void setUp() throws Exception {
        start = System.currentTimeMillis();
        executor = new FindNameEntityTypeViaDictionaryExecutor();
        end = System.currentTimeMillis();

        logger.info("Time to construct FindNameEntityTypeViaDictionaryExecutor instance : [" + ((end - start)/1000f) + " sec]");

        textBuffer = new StringBuilder();

        InputStream inputStream = ClassLoader.getSystemResourceAsStream("test/nameEntityTypeTestFile");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            textBuffer.append(line);
        }

        inputStream.close();
        bufferedReader.close();

    }


    private static void testFindNameEntityTypeViaDictionaryExecutor(String entityType, String xmlFilePath){
        System.out.println("==================================================================================");
        logger.info(String.format("Executing FindNameEntityTypeViaDictionaryExecutor.findNameEntityTypeViaDictionary(text, dictionaryFilePath, entityType)"));
        start = System.currentTimeMillis();
        results = executor.findNameEntityTypeViaDictionary(textBuffer.toString(), xmlFilePath, entityType);
        end = System.currentTimeMillis();
        logger.info(String.format("Results of Test: %s", results));
        System.out.println("-----------------------------------------------------------------------------------");
        logger.info(String.format("Time to execute FindNameEntityTypeViaDictionaryExecutor.findNameEntityTypeViaDictionary for %s : [%f sec]", entityType, ((end - start)/1000f)));

    }

}
