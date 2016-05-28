#!/usr/bin/awk -f

# Process original Road Accidents CSV file for testing

BEGIN {
	FS=",";
	OFS=",";
#	format = "%s,%s,%06.0s,%s,%s\n";
}
{
    if (NR > 1) {
        split($8,array,"/");
#        printf("%s-%s-%s\n", array[3],array[2],array[1])
        $8 = sprintf("%s-%s-%s", array[3],array[2],array[1]);
    }
	print($1, $8, $10, $13, $14);
}

