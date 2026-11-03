import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Release> releases = List.of(
                new Release("demo/release-brief", "v1.0.0",
                        "新增版本列表功能", false),
                new Release("demo/release-brief", "v1.1.0-beta",
                        "测试版本筛选功能", true),
                new Release("demo/release-brief", "v1.1.0",
                        "新增正式版本筛选功能", false)
        );

        int count = 0;

        for (Release release : releases) {
            if (release.isPrerelease()) {
                continue;
            }

            printRelease(release);
            count++;
        }

        System.out.println("共展示 " + count + " 个正式版本");
    }

    public static void printRelease(Release release) {
        System.out.println("仓库：" + release.getRepository());
        System.out.println("版本：" + release.getVersion());
        System.out.println("更新说明：" + release.getDescription());
        System.out.println("版本类型："
                + (release.isPrerelease() ? "预发布版本" : "正式版本"));
        System.out.println("--------------------");
    }
}