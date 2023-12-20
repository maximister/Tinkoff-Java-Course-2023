package edu.project3.renderer;

import edu.project3.metrics.MetricsContainer;
import edu.project3.renderers.MarkdownRenderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MarkdownRendererTest {
    @Test
    @DisplayName("Testing formatting functional")
    public void markdownRendererHeaderFormattingTest() {
        MarkdownRenderer renderer = new MarkdownRenderer();

        assertThat(renderer.addHeader("header")).isEqualTo("#### header");
    }

    @Test
    @DisplayName("Testing formatting functional")
    public void markdownRendererBuildingTableTest() {
        MarkdownRenderer renderer = new MarkdownRenderer();

        String renderedTable = renderer.getRenderedTable(getTestTable(), getTestTable().getCols());
        assertThat(renderedTable).isEqualTo("#### Test Table\n" +
            "\n" +
            "|test metrics|test value|\n" +
            "|:-----------|---------:|\n" +
            "|      metric|     value|\n");

    }

    private static MetricsContainer getTestTable() {
        MetricsContainer table = new TestMetricsContainer();
        table.build();
        return table;
    }
}
