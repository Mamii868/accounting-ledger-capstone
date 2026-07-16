package com.pluralsight.pennywise.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Lets path variables and request params accept UUIDs in any common format:
 * - standard:  7eb955ae-8014-439d-a171-be5c2371665c
 * - dash-less: 7eb955ae8014439da171be5c2371665c
 * - hex literal from MySQL: 0x7eb955ae8014439da171be5c2371665c
 *
 * Spring Boot auto-registers Converter beans, so no further config is needed.
 * Note: this applies to URL/query values only, not JSON request bodies.
 */
@Component
public class FlexibleUuidConverter implements Converter<String, UUID> {

    @Override
    public UUID convert(String source) {
        String s = source.trim();

        if (s.startsWith("0x") || s.startsWith("0X")) {
            s = s.substring(2);
        }

        if (s.length() == 32 && s.indexOf('-') == -1) {
            s = s.replaceFirst(
                    "([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{12})",
                    "$1-$2-$3-$4-$5");
        }

        return UUID.fromString(s);
    }
}
