import java.sql.*;
import java.util.*;
import java.sql.Date;

import com.mysql.jdbc.Driver;

/**
 * La méthode main est le point d'entrée de l'application.
 * Elle se charge de se connecter à la base de données, de récupérer les noms des apprenants,
 * de mélanger la liste, de demander à l'utilisateur de saisir la taille des groupes d'apprenants souhaitée,
 * de créer les groupes et de les afficher et de les rentrer en base de données.
 */
public class Main {
    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String user = "root";
        String pwd = "root";
        String url = "jdbc:mysql://localhost:8082/binome_test";
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            /**

             Connexion à la base de données
             En utilisant les informations de connexion spécifiées (url, nom d'utilisateur et mot de passe)
             */
            con = DriverManager.getConnection(url, user, pwd);

            /**

             Création d'une déclaration pour exécuter des requêtes SQL
             */
            st = con.createStatement();

            /**

             Déclaration de la liste "liste_apprenant" qui contiendra les prénoms des apprenants
             */
            List<String> liste_apprenant = new ArrayList<>();

            /**

             Requête SQL pour récupérer les prénoms des apprenants de la table "liste_apprenants"
             */
            rs = st.executeQuery("SELECT * FROM liste_apprenants;");


            /**

             Cette boucle while parcourt le résultat de la requête SQL exécutée par la méthode executeQuery() de l'objet Statement.
             Pour chaque enregistrement du résultat, la méthode next() retourne true et l'enregistrement courant est stocké dans l'objet ResultSet.
             La méthode getString("prenom_apprenants") de l'objet ResultSet permet de récupérer la valeur de la colonne "prenom_apprenants" de l'enregistrement courant.
             Cette valeur est ensuite ajoutée à la liste "liste_apprenant" grâce à la méthode add() de l'objet ArrayList.
             */
            while (rs.next()) {
                liste_apprenant.add(rs.getString("prenom_apprenants")); // ma liste prends la valeur de ma BDD

            }


            /**
             * taille_listeapprenant est la taille de la liste fourni par liste_apprenant.size()
             */
            int taille_listeapprenant = liste_apprenant.size();


            /**

             Cette méthode utilise la méthode shuffle de la classe Collections pour mélanger les éléments de la liste liste_apprenant.
             Cela permet de distribuer aléatoirement les apprenants dans les groupes.
             */
            Collections.shuffle(liste_apprenant);


            /**
             * taille_groupe est la taille des groupes d'apprenants souhaitée par l'utilisateur.
             */
            int taille_groupe = 0;
            int id_groupe = 1;
            /**
             * Création d'un objet Scanner pour lire les entrées utilisateur
             */
            Scanner scanner = new Scanner(System.in);

            /**

             Boucle qui demande à l'utilisateur de saisir un nombre valide pour créer des groupes d'apprenants
             */
            do {

                /**

                 Affichage d'un message pour indiquer à l'utilisateur les consignes pour saisir un nombre valide
                 */
                System.out.println("Veuillez entrer un nombre valide compris entre 2 et " + taille_listeapprenant + " pour créér des groupes d'apprenants\n" +
                        "Pour créer des groupes de 2 apprenants par défaut, appuyez sur Entrée.");


                /**

                 Tentative de récupération de la saisie de l'utilisateur
                 */
                try {

                    /**

                     Récupération de la saisie de l'utilisateur
                     */
                    String input = scanner.nextLine();

                    /**

                     Affectation de la valeur saisie à la variable taille_groupe ou 2 si l'utilisateur n'a rien saisi
                     */
                    taille_groupe = input.isEmpty() ? 2 : Integer.parseInt(input);


                    // Vérification que la valeur saisie est valide et affichage d'un message d'erreur si ce n'est pas le cas
                    if (taille_groupe < 2 || taille_groupe > taille_listeapprenant) {
                        System.out.println("Erreur de saisie");                    }
                    // Vérification que la valeur rentrée est un int et non pas String
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de saisie");
                    continue;
                }
            } while ((taille_groupe < 2) || (taille_groupe > taille_listeapprenant));
            //Ajout de ce catch pour gérer les erreurs de saisie de l'utilisateur, il affichera un message d'erreur si l'utilisateur tape autre chose qu'un nombre.
            //Et il continuera à répéter la boucle tant qu'il n'a pas saisi une valeur valide


            /**
             * Calcule le nombre de groupes et la taille restante pour chaque groupe
             * en utilisant l'opérateur modulo.
             */
            int taille_restant = taille_listeapprenant % taille_groupe;


            /**
             * Initialise un tableau à deux dimensions pour stocker les groupes d'apprenants
             * et ajoute une ligne supplémentaire si il y a un reste lors de la division.
             */
            int nombre_de_groupe = taille_listeapprenant / taille_groupe;
            String[][] groupe = new String[nombre_de_groupe + (taille_restant > 0 ? 1 : 0)][taille_groupe];
            int igroupe = 0;

            /**

             Boucle pour créer les groupes en utilisant la méthode subList de ArrayList
             pour découper la liste d'apprenants en fonction de la taille de groupe choisie.
             */
            for (
                    int i = 0;
                    i < taille_listeapprenant - taille_restant; i += taille_groupe) {
                groupe[igroupe] = liste_apprenant.subList(i, i + taille_groupe).toArray(new String[taille_groupe]);
                igroupe++;
            }

            /**

             Si il y a un reste lors de la division, crée un groupe avec la taille restante.
             */
            if (taille_restant != 0) {
                groupe[igroupe] = liste_apprenant.subList(taille_listeapprenant - taille_restant, taille_listeapprenant).toArray(new String[taille_restant]);
                igroupe++;
            }

            /**

             Boucle pour insérer les groupes dans la table "groupe" de la base de données,
             et affiche les groupes créés en utilisant la méthode deepToString de Arrays.
             */
            for (
                    int i = 0;
                    i < groupe.length; i++) {
                Date date = new Date(System.currentTimeMillis());
                String query = "INSERT INTO groupe (libelle,id_projet,dates_creation) VALUES (\"" + Arrays.toString(groupe[i]) + "\",1,CURRENT_DATE)";
                st.executeUpdate(query);
            }
            System.out.print(Arrays.deepToString(groupe)); // Afficher les binomes
        } catch (
                Exception e) {
            System.err.println("Exception : " + e.getMessage());
        } finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                System.err.println("Exception : " + e.getMessage());

            }
        }

    }
}