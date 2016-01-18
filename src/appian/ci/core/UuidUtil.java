package appian.ci.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UuidUtil {
    
    // matches any of these
    // 0000dc11-edca-8000-f92f-7f0000014e7a  
    // -a_0000dc11-edca-8000-f92f-7f0000014e7a  
    // _c_0000dc11-edca-8000-f92f-7f0000014e7a  
    //
    Pattern regex = Pattern.compile("((_[a-z]-)?(\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12})(_\\d+)?)");
               
    public String fromString(String uuidString)
    {
        Matcher matcher = regex.matcher(uuidString);
        if (matcher.matches())
        {
            return matcher.group(1);
        }
        
        return null;
    }
}
