#include <boost/python.hpp>
using namespace boost::python;

BOOST_PYTHON_MODULE(mp3)
{
    class_<MP3>("path", init<std::string>())
        .def("get_name", &MP3::get_name)
        ;
}
