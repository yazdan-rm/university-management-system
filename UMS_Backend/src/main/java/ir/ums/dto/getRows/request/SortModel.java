package ir.ums.dto.getRows.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SortModel implements Serializable {

    private String colId;
    private String sort;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortModel sortModel = (SortModel) o;
        return Objects.equals(colId, sortModel.colId) &&
                Objects.equals(sort, sortModel.sort);
    }

    @Override
    public int hashCode() {

        return Objects.hash(colId, sort);
    }

    @Override
    public String toString() {
        return "SortModel{" +
                "colId='" + colId + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}
