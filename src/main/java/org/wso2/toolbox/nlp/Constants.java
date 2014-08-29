package org.wso2.toolbox.nlp;

/**
 * Created by malithi on 8/29/14.
 */
public class Constants {
    private Constants() {
    }

    public enum EntityType{
        PERSON, LOCATION, ORGANIZATION, MONEY, PERCENT, DATE, TIME;
    }

    public enum DictionaryTag{
        DICTIONARY("dictionary"),
        ENTITY("entity"),
        ENTRY("entry"),
        ID("id");

        private String tag;

        DictionaryTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }
    }

}
