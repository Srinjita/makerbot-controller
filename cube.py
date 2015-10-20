# We should be able to pass this output to Java
# For instructions, see http://stackoverflow.com/questions/525212/how-to-run-unix-shell-script-from-java-code
# Should be something like Runtime.getRuntime().exec(myCommand);

import sys
import os
import subprocess

PATH_TO_OPENSCAD = "/Applications/OpenSCAD.app/Contents/MacOS/OpenSCAD"
SCAD_FILE = "cube.scad"
STL_FILE = "cube.stl"

def main(args):
	if len(args) < 7:
		print "Usage: python cube.py x y z l w h (all in mm)"
	else:
		x, y, z, l, w, h = args[1:7]
		openSCADsrc = "translate([%s,%s,%s]) { cube([%s,%s,%s]); }" % (x,y,z,l,w,h)
		# write scad file
		with open(SCAD_FILE, 'w') as f:
			f.write(openSCADsrc)
		# write STL
		cwd = os.getcwd() + "/"
		scadToSTLcmd = "%s -o %s%s %s%s" % (PATH_TO_OPENSCAD, cwd, STL_FILE, cwd, SCAD_FILE)
		# print scadToSTLcmd
		os.popen(scadToSTLcmd)
		print "%s%s" % (cwd,STL_FILE)

if __name__ == "__main__":
	main(sys.argv)
