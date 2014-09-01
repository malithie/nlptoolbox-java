package org.wso2.toolbox.nlp.operation;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.apache.log4j.Logger;
import org.wso2.toolbox.nlp.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Chanuka on 8/28/14 AD.
 */
public class FindNameEntityTypeExecutor {

    private static Logger logger = Logger.getLogger(FindNameEntityTypeExecutor.class);


    private StanfordCoreNLP pipeline;

    public FindNameEntityTypeExecutor() {
        logger.info("Initializing Annotator pipeline ...");

        Properties props = new Properties();

        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");

        pipeline = new StanfordCoreNLP(props);

        logger.info("Annotator pipeline initialized");
    }

    List<String> findNameEntityTypeConcatenate(String text,String entityType) {
        Constants.EntityType entity = Constants.EntityType.valueOf(entityType);

        if (entity == null){
            logger.error("Given Entity Type " + entityType + " is not defined" );
            throw new RuntimeException("Entity Type " + entityType + " is not defined");
        }


        logger.info("Finding matches for entity type, " + entity);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        List<String> matches = new ArrayList<String>();

        for (CoreMap sentence : sentences) {

            int previousCount = 0;
            int count = 0;
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods

            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);

                int previousWordIndex;
                if (entity.name().equals(token.get(CoreAnnotations.NamedEntityTagAnnotation.class))) {
                    count++;
                    if (previousCount != 0 && (previousCount + 1) == count) {
                        previousWordIndex = matches.size() - 1;
                        String previousWord = matches.get(previousWordIndex);
                        matches.remove(previousWordIndex);
                        previousWord = previousWord.concat(" " + word);
                        matches.add(previousWordIndex, previousWord);

                    } else {
                        matches.add(word);
                    }
                    previousCount = count;
                } else {
                    count = 0;
                    previousCount = 0;
                }


            }

        }
        return matches;
    }

    public List<String> findNameEntityType(String text, String entityType) {
        Constants.EntityType entity = Constants.EntityType.valueOf(entityType);

        if (entity == null){
            logger.error("Given Entity Type " + entityType + " is not defined" );
            throw new RuntimeException("Entity Type " + entityType + " is not defined");
        }

        logger.info("Finding matches for entity type, " + entity);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        List<String> matches = new ArrayList<String>();

        for (CoreMap sentence : sentences) {

            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods

            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);

                if (entity.name().equals(token.get(CoreAnnotations.NamedEntityTagAnnotation.class))) {

                    matches.add(word);
                }

            }

        }
        return matches;
    }
}
