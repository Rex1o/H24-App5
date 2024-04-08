import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnalLexTest {
    StringBuilder stringBuilder = new StringBuilder();

    @BeforeEach
    public void init() {
        // Reset le string builder est plus rapide qu'en faire un nouveau
        stringBuilder.setLength(0);
    }

    //Test fournis par l'ensigant #1
    @Test
    public void Validation1() {
        // Arrange
        String testValue = "(U_x + V_y ) * W_z / 35";
        System.out.println(testValue);
        List<String> expectedValues = Arrays.asList("(", "U_x", "+", "V_y", ")", "*", "W_z", "/", "35");
        // Act
        List<Terminal> actualValues = DoLexicalAnalysis(testValue);
        // Assert
        CompareValues(actualValues, expectedValues);

        PrintTerminals(actualValues);
    }

    //Test fournis par l'ensigant #2
    @Test
    public void Validation2() {
        //On expect un erreur
        // Arrange
        String testValue = "(U_x + V_y ) * W__z / 35";
        System.out.println(testValue);

        List<Terminal> actualValues = new ArrayList<>();
        // Act
        try {
            AnalLex lex = new AnalLex(testValue);

            while (lex.resteTerminal()) {
                actualValues.add(lex.prochainTerminal());
            }
        } catch (ErreurLex e) {
            System.out.println(e.toString());
        } finally {
            PrintTerminals(actualValues);
        }
    }


    // Test avec un chiffre apres une lettre dans un identificateur
    @Test
    public void ExceptionChiffreApresLettreIdentificateur() {
        // Arrange
        String testValue = "45 * 45 + A_l4 + Patate";
        System.out.println(testValue);

        List<Terminal> actualValues = new ArrayList<>();

        ErreurLex thrown = assertThrows(
                ErreurLex.class,
                () -> DoLexicalAnalysis(testValue),
                "Supposé recevoir un erreur lexical indiquant le chiffre 4 dans un identificateur"
        );
        System.out.println(thrown.toString());
    }

    // Test avec chiffre dans Identificateur
    @Test
    public void ExceptionChiffreApresUndeScoreIdentificateur() {
        // Arrange
        String testValue = "All_0o * 45 + A_l + Patate";
        System.out.println(testValue);

        List<Terminal> actualValues = new ArrayList<>();

        ErreurLex thrown = assertThrows(
                ErreurLex.class,
                () -> DoLexicalAnalysis(testValue),
                "Supposé recevoir un erreur lexical"
        );
        System.out.println(thrown.toString());
    }

    // Test avec chiffre dans Identificateur
    @Test
    public void ExceptionDeuxUnderScoreIdentificatieur() {
        // Arrange
        String testValue = "All__o * 45 + A_l + Patate";
        System.out.println(testValue);

        List<Terminal> actualValues = new ArrayList<>();

        ErreurLex thrown = assertThrows(
                ErreurLex.class,
                () -> DoLexicalAnalysis(testValue),
                "Supposé recevoir un erreur lexical"
        );
        System.out.println(thrown.toString());
    }

    // Test avec chiffre dans Identificateur
    @Test
    public void ExceptionIdentificateurFinissantAvecUnderscore() {
        // Arrange
        String testValue = "All_ * 45 + Patate";
        System.out.println(testValue);

        List<Terminal> actualValues = new ArrayList<>();

        ErreurLex thrown = assertThrows(
                ErreurLex.class,
                () -> DoLexicalAnalysis(testValue),
                "Supposé recevoir un erreur lexical"
        );
        System.out.println(thrown.toString());
    }

    @Test
    public void ExceptionIdentificateurFinissantAvecUnderscoreFin() {
        // Arrange
        String testValue = "9 + 5 * 14 + Allo + Pipato_";
        System.out.println(testValue);

        List<Terminal> actualValues = new ArrayList<>();

        ErreurLex thrown = assertThrows(
                ErreurLex.class,
                () -> DoLexicalAnalysis(testValue),
                "Supposé recevoir un erreur lexical"
        );
        System.out.println(thrown.toString());
    }

    @Test
    public void LettreSuivieChiffre() {
        // Arrange
        String testValue = "A9 + 5 * 14 + Allo + Pipato";
        System.out.println(testValue);

        List<Terminal> actualValues = new ArrayList<>();

        ErreurLex thrown = assertThrows(
                ErreurLex.class,
                () -> DoLexicalAnalysis(testValue),
                "Supposé recevoir un erreur lexical"
        );
        System.out.println(thrown.toString());
    }

    @Test
    public void ChiffreSuiviLettre() {
        // Arrange
        String testValue = "9LOL + 5 * 14 + Al_lo + Pipato";
        System.out.println(testValue);

        List<Terminal> actualValues = new ArrayList<>();

        ErreurLex thrown = assertThrows(
                ErreurLex.class,
                () -> DoLexicalAnalysis(testValue),
                "Supposé recevoir un erreur lexical"
        );
        System.out.println(thrown.toString());
    }

    @Test
    public void OperationsSansEspaces() {
        // Arrange
        String testValue = "Al_a +5 *14 + ( Allo+ Pipato)";
        System.out.println(testValue);
        List<String> expectedValues = Arrays.asList("Al_a", "+", "5", "*", "14", "+", "(", "Allo", "+", "Pipato", ")");
        // Act
        List<Terminal> actualValues = DoLexicalAnalysis(testValue);
        // Assert
        CompareValues(actualValues, expectedValues);

        PrintTerminals(actualValues);
    }

    @Test
    public void FinirParOperateur() {
        // Arrange
        String testValue = "Al_a () +";
        System.out.println(testValue);
        List<String> expectedValues = Arrays.asList("Al_a", "(", ")","+");
        // Act
        List<Terminal> actualValues = DoLexicalAnalysis(testValue);
        // Assert
        CompareValues(actualValues, expectedValues);

        PrintTerminals(actualValues);
    }

    @Test
    public void FinirAvecEspace() {
        // Arrange
        String testValue = "Al_a () +    ";
        System.out.println(testValue);
        List<String> expectedValues = Arrays.asList("Al_a", "(", ")","+");
        // Act
        List<Terminal> actualValues = DoLexicalAnalysis(testValue);
        // Assert
        CompareValues(actualValues, expectedValues);

        PrintTerminals(actualValues);
    }

    @Test
    public void Finir1Chiffre() {
        // Arrange
        String testValue = "5*5";
        System.out.println(testValue);
        List<String> expectedValues = Arrays.asList("5", "*", "5");
        // Act
        List<Terminal> actualValues = DoLexicalAnalysis(testValue);
        // Assert
        CompareValues(actualValues, expectedValues);

        PrintTerminals(actualValues);
    }

    @Test
    public void Finir1CaratereIndicateur() {
        // Arrange
        String testValue = "5*A";
        System.out.println(testValue);
        List<String> expectedValues = Arrays.asList("5", "*", "A");
        // Act
        List<Terminal> actualValues = DoLexicalAnalysis(testValue);
        // Assert
        CompareValues(actualValues, expectedValues);

        PrintTerminals(actualValues);
    }

    @Test
    public void FinirOperateur() {
        // Arrange
        String testValue = "5*";
        System.out.println(testValue);
        List<String> expectedValues = Arrays.asList("5", "*");
        // Act
        List<Terminal> actualValues = DoLexicalAnalysis(testValue);
        // Assert
        CompareValues(actualValues, expectedValues);

        PrintTerminals(actualValues);
    }

    @Test
    public void TestGeneral() {
        // Arrange
        String testValue = "5+6   + 9 -96 / (5 +3) * ((45+63) +98)";
        System.out.println(testValue);
        List<String> expectedValues = Arrays.asList("5", "+", "6","+","9","-","96","/","(","5","+","3",")","*","(","(","45","+","63",")","+","98",")");
        // Act
        List<Terminal> actualValues = DoLexicalAnalysis(testValue);
        // Assert
        CompareValues(actualValues, expectedValues);

        PrintTerminals(actualValues);
    }

    private void PrintTerminals(List<Terminal> terminals) {
        for (int i = 0; i < terminals.size(); i++) {
            System.out.println(terminals.get(i).toString());
        }
    }

    private void CompareValues(List<Terminal> terminals, List<String> expectedValues) {
        for (int i = 0; i < terminals.size(); i++) {
            assertEquals(terminals.get(i).getChaine(), expectedValues.get(i));
        }
    }

    private List<Terminal> DoLexicalAnalysis(String testValue) throws ErreurLex {
        List<Terminal> terminals = new ArrayList<>();
        AnalLex lex = new AnalLex(testValue);

        while (lex.resteTerminal()) {
            terminals.add(lex.prochainTerminal());
        }
        return terminals;
    }
}