# find src/main/resources -type f | while read -r file; do generated_file="src/main/generated/${file#src/main/resources/}"; [ -f "$generated_file" ] && rm "$generated_file"; done
# find src/main/resources -type f | while read -r file; do generated_file="src/main/generated/${file#src/main/resources/}"; [ -f "$generated_file" ] && rm "$generated_file"; done
find src/main/resources -type f | while read -r file; do rm -f -- "src/main/generated/${file#src/main/resources/}"; done
