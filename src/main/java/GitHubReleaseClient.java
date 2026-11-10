import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class GitHubReleaseClient {

    public String fetchReleases(String repository)
            throws IOException, InterruptedException {
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
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
            );

            int statusCode = response.statusCode();
            System.out.println("HTTP 状态码：" + statusCode);
            if (statusCode != 200) {
                throw new IOException("GitHub 请求失败，HTTP 状态码："
                        + statusCode + "，响应正文：" + response.body());
            }

            return response.body();
        }
    }
}
