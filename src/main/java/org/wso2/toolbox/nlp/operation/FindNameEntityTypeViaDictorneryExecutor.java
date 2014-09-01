package org.wso2.toolbox.nlp.operation;

import org.apache.log4j.*;
import org.wso2.toolbox.nlp.Constants;
import org.wso2.toolbox.nlp.dictionary.Dictionary;

import java.util.*;

/**
 * Created by Chanuka on 8/29/14 AD.
 */
public class FindNameEntityTypeViaDictorneryExecutor {

    private static Logger logger = Logger.getLogger(FindNameEntityTypeViaDictorneryExecutor.class);
    private List<String> findNameEntityResults;
    private Set<String> dictionaryNameResults;

    private FindNameEntityTypeExecutor executor;


    public FindNameEntityTypeViaDictorneryExecutor() {

        logger.info("Initiating NameEntityTypeExecutor");
        executor= new FindNameEntityTypeExecutor();
    }

    public List<String> findNameEntityTypeViaDictionary(String text, String dictionaryFilePath, String entityType){
        Constants.EntityType entity = Constants.EntityType.valueOf(entityType);

        if (entity == null){
            logger.error("Given Entity Type " + entityType + " is not defined" );
            throw new RuntimeException("Entity Type " + entityType + " is not defined");
        }

        findNameEntityResults=executor.findNameEntityType(text,entityType);

        Dictionary dictionary=null;
        try {
            dictionary = new Dictionary(entityType,dictionaryFilePath);
        } catch (Exception e) {
            logger.error("Error creating dictionary, Error:[" +e.getMessage()+"].");
        }
         dictionaryNameResults=dictionary.getEntries(entity.name());

        List<String> matchingEntityResults = new ArrayList<String>();

        for (int i = 0; i < findNameEntityResults.size(); i++) {
            if (dictionaryNameResults.contains(findNameEntityResults.get(i))) {
                matchingEntityResults.add(findNameEntityResults.get(i));
            }
        }

        return  matchingEntityResults;
    }
}
