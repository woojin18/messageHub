package kr.co.uplus.cm.common.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

@Plugin(name="LogMaskingConverter", category = "Converter")
@ConverterKeys({"spi","trscId"})
public class LogMaskingConverter extends LogEventPatternConverter{
    public static final List<String> MASKING_PATTERNS = new ArrayList<>(Arrays.asList(
            "\"phone\"\\s*:\\s*\"(.*?)\"",
            "phone\\s*=\\s*(.*?)[,|}|\\]]",
            "\"cuid\"\\s*:\\s*\"(.*?)\"",
            "cuid\\s*=\\s*(.*?)[,|}|\\]]"
    ));

    public static final Marker PRIVACY_MARKER = MarkerManager.getMarker("PRIVACY");

    protected LogMaskingConverter(String name, String style) {
        super(name, style);
    }

    public static LogMaskingConverter newInstance(String[] options) {
        return new LogMaskingConverter("spi",Thread.currentThread().getName());
    }

    @Override
    public void format(LogEvent event, StringBuilder outputMessage) {
        String message = event.getMessage().getFormattedMessage();
        String maskedMessage = message;
        try {

            if(event.getMarker() != null
                    && StringUtils.equals(PRIVACY_MARKER.getName(), event.getMarker().getName())) {
                maskedMessage = mask(message);
            }
        } catch (Exception e) {
            maskedMessage = message;
        }
        outputMessage.append(maskedMessage);

    }

    private String mask(String message) {
        Pattern multilinePattern = Pattern.compile(MASKING_PATTERNS.stream().collect(Collectors.joining("|")), Pattern.MULTILINE);
        StringBuilder sb = new StringBuilder(message);
        Matcher matcher = multilinePattern.matcher(sb);

        while (matcher.find()) {
            IntStream.rangeClosed(1, matcher.groupCount()).forEach(group -> {
                if (matcher.group(group) != null) {
                    IntStream.range(matcher.start(group), matcher.end(group)).forEach(i -> sb.setCharAt(i, '*'));
                }
            });
        }

        return sb.toString();
    }

}