# Barber
Questa applicazione mobile è pensata per semplificare e automatizzare la gestione di un salone di parrucchiere. Offre un sistema completo per la gestione di prenotazioni, clienti, servizi, turni e statistiche, tutto in un’unica piattaforma user-friendly pensata per i professionisti del settore.
## Descrizione
L'applicazione mobile Salone Manager è stata sviluppata con l'obiettivo di digitalizzare e semplificare la gestione quotidiana di un salone di parrucchiere, offrendo una soluzione completa e integrata per l’organizzazione delle attività principali. Tutte le funzionalità sono state suddivise in sezioni distinte per garantire una navigazione intuitiva e un'esperienza utente chiara e ordinata.
### Autenticazione 
 La sezione di autenticazione consente l’accesso sicuro all’applicazione tramite email e password. Durante la registrazione, l’utente fornisce i dati essenziali del salone (nome, indirizzo, ecc.) che vengono poi utilizzati per personalizzare l’ambiente di lavoro. L'accesso è riservato ai professionisti autorizzati, assicurando la protezione delle informazioni sensibili.
###  Profilo
La sezione Profilo consente la gestione delle informazioni anagrafiche e operative del salone. Include:
1) Visualizzazione e modifica dei dati utente e dell’attività
2) Configurazione dei servizi offerti (nome, costo, disponibilità)
3) Gestione dei turni di lavoro (aggiunta, modifica, rimozione)
4) Accesso a statistiche
### Dashboard
La dashboard fornisce una panoramica chiara e immediata delle attività giornaliere del salone. Permette di visualizzare:
1) Tutti gli appuntamenti previsti per la giornata (cliente, orario, servizio)
2) Statistiche relative alle prenotazioni settimanali e mensili
### Agenda
L’agenda è un calendario interattivo che mostra la disponibilità giorno per giorno. Include:
1) Indicazione visiva della disponibilità tramite colori
2) Visualizzazione delle prenotazioni quotidiane
3) Prenotazione di nuovi appuntamenti selezionando cliente e servizio
4) Eliminazione di appuntamenti esistenti
5) Invio automatico di conferme e promemoria via SMS tramite Cloud Functions
###  Clienti
La sezione Clienti consente la gestione completa dei profili dei clienti:
1) Elenco alfabetico con barra di ricerca
2) Accesso ai dati anagrafici e allo storico delle prenotazioni
3) Aggiunta, modifica ed eliminazione dei clienti
## Analisi requisiti 
In questa sezione verranno identificati e descritti i requisiti funzionali e non funzionali dell'applicazione.
### Requisiti funzionali

| Codice | Nome                             | Descrizione                                                 |
| ------ | -------------------------------- | ----------------------------------------------------------- |
| RF1    | Login                            | Consente all’utente di accedere alla piattaforma.           |
| RF2    | Registrazione                    | Permette la creazione di un nuovo account.                  |
| RF3    | Visualizzazione Profilo          | Mostra i dati personali dell’utente.                        |
| RF4    | Ricerca Utente                   | Ricerca di un utente nel sistema.                           |
| RF5    | Visualizza Dashboard             | Riepilogo giornaliero di prenotazioni e attività.           |
| RF6    | Statistiche Prenotazioni         | Grafico mensile con il numero di prenotazioni.              |
| RF7    | Statistiche Servizi              | Grafico a torta con i servizi più richiesti.                |
| RF8    | Calcolo Statistiche Prenotazioni | Calcolo aggregato delle prenotazioni.                       |
| RF9    | Calcolo Statistiche Servizi      | Calcolo aggregato dei servizi prenotati.                    |
| RF10   | Visualizza Calendario            | Calendario mensile con disponibilità dei turni.             |
| RF11   | Ricerca Giorni per Mese          | Recupero giorni del mese con turni e prenotazioni.          |
| RF12   | Lista Prenotazioni Giorno        | Visualizza tutte le prenotazioni di un giorno.              |
| RF13   | Inserimento Prenotazione         | Inserisce una nuova prenotazione con conferma SMS.          |
| RF14   | Eliminazione Prenotazione        | Cancella una prenotazione e libera il turno.                |
| RF15   | Ricerca Prenotazione per Giorno  | Ricerca delle prenotazioni per un giorno specifico.         |
| RF16   | Invio SMS Conferma               | Invia un SMS automatico alla conferma della prenotazione.   |
| RF17   | Promemoria Appuntamenti          | Invia SMS automatici di promemoria alle 9 del giorno prima. |
| RF18   | Visualizza Lista Clienti         | Mostra tutti i clienti registrati.                          |
| RF19   | Recupera Clienti                 | Recupera i clienti dal database Firebase.                   |
| RF20   | Ricerca Cliente                  | Ricerca cliente per nome o cognome.                         |
| RF21   | Visualizza Cliente               | Visualizza dettagli e storico prenotazioni di un cliente.   |
| RF22   | Prenotazioni Attive Cliente      | Recupera le prenotazioni attive di un cliente.              |
| RF23   | Storico Servizi Cliente          | Storico dei servizi ricevuti da un cliente.                 |
| RF24   | Eliminazione Cliente             | Rimuove un cliente e i suoi dati associati.                 |
| RF25   | Modifica Cliente                 | Aggiorna i dati di un cliente.                              |
| RF26   | Inserimento Cliente              | Aggiunge un nuovo cliente con nome, cognome e telefono.     |
| RF27   | Visualizza Turni                 | Elenco dei turni disponibili.                               |
| RF28   | Recupera Turni                   | Recupera i turni disponibili dal database.                  |
| RF29   | Inserimento Turno                | Inserisce un nuovo turno nel sistema.                       |
| RF30   | Modifica Turno                   | Modifica orario di inizio/fine di un turno.                 |
| RF31   | Eliminazione Turno               | Elimina un turno esistente.                                 |
| RF32   | Visualizza Servizi               | Mostra l’elenco dei servizi offerti.                        |
| RF33   | Recupera Servizi                 | Recupera i servizi disponibili dal database.                |
| RF34   | Inserimento Servizio             | Aggiunge un nuovo servizio con nome e costo.                |
| RF35   | Modifica Servizio                | Modifica i dettagli di un servizio.                         |
| RF36   | Eliminazione Servizio            | Elimina un servizio dal database.                           |

### Requisiti non funzionali

| Codice | Nome                    | Descrizione                                                  |
| ------ | ----------------------- | ------------------------------------------------------------ |
| RNF1   | Interfaccia Utente (UI) | UI intuitiva, reattiva e moderna con Jetpack Compose.        |
| RNF2   | Linguaggio Kotlin       | Sviluppo dell’applicazione con Kotlin.                       |
| RNF3   | Architettura MVVM       | Architettura Model-View-ViewModel per separazione logica.    |
| RNF4   | Persistenza Dati Online | Utilizzo di Firebase per la sincronizzazione in tempo reale. |
