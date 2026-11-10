import com.google.gson.JsonParseException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReleaseParserTest {

    @Test
    void shouldMapGitHubFieldsToRelease() {
        // 固定的模拟数据，不请求 GitHub
        String json = """
                [
                  {
                    "id": 123,
                    "tag_name": "v1.0.0",
                    "body": "修复显示问题",
                    "prerelease": false,
                    "html_url": "https://example.com/releases/v1",
                    "published_at": "2026-11-01T10:00:00Z"
                  }
                ]
                """;

        List<Release> result =
                ReleaseParser.parseGitHub(json, "demo/repo");

        assertEquals(1, result.size());

        Release release = result.get(0);

        assertEquals("demo/repo", release.getRepository());
        assertEquals("v1.0.0", release.getVersion());
        assertEquals("修复显示问题", release.getDescription());
        assertFalse(release.isPrerelease());
        assertEquals(Long.valueOf(123), release.getExternalId());
        assertEquals(
                "https://example.com/releases/v1",
                release.getHtmlUrl()
        );
        assertEquals(
                "2026-11-01T10:00:00Z",
                release.getPublishedAt()
        );
    }

    @Test
    void shouldReturnEmptyListForEmptyJsonArray() {
        List<Release> result =
                ReleaseParser.parseGitHub("[]", "demo/repo");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldRejectMalformedJson() {
        assertThrows(
                JsonParseException.class,
                () -> ReleaseParser.parseGitHub("[", "demo/repo")
        );
    }
}