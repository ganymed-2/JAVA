import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
// @charset UTF-8


// Classe d'exception pour gérer les erreurs liées aux emprunts
class EmpruntException extends Exception {
    public EmpruntException(String message) {
        super(message);
    }
}

// Classe d'exception pour gérer les erreurs liées à la modification de livre
class ModificationLivreException extends Exception {
    public ModificationLivreException(String message) {
        super(message);
    }
}

// Classe d'exception pour gérer les erreurs liées à la création d'utilisateur
class CreationUtilisateurException extends Exception {
    public CreationUtilisateurException(String message) {
        super(message);
    }
}

// Classe d'exception pour gérer les erreurs liées à la modification d'utilisateur
class ModificationUtilisateurException extends Exception {
    public ModificationUtilisateurException(String message) {
        super(message);
    }
}

// Classe représentant un livre
class Livre {
    private String titre;
    private String auteur;
    private int anneePublication;
    private String ISBN;
    private int nombreEmprunts; // Nouvelle variable pour suivre le nombre d'emprunts

    // Constructeur
    public Livre(String titre, String auteur, int anneePublication, String ISBN) {
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.ISBN = ISBN;
        this.nombreEmprunts = 0; // Initialiser le nombre d'emprunts à 0
    }

    // Getters et setters
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getNombreEmprunts() {
        return nombreEmprunts;
    }

    // Méthode pour incrémenter le nombre d'emprunts
    public void incrementerNombreEmprunts() {
        this.nombreEmprunts++;
    }

    // Méthode pour afficher les détails du livre
    @Override
    public String toString() {
        return "Livre{" +
                "titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", anneePublication=" + anneePublication +
                ", ISBN='" + ISBN + '\'' +
                ", nombreEmprunts=" + nombreEmprunts +
                '}';
    }
}

// Classe représentant un utilisateur
class Utilisateur {
    private String nom;
    private int numeroIdentification;
    private boolean cotisation;
    private ArrayList<Livre> livresEmpruntes;
    private boolean aJourCotisations; // Nouvelle variable pour enregistrer l'état des cotisations
    private int nombreEmprunts; // Nouvelle variable pour suivre le nombre d'emprunts

    // Constructeur
    public Utilisateur(String nom, int numeroIdentification, boolean cotisation) {
        this.nom = nom;
        this.numeroIdentification = numeroIdentification;
        this.cotisation = cotisation;
        this.livresEmpruntes = new ArrayList<>();
        this.aJourCotisations = true; // Par défaut, l'utilisateur est considéré comme à jour
        this.nombreEmprunts = 0; // Initialiser le nombre d'emprunts à 0
    }

    // Méthode pour obtenir le numéro d'identification de l'utilisateur
    public int getNumeroIdentification() {
        return numeroIdentification;
    }

    // Méthode pour obtenir le nom de l'utilisateur
    public String getNom() {
        return nom;
    }

    // Méthode pour modifier le nom de l'utilisateur
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Méthode pour vérifier si l'utilisateur est à jour par rapport à ses cotisations
    public boolean estAJourCotisations() {
        return aJourCotisations;
    }

    // Méthode pour définir l'état des cotisations de l'utilisateur
    public void setAJourCotisations(boolean aJourCotisations) {
        this.aJourCotisations = aJourCotisations;
    }

    // Méthode pour emprunter un livre
    public void emprunterLivre(Livre livre) throws EmpruntException {
        // Vérifier si l'utilisateur est à jour par rapport à ses cotisations
        if (!aJourCotisations) {
            throw new EmpruntException("L'utilisateur n'est pas à jour par rapport à ses cotisations. Impossible d'emprunter des livres.");
        }

        // Vérifier si l'utilisateur a déjà emprunté le nombre maximal de livres
        if (livresEmpruntes.size() >= 3) {
            throw new EmpruntException("Nombre maximal de livres empruntés atteint (3)");
        }

        livresEmpruntes.add(livre);
        livre.incrementerNombreEmprunts(); // Incrémenter le nombre d'emprunts du livre
        this.nombreEmprunts++; // Incrémenter le nombre d'emprunts de l'utilisateur
    }

    // Méthode pour retourner un livre
    public void retournerLivre(Livre livre) {
        livresEmpruntes.remove(livre);
    }

    // Méthode pour obtenir le nombre d'emprunts de l'utilisateur
    public int getNombreEmprunts() {
        return nombreEmprunts;
    }

    // Méthode pour afficher les livres empruntés par l'utilisateur
    public void afficherLivresEmpruntes() {
        if (livresEmpruntes.isEmpty()) {
            System.out.println("Aucun livre emprunté.");
        } else {
            System.out.println("Livres empruntés par " + nom + " :");
            for (Livre livre : livresEmpruntes) {
                System.out.println(livre);
            }
        }
    }
}

// Classe représentant la bibliothèque
class Bibliotheque {
    private ArrayList<Livre> listeLivres;
    private HashMap<Utilisateur, ArrayList<Livre>> empruntsUtilisateurs;
    private Utilisateur utilisateurPlusActif; // Nouvelle variable pour stocker l'utilisateur ayant effectué le plus d'emprunts
    private Livre livrePlusEmprunte; // Nouvelle variable pour stocker le livre le plus emprunté

    // Constructeur
    public Bibliotheque() {
        this.listeLivres = new ArrayList<>();
        this.empruntsUtilisateurs = new HashMap<>();
        this.utilisateurPlusActif = null;
        this.livrePlusEmprunte = null;
    }

    // Méthode pour ajouter un livre à la bibliothèque
    public void ajouterLivre(Livre livre) {
        listeLivres.add(livre);
    }

    // Méthode pour supprimer un livre de la bibliothèque
    public void supprimerLivre(Livre livre) {
        listeLivres.remove(livre);
    }

    // Méthode pour rechercher un livre par titre, auteur ou ISBN
    public Livre rechercherLivre(String critere) {
        for (Livre livre : listeLivres) {
            if (livre.getTitre().equalsIgnoreCase(critere) || livre.getAuteur().equalsIgnoreCase(critere) || livre.getISBN().equalsIgnoreCase(critere)) {
                return livre;
            }
        }
        return null; // Livre non trouvé
    }

    // Méthode pour modifier un livre
    public void modifierLivre(Livre livre, String titre, String auteur, int anneePublication, String ISBN) throws ModificationLivreException {
        // Vérifier si le livre existe dans la bibliothèque
        if (!listeLivres.contains(livre)) {
            throw new ModificationLivreException("Le livre spécifié n'existe pas dans la bibliothèque.");
        }
        // Si le livre existe, mettre à jour ses informations
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setAnneePublication(anneePublication);
        livre.setISBN(ISBN);
    }

    // Méthode pour enregistrer l'emprunt d'un livre par un utilisateur
    // Méthode pour enregistrer l'emprunt d'un livre par un utilisateur
    public void enregistrerEmprunt(Utilisateur utilisateur, Livre livre) throws EmpruntException {
        // Vérifier si l'utilisateur est à jour par rapport à ses cotisations
        if (!utilisateur.estAJourCotisations()) {
            throw new EmpruntException("L'utilisateur n'est pas à jour par rapport à ses cotisations. Impossible d'emprunter des livres.");
        }

        if (!empruntsUtilisateurs.containsKey(utilisateur)) {
            empruntsUtilisateurs.put(utilisateur, new ArrayList<>());
        }
        ArrayList<Livre> emprunts = empruntsUtilisateurs.get(utilisateur);
        if (emprunts.size() >= 3) {
            throw new EmpruntException("Nombre maximal de livres empruntés atteint (3)");
        }
        emprunts.add(livre);
        livre.incrementerNombreEmprunts(); // Incrémenter le nombre d'emprunts du livre
    }

    // Méthode pour enregistrer le retour d'un livre par un utilisateur
    public void enregistrerRetour(Utilisateur utilisateur, Livre livre) {
        if (empruntsUtilisateurs.containsKey(utilisateur)) {
            ArrayList<Livre> emprunts = empruntsUtilisateurs.get(utilisateur);
            emprunts.remove(livre);
        }
    }

    // Méthode pour obtenir la liste des utilisateurs de la bibliothèque
    public ArrayList<Utilisateur> getListeUtilisateurs() {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        for (Utilisateur utilisateur : empruntsUtilisateurs.keySet()) {
            utilisateurs.add(utilisateur);
        }
        return utilisateurs;
    }

    // Méthode pour ajouter un utilisateur à la bibliothèque
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        empruntsUtilisateurs.put(utilisateur, new ArrayList<>());
    }

    // Méthode pour afficher les utilisateurs de la bibliothèque
    public void afficherUtilisateurs() {
        if (empruntsUtilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur enregistré.");
        } else {
            System.out.println("Utilisateurs enregistrés :");
            for (Utilisateur utilisateur : empruntsUtilisateurs.keySet()) {
                System.out.println(utilisateur.getNom() + " (ID : " + utilisateur.getNumeroIdentification() + ")");
            }
        }
    }

    // Méthode pour modifier les informations d'un utilisateur
    public void modifierUtilisateur(Utilisateur utilisateur, String nom, boolean aJourCotisations) throws ModificationUtilisateurException {
        if (!empruntsUtilisateurs.containsKey(utilisateur)) {
            throw new ModificationUtilisateurException("L'utilisateur spécifié n'existe pas dans la bibliothèque.");
        }
        utilisateur.setNom(nom);
        utilisateur.setAJourCotisations(aJourCotisations);
    }

    // Méthode pour supprimer un utilisateur de la bibliothèque
    public void supprimerUtilisateur(Utilisateur utilisateur) {
        if (empruntsUtilisateurs.containsKey(utilisateur)) {
            empruntsUtilisateurs.remove(utilisateur);
        }
    }

    // Méthode pour rechercher un utilisateur par numéro d'identification
    public Utilisateur rechercherUtilisateur(int numeroIdentification) {
        for (Utilisateur utilisateur : empruntsUtilisateurs.keySet()) {
            if (utilisateur.getNumeroIdentification() == numeroIdentification) {
                return utilisateur;
            }
        }
        return null; // Utilisateur non trouvé
    }

    // Méthode pour récupérer les livres empruntés par un utilisateur spécifique
    public ArrayList<Livre> getLivresEmpruntes(Utilisateur utilisateur) {
        if (empruntsUtilisateurs.containsKey(utilisateur)) {
            return empruntsUtilisateurs.get(utilisateur);
        } else {
            return new ArrayList<>(); // Retourner une liste vide si l'utilisateur n'a pas emprunté de livres
        }
    }

    // Méthode pour afficher les livres empruntés par un utilisateur spécifique
    public void afficherLivresEmpruntes(Utilisateur utilisateur) {
        ArrayList<Livre> livresEmpruntes = getLivresEmpruntes(utilisateur);
        if (livresEmpruntes.isEmpty()) {
            System.out.println("Aucun livre emprunté par " + utilisateur.getNom() + ".");
        } else {
            System.out.println("Livres empruntés par " + utilisateur.getNom() + " :");
            for (Livre livre : livresEmpruntes) {
                System.out.println(livre);
            }
        }
    }

    // Méthode pour afficher les statistiques de la bibliothèque
    public void afficherStatistiques() {
        // Afficher le nombre total de livres
        System.out.println("Statistiques de la bibliothèque :");
        System.out.println("Nombre total de livres : " + listeLivres.size());

        // Trouver le livre le plus emprunté
        int maxEmprunts = 0;
        for (Livre livre : listeLivres) {
            if (livre.getNombreEmprunts() > maxEmprunts) {
                maxEmprunts = livre.getNombreEmprunts();
                livrePlusEmprunte = livre;
            }
        }
        if (livrePlusEmprunte != null) {
            System.out.println("Livre le plus emprunté : " + livrePlusEmprunte.getTitre() + " (Nombre d'emprunts : " + maxEmprunts + ")");
        } else {
            System.out.println("Aucun livre emprunté.");
        }

        // Trouver l'utilisateur le plus actif
        int maxEmpruntsUtilisateur = 0;
        for (Utilisateur utilisateur : empruntsUtilisateurs.keySet()) {
            int nombreEmprunts = getLivresEmpruntes(utilisateur).size();
            if (nombreEmprunts > maxEmpruntsUtilisateur) {
                maxEmpruntsUtilisateur = nombreEmprunts;
                utilisateurPlusActif = utilisateur;
            }
        }
        if (utilisateurPlusActif != null) {
            System.out.println("Utilisateur le plus actif : " + utilisateurPlusActif.getNom() + " (Nombre d'emprunts : " + maxEmpruntsUtilisateur + ")");
        } else {
            System.out.println("Aucun utilisateur actif.");
        }
    }

    // Méthode pour afficher les livres empruntés par tous les utilisateurs
    public void afficherLivresEmpruntesTousUtilisateurs() {
        if (empruntsUtilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur enregistré dans la bibliothèque !");
        } else {
            for (Utilisateur utilisateur : empruntsUtilisateurs.keySet()) {
                afficherLivresEmpruntes(utilisateur);
                System.out.println(); // Ajout d'une ligne vide entre les utilisateurs pour une meilleure lisibilité
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bibliotheque bibliotheque = new Bibliotheque();

        boolean isConnected = false;
        boolean isAdmin = false;
        Utilisateur utilisateurConnecte = null;

        while (true) {
            if (!isConnected) {
                System.out.println("\t\t\t\t\t╔════════════════════════════════════════════════════════╗");
                System.out.println("\t\t\t\t\t║                     Menu de Connexion                  ║");
                System.out.println("\t\t\t\t\t╠════════════════════════════════════════════════════════╣");
                System.out.println("\t\t\t\t\t║ 1. Connexion Admin                                     ║");
                System.out.println("\t\t\t\t\t║ 2. Connexion Utilisateur                               ║");
                System.out.println("\t\t\t\t\t║ 3. Quitter                                             ║");
                System.out.println("\t\t\t\t\t╚════════════════════════════════════════════════════════╝");

                System.out.print("\nVotre choix : ");
                int choixConnexion = scanner.nextInt();
                scanner.nextLine(); // Pour vider le buffer après la saisie d'un entier

                switch (choixConnexion) {
                    case 1:
                        // Simuler la connexion admin (utilisateur fictif)
                        isAdmin = true;
                        isConnected = true;
                        utilisateurConnecte = new Utilisateur("Admin", 0, true); // Utilisateur fictif admin
                        System.out.println("Connexion Admin réussie !");
                        break;
                    case 2:
                        // Demander le nom et le numéro d'identification de l'utilisateur
                        System.out.print("Nom : ");
                        String nom = scanner.nextLine();
                        System.out.print("Numéro d'identification : ");
                        int idUtilisateur = scanner.nextInt();
                        scanner.nextLine(); // Pour vider le buffer après la saisie d'un entier

                        // Vérifier si l'utilisateur existe dans la bibliothèque
                        Utilisateur utilisateur = bibliotheque.rechercherUtilisateur(idUtilisateur);
                        if (utilisateur != null && utilisateur.getNom().equals(nom)) {
                            isConnected = true;
                            utilisateurConnecte = utilisateur;
                            System.out.println("Connexion Utilisateur réussie !");
                        } else {
                            System.out.println("Nom ou numéro d'identification incorrect !");
                        }
                        break;
                    case 3:
                        System.out.println("Merci d'avoir utilisé le système de gestion de bibliothèque. Au revoir !");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Choix invalide !");
                }
            } else {
                if (isAdmin) {
                    System.out.println("\t\t\t\t\t╔════════════════════════════════════════════════════════╗");
                    System.out.println("\t\t\t\t\t║                    Menu Administrateur                 ║");
                    System.out.println("\t\t\t\t\t╠════════════════════════════════════════════════════════╣");
                    System.out.println("\t\t\t\t\t║ 1. Ajouter un livre                                    ║");
                    System.out.println("\t\t\t\t\t║ 2. Supprimer un livre                                  ║");
                    System.out.println("\t\t\t\t\t║ 3. Rechercher un livre                                 ║");
                    System.out.println("\t\t\t\t\t║ 4. Modifier un livre                                   ║");
                    System.out.println("\t\t\t\t\t║ 5. Ajouter un utilisateur                              ║");
                    System.out.println("\t\t\t\t\t║ 6. Modifier un utilisateur                             ║");
                    System.out.println("\t\t\t\t\t║ 7. Supprimer un utilisateur                            ║");
                    System.out.println("\t\t\t\t\t║ 8. Afficher les utilisateurs enregistrés               ║");
                    System.out.println("\t\t\t\t\t║ 9. Afficher les livres empruntés par un utilisateur    ║");
                    System.out.println("\t\t\t\t\t║ 10. Afficher les statistiques de la bibliothèque       ║");
                    System.out.println("\t\t\t\t\t║ 11.Déconnexion                                         ║");
                    System.out.println("\t\t\t\t\t╚════════════════════════════════════════════════════════╝");

                    System.out.print("\nVotre choix : ");
                    int choixAdmin = scanner.nextInt();
                    scanner.nextLine(); // Pour vider le buffer après la saisie d'un entier

                    switch (choixAdmin) {
                        // Ajouter des cas pour chaque fonctionnalité administrateur
                        case 1:
                            // Ajouter un livre
                            System.out.print("Titre du livre : ");
                            String titre = scanner.nextLine();
                            System.out.print("Auteur du livre : ");
                            String auteur = scanner.nextLine();
                            System.out.print("Année de publication : ");
                            int anneePublication = scanner.nextInt();
                            scanner.nextLine(); // Pour vider le buffer après la saisie d'un entier
                            System.out.print("ISBN : ");
                            String ISBN = scanner.nextLine();
                            Livre nouveauLivre = new Livre(titre, auteur, anneePublication, ISBN);
                            bibliotheque.ajouterLivre(nouveauLivre);
                            System.out.println("Livre ajouté avec succès !");
                            break;
                        case 2:
                            // Supprimer un livre
                            System.out.print("ISBN du livre à supprimer : ");
                            String ISBNsupprimer = scanner.nextLine();
                            Livre livreSupprimer = bibliotheque.rechercherLivre(ISBNsupprimer);
                            if (livreSupprimer != null) {
                                bibliotheque.supprimerLivre(livreSupprimer);
                                System.out.println("Livre supprimé avec succès !");
                            } else {
                                System.out.println("Livre non trouvé !");
                            }
                            break;
                        case 3:
                            // Rechercher un livre
                            System.out.print("Rechercher un livre par titre, auteur ou ISBN : ");
                            String critereRecherche = scanner.nextLine();
                            Livre livreRecherche = bibliotheque.rechercherLivre(critereRecherche);
                            if (livreRecherche != null) {
                                System.out.println("Livre trouvé :");
                                System.out.println(livreRecherche);
                            } else {
                                System.out.println("Livre non trouvé !");
                            }
                            break;
                        case 4:
                            // Modifier un livre
                            System.out.print("ISBN du livre à modifier : ");
                            String ISBNmodifier = scanner.nextLine();
                            Livre livreModifier = bibliotheque.rechercherLivre(ISBNmodifier);
                            if (livreModifier != null) {
                                System.out.print("Nouveau titre : ");
                                String nouveauTitre = scanner.nextLine();
                                System.out.print("Nouvel auteur : ");
                                String nouvelAuteur = scanner.nextLine();
                                System.out.print("Nouvelle année de publication : ");
                                int nouvelleAnneePublication = scanner.nextInt();
                                scanner.nextLine(); // Pour vider le buffer après la saisie d'un entier
                                System.out.print("Nouvel ISBN : ");
                                String nouvelISBN = scanner.nextLine();
                                try {
                                    bibliotheque.modifierLivre(livreModifier, nouveauTitre, nouvelAuteur, nouvelleAnneePublication, nouvelISBN);
                                    System.out.println("Livre modifié avec succès !");
                                } catch (ModificationLivreException e) {
                                    System.out.println("Erreur : " + e.getMessage());
                                }
                            } else {
                                System.out.println("Livre non trouvé !");
                            }
                            break;
                        case 5:
                            // Ajouter un utilisateur
                            System.out.print("Nom de l'utilisateur : ");
                            String nomUtilisateur = scanner.nextLine();
                            System.out.print("Numéro d'identification de l'utilisateur : ");
                            int numeroIdentification = scanner.nextInt();
                            scanner.nextLine(); // Pour vider le buffer après la saisie d'un entier
                            System.out.print("L'utilisateur est-il à jour par rapport à ses cotisations ? (true/false) : ");
                            boolean cotisation = scanner.nextBoolean();
                            Utilisateur nouvelUtilisateur = new Utilisateur(nomUtilisateur, numeroIdentification, cotisation);
                            bibliotheque.ajouterUtilisateur(nouvelUtilisateur);
                            System.out.println("Utilisateur ajouté avec succès !");
                            break;
                        case 6:
                            // Modifier un utilisateur
                            System.out.print("Numéro d'identification de l'utilisateur à modifier : ");
                            int idUtilisateurModifier = scanner.nextInt();
                            Utilisateur utilisateurModifier = null;
                            for (Utilisateur utilisateur : bibliotheque.getListeUtilisateurs()) {
                                if (utilisateur.getNumeroIdentification() == idUtilisateurModifier) {
                                    utilisateurModifier = utilisateur;
                                    break;
                                }
                            }
                            if (utilisateurModifier != null) {
                                try {
                                    System.out.print("Nouveau nom : ");
                                    scanner.nextLine(); // Pour vider le buffer après la saisie d'un entier
                                    String nouveauNom = scanner.nextLine();
                                    System.out.print("L'utilisateur est-il à jour par rapport à ses cotisations ? (true/false) : ");
                                    boolean aJourCotisations = scanner.nextBoolean();
                                    bibliotheque.modifierUtilisateur(utilisateurModifier, nouveauNom, aJourCotisations);
                                    System.out.println("Utilisateur modifié avec succès !");
                                } catch (ModificationUtilisateurException e) {
                                    System.out.println("Erreur : " + e.getMessage());
                                }
                            } else {
                                System.out.println("Utilisateur non trouvé !");
                            }
                            break;
                        case 7:
                            // Supprimer un utilisateur
                            System.out.print("Numéro d'identification de l'utilisateur à supprimer : ");
                            int idUtilisateurSupprimer = scanner.nextInt();
                            Utilisateur utilisateurSupprimer = null;
                            for (Utilisateur utilisateur : bibliotheque.getListeUtilisateurs()) {
                                if (utilisateur.getNumeroIdentification() == idUtilisateurSupprimer) {
                                    utilisateurSupprimer = utilisateur;
                                    break;
                                }
                            }
                            if (utilisateurSupprimer != null) {
                                bibliotheque.supprimerUtilisateur(utilisateurSupprimer);
                                System.out.println("Utilisateur supprimé avec succès !");
                            } else {
                                System.out.println("Utilisateur non trouvé !");
                            }
                            break;
                        case 8:
                            // Afficher les utilisateurs enregistrés
                            bibliotheque.afficherUtilisateurs();
                            break;
                        case 9:
                            // Afficher les livres empruntés par tous les utilisateurs
                            bibliotheque.afficherLivresEmpruntesTousUtilisateurs();
                            break;


                        case 10:
                            // Afficher les statistiques de la bibliothèque
                            bibliotheque.afficherStatistiques();
                            break;
                        case 11:
                            // Déconnexion
                            isConnected = false;
                            isAdmin = false;
                            utilisateurConnecte = null;
                            System.out.println("Déconnexion réussie !");
                            break;
                        default:
                            System.out.println("Choix invalide !");
                    }
                } else {
                    // Interface Utilisateur

                    System.out.println("\t\t\t\t\t╔════════════════════════════════════════════════════════╗");
                    System.out.println("\t\t\t\t\t║                     Menu Utilisateur                   ║");
                    System.out.println("\t\t\t\t\t╠════════════════════════════════════════════════════════╣");
                    System.out.println("\t\t\t\t\t║ 1. Emprunter un livre                                  ║");
                    System.out.println("\t\t\t\t\t║ 2. Retourner un livre                                  ║");
                    System.out.println("\t\t\t\t\t║ 3. Déconnexion                                         ║");
                    System.out.println("\t\t\t\t\t╚════════════════════════════════════════════════════════╝");

                    System.out.print("\nVotre choix : ");
                    int choixUtilisateur = scanner.nextInt();
                    scanner.nextLine(); // Pour vider le buffer après la saisie d'un entier

                    switch (choixUtilisateur) {
                        // Ajouter des cas pour chaque fonctionnalité utilisateur
                        case 1:
                            // Emprunter un livre
                            System.out.print("ISBN du livre à emprunter : ");
                            String ISBNemprunter = scanner.nextLine();
                            Livre livreEmprunter = bibliotheque.rechercherLivre(ISBNemprunter);
                            if (livreEmprunter != null) {
                                System.out.print("Numéro d'identification de l'utilisateur : ");
                                int idUtilisateur = scanner.nextInt();
                                Utilisateur utilisateurEmprunt = null;
                                for (Utilisateur utilisateur : bibliotheque.getListeUtilisateurs()) {
                                    if (utilisateur.getNumeroIdentification() == idUtilisateur) {
                                        utilisateurEmprunt = utilisateur;
                                        break;
                                    }
                                }
                                if (utilisateurEmprunt != null) {
                                    try {
                                        bibliotheque.enregistrerEmprunt(utilisateurEmprunt, livreEmprunter);
                                        System.out.println("Livre emprunté avec succès !");
                                    } catch (EmpruntException e) {
                                        System.out.println("Erreur : " + e.getMessage());
                                    }
                                } else {
                                    System.out.println("Utilisateur non trouvé !");
                                }
                            } else {
                                System.out.println("Livre non trouvé !");
                            }
                            break;
                        case 2:
                            // Retourner un livre
                            System.out.print("ISBN du livre à retourner : ");
                            String ISBNretourner = scanner.nextLine();
                            Livre livreRetourner = bibliotheque.rechercherLivre(ISBNretourner);
                            if (livreRetourner != null) {
                                System.out.print("Numéro d'identification de l'utilisateur : ");
                                int idUtilisateur = scanner.nextInt();
                                Utilisateur utilisateurRetour = null;
                                for (Utilisateur utilisateur : bibliotheque.getListeUtilisateurs()) {
                                    if (utilisateur.getNumeroIdentification() == idUtilisateur) {
                                        utilisateurRetour = utilisateur;
                                        break;
                                    }
                                }
                                if (utilisateurRetour != null) {
                                    bibliotheque.enregistrerRetour(utilisateurRetour, livreRetourner);
                                    System.out.println("Livre retourné avec succès !");
                                } else {
                                    System.out.println("Utilisateur non trouvé !");
                                }
                            } else {
                                System.out.println("Livre non trouvé !");
                            }
                            break;
                        case 3:
                            // Déconnexion
                            isConnected = false;
                            isAdmin = false;
                            utilisateurConnecte = null;
                            System.out.println("Déconnexion réussie !");
                            break;
                        default:
                            System.out.println("Choix invalide !");
                    }
                }
            }
        }
    }
}
