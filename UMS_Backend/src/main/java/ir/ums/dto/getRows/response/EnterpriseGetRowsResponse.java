package ir.ums.dto.getRows.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseGetRowsResponse {
    private List<?> data;
    private int lastRow;
}