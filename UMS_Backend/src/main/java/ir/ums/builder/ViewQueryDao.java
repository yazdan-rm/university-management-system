package ir.ums.builder;

import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Slf4j
@Repository
@Setter(onMethod_ = {@Autowired})
public class ViewQueryDao {

    private JdbcTemplate template;
    private OracleSqlQueryBuilder oracleSqlQueryBuilder;


    public EnterpriseGetRowsResponse getRows(EnterpriseGetRowsRequest request, String viewQuery) {
        String sql = oracleSqlQueryBuilder.createSql(request, viewQuery);
        log.info("####### Query for GetRows:[\n {} \n]", sql);
        List<Map<String, Object>> getRows = template.queryForList(sql);
        return EnterpriseResponseBuilder.createResponse(request, getRows);
    }
}
