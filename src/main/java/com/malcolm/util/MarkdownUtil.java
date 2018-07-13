package com.malcolm.util;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.Arrays;

/**
 * @author malcolm
 */
public class MarkdownUtil {

    private static MutableDataSet options;

    static {
        options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);

        // enable table parse!
        options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), AnchorLinkExtension.create()));
    }

    public static String flexmark(String content) {
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(content);
        String html =renderer.render(document);
        html = addTableCSS(html);
        return html;
    }

    private static String addTableCSS(String html) {
        return html.replace("<table>", "<table class=\"table table-bordered\">");

    }
}
