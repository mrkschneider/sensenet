#!/bin/bash

set -eu
set -o pipefail

# Extracts the version from a pom.xml file, provided it is contained in the first line that has a <version> tag

PomFile=$1

VersionLine=$(grep -m1 "<version>" $PomFile)
Version=$(echo $VersionLine | sed -r 's@<.*>(.*)</.*>@\1@')

echo ${Version}
