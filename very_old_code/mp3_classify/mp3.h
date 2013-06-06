#include <string>

const TAG_SIZE = 128;
const TITLE_SIZE = 30;
const ARTIST_SIZE = 30;
const ALBUM_SIZE = 30;
const YEAR_SIZE = 4;
const COMMENT_SIZE = 29;
const TRACK_LOCATION = 126;
const GENRE_LOCATION = 127;
const MAX_GENRE = 255;
const MAX_TRACK = 255;
const ENC_TYPE = "Cp437";
const TAG_START = "TAG";

//struct MP3ID3v1Info {
//};
class MP3
{
    public:
        MP3(std::string path, std::string name);
        ~MP3();
        
        std::string get_path(void);
        void set_path(std::string newpath);
         std::string get_name(void);
        void set_name(std::string newname);
        std::string get_singer(void);
    private:
        std::string mp3_path = "";
        std::string mp3_name = "";
        std::string mp3_singer = "";
};
