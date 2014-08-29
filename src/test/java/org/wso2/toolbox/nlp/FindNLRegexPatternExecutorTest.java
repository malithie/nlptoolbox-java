package org.wso2.toolbox.nlp;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.wso2.toolbox.nlp.operation.FindNLRegexPatternExecutor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FindNLRegexPatternExecutorTest {
    private static Logger logger = Logger.getLogger(FindNLRegexPatternExecutorTest.class);

    private FindNLRegexPatternExecutor executor = null;
    private StringBuilder textBuffer = null;
    private long start;
    private long end;
    private List<String> results;

    @Before
    public void setUp() throws Exception {
        start = System.currentTimeMillis();
        executor = new FindNLRegexPatternExecutor();
        end = System.currentTimeMillis();

        logger.info("Time to construct NLRegexPatternExecutor instance : [" + ((end - start)/1000f) + " sec]");

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
    public void testFindNLRegexPattern() throws Exception {
        executeTest(1, "[{ner:PERSON}]");
        executeTest(2, "[{ner:LOCATION}]");
        executeTest(3, "[{ner:DATE}]");
        executeTest(4, "([ner: PERSON]+)");
        executeTest(5, "(/President/ [ner: PERSON]+)");
        //executeTest(6, "/\"*\\s*([^\\\"]*)\\\\s*/");
        //executeTest(6, "(/\"/ /\\s*([^\"]*)\\s*/)");
    }

    private void executeTest(int testId, String regex){
        System.out.println("==================================================================================");
        logger.info(String.format("Test %d: %s",testId, regex));

        start = System.currentTimeMillis();
        results = executor.findNLRegexPattern(textBuffer.toString(), regex);
        end = System.currentTimeMillis();

        logger.info(String.format("Results of Test %d: %s", testId, results));

        logger.info(String.format("Time to execute NLRegexPatternExecutor.findNLRegexPattern for %s : [%f sec]", regex, ((end - start)/1000f)));
    }
}