package cn.gy.util;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FreemarkerUtil {

    public static void process(Configuration conf, String template, Map<String, Object> data, Path path) throws IOException, TemplateException {
        try (BufferedWriter out = Files.newBufferedWriter(path)) {
            conf.getTemplate(template).process(data, out);
        }
    }

    public static void process(Configuration conf, String template, Map<String, Object> data, Writer out) throws IOException, TemplateException {
        conf.getTemplate(template).process(data, out);
    }
}
