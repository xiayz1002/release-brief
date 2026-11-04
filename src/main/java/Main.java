import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Path path = Path.of("releases.json");

        try {
            List<Release> releases = loadReleases(path);

            int count = 0;
            for (Release release : releases) {
                if (release.isPrerelease()) {
                    continue;
                }

                printRelease(release);
                count++;
            }

            System.out.println("共展示 " + count + " 个正式版本");

        } catch (IOException e) {
            System.err.println("读取文件失败：" + path.toAbsolutePath());
            System.err.println("原因：" + e.getMessage());

        } catch (JsonParseException e) {
            System.err.println("JSON 解析失败，请检查文件格式。");
            System.err.println("原因：" + e.getMessage());
        }
    }

    public static List<Release> loadReleases(Path path) throws IOException {
        String json = Files.readString(path, StandardCharsets.UTF_8);

        Gson gson = new Gson();
        Release[] releases = gson.fromJson(json, Release[].class);

        if (releases == null) {
            throw new JsonParseException("文件内容必须是 JSON 数组");
        }

        for (Release release : releases) {
            if (release == null) {
                throw new JsonParseException("数组中不能包含 null");
            }
        }

        return Arrays.asList(releases);
    }

    public static void printRelease(Release release) {
        System.out.println("仓库：" + release.getRepository());
        System.out.println("版本：" + release.getVersion());

        System.out.println("GitHub Release ID："
                + (release.getExternalId() == null
                ? "未提供" : release.getExternalId()));

        System.out.println("发布时间："
                + (release.getPublishedAt() == null
                ? "未提供" : release.getPublishedAt()));

        System.out.println("原始链接："
                + (release.getHtmlUrl() == null
                ? "未提供" : release.getHtmlUrl()));

        System.out.println("更新说明：" + release.getDescription());

        System.out.println("版本类型："
                + (release.isPrerelease()
                ? "预发布版本" : "正式版本"));

        System.out.println("--------------------");
    }
}