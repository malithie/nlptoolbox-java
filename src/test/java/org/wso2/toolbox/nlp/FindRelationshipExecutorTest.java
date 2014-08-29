package org.wso2.toolbox.nlp;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.wso2.toolbox.nlp.operation.FindRelationshipExecutor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FindRelationshipExecutorTest {
    private static Logger logger = Logger.getLogger(FindNLRegexPatternExecutorTest.class);

    private FindRelationshipExecutor executor = null;
    private StringBuilder textBuffer = null;
    private long start;
    private long end;
    private List<String> results;

    @Before
    public void setUp() throws Exception {
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

    @Test
    public void testFindRelationship() throws Exception {
        executeTest(1,null);

    }

    private void executeTest(int testId, String regex){
        System.out.println("==================================================================================");
        logger.info(String.format("Test %d: %s",testId, regex));

        start = System.currentTimeMillis();
        results = executor.findRelationship(textBuffer.toString(), regex);
        end = System.currentTimeMillis();

        logger.info(String.format("Results of Test %d: %s", testId, results));

        logger.info(String.format("Time to execute FindRelationshipExecutor.findRelationship for %s : [%f sec]", regex, ((end - start)/1000f)));
    }
}