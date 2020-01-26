#Sletter gamle fasitfiler
for file in ./tester/fasit; do
	rm $file
done

#kjorer med fasitAsp for aa faa riktige logfiler
for file in ./tester/*.asp; do
	java -jar fasitAsp.jar -testscanner $file
done

#Flytter fasitlog til fasitmappe
mv *.log tester/fasit/
#Kjorer vanlige asp
for file in ./tester/*.asp; do
	java -jar Asp.jar -testscanner $file
done


echo "tester diff gcd"
diff tester/gcd.log tester/gcdfasit.log
echo "tester diff primes"
diff tester/primes.log tester/primesfasit.log
echo "tester diff easter"
diff tester/easter.log tester/easterfasit.log
echo "tester diff ukedag"
diff tester/ukedag.log tester/ukedagfasit.log
echo "tester diff tallmed0"
diff tester/tall-med-0.log tester/tall-med-0fasit.log
echo "tester diff blankelinjer"
diff tester/blanke-linjer.log tester/blanke-linjerfasit.log

cd tester/

for file in *.log; do
	echo "printer diff for $file"
	diff $file fasit/$file
done
cd ..


