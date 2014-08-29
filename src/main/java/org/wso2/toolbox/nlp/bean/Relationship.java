package org.wso2.toolbox.nlp.bean;

/**
 * Created by malithi on 8/28/14.
 */
public class Relationship {
    private String subject;
    private String object;
    private String relationship;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "subject='" + subject + '\'' +
                ", object='" + object + '\'' +
                ", relationship='" + relationship + '\'' +
                '}';
    }
}
