#!/usr/bin/env bash

# merge image to tiff
tesseract LAN.texas.exp0.tif LAN.texas.exp0 batch.nochop makebox
# fix box using jTessBoxEditor
tesseract LAN.texas.exp0.tif LAN.texas.exp0 nobatch box.train
unicharset_extractor LAN.texas.exp0.box
set_unicharset_properties -U unicharset -O unicharset --script_dir=./
shapeclustering –F font_properties  -U unicharset –O LAN.unicharset LAN.texas.exp0.tr
mftraining -F font_properties  -U unicharset LAN.texas.exp0.tr
cntraining LAN.texas.exp0.tr
rename unicharset texas.unicharset
rename inttemp texas.inttemp
rename normproto texas.normproto
rename pffmtable texas.pffmtable
rename shapetable texas.shapetable
combine_tessdata texas.
