package org.wso2.toolbox.nlp.dictionary;

import org.apache.log4j.Logger;
import org.wso2.toolbox.nlp.Constants;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by malithi on 8/29/14.
 */
public class Dictionary {
    private static Logger logger = Logger.getLogger(Dictionary.class);

    private HashMap<String,Set<String>> store;
    private String entityType;
    private String xmlFilePath;

    private static final String xsdFilePath = "dictionary.xsd";


    public Dictionary(String entityType, String xmlFilePath) throws Exception {
        this.entityType = entityType;
        this.xmlFilePath = xmlFilePath;
        this.store = new HashMap<String, Set<String>>();

        init();
    }

    private void init() throws Exception {
        Constants.EntityType entity = Constants.EntityType.valueOf(entityType);

        if (entity == null){
            logger.error("Given Entity Type " + entityType + " is not defined" );
            throw new RuntimeException("Entity Type " + entityType + " is not defined");
        }

        File xmlFile = new File(xmlFilePath);
        if(!xmlFile.canRead()){
            logger.error("Cannot read the given Dictionary file " + xmlFilePath);
            throw new RuntimeException("Cannot read the XML file : " + xmlFilePath);
        }

        URL xsdFileUrl= ClassLoader.getSystemResource(xsdFilePath);

        Schema schema;
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        try {
            schema = schemaFactory.newSchema(xsdFileUrl);
        } catch (SAXException e) {
            logger.error("Failed to build the schema. Error: [" + e.getMessage() + "]");
            throw e;
        }

        DictionaryHandler dictionaryHandler = new DictionaryHandler(entity.name(),this);

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setValidating(true);
        saxParserFactory.setSchema(schema);
        saxParserFactory.setFeature("http://apache.org/xml/features/validation/dynamic", true);

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(xmlFile, dictionaryHandler);

            logger.info("Dictionary XML Parse [SUCCESS]");
        } catch (SAXException e) {
            logger.error("Failed to parse the given Dictionary XML file. Error: [" + e.getMessage() + "]");
            throw e;
        }
    }

    public boolean addEntry(String entity, String entry){

        return getEntries(entity).add(entry);
    }

    public boolean removeEntry(String entity, String entry){

        return getEntries(entity).remove(entry);
    }

    public Set<String> getEntries(String entity){
        Set<String> entries = store.get(entity);
        if(entries == null){
            entries = new HashSet<String>();
            store.put(entity,entries);
        }

        return entries;
    }
}
