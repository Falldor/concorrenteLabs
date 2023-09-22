#!/bin/bash
if [ "$#" -ne 1 ]; then 
       echo "insira o <diretorio_raiz>"
       exit 1
fi
root_directory=$1
results=()

find "$root_directory" -type d | while read dir; do
	if [ -d "$dir" ]; then
		(go run ./word_count.go "$dir")	
	fi	
done
wait
