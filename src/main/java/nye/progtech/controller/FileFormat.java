/**
 * Ez a package tartalmazza az összes controllert.
 */
package nye.progtech.controller;

public enum FileFormat {
    /**
     * JSON.
     */
    JSON(1, "JSON"),

    /**
     * XML.
     */
    XML(2, "XML"),
    /**
     * TXT.
     */
    TXT(3, "Text");

    /**
     * Kiválasztott formátum value.
     */
    private final int value;
    /**
     * Kiválasztott formátum description.
     */
    private final String description;

    FileFormat(final int chosenValue, final String chosenDescription) {
        this.value = chosenValue;
        this.description = chosenDescription;
    }

    /**
     * Kiválasztott formátum value.
     *
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * Kiválasztott formátum description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * File formátum kiválasztás int-re alakítás.
     *
     * @param value kiválasztott value.
     *
     * @return FileFormat objektum.
     */
    public static FileFormat fromInt(final int value) {
        for (FileFormat format : values()) {
            if (format.getValue() == value) {
                return format;
            }
        }
        return null;
    }
}
