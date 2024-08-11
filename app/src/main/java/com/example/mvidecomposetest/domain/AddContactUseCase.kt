package com.example.mvidecomposetest.domain

class AddContactUseCase(
    private val repository: Repository
) {

    operator fun invoke(
        username: String,
        phone: String
    ) {
        val contact = Contact(
            username = username,
            mobilePhone = phone
        )
        repository.saveContact(contact)
    }
}
