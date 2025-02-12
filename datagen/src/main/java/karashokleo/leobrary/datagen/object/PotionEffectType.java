package karashokleo.leobrary.datagen.object;

public enum PotionEffectType
{
    NORMAL(3600, 0, ""),
    LONG(9600, 0, "long_"),
    STRONG(3600, 1, "strong_");

    private final int defaultDuration;
    private final int defaultAmplifier;
    private final String langPrefix;

    PotionEffectType(int defaultDuration, int defaultAmplifier, String langPrefix)
    {
        this.defaultDuration = defaultDuration;
        this.defaultAmplifier = defaultAmplifier;
        this.langPrefix = langPrefix;
    }

    public String getLangPrefix()
    {
        return langPrefix;
    }

    public int getDefaultDuration()
    {
        return defaultDuration;
    }

    public int getDefaultAmplifier()
    {
        return defaultAmplifier;
    }
}
