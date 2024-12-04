package entity;

public enum Allergy {

    CELERY("Celery-Free"),
    CRUSTACEAN("Crustacean-Free"),
    DAIRY("Dairy-Free"),
    EGG("Egg-Free"),
    FISH("Fish-Free"),
    GLUTEN("Gluten-Free"),
    LUPINE("Lupine-Free"),
    MUSTARD("Mustard-Free"),
    PEANUT("Peanut-Free"),
    SESAME("Sesame-Free"),
    SHELLFISH("Shellfish-Free"),
    SOY("Soy-Free"),
    TREE_NUT("Tree-Nut-Free"),
    WHEAT("Wheat-Free"),
    FODMAP("FODMAP-Free"),
    IMMUNO_SUPPORTIVE("Immuno-Supportive");

    private final String displayName;

    Allergy(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

