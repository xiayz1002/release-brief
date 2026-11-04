public class Release {
    private final String repository;
    private final String version;
    private final String description;
    private final boolean prerelease;
    private final Long externalId;
    private final String htmlUrl;
    private final String publishedAt;

    public Release(String repository, String version,
                   String description, boolean prerelease) {
        this(repository, version, description, prerelease,
                null, null, null);
    }

    public Release(String repository, String version,
                   String description, boolean prerelease,
                   Long externalId, String htmlUrl, String publishedAt) {
        this.repository = repository;
        this.version = version;
        this.description = description;
        this.prerelease = prerelease;
        this.externalId = externalId;
        this.htmlUrl = htmlUrl;
        this.publishedAt = publishedAt;
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
    public Long getExternalId() {
        return externalId;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}