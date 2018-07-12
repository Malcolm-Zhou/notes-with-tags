package com.malcolm.util;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

/**
 * @author malcolm
 */
public class MarkdownUtil {
    private final static PegDownProcessor md = new PegDownProcessor(Extensions.ALL_WITH_OPTIONALS);

    public static String pegDown(String content) {
        String html = md.markdownToHtml(content);
        html = addTableCSS(html);
        return html;
    }

    private static String addTableCSS(String html) {
        return html.replace("<table>", "<table class=\"table table-bordered\">");

    }
}
