/**
 * Created on 2005-8-6 5:10:29
 * @author 糊涂鬼
 */
public class ReadMp3ID3v1Info {
    private static final int TAG_SIZE = 128;
    private static final int TITLE_SIZE = 30;
    private static final int ARTIST_SIZE = 30;
    private static final int ALBUM_SIZE = 30;
    private static final int YEAR_SIZE = 4;
    private static final int COMMENT_SIZE = 29;
    private static final int TRACK_LOCATION = 126;
    private static final int GENRE_LOCATION = 127;
    private static final int MAX_GENRE = 255;
    private static final int MAX_TRACK = 255;
    private static final String ENC_TYPE = "Cp437";
    private static final String TAG_START = "TAG";
    
    public static void main(String[] args){
        try {
            File mp3 = new File("/media/sda5/音乐/music-4-1/youtookmyheartaway.mp3");
            RandomAccessFile raf = new RandomAccessFile( mp3, "r" );
            raf.seek(raf.length() - TAG_SIZE);
            byte[] buf = new byte[TAG_SIZE];
            raf.read(buf, 0, TAG_SIZE);
            String tag = new String(buf, 0, TAG_SIZE, "Cp437");
            int start = TAG_START.length();
            System.out.println("文件名:  " + mp3.getName());
            System.out.println("标题  ： " + tag.substring(start, start += TITLE_SIZE).trim());
            System.out.println("艺术家： " + tag.substring(start, start += ARTIST_SIZE).trim());
            raf.close();
            
            System.out.println("====================================");

            //File mp32 = new File("F:/音乐/MP3/英文组合/blue - you make me wanna.mp3");
            //raf = new RandomAccessFile( mp32, "r" );
            //raf.seek(raf.length() - 128);
            //raf.read(buf, 0, 128);
            //tag = new String(buf, 0, 128, "Cp437");
            //start = TAG_START.length();
            //System.out.println("文件名:  " + mp32.getName());
            //System.out.println("标题  ： " + tag.substring(start, start += 30).trim());
            //System.out.println("艺术家： " + tag.substring(start, start += 30).trim());
            //raf.close();
        } catch (Exception e) {
        }
    }
}
