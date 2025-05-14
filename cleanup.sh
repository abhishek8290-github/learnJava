#!/bin/bash

# Find and delete all .class files in the current directory and subdirectories
find . -name "*.class" -type f -delete

echo "All .class files have been deleted."