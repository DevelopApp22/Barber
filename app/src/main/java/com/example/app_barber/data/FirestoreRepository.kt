package com.example.app_barber.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.app_barber.data.Model.Cliente.Cliente
import com.example.app_barber.data.Model.Cliente.PrenotazioniStorico
import com.example.app_barber.data.Model.Cliente.StoricoServiziCliente
import com.example.app_barber.data.Model.Day
import com.example.app_barber.data.Model.Prenotazione.Prenotazione
import com.example.app_barber.data.Model.Prenotazione.Prenotazione_completa
import com.example.app_barber.data.Model.Servizio
import com.example.app_barber.data.Model.Turno
import com.example.app_barber.data.Model.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()
    private var isUpdating = false



    fun getPrenotazioneUtente(
        uid: String,
        cliente: Cliente,
        onSuccess: (List<Prenotazione>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("users").document(uid)
            .collection("Clienti").document(cliente.id)
            .get().addOnSuccessListener { document ->

                val prenotazioni = document.toObject(Cliente::class.java)?.prenotazioni ?: emptyList()

                if (prenotazioni.isEmpty()) {
                    onSuccess(emptyList())
                }

                val prenotazioniConOrario = mutableListOf<Prenotazione>()
                var prenotazioniDaCaricare = prenotazioni.size

                prenotazioni.forEach { prenotazione ->
                    db.collection("users").document(uid)
                        .collection("Turni").document(prenotazione.turno)
                        .get().addOnSuccessListener { document ->
                            val turnoSelezionato = document.toObject(Turno::class.java)
                            turnoSelezionato?.let {
                                val pren = prenotazione.copy(turno = it.start)
                                prenotazioniConOrario.add(pren)
                                println("prenotazione con orario: $pren")
                            }
                            prenotazioniDaCaricare--

                            if (prenotazioniDaCaricare == 0) {
                                onSuccess(prenotazioniConOrario)
                            }
                        }
                }



            }
    }


    fun getStoricoServizi(
        uid: String,
        cliente: Cliente,
        onSuccess: (List<StoricoServiziCliente>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("users").document(uid)
            .collection("Clienti").document(cliente.id)
            .collection("storico").document("servizi")
            .get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val storico_servizi = document.data ?: emptyMap<String, Int>()
                    val storico_servizi_nome = mutableListOf<StoricoServiziCliente>()
                    var serviziDaCaricare = storico_servizi.size

                    storico_servizi.forEach { (id, count) ->
                        db.collection("users").document(uid)
                            .collection("Servizi").document(id).get()
                            .addOnSuccessListener { document ->
                                val servizio = document.toObject(Servizio::class.java)
                                servizio?.let {
                                    storico_servizi_nome.add(
                                        StoricoServiziCliente(
                                            id,
                                            it.nome,
                                            count
                                        )
                                    )
                                }
                                serviziDaCaricare--

                                // Chiama onSuccess solo quando tutti i servizi sono stati aggiunti
                                if (serviziDaCaricare == 0) {
                                    onSuccess(storico_servizi_nome)
                                }
                            }
                            .addOnFailureListener { exception ->
                                onFailure(exception)
                            }
                    }
                } else {
                    onSuccess(emptyList())
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


    fun getStoricoCliente(
        uid: String,
        cliente: Cliente,
        onSuccess: (PrenotazioniStorico) -> Unit,
        onFailure: (Exception) -> Unit
    ) {


        db.collection("users").document(uid)
            .collection("Clienti").document(cliente.id)
            .collection("storico").document("prenotazioni")
            .get()
            .addOnSuccessListener { document ->
                val storico_prenotazioni = document.toObject(PrenotazioniStorico::class.java)
                if (storico_prenotazioni != null) {
                    onSuccess(storico_prenotazioni)
                }


            }
    }

    // Funzione per creare un documento utente su Firestore
    fun createUserDocument(
        userId: String,
        user: User,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .set(user, SetOptions.merge()) //  merge() per non sovrascrivere documenti esistenti
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // Funzione per controllare se un documento utente esiste
    fun doesUserDocumentExist(userId: String, onResult: (Boolean) -> Unit) {
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { documents ->
                onResult(documents.exists())
            }
            .addOnFailureListener { onResult(false) }
    }

    // Funzione per recuperare un documento utente da Firestore
    fun getUserDocument(
        userId: String,
        onSuccess: (User?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { documents ->
                val user = documents.toObject(User::class.java)
                onSuccess(user)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun getTurni(uid: String, onSuccess: (List<Turno>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(uid)
            .collection("Turni")
            .get()
            .addOnSuccessListener { documents ->
                val shifts = documents.map { it.toObject(Turno::class.java) }
                onSuccess(shifts)

            }
            .addOnFailureListener { onFailure(it) }
    }

    fun create_client(
        uid: String,
        nome: String,
        cognome: String,
        telefono: String,
        onSuccess: () -> Unit
    ) {
        val id_cliente = UUID.randomUUID().toString()
        val cliente = Cliente(id = id_cliente, nome = nome, cognome = cognome, telefono = telefono)

        db.collection("users").document(uid).collection("Clienti").document(id_cliente).set(cliente)
            .addOnSuccessListener {
                onSuccess()  // Esegui effettivamente il callback qui
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "PrenotazioniRepository",
                    "Errore durante la creazione del cliente",
                    exception
                )
            }
    }

    fun delete_client(uid: String, id_cliente: String, onSuccess: () -> Unit) {
        db.collection("users").document(uid).collection("Clienti").document(id_cliente)
            .delete()
            .addOnSuccessListener { onSuccess() }
    }

    fun modify_client(
        uid: String,
        id_cliente: String,
        nome: String,
        cognome: String,
        telefono: String,
        onSuccess: () -> Unit
    ) {

        val updates = mapOf(
            "cognome" to cognome,
            "nome" to nome,
            "telefono" to telefono
        )

        db.collection("users").document(uid).collection("Clienti").document(id_cliente)
            .update(updates)
            .addOnSuccessListener { onSuccess() }
    }

//---------------------------Prenotazione-----------------------------------------------------------------------------------//

    //getDayCalendar funzione in grado di recuuperare nella collectione Days(dove ci sono tutti i documenti delle prenotazioni di una data ) il documento che ha come id la data che gli passiamo
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayCalendar(date: LocalDate, uid: String, onSuccess: (Day) -> Unit) {
        db.collection("users")
            .document(uid)
            .collection("Day")
            .document(date.toString())
            .get()
            .addOnSuccessListener { documents ->
                if (documents.exists()) {
                    val day = documents.toObject(Day::class.java)!!
                    onSuccess(day)

                } else {
                    createDayInCalendar(date, uid)
                    getDayCalendar(date, uid, onSuccess)
                }
            }
            .addOnFailureListener { e ->

            }
    }


    // funzione in grado di creare nella collectione Days(dove ci sono tutti i documenti delle prenotazioni di una data ) il documento che ha come id la data che gli passiamo ha 2 campi vedere model
    @RequiresApi(Build.VERSION_CODES.O)
    fun createDayInCalendar(date: LocalDate, uid: String) {

        val dayData = Day(data = date.toString(), isFull = false)
        // Creare il documento con la data come ID
        db.collection("users")
            .document(uid)
            .collection("Day")
            .document(dayData.data)
            .set(dayData)
            .addOnSuccessListener {
                Log.d("Firebase", "Giorno ${dayData.data} creato con successo.")
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Errore nella creazione del giorno", e)
            }

    }


    //recupera una prenotazione completa con cliente turno e servizio
    @RequiresApi(Build.VERSION_CODES.O)
    fun getPrenotazioniConDettagli(
        uid: String,
        date: LocalDate,
        onComplete: (List<Prenotazione_completa>) -> Unit
    ) {
        db.collection("users")
            .document(uid)
            .collection("Day")
            .document(date.toString())
            .collection("Prenotazioni")
            .get()
            .addOnSuccessListener { result ->
                val prenotazioni = mutableListOf<Prenotazione_completa>()

                // Se non ci sono risultati, restituisci subito una lista vuota
                if (result.isEmpty) {
                    onComplete(emptyList())
                    return@addOnSuccessListener
                }

                // Step 2: Per ogni prenotazione, recupera i dettagli del cliente e del turno
                var retrievedItems = 0 // Per tracciare quanti elementi sono stati processati
                result.forEach { document ->
                    val prenotazione = document.toObject(Prenotazione::class.java)
                    if (prenotazione == null) {
                        Log.e(
                            "FirestoreRepository",
                            "Prenotazione null per documento: ${document.id}"
                        )
                        retrievedItems++
                        if (retrievedItems == result.size()) {
                            onComplete(prenotazioni)
                        }
                        return@forEach
                    }

                    // Step 3: Recupera i dati del cliente e del turno
                    val clienteRef =
                        db.collection("users")
                            .document(uid)
                            .collection("Clienti")
                            .document(prenotazione.clienteId)

                    val turnoRef =
                        db.collection("users")
                            .document(uid)
                            .collection("Turni")
                            .document(prenotazione.turno)

                    // Recupera i dettagli del cliente e del turno
                    clienteRef.get().addOnSuccessListener { clienteSnapshot ->
                        val cliente = clienteSnapshot.toObject(Cliente::class.java)
                        if (cliente == null) {
                            Log.e(
                                "FirestoreRepository",
                                "Cliente non trovato per clienteId: ${prenotazione.clienteId}"
                            )
                            retrievedItems++
                            if (retrievedItems == result.size()) {
                                onComplete(prenotazioni)
                            }
                            return@addOnSuccessListener
                        }

                        turnoRef.get().addOnSuccessListener { turnoSnapshot ->
                            val turno = turnoSnapshot.toObject(Turno::class.java)
                            if (turno == null) {
                                Log.e(
                                    "FirestoreRepository",
                                    "Turno non trovato per turnoId: ${prenotazione.turno}"
                                )
                                retrievedItems++
                                if (retrievedItems == result.size()) {
                                    onComplete(prenotazioni)
                                }
                                return@addOnSuccessListener
                            }

                            // Combina i dati in una singola classe
                            val prenotazioneConDettagli = Prenotazione_completa(
                                prenotazione = prenotazione,
                                cliente = cliente,
                                turno = turno
                            )

                            prenotazioni.add(prenotazioneConDettagli)
                            retrievedItems++

                            // Chiama il callback una volta che tutti i dati sono stati caricati
                            if (retrievedItems == result.size()) {
                                onComplete(prenotazioni)
                            }
                        }.addOnFailureListener { e ->
                            Log.e(
                                "FirestoreRepository",
                                "Errore nel recupero del turno: ${prenotazione.turno}",
                                e
                            )
                            retrievedItems++
                            if (retrievedItems == result.size()) {
                                onComplete(prenotazioni)
                            }
                        }
                    }.addOnFailureListener { e ->
                        Log.e(
                            "FirestoreRepository",
                            "Errore nel recupero del cliente: ${prenotazione.clienteId}",
                            e
                        )
                        retrievedItems++
                        if (retrievedItems == result.size()) {
                            onComplete(prenotazioni)
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                // Gestisci l'errore e restituisci una lista vuota
                Log.w("Firebase", "Error fetching prenotazioni", e)
                onComplete(emptyList())
            }
    }


    //funzione in grado di creare una nuova prenotazione
    fun createPrenotazione(
        uid: String,
        prenotazione: Prenotazione,
        onSuccess: () -> Unit,
        onUpdate: () -> Unit
    ) {
        // Riferimento al documento del giorno specifico
        val dayDocumentRef = db.collection("users")
            .document(uid)
            .collection("Day")
            .document(prenotazione.data)

        // Riferimento al documento del cliente specifico
        val clienteDocumentRef = db.collection("users")
            .document(uid)
            .collection("Clienti")
            .document(prenotazione.clienteId)

        // Aggiungi la prenotazione nella sottocollezione "Prenotazioni" all'interno di "Day"
        dayDocumentRef.collection("Prenotazioni").document(prenotazione.id)
            .set(prenotazione)
            .addOnSuccessListener {
                // Aggiungi l'ID della prenotazione alla lista di prenotazioni del cliente
                clienteDocumentRef.update("prenotazioni", FieldValue.arrayUnion(prenotazione))
                    .addOnSuccessListener {
                        print("Prenotazione aggiunta alla lista del cliente")

                        // Quando la prenotazione viene creata con successo, chiamo il callback onSuccess()
                        onSuccess()

                        // Aggiorna lo stato del giorno, ad esempio impostando isFull
                        updateDayStatus(uid, prenotazione.data) {
                            onUpdate()
                            // Potresti voler fare qualcosa dopo l'aggiornamento del giorno
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e(
                            "PrenotazioniRepository",
                            "Errore durante l'aggiornamento della lista prenotazioni nel cliente: ${exception.message}",
                            exception
                        )
                    }
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "PrenotazioniRepository",
                    "Errore durante la creazione della prenotazione: ${exception.message}",
                    exception
                )
            }
    }


    fun deletePrenotazione(
        uid: String,
        prenotazione: Prenotazione_completa,
        onSuccess: () -> Unit,
        onUpdate: () -> Unit
    ) {
        // Riferimento al documento della prenotazione nella collezione "Day"
        db.collection("users")
            .document(uid)
            .collection("Day")
            .document(prenotazione.prenotazione.data)
            .collection("Prenotazioni")
            .document(prenotazione.prenotazione.id)
            .delete()
            .addOnSuccessListener {
                Log.d("PrenotazioniRepository", "Prenotazione eliminata da Day")

                val clienteDocumentRef = db.collection("users")
                    .document(uid)
                    .collection("Clienti")
                    .document(prenotazione.prenotazione.clienteId)

                // Recupera l'array `prenotazioni` dal documento del cliente
                clienteDocumentRef.get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        val prenotazioniArray =
                            document.get("prenotazioni") as? MutableList<Map<String, Any>>
                        if (prenotazioniArray != null) {
                            // Filtra l'array per rimuovere la prenotazione con l'ID specifico
                            val updatedPrenotazioniArray =
                                prenotazioniArray.filter { prenotazioneMap ->
                                    prenotazioneMap["id"] != prenotazione.prenotazione.id
                                }

                            // Aggiorna l'array `prenotazioni` nel documento cliente
                            clienteDocumentRef.update("prenotazioni", updatedPrenotazioniArray)
                                .addOnSuccessListener {
                                    Log.d(
                                        "PrenotazioniRepository",
                                        "Prenotazione rimossa dall'array del cliente"
                                    )
                                    onSuccess()
                                    updateDayStatus(uid, prenotazione.prenotazione.data) {
                                        onUpdate()
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.e(
                                        "PrenotazioniRepository",
                                        "Errore durante l'aggiornamento dell'array prenotazioni: ${exception.message}",
                                        exception
                                    )
                                }
                        } else {
                            Log.e(
                                "PrenotazioniRepository",
                                "Array `prenotazioni` non trovato nel documento cliente"
                            )
                        }
                    }
                }.addOnFailureListener { exception ->
                    Log.e(
                        "PrenotazioniRepository",
                        "Errore durante il recupero del documento cliente: ${exception.message}",
                        exception
                    )
                }
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "PrenotazioniRepository",
                    "Errore durante l'eliminazione della prenotazione: ${exception.message}",
                    exception
                )
            }
    }


    fun getClienti(
        uid: String,
        onSuccess: (List<Cliente>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("users").document(uid).collection("Clienti")
            .get()
            .addOnSuccessListener { result ->
                val clientiList = mutableListOf<Cliente>()
                for (document in result) {
                    val cliente = document.toObject(Cliente::class.java)
                    clientiList.add(cliente)
                }
                onSuccess(clientiList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun getServizi(
        uid: String,
        onSuccess: (List<Servizio>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        db.collection("users").document(uid).collection("Servizi")
            .get()
            .addOnSuccessListener { result ->
                val serviziList = mutableListOf<Servizio>()
                for (document in result) {
                    val cliente = document.toObject(Servizio::class.java)
                    serviziList.add(cliente)
                }
                onSuccess(serviziList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun create_servizio(
        uid: String,
        nome: String,
        prezzo: String,
        onSuccess: () -> Unit,
    ) {
        val id_servizio = UUID.randomUUID().toString()
        val servizio = Servizio(nome = nome, id = id_servizio, prezzo = prezzo)

        db.collection("users").document(uid).collection("Servizi").document(id_servizio).set(servizio)
            .addOnSuccessListener {
                onSuccess()
            }
    }
    fun modify_servizio(uid: String, id_servizio: String, nome: String, prezzo: String, onSuccess: () -> Unit) {
        db.collection("users").document(uid).collection("Servizi").document(id_servizio)
            .update("nome", nome, "prezzo", prezzo)
            .addOnSuccessListener { onSuccess() }
    }

    fun delete_servizio(uid: String, id_servizio: String, onSuccess: () -> Unit) {
        db.collection("users").document(uid).collection("Servizi").document(id_servizio)
            .delete()
            .addOnSuccessListener { onSuccess() }
    }
    fun updateDayStatus(uid: String, date: String, onComplete: () -> Unit) {
        // Recupera i turni disponibili
        getTurni(uid, onSuccess = { turni ->
            val dayDocumentRef = db.collection("users")
                .document(uid)
                .collection("Day")
                .document(date)

            // Recupera tutte le prenotazioni per quel giorno
            dayDocumentRef.collection("Prenotazioni")
                .get()
                .addOnSuccessListener { prenotazioni ->
                    val turniPrenotati =
                        prenotazioni.map { it.toObject(Prenotazione::class.java).turno }

                    // Verifica se tutti i turni sono prenotati
                    val allTurniBooked = turni.all { turno -> turno.id in turniPrenotati }

                    // Se tutti i turni sono prenotati, imposta `isFull` a true, altrimenti a false
                    dayDocumentRef.update("full", allTurniBooked)
                        .addOnSuccessListener {
                            onComplete()
                        }
                }
                .addOnFailureListener {
                    Log.e("Firebase", "Errore durante il recupero delle prenotazioni", it)
                }
        }, onFailure = { e ->
            Log.e("Firebase", "Errore durante il recupero dei turni", e)
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun controllaAggiornamentoStorico(uid: String) {
        if (isUpdating) {
            Log.d(
                "AggiornamentoStorico",
                "Aggiornamento già in esecuzione, nessuna azione eseguita."
            )
            return
        }

        isUpdating = true
        val userRef = db.collection("users").document(uid)

        Log.d("AggiornamentoStorico", "Inizio controllo aggiornamento storico per utente: $uid")

        userRef.get().addOnSuccessListener { userSnapshot ->
            val storicoAggiornato = userSnapshot.getBoolean("storicoAggiornato") ?: false
            val lastUpdated = userSnapshot.getString("lastUpdated") ?: ""
            val today = LocalDate.now().toString()

            Log.d("AggiornamentoStorico", "Valore di 'storicoAggiornato': $storicoAggiornato")
            Log.d("AggiornamentoStorico", "Valore di 'lastUpdated': $lastUpdated")
            Log.d("AggiornamentoStorico", "Data corrente: $today")

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val todayDate = LocalDate.parse(today, formatter)

            if (lastUpdated.isEmpty() || todayDate.isAfter(
                    LocalDate.parse(
                        lastUpdated,
                        formatter
                    )
                ) || !storicoAggiornato
            ) {
                Log.d(
                    "AggiornamentoStorico",
                    "Aggiornamento richiesto. Eseguo aggiornamento storico."
                )

                userRef.update(mapOf("lastUpdated" to today, "storicoAggiornato" to false))
                    .addOnSuccessListener {
                        Log.d(
                            "AggiornamentoStorico",
                            "Campi 'lastUpdated' e 'storicoAggiornato' aggiornati."
                        )

                        aggiornaStoricoPrenotazioni(todayDate, uid) {
                            userRef.update("storicoAggiornato", true).addOnSuccessListener {
                                Log.d(
                                    "AggiornamentoStorico",
                                    "'storicoAggiornato' impostato su true."
                                )
                                isUpdating = false
                            }.addOnFailureListener { e ->
                                Log.e(
                                    "AggiornamentoStorico",
                                    "Errore nell'aggiornare 'storicoAggiornato': ${e.message}"
                                )
                                isUpdating = false
                            }
                        }
                    }.addOnFailureListener { e ->
                    Log.e(
                        "AggiornamentoStorico",
                        "Errore nell'aggiornare 'lastUpdated': ${e.message}"
                    )
                    isUpdating = false
                }
            } else {
                Log.d(
                    "AggiornamentoStorico",
                    "Nessun aggiornamento richiesto: dati attuali e già aggiornati."
                )
                isUpdating = false
            }
        }.addOnFailureListener { e ->
            Log.e("AggiornamentoStorico", "Errore nel recuperare i dati utente: ${e.message}")
            isUpdating = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun aggiornaStoricoPrenotazioni(date: LocalDate, uid: String, onComplete: () -> Unit) {
        val giorniRef = db.collection("users").document(uid).collection("Day")
        val statisticheRef = db.collection("users").document(uid).collection("statistiche")

        Log.d(
            "aggiornaStoricoPrenotazioni",
            "Inizio aggiornamento storico per utente $uid fino alla data $date"
        )

        giorniRef.get().addOnSuccessListener { giorniSnapshot ->
            val giorniDocuments = giorniSnapshot.documents.filter { giornoDoc ->
                val dataGiorno = giornoDoc.getString("data") ?: return@filter false
                LocalDate.parse(dataGiorno).isBefore(date)
            }

            aggiornaGiorno(0, giorniDocuments, uid, statisticheRef) {
                Log.d("aggiornaStoricoPrenotazioni", "Aggiornamento storico completato.")
                onComplete()
            }
        }.addOnFailureListener { e ->
            Log.e(
                "aggiornaStoricoPrenotazioni",
                "Errore nel recupero dei documenti Day per l'utente $uid: ${e.message}"
            )
            onComplete()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun aggiornaGiorno(
        index: Int,
        giorniDocuments: List<DocumentSnapshot>,
        uid: String,
        statisticheRef: CollectionReference,
        onComplete: () -> Unit
    ) {
        if (index >= giorniDocuments.size) {
            onComplete()
            return
        }

        val giornoDoc = giorniDocuments[index]
        val prenotazioniRef = giornoDoc.reference.collection("Prenotazioni")

        prenotazioniRef.get().addOnSuccessListener { prenotazioniSnapshot ->
            val prenotazioniDocuments = prenotazioniSnapshot.documents
            aggiornaPrenotazione(0, prenotazioniDocuments, uid, statisticheRef) {
                giornoDoc.reference.delete().addOnSuccessListener {
                    Log.d(
                        "aggiornaStoricoPrenotazioni",
                        "Documento del giorno ${giornoDoc.id} eliminato con successo"
                    )
                    aggiornaGiorno(index + 1, giorniDocuments, uid, statisticheRef, onComplete)
                }.addOnFailureListener { e ->
                    Log.e(
                        "aggiornaStoricoPrenotazioni",
                        "Errore nell'eliminare il documento del giorno ${giornoDoc.id}: ${e.message}"
                    )
                    aggiornaGiorno(index + 1, giorniDocuments, uid, statisticheRef, onComplete)
                }
            }
        }.addOnFailureListener { e ->
            Log.e(
                "aggiornaStoricoPrenotazioni",
                "Errore nel recupero delle prenotazioni per il giorno ${giornoDoc.id}: ${e.message}"
            )
            aggiornaGiorno(index + 1, giorniDocuments, uid, statisticheRef, onComplete)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun aggiornaPrenotazione(
        index: Int,
        prenotazioniDocuments: List<DocumentSnapshot>,
        uid: String,
        statisticheRef: CollectionReference,
        onComplete: () -> Unit
    ) {
        if (index >= prenotazioniDocuments.size) {
            onComplete()
            return
        }

        val prenotazioneDoc = prenotazioniDocuments[index]
        val clienteId = prenotazioneDoc.getString("clienteId") ?: return aggiornaPrenotazione(
            index + 1,
            prenotazioniDocuments,
            uid,
            statisticheRef,
            onComplete
        )
        val servizioId = prenotazioneDoc.getString("servizioId") ?: return aggiornaPrenotazione(
            index + 1,
            prenotazioniDocuments,
            uid,
            statisticheRef,
            onComplete
        )
        val dataPrenotazione = LocalDate.parse(
            prenotazioneDoc.getString("data") ?: return aggiornaPrenotazione(
                index + 1,
                prenotazioniDocuments,
                uid,
                statisticheRef,
                onComplete
            )
        )

        Log.d(
            "aggiornaPrenotazione",
            "Aggiorno storico per prenotazione ${prenotazioneDoc.id}, cliente $clienteId, servizio $servizioId"
        )

        val meseAnno =
            "${dataPrenotazione.year}-${dataPrenotazione.monthValue.toString().padStart(2, '0')}"
        val meseRef = statisticheRef.document("prenotazioni")
        meseRef.update(meseAnno, FieldValue.increment(1)).addOnFailureListener { e ->
            if (e.message?.contains("NOT_FOUND") == true) {
                meseRef.set(mapOf(meseAnno to 1), SetOptions.merge())
            }
        }

        val servizioRef = statisticheRef.document("servizi")
        servizioRef.update(servizioId, FieldValue.increment(1)).addOnFailureListener { e ->
            if (e.message?.contains("NOT_FOUND") == true) {
                servizioRef.set(mapOf(servizioId to 1), SetOptions.merge())
            }
        }

        aggiornaStoricoCliente(uid, clienteId, servizioId, prenotazioneDoc.id) {
            prenotazioneDoc.reference.delete().addOnSuccessListener {
                Log.d(
                    "aggiornaPrenotazione",
                    "Prenotazione ${prenotazioneDoc.id} eliminata con successo"
                )
                aggiornaPrenotazione(
                    index + 1,
                    prenotazioniDocuments,
                    uid,
                    statisticheRef,
                    onComplete
                )
            }.addOnFailureListener { e ->
                Log.e(
                    "aggiornaPrenotazione",
                    "Errore nell'eliminare la prenotazione ${prenotazioneDoc.id}: ${e.message}"
                )
                aggiornaPrenotazione(
                    index + 1,
                    prenotazioniDocuments,
                    uid,
                    statisticheRef,
                    onComplete
                )
            }
        }
    }

    fun aggiornaStoricoCliente(
        uid: String,
        clienteId: String,
        servizioId: String,
        prenotazioneId: String,
        onComplete: () -> Unit
    ) {
        val clienteRef =
            db.collection("users").document(uid).collection("Clienti").document(clienteId)
        val storicoRef = clienteRef.collection("storico")

        clienteRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val prenotazioniArray =
                    document.get("prenotazioni") as? MutableList<Map<String, Any>>
                if (prenotazioniArray != null) {
                    Log.d(
                        "aggiornaStoricoCliente",
                        "Array prenotazioni trovato per il cliente $clienteId"
                    )

                    val updatedPrenotazioniArray = prenotazioniArray.filter { prenotazioneMap ->
                        prenotazioneMap["id"] != prenotazioneId
                    }

                    clienteRef.update("prenotazioni", updatedPrenotazioniArray)
                        .addOnSuccessListener {
                            Log.d(
                                "aggiornaStoricoCliente",
                                "Prenotazione $prenotazioneId rimossa dall'array del cliente $clienteId"
                            )
                            onComplete()
                        }
                } else {
                    onComplete()
                }
            } else {
                onComplete()
            }
        }

        val prenotazioniRef = storicoRef.document("prenotazioni")
        prenotazioniRef.update("numeroPrenotazioni", FieldValue.increment(1)).addOnFailureListener {
            prenotazioniRef.set(mapOf("numeroPrenotazioni" to 1), SetOptions.merge())
        }

        val serviziRef = storicoRef.document("servizi")
        serviziRef.update(servizioId, FieldValue.increment(1)).addOnFailureListener {
            serviziRef.set(mapOf(servizioId to 1), SetOptions.merge())
        }
    }


    fun calcolaEntratePerCliente(uid: String) {
        val clientiRef = db.collection("users").document(uid).collection("Clienti")
        val serviziRef = db.collection("users").document(uid).collection("Servizi")

        clientiRef.get().addOnSuccessListener { clientiSnapshot ->
            val entratePerCliente = mutableMapOf<String, Double>()

            for (clienteDoc in clientiSnapshot.documents) {
                val clienteId = clienteDoc.id
                val storicoRef = clienteDoc.reference.collection("storico").document("servizi")

                storicoRef.get().addOnSuccessListener { storicoSnapshot ->
                    if (storicoSnapshot.exists()) {
                        var totaleEntrateCliente = 0.0
                        val serviziEffettuati = storicoSnapshot.data ?: emptyMap()

                        serviziEffettuati.forEach { (servizioId, conteggio) ->
                            if (conteggio is Long) {
                                serviziRef.document(servizioId).get()
                                    .addOnSuccessListener { servizioSnapshot ->
                                        val costoServizio =
                                            servizioSnapshot.getDouble("prezzo") ?: 0.0
                                        totaleEntrateCliente += costoServizio * conteggio

                                        Log.d(
                                            "EntratePerCliente",
                                            "Cliente $clienteId, Servizio $servizioId: Conteggio $conteggio, Costo totale €${costoServizio * conteggio}"
                                        )

                                        // Aggiorna il totale per cliente dopo l'ultimo servizio
                                        entratePerCliente[clienteId] = totaleEntrateCliente
                                    }.addOnFailureListener { e ->
                                    Log.e(
                                        "EntratePerCliente",
                                        "Errore nel recupero del costo per servizio $servizioId: ${e.message}"
                                    )
                                }
                            }
                        }
                    }
                }.addOnFailureListener { e ->
                    Log.e(
                        "EntratePerCliente",
                        "Errore nel recupero dello storico per cliente $clienteId: ${e.message}"
                    )
                }
            }

        }.addOnFailureListener { e ->
            Log.e(
                "EntratePerCliente",
                "Errore nel recupero dei clienti per utente $uid: ${e.message}"
            )
        }
    }

    suspend fun getServiziStats(uid: String): Map<String, Int> {
        val db = FirebaseFirestore.getInstance()
        val statisticheRef =
            db.collection("users").document(uid).collection("statistiche").document("servizi")
        val serviziRef = db.collection("users").document(uid).collection("Servizi")

        return try {
            val statisticheData = statisticheRef.get().await().data ?: emptyMap<String, Any>()
            val serviziData = serviziRef.get().await().documents.associate {
                it.id to it.getString("nome").orEmpty()
            }

            statisticheData.mapNotNull { (id, count) ->
                val nomeServizio = serviziData[id] // Usa il nome dal documento servizi
                if (nomeServizio != null) nomeServizio to (count as? Long ?: 0).toInt() else null
            }.toMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }

    fun getPrenotazioniMensiliPerAnno(
        uid: String,
        onResult: (Map<String, Map<String, Int>>) -> Unit
    ) {
        val statisticheRef =
            db.collection("users").document(uid).collection("statistiche").document("prenotazioni")
        statisticheRef.get().addOnSuccessListener { document ->
            val data = document.data ?: emptyMap()

            // Verifica e converte ogni valore in `Long` prima di mapparlo
            val result = data.entries.groupBy(
                { it.key.substring(0, 4) }, // Raggruppa per anno
                {
                    val month = it.key.substring(5, 7)
                    val count = (it.value as? Long) ?: it.value.toString().toLongOrNull() ?: 0L
                    month to count.toInt() // Mese e conteggio come Int
                }
            ).mapValues { entry -> entry.value.toMap() }

            onResult(result)
        }.addOnFailureListener { e ->
            Log.e(
                "FirestoreRepository",
                "Errore nel recupero delle prenotazioni mensili per anno: ${e.message}"
            )
        }
    }
    fun getCustomerBookingFrequencyFromStorico(uid: String, onResult: (Map<String, Float>) -> Unit) {
        val customerRef = db.collection("users").document(uid).collection("Clienti")

        customerRef.get().addOnSuccessListener { snapshot ->
            val frequencyMap = mutableMapOf(
                "1 Prenotazione" to 0f,
                "2-5 Prenotazioni" to 0f,
                "6-10 Prenotazioni" to 0f,
                "Più di 10 Prenotazioni" to 0f
            )

            // Controllo se non ci sono clienti
            if (snapshot.isEmpty) {
                onResult(frequencyMap) // Se non ci sono clienti, ritorna la mappa vuota
                return@addOnSuccessListener
            }

            var processedDocuments = 0

            snapshot.documents.forEach { document ->
                val storicoRef = document.reference.collection("storico").document("prenotazioni")

                storicoRef.get().addOnSuccessListener { storicoDoc ->
                    val bookingCount = (storicoDoc.getLong("numeroPrenotazioni") ?: 0).toInt()

                    when {
                        bookingCount == 1 -> frequencyMap["1 Prenotazione"] = frequencyMap["1 Prenotazione"]!! + 1
                        bookingCount in 2..5 -> frequencyMap["2-5 Prenotazioni"] = frequencyMap["2-5 Prenotazioni"]!! + 1
                        bookingCount in 6..10 -> frequencyMap["6-10 Prenotazioni"] = frequencyMap["6-10 Prenotazioni"]!! + 1
                        bookingCount > 10 -> frequencyMap["Più di 10 Prenotazioni"] = frequencyMap["Più di 10 Prenotazioni"]!! + 1
                    }

                    // Incrementa il contatore dei documenti processati
                    processedDocuments++

                    // Quando tutti i documenti sono processati, calcola le percentuali e chiama `onResult`
                    if (processedDocuments == snapshot.size()) {
                        val totalCustomers = snapshot.size().toFloat()
                        val percentageMap = frequencyMap.mapValues { (it.value / totalCustomers) * 100 }
                        onResult(percentageMap)
                    }
                }
            }
        }
    }

    fun create_turno(
        uid: String,
        start: String,
        end: String,
        onSuccess: () -> Unit,
    ) {
        val id_turno = UUID.randomUUID().toString()
        val turno = Turno(end = end, id = id_turno, start = start)

        db.collection("users").document(uid).collection("Turni").document(id_turno).set(turno)
            .addOnSuccessListener {
                onSuccess()
            }
    }
    fun delete_turno(uid: String, id_turno: String, onSuccess: () -> Unit) {
        db.collection("users").document(uid).collection("Turni").document(id_turno)
            .delete()
            .addOnSuccessListener { onSuccess() }
    }
    fun modidy_turno(uid: String, id_turno: String, start: String, end: String, onSuccess: () -> Unit) {
        db.collection("users").document(uid).collection("Turni").document(id_turno)
            .update("start", start, "end", end)
            .addOnSuccessListener { onSuccess() }
    }

    suspend fun getServiceName(uid: String, servizioId: String): String {
        return try {
            val servizioDoc = FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .collection("Servizi")
                .document(servizioId)
                .get()
                .await() // Utilizza await per attendere il risultato dell'operazione

            servizioDoc.getString("nome") ?: "Nome non disponibile"
        } catch (e: Exception) {
            "Errore: ${e.message}"
        }
    }

}











