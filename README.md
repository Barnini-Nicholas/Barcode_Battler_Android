# Barcode_Battler_Android - Projet Android 2017/1018

## Enoncé :

Développez une application android sur le même principe que la console vintage “Barcode Battler”. 
Il s’agit donc de concevoir un jeu qui permet :
Gérer ses créatures et équipements (ajouter / supprimer / ….)
Ajoute des créatures / équipements / potions  en scannant des codes barres
Lancer un combat en local en faisant affronter deux créatures sur le même téléphone
Lancer un combat en réseau 



Un code barre scanné donnera toujours le même résultat (je vous conseil de prendre le hash de la valeur du code-barre pour obtenir une longue chaine et splitter la chaîne pour obtenir vos valeurs).

Le réseau fonctionnera par échange de fichiers XML.

Les combats seront au tour par tour, à minima, l’attaquant devra tirer un nombre aléatoire en 0 et sa capacité d’attaque , l’adversaire devra tirer un nombre aléatoire entre 0 et sa capacité de défense. , l’ennemi sera blessé d’attaque - défense point de vie.


## Barème : 

- Application fonctionnelle      : 2 points 
- Navigation des écrans propre : 2 points
- Gestion des créatures      : 2 points 
- Scanner un code Barre      : 2 points 
- Serialisation/Deserialisation      : 2 points 
- Combat local fonctionnel      : 2 points 
- Combat réseau fonctionnel     : 2 points
- Bonus implication           : 6 points
