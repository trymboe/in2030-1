java -jar Asp.jar -testparser tester/gcd.asp
java -jar Asp.jar -testparser tester/primes.asp
java -jar Asp.jar -testparser tester/easter.asp
java -jar Asp.jar -testparser tester/ukedag.asp
java -jar Asp.jar -testparser tester/tall-med-0.asp
java -jar Asp.jar -testparser tester/blanke-linjer.asp

echo "tester diff gcd"
diff obligatorisk/gcd.log obligatorisk/gcdf.log
echo "tester diff primes"
diff tester/primes.log tester/primesf.log
echo "tester diff easter"
diff tester/easter.log tester/easterf.log
echo "tester diff ukedag"
diff tester/ukedag.log tester/ukedagf.log
echo "tester diff tallmed0"
diff tester/tall-med-0.log tester/tall-med-0f.log
echo "tester diff blankelinjer"
diff tester/blanke-linjer.log tester/blanke-linjerfasit.log
