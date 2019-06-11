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


## Code Generation

### Memory layout 

- Global variables are assigned a fixed address once variables with fixed address are “statically allocated” [WE DO NOT HAVE GV].
- References to a variable declared in an outer scope should point to a variable stored in another activation record. Use access links...

| Code          |          
| :------------ |     
| Static Data   |  
|       . . .   |
|   Stack       |      

### How is made an activation record (frame)

| control link  |          
| :------------ |     
| access link   |  
|       . . .   |
|   vars       | 

- control link: pointer to the caller frame
- access link: pointer to the frame of the enclosing syntactical block

#### How o calculate access link?
- an inner block is entered or a function declared in the
  current scope is called:
  
  ACCESS_LINK = address of ACCESS_LINK in current AR
- a function calls itself recursively or calls another function
  declared in the enclosing syntactical block:
  
  ACCESS_LINK = value of ACCESS_LINK of the current AR
- in general, call to a function outside the current scope:
  
  ACCESS LINK = follow the chain of ACCESS_LINKs for
   the difference between current
   nesting level and that of function
   declaration   

### Simple Blocks Var Declaration 

```
{
    int x = 3;
}
```
