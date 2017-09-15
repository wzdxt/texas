#!/usr/bin/env bash

# tesseract 需要添加 psm 参数
# 1 merge image to tiff
# 2 生成box文件
tesseract LAN.texas.exp0.tif LAN.texas.exp0 batch.nochop makebox
# 3 fix box using jTessBoxEditor
# 4 生成tr文件
tesseract LAN.texas.exp0.tif LAN.texas.exp0 nobatch box.train
# 5 生成unicharset文件 : unicharset_extractor LAN.texas.exp0.box ...exe1.box
unicharset_extractor LAN.texas.exp0.box
set_unicharset_properties -U unicharset -O unicharset --script_dir=./
# 6 聚类shape
shapeclustering -F font_properties  -U unicharset -O LAN.unicharset LAN.texas.exp0.tr
# 7 聚集字符特征
mftraining -F font_properties  -U unicharset LAN.texas.exp0.tr
# 8 生成normproto
cntraining LAN.texas.exp0.tr
mv unicharset texas.unicharset
mv inttemp texas.inttemp
mv normproto texas.normproto
mv pffmtable texas.pffmtable
mv shapetable texas.shapetable
# 生成语言包
combine_tessdata texas.
