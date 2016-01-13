package appian.ci.core;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UuidUtil {
    
    Pattern regex = Pattern.compile("_.-(\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12})_\\d+");
               
    public UUID fromString(String uuidString)
    {
        // _g-0000dc11-edca-8000-f92f-7f0000014e7a_74
        Matcher matcher = regex.matcher(uuidString);
        if (matcher.matches())
        {
            return UUID.fromString(matcher.group(1));
        }
        
        return null;
    }
}
