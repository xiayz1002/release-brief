public class Release {
    private final String repository;
    private final String version;
    private final String description;
    private final boolean prerelease;

    public Release(String repository, String version,
                   String description, boolean prerelease) {
        this.repository = repository;
        this.version = version;
        this.description = description;
        this.prerelease = prerelease;
    }

    public String getRepository() {
        return repository;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPrerelease() {
        return prerelease;
    }
}