import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
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

        String url = "https://api.github.com/repos/"
                + repository + "/releases?per_page=3";

        try (HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .header("Accept", "application/vnd.github+json")
                    .header("User-Agent", "release-brief")
                    .GET()
                    .build();

            System.out.println("正在请求：" + url);

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString(
                            StandardCharsets.UTF_8)
            );

            int statusCode = response.statusCode();
            System.out.println("HTTP 状态码：" + statusCode);

            if (statusCode != 200) {
                System.err.println("请求失败，GitHub 返回：");
                System.err.println(response.body());
                return;
            }

            String json = response.body();
            List<Release> releases = ReleaseParser.parseGitHub(json, repository);
            List<Release> stableReleases = ReleaseFilter.onlyStable(releases);

            for (Release release : stableReleases) {
                Main.printRelease(release);
            }

            System.out.println("本次获取 " + releases.size() + " 条 Release");
            System.out.println("其中正式版本 " + stableReleases.size() + " 条");

        } catch (IOException e) {
            System.err.println("网络请求失败：" + e.getMessage());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("请求被中断");
        } catch (JsonParseException e) {
            System.err.println("GitHub 数据解析失败：" + e.getMessage());
        }
    }
}
