package org.wso2.toolbox.nlp.test;

import org.apache.log4j.Logger;
import org.wso2.toolbox.nlp.operation.FindNameEntityTypeViaDictorneryExecutor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Chanuka on 9/1/14 AD.
 */
public class FindNameEntityTypeViaDictorneryExecutorTest {

    private static Logger logger = Logger.getLogger(DictionaryTest.class);
    private static long start;
    private static long end;
    private static List<String> results;
    private static StringBuilder textBuffer = null;
    private static FindNameEntityTypeViaDictorneryExecutor executor = null;
    public static void main(String[] args) throws Exception {
        setUp();
        testFindNameEntityTypeViaDictionaryExecutor("PERSON","src/main/resources/dictionaryTest.xml");
        testFindNameEntityTypeViaDictionaryExecutor("LOCATION","src/main/resources/dictionaryTest.xml");
        testFindNameEntityTypeViaDictionaryExecutor("ORGANIZATION","src/main/resources/dictionaryTest.xml");
    }

    public static void setUp() throws Exception {
        start = System.currentTimeMillis();
        executor = new FindNameEntityTypeViaDictorneryExecutor();
        end = System.currentTimeMillis();

        logger.info("Time to construct NameEntityTypeExecutor instance : [" + ((end - start)/1000f) + " sec]");

        textBuffer = new StringBuilder();

        InputStream inputStream = ClassLoader.getSystemResourceAsStream("nameEntityTypeTestFile");
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
        start = System.currentTimeMillis();
        results = executor.findNameEntityTypeViaDictionary(textBuffer.toString(), xmlFilePath, entityType);
        end = System.currentTimeMillis();
        logger.info(String.format("Results of Test: %s", results));
        System.out.println("-----------------------------------------------------------------------------------");
        logger.info(String.format("Time to execute FindNameEntityTypeViaDictorneryExecutor.findNameEntityTypeViaDictionary for %s : [%f sec]", entityType, ((end - start)/1000f)));

    }

}
