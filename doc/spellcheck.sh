#!/bin/bash

set -eux
set -o pipefail

aspell -t -c content/content.tex
