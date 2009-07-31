
import java.util.regex.*;
import java.io.*;
public class REX {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		//HtmlParser hp=new HtmlParser();
		String source="<A href=http://www.bookge.com/>¿ÐÊéÍø</a></td>";
		
		String regex="<[aA]\\s*href=([^ >]*)\\s*>";
		Pattern p= Pattern.compile(regex);
		Matcher m=p.matcher(source);
		System.out.println(m.groupCount());
           
	}
	
public void parse(String source)throws Exception{
	
	//String regex="r'<a .*?href=["\'](?P<url>[^ "\']+)["\'][ >]'";
	//String regex="/<a\\s+href=[\\"|\\']?([^>"\\' ]+)[\\"|\\']?\s*[^>]*>([^>]+)<\/a>/i";
	///String regex="<[aA]\\s+href=(.*)<//a>";
	//String regex="<[aA]\\s*href=[^>]*\\s*>";
	
	
	}

}
