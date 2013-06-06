#!/usr/bin/python
# Filename : sub_dirs.py

import os

for root, dirs, files in os.walk('/home/shengyan/workspace/someWork'):
	sub_dirs = [ os.path.join(root, name) for name in files ]
	sub_dirs.sort()

	for c in sub_dirs:
		print c



