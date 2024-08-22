package de.dynamicflash.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:09
 */
public class PageResult {

    private Integer total;
    private List<Page> results;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer number) {
        this.total = number;
    }

    public List<Page> getResults() {
        return results;
    }

    public void setResults(List<Page> results) {
        this.results = results;
    }
}
