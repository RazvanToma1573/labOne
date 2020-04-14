package mpp.socket.common.Repository;

import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sort {
    public static class Direction {
        public static boolean DESC = true;
    }
    private List<Pair<String, Boolean>> criteria;

    public Sort(String ...fields) {
        this.criteria = new ArrayList<>();
        Arrays.stream(fields).forEach(
          field -> this.criteria.add(new Pair<>(field, false))
        );
    }

    public Sort(boolean desc, String ...fields) {
        this.criteria = new ArrayList<>();
        Arrays.stream(fields).forEach(
                field -> this.criteria.add(new Pair<>(field, desc))
        );
    }

    public List<Pair<String, Boolean>> getCriteria() {
        return criteria;
    }

    public void and(Sort newSort){
        this.criteria.addAll(newSort.getCriteria());
    }
}
