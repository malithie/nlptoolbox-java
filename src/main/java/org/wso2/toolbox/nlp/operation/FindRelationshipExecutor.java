package org.wso2.toolbox.nlp.operation;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.semgrex.SemgrexMatcher;
import edu.stanford.nlp.semgraph.semgrex.SemgrexPattern;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import org.apache.log4j.Logger;
import org.wso2.toolbox.nlp.bean.Relationship;

import java.util.*;

/**
 * Created by malithi on 8/28/14.
 */
public class FindRelationshipExecutor {
    private static Logger logger = Logger.getLogger(FindNLRegexPatternExecutor.class);

    private StanfordCoreNLP pipeline;

    public FindRelationshipExecutor() {
        logger.info("Initializing Annotator pipeline ...");

        Properties props = new Properties();

//        props.setProperty("annotators", "tokenize, ssplit, pos, parse");
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse");

        pipeline = new StanfordCoreNLP(props);

        logger.info("Annotator pipeline initialized");
    }

    public List<Relationship> findRelationship(String text){
        logger.info("Finding all grammatical relationships");

        Annotation document = pipeline.process(text);

        List<Relationship> relationships = new ArrayList<Relationship>();

        SemanticGraph graph = null;
        Collection<TypedDependency> dependencies = null;

        for (CoreMap sentence:document.get(CoreAnnotations.SentencesAnnotation.class)){
            graph = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            dependencies = graph.typedDependencies();
            Iterator<TypedDependency> typedDependencyIterator = dependencies.iterator();

            while(typedDependencyIterator.hasNext()){
                TypedDependency dependency = typedDependencyIterator.next();

                Relationship relationship = new Relationship();
                relationship.setGovernor(dependency.gov().value());
                relationship.setDependent(dependency.dep().value());
                relationship.setRelation(dependency.reln().getShortName());

                relationships.add(relationship);
            }
        }

        return  relationships;


    }

    public List<String> findRelationship(String text, String regex){
        logger.info("Finding grammatical relationships that matches for " + regex);

        SemgrexPattern pattern = SemgrexPattern.compile(regex);
        SemgrexMatcher matcher = null;

        List<String> relationships = new ArrayList<String>();

        Annotation document = pipeline.process(text);

        for (CoreMap sentence:document.get(CoreAnnotations.SentencesAnnotation.class)){
            SemanticGraph graph = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            matcher = pattern.matcher(graph);

            while(matcher.find()){
               // System.out.println(matcher.getMatch().value() + ", " + matcher.getNodeNames() + ", " + matcher.getRelationNames());
                relationships.add(matcher.getMatch().value());
            }
        }

        return  relationships;
    }
}
