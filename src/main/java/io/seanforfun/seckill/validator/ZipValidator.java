package io.seanforfun.seckill.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/28 16:00
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class ZipValidator {
    private static final String CANADA_REGEX = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
    private static final String USA_REGEX = "^[0-9]{5}(?:-[0-9]{4})?$";

    private static final String CANADA = "CA";
    private static final String USA = "US";

    private static final Map<String, String> ZIP_MAP = new HashMap<>();
    public static final Map<String, ZipValidator> ZIP_VALIDATOR_MAP = new HashMap<>();

    static {
        ZIP_MAP.put(CANADA, CANADA_REGEX);
        ZIP_MAP.put(USA, USA_REGEX);
    }

    public static final ZipValidator CANADA_ZIP_VALIDATOR = new ZipValidator(CANADA);
    public static final ZipValidator USA_ZIP_VALIDATOR = new ZipValidator(USA);

    static {
        ZIP_VALIDATOR_MAP.put(CANADA, CANADA_ZIP_VALIDATOR);
        ZIP_VALIDATOR_MAP.put(USA, USA_ZIP_VALIDATOR);
    }

    private String regex;
    private String country;
    private Pattern pattern;

    private ZipValidator(String country){
        this.country = country;
        this.regex = ZIP_MAP.get(country);
        this.pattern = Pattern.compile(this.regex);
    }

    public boolean validate(String zip){
        Matcher matcher = this.pattern.matcher(zip);
        return matcher.matches();
    }
}
