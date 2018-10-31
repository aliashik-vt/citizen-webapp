#!/bin/bash
gradle acceptanceTests -Dheadless=false -DbaseUrl=https://qa.dft-bluebadge.org.uk -Dcucumber.options="--tags @PageValidations"