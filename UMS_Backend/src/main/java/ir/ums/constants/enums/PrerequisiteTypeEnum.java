package ir.ums.constants.enums;

import lombok.Getter;

@Getter
public enum PrerequisiteTypeEnum {
    PREREQUISITE("پیش نیاز"),
    COREQUISITE("همنياز"),
    EQUIVALENT("معادل"),
    ANTIREQUISITE("متضاد");

    private final String label;

    PrerequisiteTypeEnum(String label) {
        this.label = label;
    }

    public static PrerequisiteTypeEnum fromLabel(String label) {
        for (PrerequisiteTypeEnum type : values()) {
            if (type.label.equals(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown prerequisite type: " + label);
    }
}
