#!/usr/bin/env bash

unicharset_extractor *.box
set_unicharset_properties -U unicharset -O unicharset --script_dir=../
shapeclustering -F ../font_properties  -U unicharset -O LAN.unicharset *.tr
mftraining -F ../font_properties  -U unicharset *.tr
cntraining *.tr
mv unicharset texas.unicharset
mv inttemp texas.inttemp
mv normproto texas.normproto
mv pffmtable texas.pffmtable
mv shapetable texas.shapetable
combine_tessdata texas.

