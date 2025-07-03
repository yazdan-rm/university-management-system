package ir.ums.builder;


import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;

import java.util.List;

public class EnterpriseResponseBuilder {

    public static EnterpriseGetRowsResponse createResponse(
            EnterpriseGetRowsRequest request,
            List<?> rows) {
        int currentLastRow = request.getStartRow() + rows.size();
        int lastRow = currentLastRow <= request.getEndRow() ? currentLastRow : -1;

        return new EnterpriseGetRowsResponse(rows, lastRow);
    }
}