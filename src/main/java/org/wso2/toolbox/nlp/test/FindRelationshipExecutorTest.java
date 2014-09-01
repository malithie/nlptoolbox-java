package org.wso2.toolbox.nlp.test;

import org.apache.log4j.Logger;
import org.wso2.toolbox.nlp.operation.FindRelationshipExecutor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Chanuka on 9/1/14 AD.
 */
public class FindRelationshipExecutorTest {
    private static Logger logger = Logger.getLogger(FindRelationshipExecutorTest.class);

    private static FindRelationshipExecutor executor = null;
    private static StringBuilder textBuffer = null;
    private static long start;
    private static long end;
    private static List<String> results;

    public static void main(String[] args) throws Exception {
        setUp();
        executeTest(1,null);
    }

    public static void setUp() throws Exception {
        start = System.currentTimeMillis();
        executor = new FindRelationshipExecutor();
        end = System.currentTimeMillis();

        logger.info("Time to construct FindRelationshipExecutor instance : [" + ((end - start)/1000f) + " sec]");

        textBuffer = new StringBuilder();

        InputStream inputStream = ClassLoader.getSystemResourceAsStream("obamatohitlor.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            textBuffer.append(line);
        }

        inputStream.close();
        bufferedReader.close();


    }


    private static void executeTest(int testId, String regex){
        System.out.println("==================================================================================");
        logger.info(String.format("Test %d: %s",testId, regex));

        start = System.currentTimeMillis();
        results = executor.findRelationship(textBuffer.toString(), regex);
        end = System.currentTimeMillis();

        logger.info(String.format("Results of Test %d: %s", testId, results));

        logger.info(String.format("Time to execute FindRelationshipExecutor.findRelationship for %s : [%f sec]", regex, ((end - start)/1000f)));
    }

}
