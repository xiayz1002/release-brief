public class Main {

    public static void main(String[] args) {
        String repository = "demo/release-brief";
        String version = "v1.0.0";
        String description = "新增版本列表功能，修复显示问题";
        boolean prerelease = false;

        printRelease(repository, version, description, prerelease);
    }

    public static void printRelease(
            String repository,
            String version,
            String description,
            boolean prerelease) {

        System.out.println("仓库：" + repository);
        System.out.println("版本：" + version);
        System.out.println("更新说明：" + description);
        System.out.println("版本类型：" + (prerelease ? "预发布版本" : "正式版本"));
    }
}