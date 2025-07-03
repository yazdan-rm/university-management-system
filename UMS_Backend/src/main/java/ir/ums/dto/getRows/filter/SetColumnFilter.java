package ir.ums.dto.getRows.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SetColumnFilter extends ColumnFilter {
    private List<String> values;

}
