package org.wso2.toolbox.nlp.operation;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.TreeGraphNode;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import org.apache.log4j.Logger;

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

        props.setProperty("annotators", "tokenize, ssplit, pos, parse");

        pipeline = new StanfordCoreNLP(props);

        logger.info("Annotator pipeline initialized");
    }

    public List<String> findRelationship(String text, String regex){
        List<String> relationships = new ArrayList<String>();

        Annotation document = pipeline.process(text);

        SemanticGraph graph = null;
        Collection<TypedDependency> dependencies = null;

        for (CoreMap sentence:document.get(CoreAnnotations.SentencesAnnotation.class)){
            graph = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            dependencies = graph.typedDependencies();
            Iterator<TypedDependency> typedDependencyIterator = dependencies.iterator();
            System.out.println("Sentence: " + sentence);
            while(typedDependencyIterator.hasNext()){
                TypedDependency dependency = typedDependencyIterator.next();
                GrammaticalRelation relation = dependency.reln();
                TreeGraphNode dependent = dependency.dep();
                TreeGraphNode governor = dependency.gov();
                System.out.println(relation + " , " + governor.label().value() + " , " + dependent.label().value());

            }
        }

        return  relationships;


    }
}
