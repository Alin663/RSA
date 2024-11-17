import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Generazione di due numeri primi grandi
        int bitLength = 512; // Lunghezza dei numeri primi in bit
        Random random = new Random();
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        BigInteger q = BigInteger.probablePrime(bitLength, random);

        // Calcolo di n e φ(n)
        BigInteger n = p.multiply(q); // n = p * q
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)); // φ(n) = (p-1)*(q-1)

        // Scelta di e
        BigInteger e;
        do {
            e = BigInteger.probablePrime(bitLength / 2, random); // Sceglie un numero primo per e
        } while (!e.gcd(phi).equals(BigInteger.ONE) || e.compareTo(phi) >= 0); // e deve essere coprimo di φ(n)

        // Calcolo di d
        BigInteger d = e.modInverse(phi);

        // Visualizzazione delle chiavi
        System.out.println("Chiave pubblica: (e=" + e + ", n=" + n + ")");
        System.out.println("Chiave privata: (d=" + d + ", n=" + n + ")");

        // Input del messaggio dall'utente
        System.out.println("Inserisci un messaggio da cifrare:");
        String message = scanner.nextLine();

        // Convertire il messaggio in un numero
        BigInteger m = new BigInteger(message.getBytes());
        if (m.compareTo(n) >= 0) {
            System.out.println("Errore: il messaggio è troppo lungo per essere cifrato con questa chiave.");
            scanner.close();
            return;
        }

        // Cifratura
        BigInteger c = m.modPow(e, n); // c = m^e mod n
        System.out.println("Messaggio cifrato (numerico): " + c);

        // Decifratura
        BigInteger decrypted = c.modPow(d, n); // m = c^d mod n
        String decryptedMessage = new String(decrypted.toByteArray());
        System.out.println("Messaggio decifrato: " + decryptedMessage);

        scanner.close();
    }
}


