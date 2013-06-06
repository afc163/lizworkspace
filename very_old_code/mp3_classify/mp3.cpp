#include <iostream.h>
#include "mp3.h"

MP3::MP3(std::string path, std::string name)
{
    mp3_path = path;
    mp3_name = name;
    
    try
    {
        ifstream fin(mp3_path + mp3_name);
    }
}

MP3::~MP3()
{
    mp3_path = "";
    mp3_name = "";
}
std::string MP3::get_path(void)
{
    return mp3_path;
}
void MP3::set_path(std::string newpath)
{
    mp3_path = newpath;
}
std::string MP3::get_name(void)
{
    return mp3_name;
}
void MP3::set_name(std::string newname)
{
    mp3_name = newname;
}
std::string MP3::get_singer(void)
{
    return mp3_singer;
}
