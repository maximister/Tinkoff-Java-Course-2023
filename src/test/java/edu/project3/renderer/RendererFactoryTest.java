package edu.project3.renderer;

import edu.project3.metrics.MetricsContainer;
import edu.project3.renderers.AdocRenderer;
import edu.project3.renderers.MarkdownRenderer;
import edu.project3.renderers.TableRenderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project3.renderers.RendererFactory.getRenderer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RendererFactoryTest {
    @Test
    @DisplayName("testing getRenderer with md argument")
    public void getRenderer_shouldReturnMdRenderer() {
        String command = "markdown";
        TableRenderer renderer = getRenderer(command);

        assertThat(renderer instanceof MarkdownRenderer).isTrue();
    }

    @Test
    @DisplayName("testing getRenderer with adoc argument")
    public void getRenderer_shouldReturnAdocRenderer() {
        String command = "adoc";
        TableRenderer renderer = getRenderer(command);

        assertThat(renderer instanceof AdocRenderer).isTrue();
    }

    @Test
    @DisplayName("testing getRenderer with invalid argument")
    public void getRenderer_shouldThrowException() {
        String command = "ktoUbilMsrka";

        assertThrows(
            IllegalArgumentException.class,
            () -> getRenderer(command)
        );
    }
}
