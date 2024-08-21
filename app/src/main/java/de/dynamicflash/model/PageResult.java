package de.dynamicflash.model;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:09
 */
public class PageResult {

    private Integer total;
    private ArrayList<Page> results;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer number) {
        this.total = number;
    }

    public ArrayList<Page> getResults() {
        return results;
    }

    public void setResults(ArrayList<Page> results) {
        this.results = results;
    }
}
