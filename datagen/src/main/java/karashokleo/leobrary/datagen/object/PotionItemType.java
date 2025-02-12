package karashokleo.leobrary.datagen.object;

public enum PotionItemType
{
    POTION(
            "potion",
            "Potion of %s",
            "%s药水"
    ),
    SPLASH_POTION(
            "splash_potion",
            "Splash Potion of %s",
            "喷溅型%s药水"
    ),
    LINGERING_POTION(
            "lingering_potion",
            "Lingering Potion of %s",
            "滞留型%s药水"
    ),
    TIPPED_ARROW(
            "tipped_arrow",
            "Arrow of %s",
            "%s之箭"
    );
    private final String langKey;
    private final String templateEn;
    private final String templateZh;

    PotionItemType(String langKey, String templateEn, String templateZh)
    {
        this.langKey = langKey;
        this.templateEn = templateEn;
        this.templateZh = templateZh;
    }

    public String getLangKey()
    {
        return langKey;
    }

    public String getEN(String name)
    {
        return String.format(templateEn, name);
    }

    public String getZH(String name)
    {
        return String.format(templateZh, name);
    }
}
