package utils;

import java.io.Serializable;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DataListingSupport<T extends Serializable> implements Serializable {
	
    private static final long serialVersionUID = -7027863449540470122L;
    
    private static final Logger LOG = LoggerFactory.getLogger(DataListingSupport.class);

    private Integer recordCount = 0;
    private Integer totalPages = 0;
    private List<T> data;

    private Integer page = 1;
    private Integer rowsPerPage = null;
    private boolean ascending = true;
    private String sortField;

    public DataListingSupport() {
        LOG.info("CREATION of DataListingSupport!");
    	setRowsPerPage(10);
        refresh();
    }

    public void navigatePage(final boolean forward) {
        setPage((forward) ? ++page : --page);
        refresh();
    }

    public void sort(final String sortField) {
        setSortField(sortField);
        setAscending(getSortField().equals(sortField) ? !isAscending() : true);
        refresh();
    }

    public void updateRowsPerPage(final AjaxBehaviorEvent event) {
        setPage(1); // page must reset to the first one
        refresh();
    }

    public void refresh() {
        // hook to populate count and data
        populateCountAndData();
        // compute total pages
        setTotalPages(countTotalPages(getRecordCount(), getRowsPerPage()));
    }

    /**
     * The concreate implementation of this class must perform data retrieval based on the current information available
     * (accessible via methods such as {@link #getSortField()}, {@link #isAscending()}, etc.
     * <p>
     * The implementation is responsible in populating the values for {@link #setRecordCount(Integer)} and
     * {@link #setData(javax.faces.model.DataModel)}
     */
    protected abstract void populateCountAndData();

    /************************************************************** HELPER(S) */

    private static Integer countTotalPages(Integer totalRecord, Integer rowsPerPage) {
        Integer pageCounter = 0;
        for (Integer pageCountTracker = 0; pageCountTracker < totalRecord; ++pageCounter) {
            pageCountTracker += rowsPerPage;
        }
        return pageCounter;
    }

    /************************************************* ACCESSORS AND MUTATORS */

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

}
