package org.wso2.toolbox.nlp.operation;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.tokensregex.TokenSequenceMatcher;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by malithi on 8/22/14.
 */
public class FindNLRegexPatternExecutor {
    private static Logger logger = Logger.getLogger(FindNLRegexPatternExecutor.class);

    private StanfordCoreNLP pipeline;

    public FindNLRegexPatternExecutor() {
        logger.info("Initializing Annotator pipeline ...");

        Properties props = new Properties();

        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse");

        pipeline = new StanfordCoreNLP(props);

        logger.info("Annotator pipeline initialized");
    }

    public List<String> findNLRegexPattern(String text, String regex){
        logger.info("Finding matches for " + regex);

        TokenSequencePattern pattern = TokenSequencePattern.compile(regex);
        TokenSequenceMatcher matcher = null;

        Annotation document = pipeline.process(text);

        List<String> matches = new ArrayList<String>();

        for (CoreMap sentence:document.get(CoreAnnotations.SentencesAnnotation.class)){
            matcher = pattern.getMatcher(sentence.get(CoreAnnotations.TokensAnnotation.class));

            while( matcher.find()){
                matches.add(matcher.group());
            }
        }

        return matches;

    }


}
