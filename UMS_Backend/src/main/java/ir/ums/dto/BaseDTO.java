package ir.ums.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class BaseDTO {
    private UUID id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Integer version;
}
