(**** start of start.gcode ****)
(**** This file is for a Thing-o-Matic with a Stepstruder ****)
(To change this file, modify /ReplicatorG/machines/thingomatic/start.gcode)
G21 (set units to mm)
G90 (set positioning to absolute)
M104 S225 T0 (set extruder temperature)
M109 S110 T0 (set HBP temperature)
(**** begin homing ****)
G162 Z F1100 (home Z axis maximum)
G92 Z-5 (set Z to -5)
G1 Z0.0 (move Z to "0")
G162 Z F100 (home Z axis maximum)
G161 X Y F2500 (home XY axes minimum)
M132 X Y Z (Recall stored home offsets for XYZ)
(**** end homing ****)
M6 T0
M108 R3.0 T0
(**** end of start.gcode ****)