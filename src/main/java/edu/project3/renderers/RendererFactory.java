package edu.project3.renderers;

public final class RendererFactory {
    private RendererFactory() {
    }

    public static TableRenderer getRenderer(String type) {
        return switch (type) {
            case "markdown" -> new MarkdownRenderer();
            case "adoc" -> new AdocRenderer();
            default -> throw new IllegalArgumentException("Invalid type of report format");
        };
    }
}
