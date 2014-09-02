package org.wso2.toolbox.nlp.test;

import org.apache.log4j.Logger;
import org.wso2.toolbox.nlp.bean.Relationship;
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
    private static List<Relationship> results;
    private static List<String> resultsWithRegex;

    public static void main(String[] args) throws Exception {
        setUp();

        testFindRelationship();


        testFindRelationshipWithRegex("{} <nsubj {}");
        testFindRelationshipWithRegex("{} >nsubj {}");
        testFindRelationshipWithRegex("{} >dobj {}");
        testFindRelationshipWithRegex("{} <dobj {}");
        testFindRelationshipWithRegex("{} >nsubj {} >dobj {}");
        testFindRelationshipWithRegex("{} <agent {} | <nsubj {}");
        testFindRelationshipWithRegex("{lemma:be}");
        testFindRelationshipWithRegex("{ner:DATE}");
    }

    public static void setUp() throws Exception {
        start = System.currentTimeMillis();
        executor = new FindRelationshipExecutor();
        end = System.currentTimeMillis();

        logger.info("Time to construct FindRelationshipExecutor instance : [" + ((end - start)/1000f) + " sec]");

        textBuffer = new StringBuilder();

        InputStream inputStream = ClassLoader.getSystemResourceAsStream("test/relationshipRegexTest.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            textBuffer.append(line);
        }

        inputStream.close();
        bufferedReader.close();


    }

    public static void testFindRelationship() throws Exception {
        System.out.println("==================================================================================");
        logger.info(String.format("Executing FindRelationshipExecutor.findRelationship(text)"));

        start = System.currentTimeMillis();
        results = executor.findRelationship(textBuffer.toString());
        end = System.currentTimeMillis();

        logger.info(String.format("Results of Test: %s", results));
        System.out.println("-----------------------------------------------------------------------------------");
        logger.info(String.format("Time to execute FindRelationshipExecutor.findRelationship : [%f sec]", ((end - start)/1000f)));
    }

    public static void testFindRelationshipWithRegex(String regex) throws Exception {
        System.out.println("==================================================================================");
        logger.info(String.format("Executing FindRelationshipExecutor.findRelationship(text,regex)"));

        start = System.currentTimeMillis();
        resultsWithRegex = executor.findRelationship(textBuffer.toString(),regex);
        end = System.currentTimeMillis();

        logger.info(String.format("Results of Test: %s", resultsWithRegex));
        System.out.println("-----------------------------------------------------------------------------------");
        logger.info(String.format("Time to execute FindRelationshipExecutor.findRelationship for %s : [%f sec]", regex, ((end - start)/1000f)));
    }
}
