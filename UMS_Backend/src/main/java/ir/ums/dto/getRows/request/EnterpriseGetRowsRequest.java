package ir.ums.dto.getRows.request;


import ir.ums.dto.getRows.filter.ColumnFilter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;


@Setter
@Getter
public class EnterpriseGetRowsRequest {

    private int endRow;
    private int startRow;
    private String masterId;

    // if filtering, what the filter model is
    private Map<String, ColumnFilter> filterModel;

    // if sorting, what the sort model is
    private List<SortModel> sortModel;

    public EnterpriseGetRowsRequest() {
        this.filterModel = emptyMap();
        this.sortModel = emptyList();
    }

}