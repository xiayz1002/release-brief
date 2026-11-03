public class GitHubRelease {
    private String tag_name;
    private String body;
    private boolean prerelease;

    public Release toRelease(String repository) {
        return new Release(
                repository,
                tag_name,
                body == null ? "暂无更新说明" : body,
                prerelease
        );
    }
}