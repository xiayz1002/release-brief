import java.util.ArrayList;
import java.util.List;

public class ReleaseFilter {

    public static List<Release> onlyStable(List<Release> releases) {
        List<Release> stableReleases = new ArrayList<>();
        for (Release release : releases) {
            if (!release.isPrerelease()) {
                stableReleases.add(release);
            }
        }
        return stableReleases;
    }
}
