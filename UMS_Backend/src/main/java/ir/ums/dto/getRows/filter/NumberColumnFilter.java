package ir.ums.dto.getRows.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NumberColumnFilter extends ColumnFilter {
    private String type;
    private Integer filter;
    private Integer filterTo;
}
