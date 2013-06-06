from distutils.core import setup
import py2exe

setup(console=["download_no_stackless.py", "download.py", "download_native_thread.py"])
