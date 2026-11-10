import java.io.IOException;
import java.util.List;
import com.google.gson.JsonParseException;

public class GitHubDemo {

    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println("只接受一个仓库参数，格式：owner/repo");
            return;
        }

        String repository = args.length == 0
                ? "spring-projects/spring-ai"
                : args[0];

        if (!repository.matches("[A-Za-z0-9-]+/[A-Za-z0-9_.-]+")) {
            System.err.println("仓库格式不正确，请使用 owner/repo");
            System.err.println("例如：spring-projects/spring-ai");
            return;
        }

        GitHubReleaseClient client = new GitHubReleaseClient();

        try {
            String json = client.fetchReleases(repository);
            List<Release> releases = ReleaseParser.parseGitHub(json, repository);
            List<Release> stableReleases = ReleaseFilter.onlyStable(releases);

            for (Release release : stableReleases) {
                Main.printRelease(release);
            }

            System.out.println("本次获取 " + releases.size() + " 条 Release");
            System.out.println("其中正式版本 " + stableReleases.size() + " 条");

        } catch (IOException e) {
            System.err.println("获取 GitHub Release 失败：" + e.getMessage());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("请求被中断");
        } catch (JsonParseException e) {
            System.err.println("GitHub 数据解析失败：" + e.getMessage());
        }
    }
}
