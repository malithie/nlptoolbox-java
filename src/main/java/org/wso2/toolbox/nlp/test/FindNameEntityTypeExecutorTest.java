package org.wso2.toolbox.nlp.test;

import org.apache.log4j.Logger;
import org.wso2.toolbox.nlp.operation.FindNameEntityTypeExecutor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Chanuka on 9/1/14 AD.
 */
public class FindNameEntityTypeExecutorTest {

    private static Logger logger = Logger.getLogger(DictionaryTest.class);
    private static long start;
    private static long end;
    private static List<String> results;
    private static List<String> concatenateResults;
    private static StringBuilder textBuffer = null;
    private static FindNameEntityTypeExecutor executor = null;

    public static void main(String[] args) throws Exception {
        setUp();
        testFindNameEntityTypeExecutor("PERSON");
        testFindNameEntityTypeExecutor("LOCATION");
        testFindNameEntityTypeExecutor("ORGANIZATION");
        testFindNameEntityTypeExecutor("DATE");
        testFindNameEntityTypeExecutor("MONEY");
    }

    public static void setUp() throws Exception {
        start = System.currentTimeMillis();
        executor = new FindNameEntityTypeExecutor();
        end = System.currentTimeMillis();

        logger.info("Time to construct FindNameEntityTypeExecutor instance : [" + ((end - start)/1000f) + " sec]");

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


    private static void testFindNameEntityTypeExecutor(String entityType){
        System.out.println("==================================================================================");
        start = System.currentTimeMillis();
        results = executor.findNameEntityType(textBuffer.toString(), entityType);
        concatenateResults=executor.findNameEntityTypeConcatenate(textBuffer.toString(),entityType);

        end = System.currentTimeMillis();
        logger.info(String.format("Results of Test: %s", results));
        logger.info(String.format("Concatenate Results of Test: %s",concatenateResults));
        System.out.println("-----------------------------------------------------------------------------------");
        logger.info(String.format("Time to execute FindNameEntityTypeExecutor.findNameEntityType for %s : [%f sec]", entityType, ((end - start)/1000f)));

    }
}
