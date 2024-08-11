package com.example.mvidecomposetest.data

import com.example.mvidecomposetest.byDefault
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.ConcurrentHashMap

object ContactsStorage: Repository {
    private val data = ConcurrentHashMap<Int, Contact>()

    init {
        init()
    }

    private val pendingStorageUpdateEvent = MutableSharedFlow<Unit>(replay = 1)

    override val contacts: Flow<List<Contact>>
        get() = flow {
            pendingStorageUpdateEvent.tryEmit(Unit)
            pendingStorageUpdateEvent.collect {
                val outComingData = data.values.toList()
                emit(outComingData)
            }
        }


    override fun saveContact(contact: Contact) {
        val itemId = if (contact.id == Int.byDefault) data.size else contact.id
        data[itemId] = contact.copy(id = itemId)
        pendingStorageUpdateEvent.tryEmit(Unit)
    }

    private fun init() {
        repeat(7) { id ->
            data[id] = Contact(id = id, "Somebody $id", "+375-29-xxx-xx-x$id")
        }
    }
}
