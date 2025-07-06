package ir.ums.builder;


import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.request.SortModel;
import ir.ums.dto.getRows.filter.ColumnFilter;
import ir.ums.dto.getRows.filter.NumberColumnFilter;
import ir.ums.dto.getRows.filter.SetColumnFilter;
import ir.ums.dto.getRows.filter.TextColumnFilter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Component
public class OracleSqlQueryBuilder {

    private Map<String, ColumnFilter> filterModel;
    private List<SortModel> sortModel;
    private int startRow, endRow;

    public String createSql(EnterpriseGetRowsRequest request, String baseSqlQuery) {

        this.filterModel = request.getFilterModel();
        this.sortModel = request.getSortModel();
        this.startRow = request.getStartRow();
        this.endRow = request.getEndRow();

        return baseSqlQuery + whereSql() + orderBySql() + limitSql();
    }

    private String whereSql() {

        String whereFilters = getFilters().collect(joining(" AND "));

        return whereFilters.isEmpty() ? "" : format(" WHERE %s \n", whereFilters);
    }

    private String orderBySql() {
        Function<SortModel, String> orderByMapper = model -> model.getColId() + " " + model.getSort();

        List<String> orderByCols = sortModel.stream()
                .map(orderByMapper)
                .collect(toList());

        return orderByCols.isEmpty() ? "" : " ORDER BY " + join(",", orderByCols) + "\n";
    }

    private String limitSql() {
        if(startRow == 0 && endRow == 0) return "";
//        return " OFFSET " + startRow + " ROWS FETCH NEXT " + (endRow - startRow + 1) + " ROWS ONLY";
        return " LIMIT " + (endRow - startRow + 1) + " OFFSET " + startRow;
    }

    private Stream<String> getFilters() {
        Function<Map.Entry<String, ColumnFilter>, String> applyFilters = entry -> {
            String columnName = String.format("%s", entry.getKey());
            ColumnFilter filter = entry.getValue();

            if (filter instanceof SetColumnFilter) {
                return setFilter().apply(columnName, (SetColumnFilter) filter);
            }

            if (filter instanceof NumberColumnFilter) {
                return numberFilter().apply(columnName, (NumberColumnFilter) filter);
            }

            if (filter instanceof TextColumnFilter) {
                return textFilter().apply(columnName, (TextColumnFilter) filter);
            }


            return "";
        };

        return filterModel.entrySet().stream().map(applyFilters);
    }

    private BiFunction<String, SetColumnFilter, String> setFilter() {
        return (String columnName, SetColumnFilter filter) ->
                columnName + (filter.getValues().isEmpty() ? " IN ('') " : " IN " + asString(filter.getValues()));
    }

    private BiFunction<String, NumberColumnFilter, String> numberFilter() {
        return (String columnName, NumberColumnFilter filter) -> {
            Integer filterValue = filter.getFilter();
            String filerType = filter.getType();
            String operator = operatorMap.get(filerType);

            return columnName + (filerType.equals("inRange") ?
                    " BETWEEN " + filterValue + " AND " + filter.getFilterTo() : " " + operator + " " + filterValue);
        };
    }

    private BiFunction<String, TextColumnFilter, String> textFilter() {
        return (String columnName, TextColumnFilter filter) -> {
            var conditions = filter.getConditions();
            if(conditions == null || conditions.isEmpty()) {
                return createTextBlockFilter(columnName, filter);
            } else {
                String operator = filter.getOperator();
                return "(" + createTextBlockFilter(columnName, conditions.getFirst()) + " " + operator + " " + createTextBlockFilter(columnName, conditions.getLast()) + ")";
            }


        };
    }

    private String createTextBlockFilter(String columnName, TextColumnFilter filter) {
        String filterValue = filter.getFilter();
        String filerType = filter.getType();
        String operator = operatorMap.get(filerType);
        return switch (filerType) {
            case "equals", "notEqual" -> columnName + " " + operator + " " + filterValue;
            case "contains", "notContains" -> columnName + " " + operator + " '%" + filterValue + "%'";
            case "startsWith" -> columnName + " " + operator + " '" + filterValue + "%'";
            case "endsWith" -> columnName + " " + operator + " '%" + filterValue + "'";
            case "blank", "notBlank" -> columnName + " " + operator;
            default -> "";
        };
    }

    private String asString(List<String> l) {
        return "(" + l.stream().map(s -> "'" + s + "'").collect(joining(", ")) + ")";
    }

    private final Map<String, String> operatorMap = new HashMap<>() {{
        put("equals", "=");
        put("notEqual", "<>");
        put("lessThan", "<");
        put("lessThanOrEqual", "<=");
        put("greaterThan", ">");
        put("greaterThanOrEqual", ">=");
        put("contains", "LIKE");
        put("notContains", "NOT LIKE");
        put("startsWith", "LIKE");
        put("endsWith", "LIKE");
        put("blank", "IS NULL");
        put("notBlank", "IS NOT NULL");
    }};
}
