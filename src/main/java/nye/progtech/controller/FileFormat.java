package nye.progtech.controller;

public enum FileFormat {
    JSON(1, "JSON"),
    XML(2, "XML"),
    TXT(3, "Text");

    private final int value;
    private final String description;

    FileFormat(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static FileFormat fromInt(int value) {
        for (FileFormat format : values()) {
            if (format.getValue() == value) {
                return format;
            }
        }
        return null;
    }
}
