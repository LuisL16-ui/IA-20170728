import net.sourceforge.jFuzzyLogic.FIS;
import java.util.Scanner;

public class AnimeRecommendation {
    public static void main(String[] args) {
        String fileName = "src/anime_recommendation.fcl";
        FIS fis = FIS.load(fileName, true);

        if (fis == null) {
            System.err.println("Error al cargar el archivo FCL.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String[] generos = {"accion", "romance", "comedia", "drama", "fantasia", "ciencia_ficcion", "terror"};

        for (String genero : generos) {
            double nivel;
            do {
            System.out.print("Nivel de " + genero + " (0-10): ");
            nivel = scanner.nextDouble();
            if (nivel < 0 || nivel > 10) {
                System.out.println("Por favor, ingrese un valor entre 0 y 10.");
            }
            } while (nivel < 0 || nivel > 10);
            fis.setVariable(genero, nivel);
        }

        // Evaluar
        fis.evaluate();

        // Obtener la recomendación
        double recomendacion = fis.getVariable("recomendacion").getValue();
        System.out.println("indice de recomendación: " + recomendacion);

        // Evaluaciones más detalladas
        if (recomendacion >= 90) {
            System.out.println("Recomendación: Attack on Titan, Sword Art Online, Steins;Gate, Fullmetal Alchemist, Code Geass.");
        } else if (recomendacion >= 75) {
            System.out.println("Recomendación: Death Note, One Punch Man, Vinland Saga, Hunter x Hunter, Fate/Zero.");
        } else if (recomendacion >= 60) {
            System.out.println("Recomendación: Clannad, Your Lie in April, Re:Zero, Erased, The Promised Neverland.");
        } else if (recomendacion >= 45) {
            System.out.println("Recomendación: Tokyo Revengers, Ao Haru Ride, Noragami, Anohana, Angel Beats.");
        } else if (recomendacion >= 30) {
            System.out.println("Recomendación: Lucky Star, Nichijou, K-On!, Saiki Kusuo no Psi-nan, Konosuba.");
        } else {
            System.out.println("Recomendación: Mirai Nikki, Another, Tokyo Ghoul, School Days, Elfen Lied.");
        }
    }
}
