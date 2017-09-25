#!/usr/bin/env bash

unicharset_extractor *.box
set_unicharset_properties -U unicharset -O unicharset --script_dir=../
shapeclustering -F ../font_properties  -U unicharset -O LAN.unicharset *.tr
mftraining -F ../font_properties  -U unicharset *.tr
cntraining *.tr
mv unicharset texas-amount.unicharset
mv inttemp texas-amount.inttemp
mv normproto texas-amount.normproto
mv pffmtable texas-amount.pffmtable
mv shapetable texas-amount.shapetable
combine_tessdata texas-amount.

