package ir.ums.dto.getRows.filter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TextColumnFilter extends ColumnFilter {

    private String type;
    private String filter;
    private String operator;
    private List<TextColumnFilter> conditions;
}
