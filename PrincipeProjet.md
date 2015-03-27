# Introduction #

  * Trouver une image de nuages (ciel partiellement nuageux de préférence)
  * Rechercher de manière automatique des formes "connues" parmi les nuages de la scène.
  * Afficher en surimpression sur l'image d'origine les formes identifiées

## Processus ##

  * Constituer une petite base d'images de ciels nuageux (merci Internet)
  * Traiter ces images de façon à séparer les nuages du ciel, en utilisant un algorithme de clustering (C-moyennes, ou "k-means") dont le code est disponible sur le site Numerical Recipes (http://www.nr.com/), par exemple.
  * Une fois les nuages isolés, utiliser les web-services liés à google pour rechercher des images similaires à tout ou partie d'un nuage.
  * Récupérer les images résultantes (web-service encore) et les afficher avec l'image d'origine.

## Outils utilisés ##

  * Eclipse IDE for JAVA EE Developers (https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/lunasr1a)
  * JDK version 7 (http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
  * Subversive Plugin for Eclipse (http://eclipse.org/subversive/downloads.php), choisir SVN Kit 1.8.7 (Window > Preferences > SVN > Get Connectors)

## Quelques liens utiles ##

  * Infos permettant de récupérer des images comme résultats d'une requête Google : http://googlecode.blogspot.fr/2012/02/image-results-now-available-from-custom.html
  * Bing search API : http://datamarket.azure.com/dataset/bing/search
  * API Flickr : https://www.flickr.com/services/api

## Livrables ##

  * Rapport collectif rassemblant :
    * Une présentation des sous-groupes
    * Les travaux effectués par chacun des sous-groupes
    * Les outils utilisés
    * Des exemples de fonctionnement de votre programme
    * Une partie indiquant la façon dont vous avez géré et perçu le travail d'équipe
  * Manuel d'utilisateur
  * Documentation
  * Programme en état de fonctionnement
  * Soutenance avec démonstration (Date : **vendredi 20 février à 12h30**)

## Normes de codage ##

  * Suivre ces règles : http://www.jmdoudoux.fr/java/dej/chap-normes-dev.htm
  * Installer checkstyle (suivre ce tuto : http://www.objis.com/formation-java/tutoriel-integration-continue-checkstyle-eclipse.html#partie1) puis :
    * aller dans Eclipse > Window > Preferences > checkstyle > Global Check Configurations > New...
    * Name : projet\_nuages\_checks
    * Type : choisir Project Relative Configuration et choisir le fichier xml : projetnuages/config/config\_nuages\_checks.xml
    * Après avoir ajouter cette configuration, il faut la mettre par défaut grâce à 'Set as Default'
    * Enfin, clic droit sur le projet dans eclipse > checkstyle > Activate checkstyle
  * Nom des variables/fonctions/classes en **Anglais**
  * Javadoc : documenter ses fonctions/classes en **Anglais**
  * Interface utilisateur final : en **Français**

## Commit ##

  * N'importe qui peut commiter sur le svn
  * Lorsque vous commitez, faire en sorte que le commit soit complet.
  * **1 commit par jour par personne maximum autorisé (hors séance de projet).** Faire en sorte que le commentaire du commit soit correct, compréhensif et en **Anglais**. Après avoir commiter , vous devez préparer une code review request aux membres de votre équipe (ici : https://code.google.com/p/projetnuages/issues/entry?show=review&former=sourcelist), notamment le coordinateur pour qu'il relise votre code et valide votre request.
  * Chaque équipe commit dans son package situé dans /trunk/src

## Librairies ##

  * Un dossier 'libs' est créé dans chaque package (un pour client et un pour server), il contient les .jar a ajouter à votre buildpath (Add JARs...), il contient les librairies utilisées par le projet.