import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class GitHubDemo {

    public static void main(String[] args) {
        String repository = "spring-projects/spring-ai";
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

            Gson gson = new Gson();
            GitHubRelease[] releases = gson.fromJson(
                    json,
                    GitHubRelease[].class
            );

            if (releases == null) {
                throw new JsonParseException("GitHub 返回的 Release 列表为 null");
            }

            int count = 0;

            for (GitHubRelease githubRelease : releases) {
                if (githubRelease == null) {
                    throw new JsonParseException("Release 列表中包含 null");
                }

                Release release = githubRelease.toRelease(repository);

                if (release.isPrerelease()) {
                    continue;
                }

                Main.printRelease(release);
                count++;
            }

            System.out.println("本次获取 " + releases.length + " 条 Release");
            System.out.println("其中正式版本 " + count + " 条");

        } catch (IOException e) {
            System.err.println("网络请求失败：" + e.getMessage());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("请求被中断");
        }catch (JsonParseException e) {
            System.err.println("GitHub 数据解析失败：" + e.getMessage());
        }
    }
}