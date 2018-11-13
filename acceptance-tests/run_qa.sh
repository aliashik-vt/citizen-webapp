#!/bin/bash
gradle acceptanceTests -Dheadless=false -DbaseUrl=https://qa.dft-bluebadge.org.uk
#-DbStackMode=true -DbStackBrowserName="ie" -DbStackBrowserVersion="11.0" -DbStackUser="sampathmahavitha2" -DbStackKey="TgSoo4cFJycJxqXkzHxT"
# -Dcucumber.options="--tags @PageValidations"
