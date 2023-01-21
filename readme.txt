/**
 * La méthode main est le point d'entrée de l'application.
 * Elle se charge de se connecter à la base de données, de récupérer les noms des apprenants,
 * de mélanger la liste, de demander à l'utilisateur de saisir la taille des groupes d'apprenants souhaitée,
 * de créer les groupes et de les afficher et de les rentrer en base de données.
 */

/**
  *Connexion à la base de données
  *En utilisant les informations de connexion spécifiées (url, nom d'utilisateur et mot de passe)
  */

/**

   *Création d'une déclaration pour exécuter des requêtes SQL
   */

/**

   *Déclaration de la liste "liste_apprenant" qui contiendra les prénoms des apprenants
   */

/**

   *Requête SQL pour récupérer les prénoms des apprenants de la table "liste_apprenants"
   */

/**

   *Cette boucle while parcourt le résultat de la requête SQL exécutée par la méthode executeQuery() de l'objet Statement.
   *Pour chaque enregistrement du résultat, la méthode next() retourne true et l'enregistrement courant est stocké dans l'objet ResultSet.
   *La méthode getString("prenom_apprenants") de l'objet ResultSet permet de récupérer la valeur de la colonne "prenom_apprenants" de l'enregistrement courant.
   *Cette valeur est ensuite ajoutée à la liste "liste_apprenant" grâce à la méthode add() de l'objet ArrayList.
   */

/**
   * taille_listeapprenant est la taille de la liste fourni par liste_apprenant.size()
   */

/**

    *Cette méthode utilise la méthode shuffle de la classe Collections pour mélanger les éléments de la liste liste_apprenant.
    *Cela permet de distribuer aléatoirement les apprenants dans les groupes.
    */

 /**
    *taille_groupe est la taille des groupes d'apprenants souhaitée par l'utilisateur.
    */

/**
    *Création d'un objet Scanner pour lire les entrées utilisateur
    */

/**

    *Boucle qui demande à l'utilisateur de saisir un nombre valide pour créer des groupes d'apprenants
    */

/**

    *Affichage d'un message pour indiquer à l'utilisateur les consignes pour saisir un nombre valide
    */

/**

     *Tentative de récupération de la saisie de l'utilisateur
     */


/**

     *Récupération de la saisie de l'utilisateur
     */

/**

     *Affectation de la valeur saisie à la variable taille_groupe ou 2 si l'utilisateur n'a rien saisi
     */


/**
     *Calcule le nombre de groupes et la taille restante pour chaque groupe
     *en utilisant l'opérateur modulo.
     */

/**
     *Initialise un tableau à deux dimensions pour stocker les groupes d'apprenants
     *et ajoute une ligne supplémentaire si il y a un reste lors de la division.
     */


/**

      *Boucle pour créer les groupes en utilisant la méthode subList de ArrayList
      *pour découper la liste d'apprenants en fonction de la taille de groupe choisie.
      */

 /**

       *Si il y a un reste lors de la division, crée un groupe avec la taille restante.
       */


/**

       *Boucle pour insérer les groupes dans la table "groupe" de la base de données,
       *et affiche les groupes créés en utilisant la méthode deepToString de Arrays.
       */


