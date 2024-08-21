package de.dynamicflash.model;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:09
 */
public class PageResult {

    private Integer total;
    private Page[] results;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer number) {
        this.total = number;
    }

    public Page[] getResults() {
        return results;
    }

    public void setResults(Page[] results) {
        this.results = results;
    }
}
