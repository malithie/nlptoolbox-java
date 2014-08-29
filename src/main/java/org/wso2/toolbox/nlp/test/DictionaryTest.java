package org.wso2.toolbox.nlp.test;

import org.apache.log4j.Logger;
import org.wso2.toolbox.nlp.dictionary.Dictionary;

/**
 * Created by malithi on 8/29/14.
 */
public class DictionaryTest {
    private static Logger logger = Logger.getLogger(DictionaryTest.class);

    public static void main(String[] args) {

        loadDictionary("PERSON","src/main/resources/dictionary.xml");
        loadDictionary("LOCATION","src/main/resources/dictionary.xml");
        loadDictionary("ORGANIZATION","src/main/resources/dictionary.xml");
    }

    private static void loadDictionary(String entityType, String xmlFilePath){
        System.out.println("==================================================================================");
        logger.info("Loading Dictionary for entity Type: " + entityType);

        Dictionary dictionary = null;
        try {
            dictionary = new Dictionary(entityType, xmlFilePath);
        } catch (Exception e) {
            logger.error("Failed to load Dictionary. ERROR[" + e.getMessage() + "]");
        }

        logger.info("Loaded values for entity type: " + entityType + " -> " + dictionary.getEntries(entityType));

    }

}
