package algorithm.obtainpattern;

import java.util.List;

public interface ObtainPattern {
    String group1 = "g1";
    String group2 = "g2";
    String group3 = "g3";
    String group4 = "g4";
    String group5 = "g5";
    String group6 = "g6";

    public List<String> getTrace(String path);

    public String obtainPattern(List<String> sourceSeq);


}
