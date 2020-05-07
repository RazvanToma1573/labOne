package ro.mpp.core.Repository;

import java.util.*;

public class Sort {
    public static class Direction {
        public static boolean DESC = true;
    }
    private Map<String, Boolean> criteria;

    public Sort() {
        this.criteria = new HashMap<>();
    }

    public Sort(String ...fields) {
        this.criteria = new HashMap<>();
        Arrays.stream(fields).forEach(
          field -> this.criteria.put(field, false)
        );
    }

    public Sort(boolean desc, String ...fields) {
        this.criteria = new HashMap<>();
        Arrays.stream(fields).forEach(
                field -> this.criteria.put(field, desc));
    }

    public Map<String, Boolean> getCriteria() {
        return criteria;
    }

    public void and(Sort newSort){
        this.criteria.putAll(newSort.getCriteria());
    }
}
