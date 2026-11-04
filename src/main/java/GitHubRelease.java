public class GitHubRelease {
    private Long id;
    private String tag_name;
    private String body;
    private boolean prerelease;
    private String html_url;
    private String published_at;

    public Release toRelease(String repository) {
        return new Release(
                repository,
                tag_name,
                body == null ? "暂无更新说明" : body,
                prerelease,
                id,
                html_url,
                published_at
        );
    }
}