import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * lingfan
 * 2019-07-26 17:42
 */
public class Test {


    public static void main(String[] args) {
//        Map<String,Object> map = new HashMap();
//        map.put("11","aaa");
//        map.put("22","bbb");
//
//        for (String key : map.keySet()) {
//            System.out.println("key:"+key+",value:"+map.get(key));
//            map.put("33","ccc");
//
//        }

        String line = "<b>Message:￥{detail}</b><br/>\n";
        Pattern pattern = Pattern.compile("￥\\{[^\\}]+}",Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(line);
        while(matcher.find()){
            String findStr = matcher.group(0);
            findStr = findStr.replaceAll("￥\\{||}", "");

            //matcher = pattern.matcher(line);
        }
    }

}
