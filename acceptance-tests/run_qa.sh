#!/bin/bash
gradle acceptanceTests -Dheadless=false -DbaseUrl=https://qa.dft-bluebadge.org.uk
#-DbStackMode=false -DbStackBrowserName="ie" -DbStackBrowserVersion="11.0"
#-Dcucumber.options="--tags @SubmitApplicationWPMSRoute"