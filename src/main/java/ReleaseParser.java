import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.List;

public class ReleaseParser {

    public static List<Release> parseGitHub(String json, String repository) {
        Gson gson = new Gson();
        GitHubRelease[] githubReleases = gson.fromJson(json, GitHubRelease[].class);

        if (githubReleases == null) {
            throw new JsonParseException("GitHub 返回的 Release 列表为 null");
        }

        List<Release> releases = new ArrayList<>();
        for (GitHubRelease githubRelease : githubReleases) {
            if (githubRelease == null) {
                throw new JsonParseException("Release 列表中包含 null");
            }
            releases.add(githubRelease.toRelease(repository));
        }
        return releases;
    }
}
