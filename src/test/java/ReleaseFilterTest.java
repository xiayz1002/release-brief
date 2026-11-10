import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReleaseFilterTest {

    @Test
    void shouldKeepOnlyStableReleases() {
        // 准备数据
        Release stable =
                new Release("demo/repo", "v1.0", "正式版", false);

        Release beta =
                new Release("demo/repo", "v2.0-beta", "预发布版", true);

        List<Release> input = List.of(stable, beta);

        // 执行筛选
        List<Release> result = ReleaseFilter.onlyStable(input);

        // 检查结果
        assertEquals(1, result.size());
        assertSame(stable, result.get(0));

        // 原列表应该保持不变
        assertEquals(List.of(stable, beta), input);
    }

    @Test
    void shouldReturnEmptyListForEmptyInput() {
        List<Release> result =
                ReleaseFilter.onlyStable(List.of());

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenAllArePrereleases() {
        Release beta =
                new Release("demo/repo", "v1-beta", "预发布版", true);

        List<Release> result =
                ReleaseFilter.onlyStable(List.of(beta));

        assertTrue(result.isEmpty());
    }
}