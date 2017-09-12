#!/usr/bin/env bash

# merge image to tiff
tesseract LAN.texas.exp0.tif LAN.texas.exp0 batch.nochop makebox
# fix box using jTessBoxEditor
tesseract LAN.texas.exp0.tif LAN.texas.exp0 nobatch box.train
unicharset_extractor LAN.texas.exp0.box
set_unicharset_properties -U unicharset -O unicharset --script_dir=./
shapeclustering -F font_properties  -U unicharset -O LAN.unicharset LAN.texas.exp0.tr
mftraining -F font_properties  -U unicharset LAN.texas.exp0.tr
cntraining LAN.texas.exp0.tr
mv unicharset texas.unicharset
mv inttemp texas.inttemp
mv normproto texas.normproto
mv pffmtable texas.pffmtable
mv shapetable texas.shapetable
combine_tessdata texas.
