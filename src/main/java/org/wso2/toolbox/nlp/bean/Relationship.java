package org.wso2.toolbox.nlp.bean;

/**
 * Created by malithi on 8/28/14.
 */
public class Relationship {
    private String governor;
    private String dependent;
    private String relation;

    public String getGovernor() {
        return governor;
    }

    public void setGovernor(String governor) {
        this.governor = governor;
    }

    public String getDependent() {
        return dependent;
    }

    public void setDependent(String dependent) {
        this.dependent = dependent;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "governor='" + governor + '\'' +
                ", dependent='" + dependent + '\'' +
                ", relation='" + relation + '\'' +
                '}';
    }
}
