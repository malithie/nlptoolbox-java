package org.wso2.toolbox.nlp.test;

import org.apache.log4j.Logger;
import org.wso2.toolbox.nlp.operation.FindNLRegexPatternExecutor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Chanuka on 9/1/14 AD.
 */
public class FindNLRegexPatternExecutorTest {

    private static Logger logger = Logger.getLogger(FindNLRegexPatternExecutorTest.class);

    private static FindNLRegexPatternExecutor executor = null;
    private static StringBuilder textBuffer = null;
    private static long start;
    private static long end;
    private static List<String> results;

    public static void main(String[] args) throws Exception {
        setUp();
        testFindNLRegexPattern("[{ner:PERSON}]");
        testFindNLRegexPattern("[{ner:LOCATION}]");
        testFindNLRegexPattern("[{ner:DATE}]");
        testFindNLRegexPattern("([ner: PERSON]+)");
        testFindNLRegexPattern("(/President/ [ner: PERSON]+)");
        //testFindNLRegexPattern("/\"*\\s*([^\\\"]*)\\\\s*/");
        //testFindNLRegexPattern("(/\"/ /\\s*([^\"]*)\\s*/)");
    }

    public static void setUp() throws Exception {
        start = System.currentTimeMillis();
        executor = new FindNLRegexPatternExecutor();
        end = System.currentTimeMillis();

        logger.info("Time to construct FindNLRegexPatternExecutor instance : [" + ((end - start)/1000f) + " sec]");

        textBuffer = new StringBuilder();

        InputStream inputStream = ClassLoader.getSystemResourceAsStream("test/obamatohitlor.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            textBuffer.append(line);
        }

        inputStream.close();
        bufferedReader.close();

    }

    public static void testFindNLRegexPattern(String regex) throws Exception {
        System.out.println("==================================================================================");
        logger.info(String.format("Executing FindNLRegexPatternExecutor.findNLRegexPattern(text, regex)"));

        start = System.currentTimeMillis();
        results = executor.findNLRegexPattern(textBuffer.toString(), regex);
        end = System.currentTimeMillis();

        logger.info(String.format("Results of Test: %s", results));
        System.out.println("-----------------------------------------------------------------------------------");
        logger.info(String.format("Time to execute FindNLRegexPatternExecutor.findNLRegexPattern for %s : [%f sec]", regex, ((end - start) / 1000f)));
    }

}
