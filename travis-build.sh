#!/bin/bash
set -e
rm -rf *.zip
./grailsw refresh-dependencies --non-interactive
./grailsw test-app --non-interactive
./grailsw package-plugin --non-interactive
./grailsw doc --pdf --non-interactive
