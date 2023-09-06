package com.chocolate.remote.data_source

import android.util.Log
import com.chocolate.entities.Organization
import com.chocolate.entities.exceptions.EmptyOrganizationNameException
import com.chocolate.entities.exceptions.OrganizationNotFoundException
import com.chocolate.remote.firebase.util.Constants
import com.chocolate.remote.firebase.util.tryToExecuteSuspendCall
import com.chocolate.repository.datastore.remote.OrganizationDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrganizationDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : OrganizationDataSource {

    override suspend fun getOrganizationByName(organizationName: String): Organization? {
        return tryToExecuteSuspendCall {
            val organizationsRef = firebaseFirestore
                .collection(Constants.ORGANIZATION)
                .whereEqualTo("name", organizationName)
                .get()
                .await()
            organizationsRef.documents.firstOrNull()?.toObject<Organization>()
        }
    }

    override suspend fun addOrganization(organization: Organization) {
        tryToExecuteSuspendCall {
            firebaseFirestore
                .collection(Constants.ORGANIZATION)
                .document(organization.name)
                .set(organization)
                .await()
        }
    }

    override suspend fun deleteOrganizationByOrganizationName(organizationName: String) {
        tryToExecuteSuspendCall {
            firebaseFirestore
                .collection(Constants.ORGANIZATION)
                .document(organizationName)
                .delete()
                .await()
        }
    }

    override suspend fun updateOrganization(organization: Organization) {
        tryToExecuteSuspendCall {
            firebaseFirestore
                .collection(Constants.ORGANIZATION)
                .document(organization.name)
                .set(organization)
                .await()
        }
    }
}