package com.obnovime.enums;

public enum DocumentStatus {
    NO_RENEWAL("Nema obnove", "badge-status-active"),
    ACTIVE("Aktivno", "badge-status-active"),
    RENEWAL_TIME("Vrijeme za obnovu", "badge-renewal-progress"),
    DEFAULT("-", "bg-secondary");

    private final String displayName;
    private final String badgeClass;

    DocumentStatus(String displayName, String badgeClass) {
        this.displayName = displayName;
        this.badgeClass = badgeClass;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBadgeClass() {
        return badgeClass;
    }

    public static DocumentStatus fromDisplayName(String displayName) {
        if (displayName == null) {
            return DEFAULT;
        }
        
        for (DocumentStatus status : values()) {
            if (status.getDisplayName().equals(displayName)) {
                return status;
            }
        }
        return DEFAULT;
    }
}
