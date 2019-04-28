# Exercise Specifications (italian only)

Realizzare un sistema di ANALISI SEMANTICA per l'estensione di SimpleStaticAnalysis con dichiarazioni di funzioni e tipi di dato che si trova in allegato.

## ESERCIZIO 1

Implementare un sistema di analisi 

* **di variabili/funzioni non dichiarate**

* **di variabili dichiarate piu` volte nello stesso ambiente**
    * in questa analisi **non è corretto** il codice  
~~~~
int x = 4 ;
delete x ; 
int x = 5 ;
~~~~

* **di parametri attuali non conformi ai parametri formali**
    * inclusa la verifica sui parametri passati per var

* **della correttezza dei tipi** 

## ESERCIZIO 2

Controllare gli accessi a identificatori "cancellati"


1. il codice seguente deve essere considerato corretto.
~~~~
int x = 4 ;
delete x ; 
int x = 5 ;
~~~~

2. Funzioni problematiche

    * f(var int x, var int y){ delete x; delete y; }  cosa accade in {int x = 3 ; f(x,x) ;} 

    * f(var int x, int y){if (y== 0) delete x; else x=x+y ; } 
    
    * f(var int x, var int y){ int z = x ; delete x ; y = y+z ;} cosa accade in {int x = 3 ; f(x,x) ;} ?

    * g(var int x, var int y){ delete x ; delete y ;} f(var int z){ g(z,z) ; }




