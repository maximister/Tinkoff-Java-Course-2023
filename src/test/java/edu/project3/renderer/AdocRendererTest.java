package edu.project3.renderer;

import edu.project3.metrics.MetricsContainer;
import edu.project3.renderers.AdocRenderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AdocRendererTest {
    @Test
    @DisplayName("Testing formatting functional")
    public void adocRendererHeaderFormattingTest() {
        AdocRenderer renderer = new AdocRenderer();

        assertThat(renderer.addHeader("header")).isEqualTo("==== header");
    }

    @Test
    @DisplayName("Testing formatting functional")
    public void adocRendererBuildingTableTest() {
        AdocRenderer renderer = new AdocRenderer();

        String renderedTable = renderer.getRenderedTable(getTestTable(), getTestTable().getCols());
        assertThat(renderedTable).isEqualTo("==== Test Table\n" +
            "\n" +
            "|=======================\n" +
            "|test metrics|test value\n" +
            "|      metric|     value\n" +
            "|=======================\n");

    }

    private static MetricsContainer getTestTable() {
        MetricsContainer table = new TestMetricsContainer();
        table.build();
        return table;
    }
}
