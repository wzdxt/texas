#!/usr/bin/env bash

unicharset_extractor *.box
set_unicharset_properties -U unicharset -O unicharset --script_dir=../
shapeclustering -F ../font_properties  -U unicharset -O LAN.unicharset *.tr
mftraining -F ../font_properties  -U unicharset *.tr
cntraining *.tr
mv unicharset texas-rank.unicharset
mv inttemp texas-rank.inttemp
mv normproto texas-rank.normproto
mv pffmtable texas-rank.pffmtable
mv shapetable texas-rank.shapetable
combine_tessdata texas-rank.

